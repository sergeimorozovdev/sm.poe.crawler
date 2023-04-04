package sm.poe.builds.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sm.poe.builds.client.HttpClient;
import sm.poe.builds.model.BuildDto;
import sm.poe.builds.model.PoeClassDto;
import sm.poe.builds.service.BuildService;
import sm.poe.builds.service.CrawlerService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class CrawlerServiceImpl implements CrawlerService {
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Scheduled(fixedDelay = 500000)
    public void crawl() throws URISyntaxException, IOException, InterruptedException {
        List<PoeClassDto> poeClassDtos = aggregate();
        buildService.saveBuilds(poeClassDtos);
        System.out.println("Builds saved");
    }

    public List<PoeClassDto> aggregate()
            throws URISyntaxException, IOException, InterruptedException {
        List<PoeClassDto> poeClassDtos = new ArrayList<>();
        for (String classPath : classUrls) {
            String fullClassUrl = forumPath + classPath;
            String html = httpClient.get(fullClassUrl);
            Document document = parseHtml(html);
            String className = getClassName(document);
            int totalPages = getTotalPages(document);

            PoeClassDto poeClassDto = PoeClassDto.builder()
                    .className(className)
                    .builds(new ArrayList<>())
                    .build();
            mapDocumentToDto(document, poeClassDto);
            for (int i = 2; i <= totalPages; i++) {
                System.out.println("Parse " + className + " page " + i);
                html = httpClient.get(fullClassUrl + pagePostfix + i);
                document = parseHtml(html);
                mapDocumentToDto(document, poeClassDto);
            }
            poeClassDtos.add(poeClassDto);
        }
        System.out.println("Parsing finished");
        return poeClassDtos;
    }


    private Document parseHtml(String html) {
        return Jsoup.parse(html);
    }

    private void mapDocumentToDto(Document document, PoeClassDto poeClassDto) {
        List<BuildDto> builds = document.getElementsByClass("title")
                .stream()
                .map(e ->
                {
                    Elements a = e.select("a");
                    return BuildDto.builder()
                            .name(a.text())
                            .url(a.attr("href"))
                            .version(getBuildVersion(a.text()))
                            .build();
                })
                .filter(e -> Strings.isNotBlank(e.getVersion()))
                .toList();
        poeClassDto.getBuilds().addAll(builds);

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
