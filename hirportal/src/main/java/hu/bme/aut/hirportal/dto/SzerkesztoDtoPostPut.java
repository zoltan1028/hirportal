package hu.bme.aut.hirportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;

public class SzerkesztoDtoPostPut {
    public SzerkesztoDtoPostPut() {}
    private Long id;
    private String felhasznalonev;
    private String nev;
    private String jelszo;
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev = felhasznalonev;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

