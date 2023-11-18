package hu.bme.aut.hirportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.aut.hirportal.model.Hir;
import hu.bme.aut.hirportal.model.Kategoria;
import hu.bme.aut.hirportal.model.Szerkeszto;
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

    @GetMapping("{id}")
    public ResponseEntity<Hir> GetHirById(@PathVariable Long id) {
        Optional<Hir> hirOptional = hirRepository.findById(id);
        if (hirOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hirOptional.get());
    }
    @GetMapping
    public ResponseEntity<List<Hir>> GetHirekFoOldal() {
        //reszhalmaz
        return ResponseEntity.ok(hirRepository.findAll());
    }
    @PostMapping("fooldal")
    public ResponseEntity<Object> PostHirekFoOldal(@RequestBody String str) {
        String[] arrOfStr = str.split(",");
        int[] ids = new int[arrOfStr.length];
        for(int i = 0;i < arrOfStr.length;i++) {ids[i] = Integer.parseInt(arrOfStr[i]);}
        //id tabla
        List<Hir> fooldal = new ArrayList<>();
        List<Hir> optionaHirek = hirRepository.findAll();
        for (Hir h: optionaHirek) {
            for (int i: ids) {
                if(h.getId() == i) {
                    fooldal.add(h);
                }
            }
        }
        return ResponseEntity.ok(fooldal);
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

