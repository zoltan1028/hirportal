package hu.bme.aut.hirportal.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.bme.aut.hirportal.model.Hir;
import hu.bme.aut.hirportal.model.Szerkeszto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class SzerkesztoDto {
    public SzerkesztoDto() {}
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

