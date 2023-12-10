package hu.bme.aut.hirportal.controller;

import hu.bme.aut.hirportal.dto.SzerkesztoDtoGet;
import hu.bme.aut.hirportal.dto.SzerkesztoDtoPostPut;
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
    public ResponseEntity<List<SzerkesztoDtoGet>> GetSzerkesztok(@RequestHeader String Token) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        List<SzerkesztoDtoGet> dtoList = new ArrayList<>();
        for (Szerkeszto sz: szerkesztoRepository.findAll()) {
            SzerkesztoDtoGet szdto = new SzerkesztoDtoGet();
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
    public ResponseEntity<SzerkesztoDtoGet> PostSzerkeszto(@RequestHeader String Token, @RequestBody SzerkesztoDtoPostPut szerkeszto) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        if((szerkeszto.getFelhasznalonev().isEmpty() && szerkeszto.getJelszo().isEmpty())) {return ResponseEntity.badRequest().build();}
        Szerkeszto newszerkeszto = new Szerkeszto();
        szerkesztoRepository.save(newszerkeszto);
        newszerkeszto.setFelhasznalonev(szerkeszto.getFelhasznalonev());
        newszerkeszto.setJelszo(szerkeszto.getJelszo());
        newszerkeszto.setNev(szerkeszto.getNev());
        newszerkeszto.setToken("");
        szerkesztoRepository.save(newszerkeszto);
        SzerkesztoDtoGet responsedto = new SzerkesztoDtoGet();
        responsedto.setNev(newszerkeszto.getNev());
        responsedto.setId(newszerkeszto.getId());
        return ResponseEntity.ok(responsedto);
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<SzerkesztoDtoGet> PutSzerkeszto(@RequestHeader String Token, @RequestBody SzerkesztoDtoPostPut szerkeszto, @PathVariable Long id) {
        if(!authenticationService.AuthenticateByToken(Token)) {return ResponseEntity.badRequest().build();}
        Optional<Szerkeszto> optszerkeszto = szerkesztoRepository.findById(id);
        if (optszerkeszto.isEmpty()){return ResponseEntity.badRequest().build();}
        Szerkeszto managedszerkeszto = optszerkeszto.get();
        managedszerkeszto.setFelhasznalonev(szerkeszto.getFelhasznalonev());
        managedszerkeszto.setJelszo(szerkeszto.getJelszo());
        managedszerkeszto.setNev(szerkeszto.getNev());
        SzerkesztoDtoGet responsedto = new SzerkesztoDtoGet();
        responsedto.setNev(managedszerkeszto.getNev());
        responsedto.setId(managedszerkeszto.getId());
        return ResponseEntity.ok(responsedto);
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

        //deleting all hir reference from szerkeszto
        var szo = szerkesztoRepository.findById(id);
        if(szo.isPresent()) {
            var szp = szo.get();
            var list = new ArrayList<>(szp.getHirek());
            for (var h: list) {szp.removeHir(h);}
        }
        szerkesztoRepository.deleteById(szerkesztoToDelete.get().getId());
        return ResponseEntity.ok().build();
    }
}
