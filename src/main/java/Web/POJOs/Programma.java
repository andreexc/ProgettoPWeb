package Web.POJOs;

public class Programma {
    private Long id;
    private String username;      // Utente proprietario (fondamentale per i ROLE_USER_PRO)
    private String nomeAllenamento; // Il nome della scheda (es. "Massa", "Cardio")
    private String nomeEsercizio;  // Il singolo esercizio (es. "Panca Piana")
    private int serie;
    private int ripetizioni;
    private int kcal;

    // Costruttore vuoto richiesto da Spring
    public Programma() {}

    // Costruttore completo
    public Programma(String username, String nomeAllenamento, String nomeEsercizio, int serie, int ripetizioni, int kcal) {
        this.username = username;
        this.nomeAllenamento = nomeAllenamento;
        this.nomeEsercizio = nomeEsercizio;
        this.serie = serie;
        this.ripetizioni = ripetizioni;
        this.kcal = kcal;
    }

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNomeAllenamento() { return nomeAllenamento; }
    public void setNomeAllenamento(String nomeAllenamento) { this.nomeAllenamento = nomeAllenamento; }

    public String getNomeEsercizio() { return nomeEsercizio; }
    public void setNomeEsercizio(String nomeEsercizio) { this.nomeEsercizio = nomeEsercizio; }

    public int getSerie() { return serie; }
    public void setSerie(int serie) { this.serie = serie; }

    public int getRipetizioni() { return ripetizioni; }
    public void setRipetizioni(int ripetizioni) { this.ripetizioni = ripetizioni; }

    public int getKcal() { return kcal; }
    public void setKcal(int kcal) { this.kcal = kcal; }
}