package com.example.course_work.security;


import com.example.course_work.data.UserRepository;
import com.example.course_work.entities.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute(name = "registrationForm")
    public RegistrationForm form(){return new RegistrationForm();}

    @GetMapping
    public String registerForm() {
        return "registration";
    }
    @PostMapping
    public String processRegistration(@Valid RegistrationForm form, Errors errors, BindingResult result) {
        if(errors.hasErrors()){
            return "registration";
        }

        if(userRepo.findByUsername(form.getUsername())!=null){
            String err = "Это имя пользователя уже занято!";
            ObjectError error = new ObjectError("globalError", err);
            result.addError(error);
        }

        if(result.hasErrors()){
            return "registration";
        }

        User user = form.toUser(passwordEncoder);

        userRepo.save(user);


        return "redirect:/login";
    }
}