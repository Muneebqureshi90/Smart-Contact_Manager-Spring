package com.smartcontactmanger.smartcontactmangerproject.controller;



import com.smartcontactmanger.smartcontactmangerproject.dao.UserRespositary;
import com.smartcontactmanger.smartcontactmangerproject.entity.User;
import com.smartcontactmanger.smartcontactmangerproject.helper.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class HomeController {

    @Autowired
    private UserRespositary userRespositary;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public HomeController(UserRespositary theUserRespositary){
        this.userRespositary = theUserRespositary;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home - Smart Contact Manager");
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - Smart Contact Manager");
        return "about";
    }
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Signup - Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }
    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
                               @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
                               HttpSession session) {
        try {
            if (!agreement) {
                model.addAttribute("agreementError", "You must agree to the Terms and Conditions.");
                throw new Exception("You Dont Agree the Terms And Conditions");

            }

            // Check if the password field has validation errors
            if (result1.hasFieldErrors("password")) {
                model.addAttribute("passwordError",
                        "Password must be 8-20 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character (@$!%*?&).");
                throw new Exception("Password must be 8-20 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character (@$!%*?&).");
            }

            if (result1.hasErrors()) {
                return "signup";
            }

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImg("default.png");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            User result = userRespositary.save(user);

            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Registration Successful!!", "alert-success"));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong: " + e.getMessage(), "alert-danger"));
            return "signup";
        }
        return "signup";
    }
    @GetMapping("/sigin")
    public String customUserLogin(Model model){
        model.addAttribute("title", "Login - Smart Contact Manager");

        return "login";
    }


}