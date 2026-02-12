package programa.model;

public class Makina {
    private int id;
    private String izena;
    private String deskribapena;
    private int potentzia;
    private String instalazioData;

    public Makina(int id, String izena, String deskribapena, int potentzia, String instalazioData) {
        this.id = id;
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.potentzia = potentzia;
        this.instalazioData = instalazioData;
    }

    // Getters necesarios para que la TableView pueda leer los datos
    public int getId() { return id; }
    public String getIzena() { return izena; }
    public String getDeskribapena() { return deskribapena; }
    public int getPotentzia() { return potentzia; }
    public String getInstalazioData() { return instalazioData; }
}