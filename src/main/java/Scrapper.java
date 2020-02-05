import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
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

        //Opening Page and waiting for javascript to execute to start scrapping
        HtmlPage page = client.getPage(baseURL);
        client.waitForBackgroundJavaScript(10_000);


        List<HtmlElement> items = page.getByXPath("//div[@class='hit-card faux-block-link card']") ;
        if(items.isEmpty())
        {
            System.out.println("No items found !");
        }
        else {

            for(HtmlElement item : items)
            {
                HtmlAnchor itemAnchor = item.getFirstByXPath(".//a[@class='d-flex btn btn-primary']");
                HtmlElement spanPrice = item.getFirstByXPath(".//span[@class='card-price price']");
                HtmlElement itemName = item.getFirstByXPath(".//a[@class='faux-block-link__overlay-link']");

                String itemURL = itemAnchor.getHrefAttribute();
                String listingName = itemName == null ? "No Name" : itemName.asText();
                String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();

                System.out.println(String.format("Name: %s, Price: %s , URL: %s ", listingName, itemPrice, itemURL ));
            }

        }



    }
}
