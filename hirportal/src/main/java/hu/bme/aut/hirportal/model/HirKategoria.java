package hu.bme.aut.hirportal.model;

import jakarta.persistence.*;

@Entity
public class HirKategoria {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hirid")
    private Hir hir;
    @ManyToOne
    @JoinColumn(name = "kategoriaid")
    private Kategoria kategoria;

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

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

}
