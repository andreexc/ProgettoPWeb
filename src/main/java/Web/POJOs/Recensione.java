package Web.POJOs;

import java.sql.Time;
import java.sql.Timestamp;

public class Recensione {
    private Long id;
    private String username;
    private String testoRecensione;
    private Timestamp dataRecensione;

    public Recensione() { }

    public Recensione(Long id, String username, String testoRecensione,
                      Timestamp dataRecensione) {
        this.id = id;
        this.username = username;
        this.testoRecensione = testoRecensione;
        this.dataRecensione = dataRecensione;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getTestoRecensione() { return testoRecensione; }
    public void setTestoRecensione(String testoRecensione) { this.testoRecensione = testoRecensione; }

    public Timestamp getDataRecensione() { return dataRecensione; }
    public void setDataRecensione(Timestamp dataRecensione) { this.dataRecensione = dataRecensione; }
}