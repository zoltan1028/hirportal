package hu.bme.aut.hirportal.service;

import hu.bme.aut.hirportal.auth.Authentication;
import hu.bme.aut.hirportal.repository.SzerkesztoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
}
