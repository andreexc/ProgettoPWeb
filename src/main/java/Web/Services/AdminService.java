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
        String sql = "SELECT u.username, d.nome, d.cognome, d.email, d.data_iscrizione, a.authority, u.enabled " +
                "FROM users u " +
                "JOIN authorities a ON u.username = a.username " +
                "LEFT JOIN user_details d ON u.username = d.username";
        return jdbcTemplate.queryForList(sql);
    }
    /* removes not enabled users and returns the number of how many got removed */
    @Transactional // transactional is useful because we do multiple queries and operation :)@Transactional
    public int rimuoviUtentiScaduti() {
        String targetUsersQuery = "SELECT u.username FROM users u " +
                "JOIN authorities a ON u.username = a.username " +
                "WHERE a.authority = 'ROLE_USER_PROVA' AND u.enabled = false";

        List<String> usernamesScaduti = jdbcTemplate.queryForList(targetUsersQuery, String.class);

        if (!usernamesScaduti.isEmpty()) {
            for (String username : usernamesScaduti) {
                // Deleting correlated tables first
                jdbcTemplate.update("DELETE FROM recensioni WHERE username = ?", username);
                jdbcTemplate.update("DELETE FROM authorities WHERE username = ?", username);
                jdbcTemplate.update("DELETE FROM user_details WHERE username = ?", username);

                Long userId = jdbcTemplate.queryForObject("SELECT id FROM users WHERE username = ?", Long.class, username);

                if (userId != null) {
                    jdbcTemplate.update("DELETE FROM completed WHERE id_utente = ?", userId);
                    jdbcTemplate.update("DELETE FROM programma_utente WHERE id_utente = ?", userId);

                    // Finally deleting user
                    jdbcTemplate.update("DELETE FROM users WHERE id = ?", userId);
                }
            }
        }

        return usernamesScaduti.size();
    }
}