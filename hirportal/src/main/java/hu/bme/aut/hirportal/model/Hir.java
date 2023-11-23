package hu.bme.aut.hirportal.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Hir {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnoreProperties("hirek")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Kategoria> kategoriak = new ArrayList<>();
    @OneToOne(mappedBy = "hir")
    private HirFooldal hirFooldal;

    private String cim;
    private Timestamp lejarat;
    private Timestamp letrehozas = new Timestamp(new Date().getTime());
    @Column(columnDefinition = "varchar(max)")
    private String szoveg;
    public Hir() {
    }
    public Hir(Long id, List<Kategoria> kategoriak, String cim, Timestamp lejarat, String szoveg) {
        this.id = id;
        this.kategoriak = kategoriak;
        this.cim = cim;
        this.lejarat = lejarat;
        this.szoveg = szoveg;
    }

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

    public void setLejarat(Timestamp lejarat) {
        this.lejarat = lejarat;
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
}
