package Web.Services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class StatisticsService {

    private final JdbcTemplate jdbcTemplate;

    public StatisticsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* Counts how many times a base program is completed */
    public Map<String, Integer> getAllenamentiTotaliPerProgramma() {
        String sql = "SELECT c.id_programma, COUNT(*) as totale " +
                "FROM completed c " +
                "JOIN users u ON c.id_utente = u.id " +
                "JOIN authorities a ON u.username = a.username " +
                "WHERE a.authority IN ('ROLE_USER_BASIC', 'ROLE_USER_PRO') " +
                "GROUP BY c.id_programma";

        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("Full Body", 0);
        stats.put("Push/Pull/Legs", 0);
        stats.put("Cardio", 0);
        stats.put("Strength", 0);

        jdbcTemplate.query(sql, rs -> {
            long idProgramma = rs.getLong("id_programma");
            int totale = rs.getInt("totale");

            switch ((int) idProgramma) {
                case 1 -> stats.put("Full Body", totale);
                case 2 -> stats.put("Push/Pull/Legs", totale);
                case 3 -> stats.put("Cardio", totale);
                case 4 -> stats.put("Strength", totale);
            }
        });

        return stats;
    }

    /* Counts the total of completed session per role divided by the number of user of that role */
    public Map<String, Double> getMediaAllenamentiPerRuolo() {
        Map<String, Double> medie = new HashMap<>();

        // BASIC MEMBERS
        String sqlBasic =
                "SELECT CAST(COUNT(c.id) AS DOUBLE) / NULLIF((SELECT COUNT(*) FROM authorities WHERE authority = 'ROLE_USER_BASIC'), 0) " +
                        "FROM completed c " +
                        "JOIN users u ON c.id_utente = u.id " +
                        "JOIN authorities a ON u.username = a.username " +
                        "WHERE a.authority = 'ROLE_USER_BASIC'";

        Double mediaBasic = jdbcTemplate.queryForObject(sqlBasic, Double.class);
        medie.put("BASIC", mediaBasic != null ? mediaBasic : 0.0);

        // PRO MEMBERS
        String sqlPro =
                "SELECT CAST(COUNT(c.id) AS DOUBLE) / NULLIF((SELECT COUNT(*) FROM authorities WHERE authority = 'ROLE_USER_PRO'), 0) " +
                        "FROM completed c " +
                        "JOIN users u ON c.id_utente = u.id " +
                        "JOIN authorities a ON u.username = a.username " +
                        "WHERE a.authority = 'ROLE_USER_PRO'";

        Double mediaPro = jdbcTemplate.queryForObject(sqlPro, Double.class);
        medie.put("PRO", mediaPro != null ? mediaPro : 0.0);

        return medie;
    }

    /* Basic and Pro plans statistics */
    public Map<String, Integer> getAllenamentiUtentePerProgramma(String username) {
        String sql = "SELECT c.id_programma, COUNT(*) as totale " +
                "FROM completed c " +
                "JOIN users u ON c.id_utente = u.id " +
                "WHERE u.username = ? " +
                "GROUP BY c.id_programma";

        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("Full Body", 0);
        stats.put("Push/Pull/Legs", 0);
        stats.put("Cardio", 0);
        stats.put("Strength", 0);

        jdbcTemplate.query(sql, rs -> {
            long idProgramma = rs.getLong("id_programma");
            int totale = rs.getInt("totale");

            switch ((int) idProgramma) {
                case 1 -> stats.put("Full Body", totale);
                case 2 -> stats.put("Push/Pull/Legs", totale);
                case 3 -> stats.put("Cardio", totale);
                case 4 -> stats.put("Strength", totale);
            }
        }, username);

        return stats;
    }

    /* Calculates the number of programs completed given the user (useful for the X/3 format) */
    public int getConteggioAllenamenti(String username) {
        String sql = "SELECT COUNT(*) FROM completed c " +
                "JOIN users u ON c.id_utente = u.id " +
                "WHERE u.username = ?";

        Integer conteggio = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return conteggio != null ? conteggio : 0;
    }

    // Adds row to database to log new training sessions
    public boolean addInCompleted(Long programID, String username) {
        String sql = "INSERT INTO completed (id_utente, id_programma, data) " +
                "SELECT id, ?, CURRENT_DATE FROM users WHERE username = ?";

        int insertedRows = jdbcTemplate.update(sql, programID, username);
        return insertedRows > 0;
    }

    // Used to add also personalized trainings in pro users chart
    public Map<Long, Integer> getAllenamentiPersonalizzatiUtente(String username) {
        String sql = "SELECT c.id_programma, COUNT(*) as totale " +
                "FROM completed c " +
                "JOIN users u ON c.id_utente = u.id " +
                "WHERE u.username = ?" +
                "GROUP BY c.id_programma";

        Map<Long, Integer> stats = new LinkedHashMap<>();

        jdbcTemplate.query(sql, rs -> {
            Long idProgramma = rs.getLong("id_programma");
            int totale = rs.getInt("totale");
            stats.put(idProgramma, totale);
        }, username);

        return stats;
    }
}