package hu.bme.aut.hirportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class HirFooldal {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "hirid", referencedColumnName = "id")
    private Hir hir;

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
