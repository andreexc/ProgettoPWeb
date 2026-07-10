package Web.POJOs;

public class Programma {
    private Long id;
    private int tipoProgramma;

    public Programma() { }

    public Programma(Long id, int tipoProgramma) {
        this.id = id;
        this.tipoProgramma = tipoProgramma;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getTipoProgramma() { return tipoProgramma; }
    public void setTipoProgramma(int tipoProgramma) { this.tipoProgramma = tipoProgramma; }
}
