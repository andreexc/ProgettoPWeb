package Web.Services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    private final JdbcTemplate jdbc;
    public ReviewService(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public void salvaRecensione(String user, String testo) {
        jdbc.update("INSERT INTO recensioni (username, testo, data_creazione) VALUES (?, ?, CURRENT_TIMESTAMP)", user, testo);
    }

    public List<Map<String, Object>> getAllRecensioni() {
        return jdbc.queryForList("SELECT username, testo FROM recensioni ORDER BY data_creazione DESC");
    }
}