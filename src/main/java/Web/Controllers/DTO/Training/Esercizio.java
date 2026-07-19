package Web.Controllers.DTO.Training;

public class Esercizio {
    private Long id;
    private String esercizio;
    private int kcal;

    public Esercizio() { }

    public Esercizio(Long id, String esercizio, int kcal) {
        this.id = id;
        this.esercizio = esercizio;
        this.kcal = kcal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEsercizio() { return esercizio; }
    public void setEsercizio(String esercizio) { this.esercizio = esercizio; }

    public int getKcal() { return kcal; }
    public void setKcal(int kcal) { this.kcal = kcal; }
}