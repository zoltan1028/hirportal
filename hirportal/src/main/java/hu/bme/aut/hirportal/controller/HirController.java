package hu.bme.aut.hirportal.controller;

import hu.bme.aut.hirportal.model.Hir;
import hu.bme.aut.hirportal.model.Kategoria;
import hu.bme.aut.hirportal.repository.HirRepository;
import hu.bme.aut.hirportal.repository.KategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("{id}")
    public ResponseEntity<Hir> GetHirById(@PathVariable Long id) {
        Optional<Hir> hirOptional = hirRepository.findById(id);
        if (hirOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hirOptional.get());
    }
    @GetMapping
    public ResponseEntity<List<Hir>> GetHirek() {
        return ResponseEntity.ok(hirRepository.findAll());
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

