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
import hu.bme.aut.hirportal.service.AuthenticationService;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AuthenticationService authenticationService;


    //Unprotected endpoints
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

    //Protected endpoints by token auth

    @GetMapping("vedett")
    public ResponseEntity<List<Hir>> GetHirek() {
        return ResponseEntity.ok(hirRepository.findAll());
    }
    @PostMapping
    @Transactional
    public ResponseEntity<Hir> PostHir(@RequestBody Hir hir, @RequestHeader String Token) {
        if (!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.ok().build();}

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
    public ResponseEntity<Hir> PutHir(@RequestBody Hir hir, @PathVariable("id") Long id, @RequestHeader String Token) {
        if (!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.ok().build();}

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
    //for /szerkeszto page
    @GetMapping("fooldalhirids")
    public ResponseEntity<List<HirFooldal>> GetFooldalIds() {
        return ResponseEntity.ok(hirFooldalRepository.findAll());
    }
    @PostMapping("fooldal")
    public ResponseEntity<Object> PostHirekFoOldal(@RequestBody String str, @RequestHeader String Token) {
        if (!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.ok().build();}

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
}

