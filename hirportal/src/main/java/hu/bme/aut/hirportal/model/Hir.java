package hu.bme.aut.hirportal.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Hir {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "hir")
    private List<HirKategoria> hirkategorias;
    private String cim;
    private Timestamp lejarat;
    private String szoveg;
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

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public Timestamp getLejarat() {
        return lejarat;
    }

    public void setLejarat(Timestamp lejarat) {
        this.lejarat = lejarat;
    }

    public String getSzoveg() {
        return szoveg;
    }

    public void setSzoveg(String szoveg) {
        this.szoveg = szoveg;
    }
}
