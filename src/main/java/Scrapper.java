import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Scrapper {



    public static void main(String[] args) throws IOException, InterruptedException {

        SeleniumScrapper sc = new SeleniumScrapper();
        List<GameListingItem> final_deals = new ArrayList<GameListingItem>();
        final_deals.addAll( sc.ScrapeDeals("fanatical","https://www.fanatical.com/en/on-sale?onSale=true", 1, "//div[@class='hit-card faux-block-link card']", ".//a[@class='d-flex btn btn-primary']", ".//span[@class='card-price price']", ".//div[@class='card-saving saving-background']",".//a[@class='faux-block-link__overlay-link']" , ".//img[@class='img-fluid img-full img-force-full']"));
        final_deals.addAll( sc.ScrapeDeals("humble","https://www.humblebundle.com/store/search?sort=discount&filter=onsale",1,"//div[@class='entity js-entity on-sale']",".//a[@class='entity-link js-entity-link']",".//span[@class='price']",".//div[@class='js-discount-amount discount-amount']",".//span[@class='entity-title']" , ".//img[@class='entity-image']")); //Humble Scraper
        sc.CloseBrowser();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String filePath = "tmp_deals.json";
        FileWriter file = new FileWriter(filePath);
        gson.toJson(final_deals, file);
        file.close();

    }
}
