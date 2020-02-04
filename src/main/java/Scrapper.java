import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class Scrapper {

    private static final String baseURL = "https://www.fanatical.com/en/";


    public static void main(String[] args)
    {

        try {
            Document doc = Jsoup.connect(baseURL).get();

            System.out.printf("Title: %s", doc.title());

            Elements repos = doc.getElementsByClass("col-12");

            if(repos.isEmpty())
            {
                System.out.println("ıs empter");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
