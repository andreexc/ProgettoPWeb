package Web.Controllers;

import Web.Controllers.DTO.LoginForm;
import Web.Controllers.DTO.SignupForm;
import Web.Services.CheckUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class PublicController {

    private final CheckUserService checkUserService;

    // Injecting
    public PublicController(CheckUserService checkUserService) {
        this.checkUserService = checkUserService;
    }

    @GetMapping({"/", "/index"})
    public String home() {
        return "public/index";
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                HttpServletRequest request,
                                Model model) {
        // login unsuccessfull
        if (error != null) {
            String sessionMessage = (String) request.getSession().getAttribute("securityErrorMessage");

            if (sessionMessage != null) {
                // if there is already an AuthenticationFailureHandler's message
                model.addAttribute("loginError", sessionMessage);
                request.getSession().removeAttribute("securityErrorMessage");
            } else {
                // fallback in case the session expires
                model.addAttribute("loginError",  "#20: That user is not authenticated!");
            }
        }
        model.addAttribute("loginForm", new LoginForm());

        return "auth/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "auth/signup";
    }

    /* Register new users */
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("signupForm") SignupForm signupForm, Model model) {

        // Double check also server-side
        if (signupForm.getPassword().length() != 8 || !signupForm.getPassword().contains("id_20")) {
            model.addAttribute("signupError", "La password deve essere di 8 caratteri e contenere 'id_20'");
            return "auth/signup";
        }

        if (!signupForm.getPassword().equals(signupForm.getConfermaPassword())) {
            model.addAttribute("signupError", "Le password inserite non coincidono!");
            return "auth/signup";
        }

        // External service for registration
        boolean registrationSuccess = checkUserService.checkAndRegisterUser(signupForm);

        if (!registrationSuccess) {
            // Se l'utente esiste già, il servizio restituisce false e ricarichiamo la pagina con l'errore
            model.addAttribute("signupError", "Questo username è già registrato!");
            return "auth/signup";
        }

        return "auth/registration_success";
    }

    @GetMapping("/logout-success")
    public String logout() {
        return "auth/logout";
    }

    @GetMapping("/contatti")
    public String contatti() {
        return "public/contatti";
    }
}