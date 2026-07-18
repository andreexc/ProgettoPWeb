package Web.Services;

import Web.Clients.RestClient;
import Web.Controllers.DTO.Training.Personalized.NuovoProgrammaRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramUserService {

    private final RestClient restClient;
    private final JdbcTemplate jdbcTemplate;

    public ProgramUserService(RestClient restClient, JdbcTemplate jdbcTemplate) {
        this.restClient = restClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long creaProgrammaPersonale(NuovoProgrammaRequest request, String username) {
        Long idProgramma = restClient.createNewProgram(request);

        Long idUtente = jdbcTemplate.queryForObject(
                "SELECT id FROM users WHERE username = ?", Long.class, username);

        String sqlProgrammaUtente = "INSERT INTO programma_utente (id_utente, id_programma) VALUES (?, ?)";
        jdbcTemplate.update(sqlProgrammaUtente, idUtente, idProgramma);

        return idProgramma;
    }

    public List<Long> getIdProgrammiUtente(String username) {
        String sql = "SELECT pu.id_programma FROM programma_utente pu " +
                "JOIN users u ON pu.id_utente = u.id " +
                "WHERE u.username = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id_programma"), username);
    }
}