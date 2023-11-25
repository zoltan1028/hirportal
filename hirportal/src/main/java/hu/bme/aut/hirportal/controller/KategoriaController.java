package hu.bme.aut.hirportal.controller;

import hu.bme.aut.hirportal.model.Kategoria;
import hu.bme.aut.hirportal.repository.KategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.bme.aut.hirportal.service.AuthenticationService;

import java.util.List;

@RestController
@RequestMapping("/kategoriak")
public class KategoriaController {
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
    public ResponseEntity<Void> PostKategoria(@RequestHeader String Token, @PathVariable String ujkategorianev) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}

        Kategoria ujkategoria = new Kategoria();
        kategoriaRepository.save(ujkategoria);

        ujkategoria.setNev(ujkategorianev);


        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{kategorianev}")
    @Transactional
    public ResponseEntity<Void> DeleteHir(@RequestHeader String Token, @PathVariable String kategorianev) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        var k = kategoriaRepository.findByNev(kategorianev);
        System.out.println(k.getNev());
        kategoriaRepository.deleteById(k.getId());
        return ResponseEntity.ok().build();
    }


}
