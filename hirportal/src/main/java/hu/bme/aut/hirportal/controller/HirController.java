package hu.bme.aut.hirportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.aut.hirportal.model.Hir;
import hu.bme.aut.hirportal.model.HirFooldal;
import hu.bme.aut.hirportal.model.Kategoria;
import hu.bme.aut.hirportal.model.Szerkeszto;
import hu.bme.aut.hirportal.repository.HirFooldalRepository;
import hu.bme.aut.hirportal.repository.HirRepository;
import hu.bme.aut.hirportal.repository.KategoriaRepository;
import hu.bme.aut.hirportal.repository.SzerkesztoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/hirek")
public class HirController {
    @Autowired
    private HirRepository hirRepository;
    @Autowired
    private KategoriaRepository kategoriaRepository;
    @Autowired
    private SzerkesztoRepository szerkesztoRepository;
    @Autowired
    private HirFooldalRepository hirFooldalRepository;

    @GetMapping("{id}")
    public ResponseEntity<Hir> GetHirById(@PathVariable Long id) {
        Optional<Hir> hirOptional = hirRepository.findById(id);
        if (hirOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hirOptional.get());
    }
    @GetMapping("fooldalhirids")
    public ResponseEntity<List<HirFooldal>> GetFooldalIds() {
        return ResponseEntity.ok(hirFooldalRepository.findAll());
    }
    @GetMapping
    public ResponseEntity<List<Hir>> GetHirekFoOldal() {
        var osszeshir = hirRepository.findAll();
        var fooldalids = hirFooldalRepository.findAll();
        List<Hir> fooldalhirek = new ArrayList<>();
        for (HirFooldal hirfoldal: fooldalids) {
            for (Hir hir: osszeshir) {
                if(Objects.equals(hirfoldal.getHir().getId(), hir.getId())) {fooldalhirek.add(hir);}
            }
        }
        return ResponseEntity.ok(fooldalhirek);
    }
    @PostMapping("fooldal")
    public ResponseEntity<Object> PostHirekFoOldal(@RequestBody String str) {
        var hirfooldal = hirFooldalRepository.findAll();
        String[] arrOfStr = str.split(",");
        Long[] ids = new Long[arrOfStr.length];
        for(int i = 0;i < arrOfStr.length;i++) {ids[i] = Long.parseLong(arrOfStr[i]);}
        hirFooldalRepository.deleteAll();
        for (Long i: ids) {
            Optional<Hir> hir = hirRepository.findById(i);
            if(hir.isPresent()) {
                var fooldalhir = new HirFooldal();
                fooldalhir.setHir(hir.get());
                hirFooldalRepository.save(fooldalhir);
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("vedett")
    public ResponseEntity<List<Hir>> GetHirek(@RequestHeader String Token) {
        //auth servicebe id es token kinek a tokenje
        var szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByToken(Token));
        if(szerkesztoOpt.isPresent()) {
            Szerkeszto szerkeszto = szerkesztoOpt.get();
            if (Token.equals(szerkeszto.getToken())) {
                return ResponseEntity.ok(hirRepository.findAll());
            }
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping
    @Transactional
    public ResponseEntity<Hir> PostHir(@RequestBody Hir hir) {
        hirRepository.save(hir);
        List<Kategoria> kats = new ArrayList<>();
        for (var k : hir.getKategoriak()) {
            Optional<Kategoria> kategoria = kategoriaRepository.findById(k.getId());
            if (!kategoria.isEmpty()) {
                if (k.getId() == kategoria.get().getId()) {
                    //settel jobb
                    kats.add(k);
                }
            }
        }
        hir.setKategoriak(kats);
        return ResponseEntity.ok(hir);
    }
    @PutMapping(value = "{id}")
    @Transactional
    public ResponseEntity<Hir> PutHir(@RequestBody Hir hir, @PathVariable("id") Long id) {
        Optional<Hir> optionalHir = hirRepository.findById(id);
        if(optionalHir.isEmpty()) {
           return ResponseEntity.notFound().build();
        } else {
            var paramkat = hir.getKategoriak();
            hirRepository.save(hir);
            hir.setKategoriak(paramkat);
            return ResponseEntity.ok(optionalHir.get());
        }
    }
}

