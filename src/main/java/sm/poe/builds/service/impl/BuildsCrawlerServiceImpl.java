package sm.poe.builds.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sm.poe.builds.client.HttpClient;
import sm.poe.builds.model.BuildDto;
import sm.poe.builds.model.PoeClassDto;
import sm.poe.builds.service.BuildService;
import sm.poe.builds.service.BuildsCrawlerService;
import sm.poe.builds.service.PoeClassService;
import sm.poe.builds.utility.HtmlHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class BuildsCrawlerServiceImpl implements BuildsCrawlerService {
    @Value("${poe.forum.path}")
    private String forumPath;

    @Value("${poe.forum.path.classes}")
    private String[] classUrls;

    @Value("${poe.versions}")
    private Set<String> versions;

    @Value("${poe.forum.path.page}")
    private String pagePostfix;

    private final HttpClient httpClient;
    private final BuildService buildService;
    private final PoeClassService poeClassService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    public void crawl() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("Builds parsing started");
        List<PoeClassDto> poeClassDtos = new ArrayList<>();
        List<BuildDto> buildDtos = new ArrayList<>();
        for (String classUrl : classUrls) {
            List<BuildDto> buildDtosByClass = aggregateBuildsByClass(classUrl);
            String poeClassName = buildDtosByClass.get(0).getPoeClassName();
            poeClassDtos.add(new PoeClassDto(poeClassName));
            buildDtos.addAll(buildDtosByClass);
        }

        System.out.println("Builds parsing finished");
        poeClassService.savePoeClasses(poeClassDtos);
        buildService.saveBuilds(buildDtos);
        System.out.println("Builds saved");
    }

    public List<BuildDto> aggregateBuildsByClass(String classUrl)
            throws URISyntaxException, IOException, InterruptedException {
        String fullClassUrl = forumPath + classUrl;
        String html = httpClient.get(fullClassUrl);
        Document document = HtmlHelper.parseHtml(html);
        String poeClassName = getClassName(document);
        int totalPages = getTotalPages(document);

        List<BuildDto> buildDtos = mapDocumentToBuilds(document, poeClassName);
        System.out.println("Parse " + poeClassName + " page 1");
        for (int i = 2; i <= totalPages; i++) {
            System.out.println("Parse " + poeClassName + " page " + i);
            html = httpClient.get(fullClassUrl + pagePostfix + i);
            document = HtmlHelper.parseHtml(html);
            buildDtos.addAll(mapDocumentToBuilds(document, poeClassName));
        }

        return buildDtos;
    }

    private List<BuildDto> mapDocumentToBuilds(Document document, String poeClassName) {
        return document.getElementsByClass("thread")
                .stream()
                .map(threadElement ->
                {
                    Element postStatElement = threadElement.parent().getElementsByClass("post-stat").get(0);
                    Element titleElement = threadElement.getElementsByClass("title").get(0);
                    Elements a = titleElement.select("a");
                    return BuildDto.builder()
                            .name(a.text())
                            .url(a.attr("href"))
                            .version(getBuildVersion(a.text()))
                            .views(Integer.parseInt(postStatElement.select("span").text()))
                            .poeClassName(poeClassName)
                            .build();
                })
                .filter(e -> Strings.isNotBlank(e.getVersion()))
                .collect(Collectors.toList());
    }

    private String getBuildVersion(String name) {
        return versions.stream()
                .filter(name::contains)
                .findFirst()
                .orElse("");
    }

    private String getClassName(Document document) {
        return document
                .getElementsByClass("topBar first").get(0)
                .getElementsByClass("breadcrumb").get(0)
                .textNodes().get(0)
                .text();
    }

    private int getTotalPages(Document document) {
        Element pagination = document
                .getElementsByClass("botBar last forumControls").get(0)
                .getElementsByClass("pagination").get(0);
        int childNodeSize = pagination.childNodeSize();
        Element child = pagination.child(childNodeSize - 2);
        return Integer.parseInt(child.text());
    }
}
