import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scrapper {

    private static final String baseURL = "https://www.fanatical.com/en/on-sale?onSale=true";


    public static void main(String[] args) throws IOException {
        WebClient client = new WebClient(BrowserVersion.FIREFOX_60);//creating webclient
        client.getOptions().setCssEnabled(false);
        client.getOptions().setThrowExceptionOnScriptError(false);

        final List<String> list = new ArrayList<>();

        new WebConnectionWrapper(client) {
            @Override
            public WebResponse getResponse(final WebRequest request) throws IOException {
                final WebResponse response = super.getResponse(request);
                list.add(request.getHttpMethod() + " " + request.getUrl());
                return response;
            }
        };

        client.getPage(baseURL);
        client.waitForBackgroundJavaScript(10_000);
        list.forEach(System.out::println);



    }
}
