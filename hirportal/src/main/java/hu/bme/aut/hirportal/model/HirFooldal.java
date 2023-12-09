package hu.bme.aut.hirportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class HirFooldal {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hirid", referencedColumnName = "id")
    private Hir hir;


    public boolean isVezercikk() {
        return isVezercikk;
    }

    public void setVezercikk(boolean vezercikk) {
        isVezercikk = vezercikk;
    }

    private boolean isVezercikk = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hir getHir() {
        return hir;
    }

    public void setHir(Hir hir) {
        this.hir = hir;
    }
}
