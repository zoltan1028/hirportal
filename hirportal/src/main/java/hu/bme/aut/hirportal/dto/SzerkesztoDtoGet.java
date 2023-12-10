package hu.bme.aut.hirportal.dto;


public class SzerkesztoDtoGet {
    public SzerkesztoDtoGet() {}
    private Long id;
    private String nev;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNev() {
        return nev;
    }
    public void setNev(String nev) {
        this.nev = nev;
    }
}

