package Web.Services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final JdbcTemplate jdbcTemplate;

    public AdminService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* Get the list of users ordered as requested in the assignment */
    public List<Map<String, Object>> ottieniListaUtenti() {
        // Prendiamo i dati anagrafici da user_details e il piano/ruolo REALE da authorities
        String sql = "SELECT d.username, d.nome, d.cognome, d.email, d.data_iscrizione, a.authority " +
                "FROM user_details d " +
                "JOIN authorities a ON d.username = a.username";
        return jdbcTemplate.queryForList(sql);
    }
    /* removes not enabled users and returns the number of how many got removed */
    @Transactional // transactional is useful because we do multiple queries and operation :)
    public int rimuoviUtentiScaduti() {
        // Filters the interested users
        String targetUsersQuery = "SELECT u.username FROM users u " +
                "JOIN authorities a ON u.username = a.username " +
                "WHERE a.authority = 'ROLE_USER_PROVA' AND u.enabled = false";

        List<String> usernamesScaduti = jdbcTemplate.queryForList(targetUsersQuery, String.class);

        if (!usernamesScaduti.isEmpty()) {
            for (String username : usernamesScaduti) {
                // delete on cascade due to foreign-key constraint
                jdbcTemplate.update("DELETE FROM user_details WHERE username = ?", username);
                jdbcTemplate.update("DELETE FROM authorities WHERE username = ?", username);
                jdbcTemplate.update("DELETE FROM users WHERE username = ?", username);
            }
        }

        return usernamesScaduti.size();
    }
}