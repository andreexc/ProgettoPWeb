package Web.Controllers.DTO.Training;

import java.util.List;

public class Programma {
    private Long id;
    private String nomeProgramma;
    private int kcalTotali;
    private List<EsercizioInProgramma> esercizi;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeProgramma() { return nomeProgramma; }
    public void setNomeProgramma(String nomeProgramma) { this.nomeProgramma = nomeProgramma; }

    public int getKcalTotali() { return kcalTotali; }
    public void setKcalTotali(int kcalTotali) { this.kcalTotali = kcalTotali; }

    public List<EsercizioInProgramma> getEsercizi() { return esercizi; }
    public void setEsercizi(List<EsercizioInProgramma> esercizi) { this.esercizi = esercizi; }
}