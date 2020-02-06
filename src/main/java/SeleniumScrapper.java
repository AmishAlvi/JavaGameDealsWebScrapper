import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SeleniumScrapper {

    static FirefoxDriver driver = new FirefoxDriver();
    static WebDriverWait wait = new WebDriverWait(driver, 30);


    public static void ScrapeDeals(String site, String filename, String URL, int last_page, String top_element, String link_element, String price_element, String sale_element, String name_element, String img_element) throws IOException, InterruptedException {

        FileWriter file = new FileWriter(filename);

        for(int i = 0 ; i < last_page; i++)
        {
            switch (site)
            {
                case "fanatical":
                    driver.navigate().to(URL + "&page=" + (i+1));
                break;

                case "humble":
                    driver.navigate().to(URL + "&page=" + i);
                break;
            }

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(top_element)));
            List<WebElement> containers =  driver.findElements(new By.ByXPath(top_element));

            for (WebElement item : containers)
            {
                String itemURL = item.findElement(By.xpath(link_element)).getAttribute("href"); // link
                String itemPrice = item.findElement(By.xpath(price_element)).getText(); //price
                String itemSale = item.findElement(By.xpath(sale_element)).getText(); //Sale
                String itemName = item.findElement(By.xpath(name_element)).getAttribute("innerText"); // Name
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(img_element)));
                WebElement img_element_scroll = item.findElement(By.xpath(img_element));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", img_element_scroll);
                Thread.sleep(500);
                String itemImg = item.findElement(By.xpath(img_element)).getAttribute("src");


                GameListingItem gameListingItem = new GameListingItem();

                gameListingItem.setTitle(itemName);
                gameListingItem.setSale(itemSale);
                gameListingItem.setSite(site);
                gameListingItem.setPrice(itemPrice);
                gameListingItem.setUrl(itemURL);
                gameListingItem.setImg(itemImg);

                ObjectMapper mapper = new ObjectMapper();  //new json object mapper
                String jsonString = mapper.writeValueAsString(gameListingItem); // mapping all items as json values

                file.write(jsonString + "\n");

                System.out.println(jsonString);

            }

        }

        driver.close();
        file.close();

    }



}
