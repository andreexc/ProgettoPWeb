package Web.Controllers;

import Web.Controllers.DTO.LoginForm;
import Web.Controllers.DTO.SignupForm;
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

    private final JdbcUserDetailsManager userDetailsManager;
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder; // 1. Aggiungi il campo

    // Injecting
    public PublicController(JdbcUserDetailsManager userDetailsManager,
                            JdbcTemplate jdbcTemplate,
                            PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/", "/index"})
    public String home() {
        return "index";
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
                model.addAttribute("loginError", "Username o password errati. Riprova.");
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

        // Password server-side check
        if (signupForm.getPassword().length() != 8 || !signupForm.getPassword().contains("id_20")) {
            model.addAttribute("signupError", "La password deve essere di 8 caratteri e contenere 'id_20'");
            return "auth/signup";
        }

        // Password match check
        if (!signupForm.getPassword().equals(signupForm.getConfermaPassword())) {
            model.addAttribute("signupError", "Le password inserite non coincidono!");
            return "auth/signup";
        }

        // Check if username already exists
        if (userDetailsManager.userExists(signupForm.getUsername())) {
            model.addAttribute("signupError", "Questo username è già registrato!");
            return "auth/signup";
        }

        // Fitness plan parsing
        String authorityString;
        switch (signupForm.getPianoAllenamento()) {
            case "Prova" -> authorityString = "ROLE_USER_PROVA";
            case "Pro" -> authorityString = "ROLE_USER_PRO";
            default -> authorityString = "ROLE_USER_BASIC";
        }

        // Passwordd encoding
        String passwordCifrata = passwordEncoder.encode(signupForm.getPassword());

        String sqlUser = "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlUser, signupForm.getUsername(), passwordCifrata, true); // <-- Encoded password

        String sqlAuth = "INSERT INTO authorities (username, authority) VALUES (?, ?)";
        jdbcTemplate.update(sqlAuth, signupForm.getUsername(), authorityString);

        String sqlDetails = "INSERT INTO user_details (username, nome, cognome, data_nascita, email, data_iscrizione, piano_allenamento, allenamenti_completati) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlDetails,
                signupForm.getUsername(),
                signupForm.getNome(),
                signupForm.getCognome(),
                signupForm.getDataNascita(),
                signupForm.getEmail(),
                LocalDate.now(),
                signupForm.getPianoAllenamento(),
                0
        );
        return "auth/registration_success";
    }

    @GetMapping("/logout-success")
    public String logout() {
        return "auth/logout";
    }

    @GetMapping("/contatti")
    public String contatti() {
        return "contatti";
    }
}