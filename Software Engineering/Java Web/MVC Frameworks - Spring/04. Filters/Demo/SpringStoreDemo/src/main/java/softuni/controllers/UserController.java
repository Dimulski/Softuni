package softuni.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.models.binding.user.UserLoginModel;
import softuni.models.binding.user.UserRegistrationModel;
import softuni.services.StoreUserDetailsService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController {

    private final StoreUserDetailsService userDetailsService;

    @Autowired
    public UserController(StoreUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "user/register");
        model.addAttribute("title", "Register");

        if(!model.containsAttribute("userRegistrationModel")){
            model.addAttribute("userRegistrationModel", new UserRegistrationModel());
        }

        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid UserRegistrationModel userRegistrationModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationModel", userRegistrationModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationModel", bindingResult);
            return "redirect:/register";
        }

        this.userDetailsService.register(userRegistrationModel);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("view", "user/login");
        model.addAttribute("title", "Login");
        model.addAttribute("user", new UserLoginModel());

        return "base-layout";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response, Authentication auth) {

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }
}
