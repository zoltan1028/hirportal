package hu.bme.aut.hirportal.controller;

import hu.bme.aut.hirportal.auth.Authentication;
import hu.bme.aut.hirportal.model.Szerkeszto;
import hu.bme.aut.hirportal.repository.SzerkesztoRepository;
import hu.bme.aut.hirportal.service.AuthenticationService;
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
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping
    public ResponseEntity<String> PostLogin(@RequestHeader String Felhasznalonev, @RequestHeader String Jelszo) {

        System.out.println(Felhasznalonev);
        //jsz is
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
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<Void> PostLogout(@RequestHeader String Token) {
        boolean isSuccess = authenticationService.LogoutUser(Token);
        if(isSuccess) {return ResponseEntity.ok().build();}
        return ResponseEntity.notFound().build();
    }
}
