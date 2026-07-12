package Web.Services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public UserService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    /* Retrieve users personal data */
    public Map<String, Object> ottieniProfilo(String username) {
        String sql = "SELECT u.username, d.nome, d.cognome, d.email, d.data_nascita, a.authority " +
                "FROM users u " +
                "JOIN user_details d ON u.username = d.username " +
                "JOIN authorities a ON u.username = a.username " +
                "WHERE u.username = ?";

        try {
            return jdbcTemplate.queryForMap(sql, username);
        } catch (Exception e) {
            // Returns empty map so the page rendering doesn't crash
            return Map.of();
        }
    }

    /* Calculate the number of completed programs (useful for the X/3 format) */
    public int getConteggioAllenamenti(String username) {
        String sql = "SELECT COUNT(*) FROM completed c " +
                "JOIN users u ON c.id_utente = u.id " +
                "WHERE u.username = ?";

        Integer conteggio = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return conteggio != null ? conteggio : 0;
    }

    /* Modifies the user's password (Transactional tab is a must) */
    @Transactional
    public boolean cambiaPassword(String username, String nuovaPassword) {
        String passwordCriptata = passwordEncoder.encode(nuovaPassword);
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        int righeModificate = jdbcTemplate.update(sql, passwordCriptata, username);
        return righeModificate > 0;
    }

    /* Changes the authority (role) of an user (it also re-enable the user because we can see the upgrade also as a subscription renewal*/
    @Transactional
    public boolean eseguiUpgrade(String username, String nuovoPiano) {
        String nuovaAuthority = switch (nuovoPiano.toUpperCase()) {
            case "BASIC" -> "ROLE_USER_BASIC";
            case "PRO" -> "ROLE_USER_PRO";
            default -> null;
        };

        if (nuovaAuthority == null) {
            return false;
        }

        // Authority update
        String sqlAuthority = "UPDATE authorities SET authority = ? WHERE username = ?";
        int updateAuth = jdbcTemplate.update(sqlAuthority, nuovaAuthority, username);

        // If the user had a trial plan we make sure he got enabled again
        String sqlAbilita = "UPDATE users SET enabled = true WHERE username = ?";
        jdbcTemplate.update(sqlAbilita, username);

        return updateAuth > 0;
    }
}