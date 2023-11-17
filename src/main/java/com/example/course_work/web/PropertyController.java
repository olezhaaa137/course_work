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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private LikedPropertyRepository likedPropertyRepository;

    @ModelAttribute
    public User user(@AuthenticationPrincipal User user){
        return user;
    }

    @GetMapping("/addToFavorite/{id}")
    public String addToFavorite(@PathVariable("id") Long id, @AuthenticationPrincipal User user){
        if (id!=null&&user!=null){
            Property property = propertyRepository.findById(id).get();
            LikedProperty likedProperty = new LikedProperty();
            likedProperty.setProperty(property);
            likedProperty.setUser(user);
            likedPropertyRepository.save(likedProperty);
        }
        return "home";
    }

    @GetMapping("/deleteFromFavorite/{id}")
    public String deleteFromFavorite(@PathVariable("id") Long id, @AuthenticationPrincipal User user){
        if (id!=null&&user!=null){
            LikedProperty likedProperty = likedPropertyRepository.findByPropertyAndUser(propertyRepository.findById(id).get(), user);
            likedPropertyRepository.delete(likedProperty);
        }
        return "home";
    }



    @GetMapping("/viewProperty")
    public String viewProperty(Long id, Model model){
        if (id != null){
            Property property = propertyRepository.findById(id).get();
            if (property != null){
                model.addAttribute("property", property);
            }
        }

        return "viewProperty";
    }

}
