package programa.model;

public class Erabiltzailea {
    private int id;
    private String izena;
    private String abizena;
    private String nan;
    private String helbidea;
    private int posta;
    private String email;
    private String jaiotze;
    private String alta;

    public Erabiltzailea(int id, String izena, String abizena, String nan, String helbidea, int posta, String email, String jaiotze, String alta) {
        this.id = id;
        this.izena = izena;
        this.abizena = abizena;
        this.nan = nan;
        this.helbidea = helbidea;
        this.posta = posta;
        this.email = email;
        this.jaiotze = jaiotze;
        this.alta = alta;
    }

    // Getters idénticos al estilo de pieza.java
    public int getId() { return id; }
    public String getIzena() { return izena; }
    public String getAbizena() { return abizena; }
    public String getNan() { return nan; }
    public String getHelbidea() { return helbidea; }
    public int getPosta() { return posta; }
    public String getEmail() { return email; }
    public String getJaiotze() { return jaiotze; }
    public String getAlta() { return alta; }
}