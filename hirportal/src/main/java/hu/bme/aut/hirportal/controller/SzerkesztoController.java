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
    public ResponseEntity<String> PostLogin(@RequestBody Szerkeszto szerkesztoFromBody) {
        Optional<Szerkeszto> szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByFelhasznalonev(szerkesztoFromBody.getFelhasznalonev()));
        if(szerkesztoOpt.isEmpty()) {return ResponseEntity.badRequest().build();}

        var szerkeszto = szerkesztoOpt.get();
        if(szerkeszto.getFelhasznalonev().equals(szerkesztoFromBody.getFelhasznalonev()) && szerkeszto.getJelszo().equals(szerkesztoFromBody.getJelszo())) {
            HttpHeaders responseHeader = new HttpHeaders();
            String newToken = authenticationService.GenerateTokenWithAuth();
            responseHeader.set("Token", newToken);
            responseHeader.set("Authorization", szerkeszto.getId().toString());
            szerkeszto.setToken(newToken);
            szerkesztoRepository.save(szerkeszto);
            return ResponseEntity.ok().headers(responseHeader).build();
        }
        return ResponseEntity.ok().build();
    }
    @GetMapping("{userid}")
    public ResponseEntity<Void> PostLogout(@PathVariable String userid) {
        System.out.println(userid);

        boolean isloggedin = authenticationService.isLoggedIn(Long.valueOf(userid));
        if(!isloggedin) {return ResponseEntity.badRequest().build();}
        //empty token
        Optional<Szerkeszto> szerkesztoOpt = szerkesztoRepository.findById(Long.valueOf(userid));
        var szerkeszto = szerkesztoOpt.get();
        szerkeszto.setToken("");
        szerkesztoRepository.save(szerkeszto);
        return ResponseEntity.ok().build();
    }
}
