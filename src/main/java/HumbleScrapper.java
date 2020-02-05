import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HumbleScrapper {

    private static final String baseURL = "https://www.humblebundle.com/store/search?sort=discount&filter=onsale";
    WebClient client = new WebClient(BrowserVersion.FIREFOX_60); //creating web client using firefox as base browser
    FileWriter file = new FileWriter("humble_bundle_deals.json");

    public HumbleScrapper() throws IOException {
    }


    public void Scrape(int LastPage) throws IOException {

        client.getOptions().setCssEnabled(false); // disabling CSS and CSS related scripts from loading
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
        client.setCssErrorHandler(new SilentCssErrorHandler());
        client.waitForBackgroundJavaScript(10_000);


        client.waitForBackgroundJavaScriptStartingBefore(10_000);
        HtmlPage page = client.getPage(baseURL);

        System.out.println(page.asXml());

        /*for (int i = 0; i < LastPage; i++)
        {
            System.out.println(baseURL + "&page=" + (i+1));  // iterating over each page of the fanatical listings
            try {
                HtmlPage page = client.getPage(baseURL + "&page=" + i);
                client.waitForBackgroundJavaScript(10_000);

                List<HtmlElement> items = page.getByXPath("//div[@class='entity js-entity on-sale']") ; //making a list of all items under the given Xpath node in order to traverse later
                if(items.isEmpty())
                {
                    System.out.println("No items found !");
                }
                else {

                    for(HtmlElement item : items)
                    {
                        HtmlAnchor itemAnchor = item.getFirstByXPath(".//a[@class='entity-link js-entity-link']"); // selecting all anchor items with the given class XPath
                        HtmlElement spanPrice = item.getFirstByXPath(".//span[@class='price']");
                        HtmlElement itemName = item.getFirstByXPath(".//span[@class='entity-title']");
                        HtmlDivision divSale = item.getFirstByXPath(".//div[@class='js-discount-amount discount-amount']");

                        String itemURL = itemAnchor.getHrefAttribute();
                        String listingName = itemName == null ? "No Name" : itemName.asText();
                        String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();
                        itemPrice = itemPrice.equals("From1") ? "1.00" : itemPrice;
                        String itemSale = divSale == null ? "0%" : divSale.asText();

                        GameListingItem gameListingItem = new GameListingItem();

                        gameListingItem.setTitle(listingName);
                        gameListingItem.setPrice(itemPrice);
                        gameListingItem.setUrl("https://www.humblebundle.com" + itemURL + "?partner=twinkietalks");
                        gameListingItem.setSite("humble");
                        gameListingItem.setSale(itemSale);

                        ObjectMapper mapper = new ObjectMapper();  //new json object mapper
                        String jsonString = mapper.writeValueAsString(gameListingItem); // mapping all items as json values

                        file.write(jsonString + "\n");

                        System.out.println(jsonString);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        file.close();


    }


}
