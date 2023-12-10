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

import java.util.ArrayList;
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
    public ResponseEntity<List<SzerkesztoDto>> GetSzerkesztok(@RequestHeader String Token) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        List<SzerkesztoDto> dtoList = new ArrayList<>();
        for (Szerkeszto sz: szerkesztoRepository.findAll()) {
            SzerkesztoDto szdto = new SzerkesztoDto();
            szdto.setId(sz.getId());
            szdto.setNev(sz.getNev());
            dtoList.add(szdto);
        }
        return ResponseEntity.ok(dtoList);
    }
    @PostMapping("login")
    public ResponseEntity<String> PostLogin(@RequestHeader String Felhasznalonev, @RequestHeader String Jelszo) {
        HttpHeaders responseHeader = new HttpHeaders();
        var token = authenticationService.LoginUser(Felhasznalonev, Jelszo);
        if (!token.equals("")) {
            Optional<Szerkeszto> szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByFelhasznalonev(Felhasznalonev));
            szerkesztoOpt.ifPresent(szerkeszto -> responseHeader.set("Id", szerkeszto.getId().toString()));
            responseHeader.set("Token", token);
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
    public ResponseEntity<SzerkesztoDto> PostSzerkeszto(@RequestHeader String Token,@RequestBody Szerkeszto szerkeszto) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        if((szerkeszto.getFelhasznalonev().isEmpty() && szerkeszto.getJelszo().isEmpty())) {return ResponseEntity.badRequest().build();}
        szerkesztoRepository.save(szerkeszto);
        SzerkesztoDto szdto = new SzerkesztoDto();
        szdto.setNev(szerkeszto.getNev());
        szdto.setId(szerkeszto.getId());
        return ResponseEntity.ok(szdto);
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<SzerkesztoDto> PutSzerkeszto(@RequestHeader String Token, @RequestBody Szerkeszto szerkeszto, @PathVariable Long id) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        var optszerkeszto = szerkesztoRepository.findById(id);
        if (optszerkeszto.isEmpty()){return ResponseEntity.badRequest().build();}
        szerkesztoRepository.save(szerkeszto);
        SzerkesztoDto szdto = new SzerkesztoDto();
        szdto.setNev(szerkeszto.getNev());
        szdto.setId(szerkeszto.getId());
        return ResponseEntity.ok(szdto);
    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> DeleteSzerkeszto(@RequestHeader String Token, @PathVariable Long id) {
        var szerkesztoToDelete = szerkesztoRepository.findById(id);
        var szerkesztoWhoDeletes = szerkesztoRepository.findByToken(Token);
        if (szerkesztoToDelete.isEmpty()) {return ResponseEntity.badRequest().build();}
        //onmagunk torlse check
        if(szerkesztoToDelete.get().getId().equals(szerkesztoWhoDeletes.getId())) {return ResponseEntity.ok().build();}
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        szerkesztoRepository.delete(szerkesztoToDelete.get());
        return ResponseEntity.ok().build();
    }
}
