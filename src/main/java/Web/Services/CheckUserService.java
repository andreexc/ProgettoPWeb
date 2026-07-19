package Web.Services;

import Web.Controllers.DTO.SignupForm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class CheckUserService {

    private final JdbcUserDetailsManager userDetailsManager;
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public CheckUserService(JdbcUserDetailsManager userDetailsManager,
                            JdbcTemplate jdbcTemplate,
                            PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    /* Verifies the existence of the user and perfom the registration */
    public boolean checkAndRegisterUser(SignupForm signupForm) {

        if (userDetailsManager.userExists(signupForm.getUsername())) return false;
        
        String authorityString;
        switch (signupForm.getPianoAllenamento()) {
            case "Prova" -> authorityString = "ROLE_USER_PROVA";
            case "Pro" -> authorityString = "ROLE_USER_PRO";
            case "Basic" -> authorityString = "ROLE_USER_BASIC";
            default -> { return false; }  // error with plan selection
        }

        String passwordCifrata = passwordEncoder.encode(signupForm.getPassword());

        String sqlUser = "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlUser, signupForm.getUsername(), passwordCifrata, true);

        String sqlAuth = "INSERT INTO authorities (username, authority) VALUES (?, ?)";
        jdbcTemplate.update(sqlAuth, signupForm.getUsername(), authorityString);

        String sqlDetails = "INSERT INTO user_details (username, nome, cognome, data_nascita, email, data_iscrizione) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlDetails,
                signupForm.getUsername(),
                signupForm.getNome(),
                signupForm.getCognome(),
                signupForm.getDataNascita(),
                signupForm.getEmail(),
                java.sql.Date.valueOf(LocalDate.now())
        );

        return true;
    }
}