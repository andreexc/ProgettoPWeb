package Web.Controllers.DTO.Training.Personalized;

import java.util.List;

public class NuovoProgrammaRequest {
    private String nomeProgramma;
    private List<NuovoEsercizioRequest> esercizi;

    public String getNomeProgramma() { return nomeProgramma; }
    public void setNomeProgramma(String nomeProgramma) { this.nomeProgramma = nomeProgramma; }

    public List<NuovoEsercizioRequest> getEsercizi() { return esercizi; }
    public void setEsercizi(List<NuovoEsercizioRequest> esercizi) { this.esercizi = esercizi; }
}
