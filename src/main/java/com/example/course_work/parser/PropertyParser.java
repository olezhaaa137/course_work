package com.example.course_work.parser;

import com.example.course_work.entities.Property;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyParser {

    private  final String  URL = "https://re.kufar.by/l/minsk/kupit/kvartiru?cur=USD&size=30";
    public  List<Property> getAllProperties() throws IOException {
        Document doc = Jsoup.connect(URL).
                timeout(60000).get();
        List<Property> result = new ArrayList<>();
        Elements properties = doc.select("a.styles_wrapper__Q06m9"); //select all properties on the main page
        for (Element property:properties){
            String referenceToProperty = property.attr("href");
            Document propertyMainPage = Jsoup.connect(referenceToProperty).timeout(6000).get();
            Elements elements1 = propertyMainPage.select("div.styles_wrapper__YeeOd");
            List<String> propertyInternals = elements1.select("div.styles_element___PgYv").stream()
                    .map(element1 -> element1.select("span").text())
                    .collect(Collectors.toList()); // select all properties under photo
            for (int i = propertyInternals.size(); i < 6; i++) {
                propertyInternals.add(null);
            } // add null if not all Internals where under photo

            String price = propertyMainPage.select("span.styles_price--main__KHbAp").text();
            String imageLocation =
                    propertyMainPage.select("div.swiper-zoom-container img").stream()
                            .limit(1)
                            .map(el->el.attr("src"))
                            .findAny().get();
            String description = propertyMainPage.select("#description div").get(0).select("div").stream()
                    .filter(el->el.attr("itemprop").equals("description")).findAny().get().text();
            propertyInternals.add(price);
            propertyInternals.add(imageLocation);
            propertyInternals.add(description);
            result.add(toProperty(propertyInternals));
        }


        return result;
    }

    public Property toProperty(List<String> internals){
        return null;
    }

}