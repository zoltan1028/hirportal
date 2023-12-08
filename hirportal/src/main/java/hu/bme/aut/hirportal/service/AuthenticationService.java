package hu.bme.aut.hirportal.service;

import hu.bme.aut.hirportal.auth.Authentication;
import hu.bme.aut.hirportal.model.Szerkeszto;
import hu.bme.aut.hirportal.repository.SzerkesztoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    SzerkesztoRepository szerkesztoRepository;
    private Authentication auth = new Authentication();
    public String GenerateTokenWithAuth() {
        return auth.GenerateToken();
    }
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
        String authToken = "";
        Optional<Szerkeszto> szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByFelhasznalonev(felhasznalonev));
        if(szerkesztoOpt.isEmpty()) {return authToken;}
        var szerkeszto = szerkesztoOpt.get();
        if(szerkeszto.getFelhasznalonev().equals(felhasznalonev) && szerkeszto.getJelszo().equals(jelszo)) {
            authToken = GenerateTokenWithAuth();
            szerkeszto.setToken(authToken);
            szerkesztoRepository.save(szerkeszto);
            return authToken;
        }
        return authToken;
    }
}
