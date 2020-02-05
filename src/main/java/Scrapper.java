import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Scrapper {

    private static final String baseURL = "https://www.fanatical.com/en/on-sale?onSale=true";  // URL to scrap from


    public static void main(String[] args) throws IOException {
        WebClient client = new WebClient(BrowserVersion.FIREFOX_60); //creating web client using firefox as base browser
        client.getOptions().setCssEnabled(false); // disabling CSS and CSS related scripts from loading
        client.getOptions().setThrowExceptionOnScriptError(false);

        //Opening Page and waiting for javascript to execute to start scrapping
        HtmlPage page = client.getPage(baseURL);
        client.waitForBackgroundJavaScript(10_000);


        List<HtmlElement> items = page.getByXPath("//div[@class='hit-card faux-block-link card']") ; //making a list of all items under the given Xpath node in order to traverse later
        if(items.isEmpty())
        {
            System.out.println("No items found !");
        }
        else {

            for(HtmlElement item : items)
            {
                HtmlAnchor itemAnchor = item.getFirstByXPath(".//a[@class='d-flex btn btn-primary']"); // selecting all anchor items with the given class XPath
                HtmlElement spanPrice = item.getFirstByXPath(".//span[@class='card-price price']");
                HtmlElement itemName = item.getFirstByXPath(".//a[@class='faux-block-link__overlay-link']");

                String itemURL = itemAnchor.getHrefAttribute();
                String listingName = itemName == null ? "No Name" : itemName.asText();
                String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();

                Item item1 = new Item();

                item1.setTitle(listingName);
                item1.setPrice(itemPrice);
                item1.setUrl(itemURL);

                ObjectMapper mapper = new ObjectMapper();  //new json object mapper
                String jsonString = mapper.writeValueAsString(item1); // mapping all items as json values

                System.out.println(jsonString);
            }

        }



    }
}
