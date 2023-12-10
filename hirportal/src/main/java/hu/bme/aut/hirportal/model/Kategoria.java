package hu.bme.aut.hirportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Kategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @ManyToMany(mappedBy = "kategoriak", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Hir> hirek = new ArrayList<>();
    private String nev;
    public Kategoria() {
    }
    public Kategoria(Long id, List<Hir> hirek, String nev) {
        this.id = id;
        this.hirek = hirek;
        this.nev = nev;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Hir> getHirek() {
        return hirek;
    }

    public void setHirek(List<Hir> hirek) {
        this.hirek = hirek;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }
    public void addHir(Hir hir) {
        this.hirek.add(hir);
        hir.getKategoriak().add(this);
    }
    public void removeHir(Hir hir) {
        this.hirek.remove(hir);
        hir.getKategoriak().remove(this);
    }

}
