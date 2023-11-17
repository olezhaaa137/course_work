package com.example.course_work.web;

import com.example.course_work.data.LikedPropertyRepository;
import com.example.course_work.data.PropertyRepository;
import com.example.course_work.entities.LikedProperty;
import com.example.course_work.entities.Property;
import com.example.course_work.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    PropertyRepository propertyRepo;
    @Autowired
    LikedPropertyRepository likedPropertyRepository;

    @ModelAttribute
    public User user(@AuthenticationPrincipal User user){
        return user;
    }

    @ModelAttribute
    public void addPropertiesToModel(Model model,@AuthenticationPrincipal User user){
        if (user!=null){
            List<PropertyToShowToUser> result = new LinkedList<>();
            Iterable<Property> properties = propertyRepo.findAll();
            properties.forEach(property -> {
                LikedProperty likedProperty = likedPropertyRepository.findByPropertyAndUser(property, user);
                if (likedProperty!=null){
                    PropertyToShowToUser propertyToShowToUser =
                            new PropertyToShowToUser(property.getId(),property.getNumberOfRooms(), property.getSquare(), property.getLivingSquare(), property.getKitchenSquare()
                            ,property.getYearOfConstruction(), property.getPrice(), property.getDescription(), property.getFloor(), property.getImages().get(0), true);
                    result.add(propertyToShowToUser);
                }else{
                    PropertyToShowToUser propertyToShowToUser =
                            new PropertyToShowToUser(property.getId(),property.getNumberOfRooms(), property.getSquare(), property.getLivingSquare(), property.getKitchenSquare()
                                    ,property.getYearOfConstruction(), property.getPrice(), property.getDescription(), property.getFloor(), property.getImages().get(0), false);
                    result.add(propertyToShowToUser);
                }
            });
            model.addAttribute("properties", result);
        }else{
            List<PropertyToShowToUser> result = new LinkedList<>();
            Iterable<Property> properties = propertyRepo.findAll();
            properties.forEach(property -> {
                PropertyToShowToUser propertyToShowToUser =
                        new PropertyToShowToUser(property.getId(),property.getNumberOfRooms(), property.getSquare(), property.getLivingSquare(), property.getKitchenSquare()
                                ,property.getYearOfConstruction(), property.getPrice(), property.getDescription(), property.getFloor(), property.getImages().get(0), true);
                result.add(propertyToShowToUser);
            });
            model.addAttribute("properties", result);
        }

    }
    @GetMapping
    public String homePage(){
        return "home";
    }

}
