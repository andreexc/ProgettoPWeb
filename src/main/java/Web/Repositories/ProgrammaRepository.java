package Web.Repositories;

import Web.POJOs.Programma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProgrammaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Programma> findByAllenamento(String username, String nomeAllenamento) {
        String sql = "SELECT id, username, nome_allenamento as nomeAllenamento, " +
                     "nome_esercizio as nomeEsercizio, numero_serie as serie, " +
                     "numero_ripetizioni as ripetizioni, kcal " +
                     "FROM personal_programs WHERE username = ? AND nome_allenamento = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Programma.class), username, nomeAllenamento);
    }

    // se un allenamento è composto da più esercizi allora
    // questo metodo deve essere invocato più volte
    public void saveEsercizio(Programma p) {
        String sql = "INSERT INTO personal_programs (username, nome_allenamento, nome_esercizio, " +
                     "numero_serie, numero_ripetizioni, kcal) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                p.getUsername(),
                p.getNomeAllenamento(),
                p.getNomeEsercizio(),
                p.getSerie(),
                p.getRipetizioni(),
                p.getKcal()
        );
    }

    public void deleteAllenamento(String username, String nomeAllenamento) {
        String sql = "DELETE FROM personal_programs WHERE username = ? AND nome_allenamento = ?";
        jdbcTemplate.update(sql, username, nomeAllenamento);
    }

    public void deleteEsercizioByAllenamento(String username, String nomeAllenamento, String nomeEsercizio) {
        String sql = "DELETE FROM personal_programs WHERE username = ? AND nome_allenamento = ? AND nome_esercizio = ?";
        jdbcTemplate.update(sql, username, nomeAllenamento, nomeEsercizio);
    }
}