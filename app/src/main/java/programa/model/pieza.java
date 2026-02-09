package programa.model;

public class pieza {
    // Atributuak
    private int id;
    private String izena;
    private int pisua;
    private double prezioa;
    private String deskribapena;

    //Datu-baseko datuak hasieratzeko eraikitzailea
    public pieza(int id, String izena, int pisua, double prezioa, String deskribapena) {
        this.id = id;
        this.izena = izena;
        this.pisua = pisua;
        this.prezioa = prezioa;
        this.deskribapena = deskribapena;
    }

    // GETTER-ak: TableView-a erabiltzen ditu jakitzeko zer bistaratu.
    public int getId() { return id; }
    public String getIzena() { return izena; }
    public int getPisua() { return pisua; }
    public double getPrezioa() { return prezioa; }
    public String getDeskribapena() { return deskribapena; }

    // SETTER-ak
    public void setId(int id) { this.id = id; }
    public void setIzena(String izena) { this.izena = izena; }
    public void setPisua(int pisua) { this.pisua = pisua; }
    public void setPrezioa(double prezioa) { this.prezioa = prezioa; }
    public void setDeskribapena(String deskribapena) { this.deskribapena = deskribapena; }
}