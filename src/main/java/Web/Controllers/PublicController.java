package Web.Controllers;

import Web.Controllers.DTO.LoginForm;
import Web.Controllers.DTO.SignupForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublicController {

    @GetMapping({"/", "/index"})
    public String home() { return "index"; }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "auth/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "auth/signup";
    }

    /* Register new users (data via post) */
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("signupForm") SignupForm signupForm) {

        // need checkuser, print only for testing
        System.out.println("Simulazione Signup - Ricevuto utente: " + signupForm.getUsername());

        return "auth/registration_success";
    }

    @GetMapping("/logout")
    public String logout() { return "auth/logout"; }

    @GetMapping("/contatti")
    public String contatti() { return "contatti"; }
}