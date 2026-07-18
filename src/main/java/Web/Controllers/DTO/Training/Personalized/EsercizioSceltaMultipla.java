package Web.Controllers.DTO.Training.Personalized;

public class EsercizioSceltaMultipla {
    private Long id;
    private String esercizio;
    private int kcal;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEsercizio() { return esercizio; }
    public void setEsercizio(String esercizio) { this.esercizio = esercizio; }

    public int getKcal() { return kcal; }
    public void setKcal(int kcal) { this.kcal = kcal; }
}