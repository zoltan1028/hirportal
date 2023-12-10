package hu.bme.aut.hirportal.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Hir {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Kategoria> kategoriak = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Szerkeszto> szerkesztok = new ArrayList<>();
    @JsonIgnoreProperties("hir")
    @OneToOne(mappedBy = "hir", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private HirFooldal hirFooldal;
    private String cim;
    private Timestamp lejarat;
    private Timestamp letrehozas = new Timestamp(new Date().getTime());
    @Column(columnDefinition = "varchar(max)")
    private String szoveg;
    private String keplink;
    public Hir() {
    }
    public Hir(Long id, List<Kategoria> kategoriak, String cim, Timestamp lejarat, String szoveg) {
        this.id = id;
        this.kategoriak = kategoriak;
        this.cim = cim;
        this.lejarat = lejarat;
        this.szoveg = szoveg;
    }
    public List<Szerkeszto> getSzerkesztok() {
        return szerkesztok;
    }

    public void setSzerkesztok(List<Szerkeszto> szerkesztok) {
        this.szerkesztok = szerkesztok;
    }
    public void addToSzerkesztok(Szerkeszto sz) {this.szerkesztok.add(sz);}
    public void addToKategoriak(Kategoria l) {
        this.kategoriak.add(l);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Kategoria> getKategoriak() {
        return kategoriak;
    }

    public void setKategoriak(List<Kategoria> kategoriak) {
        this.kategoriak = kategoriak;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public String getLejarat() {

        if (this.lejarat != null) {
            SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            var date = formatdate.format(new Date(this.lejarat.getTime()));
            return date;
        }
        return null;
    }

    public void setLejarat(String lejarat) {

        this.lejarat = Timestamp.valueOf(lejarat);

    }

    public String getSzoveg() {
        return szoveg;
    }

    public void setSzoveg(String szoveg) {
        this.szoveg = szoveg;
    }

    public String getLetrehozas() {
        if (this.letrehozas != null) {
            SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            var date = formatdate.format(new Date(this.letrehozas.getTime()));
            return date;
        }
        return null;
    }

    public void setLetrehozas(Timestamp letrehozas) {
        this.letrehozas = letrehozas;
    }

    public String getKeplink() {
        return keplink;
    }

    public void setKeplink(String keplink) {
        this.keplink = keplink;
    }
    public HirFooldal getHirFooldal() {
        return hirFooldal;
    }
}
