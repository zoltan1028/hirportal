package hu.bme.aut.hirportal.controller;

import hu.bme.aut.hirportal.dto.SzerkesztoDto;
import hu.bme.aut.hirportal.model.Szerkeszto;
import hu.bme.aut.hirportal.repository.SzerkesztoRepository;
import hu.bme.aut.hirportal.service.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/szerkesztok")
public class SzerkesztoController {
    @Autowired
    SzerkesztoRepository szerkesztoRepository;
    @Autowired
    AuthenticationService authenticationService;
    @GetMapping
    public ResponseEntity<List<Szerkeszto>> GetSzerkesztok(@RequestHeader String Token) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        return ResponseEntity.ok(szerkesztoRepository.findAll());
    }
    @PostMapping("login")
    public ResponseEntity<String> PostLogin(@RequestHeader String Felhasznalonev, @RequestHeader String Jelszo) {
        Optional<Szerkeszto> szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByFelhasznalonev(Felhasznalonev));
        if(szerkesztoOpt.isEmpty()) {return ResponseEntity.badRequest().build();}
        var szerkeszto = szerkesztoOpt.get();
        if(szerkeszto.getFelhasznalonev().equals(Felhasznalonev) && szerkeszto.getJelszo().equals(Jelszo)) {
            HttpHeaders responseHeader = new HttpHeaders();
            String newToken = authenticationService.GenerateTokenWithAuth();
            responseHeader.set("Token", newToken);
            szerkeszto.setToken(newToken);
            szerkesztoRepository.save(szerkeszto);
            return ResponseEntity.ok().headers(responseHeader).build();
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("logout")
    public ResponseEntity<Void> PostLogout(@RequestHeader String Token) {
        boolean isSuccess = authenticationService.LogoutUser(Token);
        if(isSuccess) {return ResponseEntity.ok().build();}
        return ResponseEntity.badRequest().build();
    }
    @PostMapping
    @Transactional
    public ResponseEntity<Void> PostSzerkeszto(@RequestHeader String Token,@RequestBody SzerkesztoDto szerkesztodto) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        if((szerkesztodto.getFelhasznalonev().isEmpty() && szerkesztodto.getJelszo().isEmpty())) {return ResponseEntity.badRequest().build();}
        Szerkeszto szerkeszto = new Szerkeszto();
        szerkesztoRepository.save(szerkeszto);
        szerkeszto.setNev(szerkesztodto.getNev());
        szerkeszto.setFelhasznalonev(szerkesztodto.getFelhasznalonev());
        szerkeszto.setJelszo(szerkesztodto.getJelszo());
        return ResponseEntity.ok().build();
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<Void> PutSzerkeszto(@RequestHeader String Token, @RequestBody SzerkesztoDto szerkesztodto, @PathVariable Long id) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        var optszerkeszto = szerkesztoRepository.findById(id);
        if (optszerkeszto.isEmpty()){return ResponseEntity.badRequest().build();}
        var managedszerkeszto = optszerkeszto.get();
        managedszerkeszto.setFelhasznalonev(szerkesztodto.getFelhasznalonev());
        managedszerkeszto.setJelszo(szerkesztodto.getJelszo());
        managedszerkeszto.setNev(szerkesztodto.getNev());
        szerkesztoRepository.save(managedszerkeszto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> DeleteSzerkeszto(@RequestHeader String Token, @PathVariable Long id) {
        var szerkesztoToDelete = szerkesztoRepository.findById(id);
        var szerkesztoWhoDeletes = szerkesztoRepository.findByToken(Token);
        if (szerkesztoToDelete.isEmpty()) {return ResponseEntity.badRequest().build();}
        //onmagunk torlse check
        if(szerkesztoToDelete.get().getId().equals(szerkesztoWhoDeletes.getId())) {return ResponseEntity.badRequest().build();}
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        szerkesztoRepository.delete(szerkesztoToDelete.get());
        return ResponseEntity.ok().build();
    }
}
