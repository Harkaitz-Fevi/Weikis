package programa.model;

public class makina {
    private String id;
    private String izena;
    private String deskripzioa;
    private String potentzia;
    private String instalazioa;

    public makina(String id, String izena, String deskripzioa, String potentzia, String instalazioa) {
        this.id = id;
        this.izena = izena;
        this.deskripzioa = deskripzioa;
        this.potentzia = potentzia;
        this.instalazioa = instalazioa;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "makina [id=" + id + ", izena=" + izena + ", deskripzioa=" + deskripzioa + ", potentzia=" + potentzia
                + ", instalazioa=" + instalazioa + "]";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getDeskripzioa() {
        return deskripzioa;
    }

    public void setDeskripzioa(String deskripzioa) {
        this.deskripzioa = deskripzioa;
    }

    public String getPotentzia() {
        return potentzia;
    }

    public void setPotentzia(String potentzia) {
        this.potentzia = potentzia;
    }

    public String getInstalazioa() {
        return instalazioa;
    }

    public void setInstalazioa(String instalazioa) {
        this.instalazioa = instalazioa;
    }

}
