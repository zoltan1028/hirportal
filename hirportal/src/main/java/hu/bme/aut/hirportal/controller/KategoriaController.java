package hu.bme.aut.hirportal.controller;

import hu.bme.aut.hirportal.model.Kategoria;
import hu.bme.aut.hirportal.repository.KategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kategoriak")
public class KategoriaController {
    @Autowired
    private KategoriaRepository kategoriaRepository;
    @GetMapping
    public ResponseEntity<List<Kategoria>> GetKategoriak() {
        return ResponseEntity.ok(kategoriaRepository.findAll());
    }
}
