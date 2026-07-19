package Web.Controllers.DTO;

public class SignupForm {
    private String nome;
    private String cognome;
    private String dataNascita;
    private String email;
    private String username;
    private String password;
    private String confermaPassword;
    private String pianoAllenamento;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getDataNascita() { return dataNascita; }
    public void setDataNascita(String dataNascita) { this.dataNascita = dataNascita; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public void setConfermaPassword(String password) { this.confermaPassword = password; }
    public String getConfermaPassword() { return this.confermaPassword; }
    public String getPianoAllenamento() { return pianoAllenamento; }
    public void setPianoAllenamento(String pianoAllenamento) { this.pianoAllenamento = pianoAllenamento; }
}