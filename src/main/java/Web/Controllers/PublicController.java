package Web.Controllers;

import Web.Controllers.DTO.LoginForm;
import Web.Controllers.DTO.SignupForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/contatti")
    public String contatti() { return "contatti"; }
}