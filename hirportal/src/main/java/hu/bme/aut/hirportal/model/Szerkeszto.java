package hu.bme.aut.hirportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Szerkeszto {
    @JsonIgnore
    @GeneratedValue
    @Id
    private Long id;
    //@JsonIgnore
    private String felhasznalonev;
    @JsonIgnore
    private String jelszo;
    @JsonIgnore
    private String token;
    @JsonIgnore
    @ManyToMany(mappedBy = "szerkesztok", fetch = FetchType.EAGER)
    private List<Hir> hirek = new ArrayList<>();
    public List<Hir> getHirek() {
        return hirek;
    }

    public void setHirek(List<Hir> hirek) {
        this.hirek = hirek;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String nev) {
        this.felhasznalonev = nev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }
}
