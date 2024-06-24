package sm.poe.builds.utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlHelper {
    public static Document parseHtml(String html) {
        return Jsoup.parse(html);
    }
}
