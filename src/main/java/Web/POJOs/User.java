package Web.POJOs;

import java.sql.Date;

public class User {
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String email;
    private String ruolo;
    private String pianoAllenamento;
    private Date dataIscrizione;
    private boolean enabled;
    private int allenamentiCompletati;

    public User() { }

    public User(String username, String password, String nome, String cognome, Date dataNascita,
                String email, String ruolo, String pianoAllenamento, Date dataIscrizione, boolean enabled) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.email = email;
        this.ruolo = ruolo;
        this.pianoAllenamento = pianoAllenamento;
        this.dataIscrizione = dataIscrizione;
        this.enabled = enabled;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public Date getDataNascita() { return dataNascita; }
    public void setDataNascita(Date dataNascita) { this.dataNascita = dataNascita; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public String getPianoAllenamento() { return pianoAllenamento; }
    public void setPianoAllenamento(String pianoAllenamento) { this.pianoAllenamento = pianoAllenamento; }

    public Date getDataIscrizione() { return dataIscrizione; }
    public void setDataIscrizione(Date dataIscrizione) { this.dataIscrizione = dataIscrizione; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public int getAllenamentiCompletati() { return allenamentiCompletati; }
    public void setAllenamentiCompletati(int allenamentiCompletati) { this.allenamentiCompletati = allenamentiCompletati; }
}