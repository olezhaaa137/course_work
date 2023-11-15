package com.example.course_work.parser;

import com.example.course_work.data.ImageRepository;
import com.example.course_work.data.PropertyRepository;
import com.example.course_work.entities.Image;
import com.example.course_work.entities.Property;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class PropertyParser {

    public static int numberOfProp = 0;

    private static   final String  URL = "https://re.kufar.by/l/minsk/kupit/kvartiru?cur=USD&size=30";




    public static Iterator<Element> getIterator() throws IOException {
        Document doc = Jsoup.connect(URL).
                timeout(60000).get();
        List<Property> result = new ArrayList<>();
        Elements properties = doc.select("a.styles_wrapper__Q06m9"); //select all properties on the main page
        return properties.iterator();
    }

    public static List<Property> getAllPropertiesWithParallelism() throws IOException {
        Document doc = Jsoup.connect(URL).
                timeout(60000).get();
        List<Property> result = new ArrayList<>();
        Elements properties = doc.select("a.styles_wrapper__Q06m9");
        result = properties.parallelStream()
                .map(property->{
                    try{
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
                        return toProperty(propertyInternals);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return result;
    }

    public static Property getProperty(Element property) throws IOException {
        List<Property> result = new ArrayList<>();
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

        return result.get(0);
    }

    public static Property getProperty() throws IOException{
        Document doc = Jsoup.connect(URL).
                timeout(60000).get();
        List<Property> result = new ArrayList<>();
        Elements properties = doc.select("a.styles_wrapper__Q06m9"); //select all properties on the main page
        Element property = properties.first();

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

        return result.get(0);
    }
    public static   List<Property> getAllProperties() throws IOException {
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

    private static Property toProperty(List<String> internals){
        Property property = new Property();
        property.setNumberOfRooms(internals.get(0));
        property.setFloor(internals.get(1));
        property.setYearOfConstruction(internals.get(2));
        property.setSquare(internals.get(3));
        property.setLivingSquare(internals.get(4));
        property.setKitchenSquare(internals.get(5));
        property.setPrice(internals.get(6));
        Image image = new Image();
        image.setLocation(internals.get(7));
        property.setDescription(internals.get(8));
        Set<Image> images = new HashSet<>();
        images.add(image);
        property.setImages(images);
        return property;
    }

}