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
        String sql = "SELECT d.nome, d.cognome, d.data_nascita, a.authority " +
                "FROM user_details d " +
                "JOIN authorities a ON d.username = a.username " +
                "WHERE d.username = ?";
        try {
            return jdbcTemplate.queryForMap(sql, username);
        } catch (Exception e) {
            return Map.of(
                    "nome", "Non", "cognome", "Disponibile",
                    "data_nascita", java.sql.Date.valueOf(java.time.LocalDate.now()),
                    "authority", "ROLE_USER_PROVA"
            );
        }
    }

    /* Modifies the user's password (Transactional tag is a must) */
    @Transactional
    public boolean cambiaPassword(String username, String vecchiaPassword, String nuovaPassword) {
        String sqlGetPass = "SELECT password FROM users WHERE username = ?";
        String currentPasswordHash;
        try {
            currentPasswordHash = jdbcTemplate.queryForObject(sqlGetPass, String.class, username);
        } catch (Exception e) {
            return false;
        }

        if (currentPasswordHash == null || !passwordEncoder.matches(vecchiaPassword, currentPasswordHash)) {
            return false;
        }

        String nuovaPasswordHash = passwordEncoder.encode(nuovaPassword);
        String sqlUpdate = "UPDATE users SET password = ? WHERE username = ?";
        return jdbcTemplate.update(sqlUpdate, nuovaPasswordHash, username) > 0;
    }

    /* Changes the authority (role) of a user (it also re-enable the user because we can see the upgrade also as a subscription renewal*/
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

        String sqlAuthority = "UPDATE authorities SET authority = ? WHERE username = ?";
        int updateAuth = jdbcTemplate.update(sqlAuthority, nuovaAuthority, username);

        String sqlAbilita = "UPDATE users SET enabled = true WHERE username = ?";
        jdbcTemplate.update(sqlAbilita, username);

        return updateAuth > 0;
    }
}