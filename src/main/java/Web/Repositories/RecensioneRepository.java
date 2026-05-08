package Web.Repositories;

import Web.POJOs.Recensione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecensioneRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Recensione> findAll() {
        String sql = "SELECT id, username, " +
                     "testo_recensione AS testoRecensione, " +
                     "data_recensione AS dataRecensione " +
                     "FROM reviews ORDER BY data_recensione DESC";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recensione.class));
    }

    public void save(Recensione r) {
        String sql = "INSERT INTO reviews (username, testo_recensione) VALUES (?, ?)";
        jdbcTemplate.update(sql, r.getUsername(), r.getTestoRecensione());
    }