import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
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

        HtmlPage page = client.getPage(baseURL);
        client.waitForBackgroundJavaScript(10_000);
        //list.forEach(System.out::println);

        //System.out.println(page.asXml());

        List<HtmlElement> items = page.getByXPath("//div[@class='hit-card faux-block-link card']") ;
        if(items.isEmpty()){
            System.out.println("No items found !");
        }else{
            for(HtmlElement item : items){
                System.out.println(item.getTagName());
                HtmlAnchor itemAnchor = ((HtmlAnchor) item.getFirstByXPath("//a[@class='d-flex btn btn-primary']"));

                HtmlElement spanPrice = ((HtmlElement) item.getFirstByXPath("//span[@class='card-price price']")) ;

                String itemName = itemAnchor.asText();
                String itemUrl =  itemAnchor.getHrefAttribute();

                // It is possible that an item doesn't have any price
                String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText() ;

                System.out.println( String.format("Name : %s Url : %s Price : %s", itemName, itemPrice, itemUrl));
            }
        }



    }
}
