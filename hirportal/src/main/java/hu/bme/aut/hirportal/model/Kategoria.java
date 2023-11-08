package hu.bme.aut.hirportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Kategoria {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "kategoria")
    private List<HirKategoria> hirkategorias;
    private String nev;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<HirKategoria> getHirkategorias() {
        return hirkategorias;
    }

    public void setHirkategorias(List<HirKategoria> hirkategorias) {
        this.hirkategorias = hirkategorias;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }
}
