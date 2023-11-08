package hu.bme.aut.hirportal.controller;

import hu.bme.aut.hirportal.model.Hir;
import hu.bme.aut.hirportal.repository.HirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hir")
public class HirController {
    @Autowired
    private HirRepository hirRepository;

    @GetMapping
    public ResponseEntity<List<Hir>> GetHirek() {
        return ResponseEntity.ok(hirRepository.findAll());
    }
}
