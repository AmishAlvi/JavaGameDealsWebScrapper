import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SeleniumScrapper {

    static FirefoxDriver driver = new FirefoxDriver();
    static WebDriverWait wait = new WebDriverWait(driver, 30);

    public static void ScrapeDeals(String URL, int last_page, String top_element, String link_element, String price_element, String sale_element, String name_element )
    {
        driver.navigate().to(URL);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(top_element)));
        List<WebElement> containers =  driver.findElements(new By.ByXPath(top_element));

        for (WebElement item : containers)
        {
            System.out.println(item.findElement(By.xpath(link_element)).getAttribute("href")); // link
            System.out.println(item.findElement(By.xpath(price_element)).getText()); //price
            System.out.println(item.findElement(By.xpath(sale_element)).getText()); //Sale
            System.out.println(item.findElement(By.xpath(".//a[@class='faux-block-link__overlay-link']")).getText()); // Name
        }

    }



}
