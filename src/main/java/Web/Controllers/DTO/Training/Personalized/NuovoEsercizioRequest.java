package Web.Controllers.DTO.Training.Personalized;

public class NuovoEsercizioRequest {
    private Long idEsercizio;
    private int nSerie;
    private int nRipetizioni;

    public Long getIdEsercizio() {
        return idEsercizio;
    }

    public void setIdEsercizio(Long idEsercizio) {
        this.idEsercizio = idEsercizio;
    }

    public int getNSerie() {
        return nSerie;
    }

    public void setNSerie(int nSerie) {
        this.nSerie = nSerie;
    }

    public int getNRipetizioni() {
        return nRipetizioni;
    }

    public void setNRipetizioni(int nRipetizioni) {
        this.nRipetizioni = nRipetizioni;
    }
}