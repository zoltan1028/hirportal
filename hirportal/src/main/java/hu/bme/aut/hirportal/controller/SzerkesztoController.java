package hu.bme.aut.hirportal.controller;

import hu.bme.aut.hirportal.auth.Authentication;
import hu.bme.aut.hirportal.model.Szerkeszto;
import hu.bme.aut.hirportal.repository.SzerkesztoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/szerkesztok")
public class SzerkesztoController {
    @Autowired
    SzerkesztoRepository szerkesztoRepository;
    @PostMapping
    public ResponseEntity<String> PostLogin(@RequestBody Szerkeszto szerkesztoFromBody) {
        Optional<Szerkeszto> szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByFelhasznalonev(szerkesztoFromBody.getFelhasznalonev()));
        if(szerkesztoOpt.isEmpty()) {return ResponseEntity.badRequest().build();}

        var szerkeszto = szerkesztoOpt.get();
        if(szerkeszto.getFelhasznalonev().equals(szerkesztoFromBody.getFelhasznalonev()) && szerkeszto.getJelszo().equals(szerkesztoFromBody.getJelszo())) {
            Authentication auth = new Authentication();
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("Token", auth.getToken());
            szerkeszto.setToken(auth.getToken());
            szerkesztoRepository.save(szerkeszto);
            return ResponseEntity.ok().headers(responseHeader).build();
        }
        return ResponseEntity.ok().build();
    }
    @GetMapping("{felhasznalonev}")
    public ResponseEntity<Void> PostLogout(@PathVariable String felhasznalonev) {
        Optional<Szerkeszto> szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByFelhasznalonev(felhasznalonev));
        if(szerkesztoOpt.isEmpty()) {return ResponseEntity.badRequest().build();}
        var szerkeszto = szerkesztoOpt.get();
        szerkeszto.setToken("");
        szerkesztoRepository.save(szerkeszto);
        return ResponseEntity.ok().build();
    }
    public boolean CheckTokenValidity(String felhasznalonev, String token) {
        Optional<Szerkeszto> szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByFelhasznalonev(felhasznalonev));
        if(szerkesztoOpt.isEmpty()) {return false;}
        var szerkeszto = szerkesztoOpt.get();
        return szerkeszto.getToken().equals(token);
    }
}
