package hu.bme.aut.hirportal.controller;

import hu.bme.aut.hirportal.model.Hir;
import hu.bme.aut.hirportal.model.Kategoria;
import hu.bme.aut.hirportal.repository.HirRepository;
import hu.bme.aut.hirportal.repository.KategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.bme.aut.hirportal.service.AuthenticationService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/kategoriak")
public class KategoriaController {
    @Autowired
    private HirRepository hirRepository;
    @Autowired
    private KategoriaRepository kategoriaRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @GetMapping
    public ResponseEntity<List<Kategoria>> GetKategoriak() {
        return ResponseEntity.ok(kategoriaRepository.findAll());
    }
    @PostMapping("{ujkategorianev}")
    @Transactional
    public ResponseEntity<Kategoria> PostKategoria(@RequestHeader String Token, @PathVariable String ujkategorianev) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        var katbyname = kategoriaRepository.findByNev(ujkategorianev);
        if (katbyname.isPresent() && katbyname.get().getNev().equals(ujkategorianev)) {return ResponseEntity.badRequest().build();}
        Kategoria ujkategoria = new Kategoria();
        kategoriaRepository.save(ujkategoria);
        ujkategoria.setNev(ujkategorianev);
        return ResponseEntity.ok(ujkategoria);
    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> DeleteHir(@RequestHeader String Token, @PathVariable Long id) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        if(id == null) {return ResponseEntity.badRequest().build();}
        //deleting hir reference from hirek
        var ko = kategoriaRepository.findById(id);
        if(ko.isPresent()) {
            var kp = ko.get();
            var list = new ArrayList<>(kp.getHirek());
            for (var h: list) {kp.removeHir(h);}
        }
        kategoriaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
