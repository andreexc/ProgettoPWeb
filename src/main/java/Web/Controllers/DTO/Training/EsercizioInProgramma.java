package Web.Controllers.DTO.Training;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EsercizioInProgramma {
    private String esercizio;
    private int nSerie;
    private int nRipetizioni;

    public String getEsercizio() { return esercizio; }
    public void setEsercizio(String esercizio) { this.esercizio = esercizio; }

    @JsonProperty("nSerie")
    public int getNSerie() { return nSerie; }
    public void setNSerie(int nSerie) { this.nSerie = nSerie; }

    @JsonProperty("nRipetizioni")
    public int getNRipetizioni() { return nRipetizioni; }
    public void setNRipetizioni(int nRipetizioni) { this.nRipetizioni = nRipetizioni; }
}