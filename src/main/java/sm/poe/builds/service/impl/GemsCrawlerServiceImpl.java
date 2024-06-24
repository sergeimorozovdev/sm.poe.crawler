package sm.poe.builds.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sm.poe.builds.client.HttpClient;
import sm.poe.builds.model.GemColor;
import sm.poe.builds.model.GemDto;
import sm.poe.builds.model.GemType;
import sm.poe.builds.service.GemService;
import sm.poe.builds.service.GemsCrawlerService;
import sm.poe.builds.utility.HtmlHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class GemsCrawlerServiceImpl implements GemsCrawlerService {

    @Value("${poe.gems.datasource.path}")
    private String gemsDatasourcePath;

    private Set<String> gemColors = new HashSet<>(Arrays.asList("red", "blue", "green"));

    private final HttpClient httpClient;
    private final GemService gemService;

    //@Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    public void crawl() throws URISyntaxException, IOException, InterruptedException {
        List<GemDto> gemDtos = aggregateGems();
        gemService.saveGems(gemDtos);
        System.out.println("Gems saved");
    }


    public List<GemDto> aggregateGems()
            throws URISyntaxException, IOException, InterruptedException {
        System.out.println("Gems parsing started");

        String html = httpClient.get(gemsDatasourcePath);
        Document document = HtmlHelper.parseHtml(html);

        List<GemDto> gemDtos = new ArrayList<>();
        Arrays.stream(GemType.values()).forEach(gemType -> gemDtos.addAll(getGemDtos(document, gemType.getId())));

        System.out.println("Gems parsing finished");
        return gemDtos;
    }

    private static List<GemDto> getGemDtos(Document document, String gemTypeId) {
        List<GemDto> gemDtos = document.getElementById(gemTypeId)
                .getElementsByClass("col-md-4")
                .stream()
                .map(colMdElement -> colMdElement.getElementsByTag("tbody").get(0))
                .flatMap(colMdElement -> colMdElement.getElementsByTag("tr").stream())
                .map(trElement -> {
                    Elements tdTags = trElement.getElementsByTag("td");
                    String imageUrl = tdTags.get(0).getElementsByTag("img").get(0).attr("src");
                    String gemName = tdTags.get(1).getElementsByTag("a").text();
                    String gemHtmlClass = tdTags.get(1).getElementsByTag("a").attr("class");
                    Optional<GemColor> optionalGemColor = Stream.of(GemColor.values()).filter(gemColor -> gemHtmlClass.contains(gemColor.getId())).findFirst();

                    return GemDto.builder().name(gemName)
                            .imageUrl(imageUrl)
                            .type(gemTypeId)
                            .color(optionalGemColor.orElse(GemColor.OTHER).getId())
                            .build();
                }).toList();
        return gemDtos;
    }
}
