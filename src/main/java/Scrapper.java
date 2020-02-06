import java.io.IOException;


public class Scrapper {

    private static final String baseURL = "https://www.fanatical.com/en/on-sale?onSale=true";  // URL to scrap from

    public static void main(String[] args) throws IOException, InterruptedException {


        SeleniumScrapper.ScrapeDeals("fanatical","fanatical_deals.json","https://www.fanatical.com/en/on-sale?onSale=true", 3, "//div[@class='hit-card faux-block-link card']", ".//a[@class='d-flex btn btn-primary']", ".//span[@class='card-price price']", ".//div[@class='card-saving saving-background']",".//a[@class='faux-block-link__overlay-link']" , ".//img[@class='img-fluid img-full img-force-full']");
       // SeleniumScrapper.ScrapeDeals("humble","humble_deals.json","https://www.humblebundle.com/store/search?sort=discount&filter=onsale",3,"//div[@class='entity js-entity on-sale']",".//a[@class='entity-link js-entity-link']",".//span[@class='price']",".//div[@class='js-discount-amount discount-amount']",".//span[@class='entity-title']" , ".//img[@class='entity-image']"); //Humble Scraper
    }
}
