package hu.bme.aut.hirportal.controller;
import hu.bme.aut.hirportal.model.Hir;
import hu.bme.aut.hirportal.model.HirFooldal;
import hu.bme.aut.hirportal.repository.HirFooldalRepository;
import hu.bme.aut.hirportal.repository.HirRepository;
import hu.bme.aut.hirportal.repository.KategoriaRepository;
import hu.bme.aut.hirportal.repository.SzerkesztoRepository;
import hu.bme.aut.hirportal.service.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @GetMapping
    public ResponseEntity<List<Hir>> GetHirek() {
        return ResponseEntity.ok(hirRepository.findAll());
    }
    @GetMapping("fooldal")
    public ResponseEntity<List<Hir>> GetHirekFoOldal() {
        List<Hir> osszeshir = hirRepository.findAll();
        List<HirFooldal> fooldalids = hirFooldalRepository.findAll();
        List<Hir> fooldalhirek = new ArrayList<>();
        for (HirFooldal hirfoldal: fooldalids) {
            for (Hir hir: osszeshir) {
                if(Objects.equals(hirfoldal.getHir().getId(), hir.getId())) {fooldalhirek.add(hir);}
            }
        }
        return ResponseEntity.ok(fooldalhirek);
    }
    @GetMapping("{id}")
    public ResponseEntity<Hir> GetHirById(@PathVariable Long id) {
        Optional<Hir> hirOptional = hirRepository.findById(id);
        if (hirOptional.isEmpty()) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(hirOptional.get());
    }
    //Protected endpoints by token auth
    @GetMapping("fooldalhirids")
    public ResponseEntity<List<HirFooldal>> GetFooldalIds() {
        return ResponseEntity.ok(hirFooldalRepository.findAll());
    }
    @PostMapping("fooldal")
    public ResponseEntity<Object> PostHirekFoOldal(@RequestBody String str, @RequestHeader String Token) {
        if (!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        List<HirFooldal> hirfooldal = hirFooldalRepository.findAll();
        //array of fooldal ids => [2,1,7,2]
        String[] arrOfStr = str.split(",");
        Long[] ids = new Long[arrOfStr.length];
        for(int i = 0;i < arrOfStr.length;i++) {ids[i] = Long.parseLong(arrOfStr[i]);}
        //empty records from fooldal
        hirFooldalRepository.deleteAll();
        //last element id
        var vezcikkid = ids[ids.length-1];
        var idswithoutvezer = Arrays.copyOf(ids, ids.length -1);
        // => [2,1,7]
        for (Long i: idswithoutvezer) {
            Optional<Hir> hir = hirRepository.findById(i);
            if(hir.isPresent()) {
                var fooldalhir = new HirFooldal();
                fooldalhir.setHir(hir.get());
                //find vezer cikk by id => [2,1,7] 2 == 2
                if (vezcikkid.equals(hir.get().getId())) {fooldalhir.setVezercikk(true);}
                //save to fooldal
                hirFooldalRepository.save(fooldalhir);
            }
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping
    @Transactional
    public ResponseEntity<Hir> PostHir(@RequestBody Hir hir, @RequestHeader String Token) {
        if (!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        var optionalSzerkeszto = Optional.ofNullable(szerkesztoRepository.findByToken(Token));
        //save hir from body
        hirRepository.save(hir);
        //set szerkeszto for hir
        if (optionalSzerkeszto.isPresent()) {hir.addToSzerkesztok(optionalSzerkeszto.get());}
        //set kategoriak for hir
        hir.setKategoriak(hir.getKategoriak());
        return ResponseEntity.ok(hir);
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<Hir> PutHir(@RequestBody Hir hir, @PathVariable("id") Long id, @RequestHeader String Token) {
        if (!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.ok().build();}
        var optionalSzerkeszto = Optional.ofNullable(szerkesztoRepository.findByToken(Token));
        Optional<Hir> optionalHir = hirRepository.findById(id);
        if(optionalHir.isEmpty() || optionalSzerkeszto.isEmpty()) {return ResponseEntity.badRequest().build();}
        var paramkat = hir.getKategoriak();
        var managedszerkeszto = optionalSzerkeszto.get();
        var managedhir = optionalHir.get();
        managedhir.setKategoriak(paramkat);
        managedhir.setCim(hir.getCim());
        managedhir.setLejarat(hir.getLejarat());
        managedhir.setKeplink(hir.getKeplink());
        managedhir.setSzoveg(hir.getSzoveg());
        //szerkeszto should be added only once
        for (var sz: managedhir.getSzerkesztok()) {if ((Objects.equals(sz.getId(), managedszerkeszto.getId()))) {return ResponseEntity.ok().build();}}
        managedhir.addToSzerkesztok(managedszerkeszto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Void> DeleteHir(@RequestHeader String Token, @PathVariable Long id) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        if(id == null) {return ResponseEntity.badRequest().build();}
        hirRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

