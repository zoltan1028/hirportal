package hu.bme.aut.hirportal.service;

import hu.bme.aut.hirportal.auth.Hash;
import hu.bme.aut.hirportal.auth.LoginToken;
import hu.bme.aut.hirportal.model.Szerkeszto;
import hu.bme.aut.hirportal.repository.SzerkesztoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    Hash hash;
    @Autowired
    SzerkesztoRepository szerkesztoRepository;
    private LoginToken token;
    public boolean AuthenticateByToken(String token) {
        var szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByToken(token));
        return szerkesztoOpt.isPresent();
    }
    public boolean LogoutUser(String token) {
        var szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByToken(token));
        if (szerkesztoOpt.isPresent()) {
            var sz = szerkesztoOpt.get();
            sz.setToken("");
            szerkesztoRepository.save(sz);
            return true;
        }
        return false;
    }

    public String LoginUser(String felhasznalonev, String jelszo) {
        Optional<Szerkeszto> szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByFelhasznalonev(felhasznalonev));
        if(szerkesztoOpt.isEmpty()) {return null;}
        Szerkeszto szerkeszto = szerkesztoOpt.get();
        //
        if (hash.isHashOk(jelszo, szerkeszto.getJelszo())) {
            LoginToken authToken = new LoginToken();
            szerkeszto.setToken(authToken.GetToken());
            szerkesztoRepository.save(szerkeszto);
            return authToken.GetToken();
        }
        return null;
    }
}
