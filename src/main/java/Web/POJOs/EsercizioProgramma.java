package Web.POJOs;

public class EsercizioProgramma {
    private Long idEsercizio;
    private String esercizio;
    private int kcal;
    private Integer nSerie;
    private Integer nRipetizioni;

    public EsercizioProgramma() { }

    public EsercizioProgramma(Long idEsercizio, String esercizio, int kcal, Integer nSerie, Integer nRipetizioni) {
        this.idEsercizio = idEsercizio;
        this.esercizio = esercizio;
        this.kcal = kcal;
        this.nSerie = nSerie;
        this.nRipetizioni = nRipetizioni;
    }

    public Long getIdEsercizio() { return idEsercizio; }
    public void setIdEsercizio(Long idEsercizio) { this.idEsercizio = idEsercizio; }

    public String getEsercizio() { return esercizio; }
    public void setEsercizio(String esercizio) { this.esercizio = esercizio; }

    public int getKcal() { return kcal; }
    public void setKcal(int kcal) { this.kcal = kcal; }

    public Integer getnSerie() { return nSerie; }
    public void setnSerie(Integer nSerie) { this.nSerie = nSerie; }

    public Integer getnRipetizioni() { return nRipetizioni; }
    public void setnRipetizioni(Integer nRipetizioni) { this.nRipetizioni = nRipetizioni; }
}
