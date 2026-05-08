package Web.Repositories;

import Web.POJOs.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByUsername(String username) {
        String sql = "SELECT u.username, u.enabled, ud.nome, ud.cognome, ud.email, ud.data_nascita, ud.piano_allenamento " +
                     " FROM users u JOIN user_details ud ON u.username = ud.username WHERE u.username = ?";
        try                 { return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username); }
        catch (Exception e) { return null; }
    }

    public void save(User user) {

        jdbcTemplate.update("INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)", user.getUsername(), user.getPassword(), true);
        jdbcTemplate.update("INSERT INTO authorities (username, authority) VALUES (?, ?)", user.getUsername(), user.getRuolo());
        jdbcTemplate.update("INSERT INTO user_details (username, nome, cognome, data_nascita, email, data_iscrizione, piano_allenamento) VALUES (?, ?, ?, ?, ?, ?, ?)",
                user.getUsername(), user.getNome(), user.getCognome(), user.getDataNascita(),
                user.getEmail(), new java.sql.Date(System.currentTimeMillis()), user.getPianoAllenamento());
    }
}