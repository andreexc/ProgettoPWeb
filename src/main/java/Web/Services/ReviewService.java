package Web.Services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    private final JdbcTemplate jdbc;

    public ReviewService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void salvaRecensione(String user, String testo) {
        jdbc.update("INSERT INTO recensioni (username, testo, data_creazione) VALUES (?, ?, CURRENT_TIMESTAMP)", user, testo);
    }

    // old carousel method
    public List<Map<String, Object>> getAllRecensioniOriginal() {
        return jdbc.queryForList("SELECT username, testo FROM recensioni ORDER BY data_creazione DESC");
    }

    // RANDOM carousel order
    public List<Map<String, Object>> getAllRecensioni(int limit) {
        String sql = "SELECT username, testo FROM recensioni ORDER BY RANDOM() LIMIT ?";
        return jdbc.queryForList(sql, limit);
    }
}