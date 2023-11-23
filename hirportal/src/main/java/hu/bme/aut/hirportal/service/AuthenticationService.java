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
    public boolean AuthenticateByToken(String token, String userid) {
        var szerkesztoOpt = Optional.ofNullable(szerkesztoRepository.findByToken(token));
        if(szerkesztoOpt.isPresent()) {
            var sz = szerkesztoOpt.get();
            System.out.println(sz.getId().toString().equals(userid));
            return sz.getId().toString().equals(userid);
        }
        return false;
    }
    public boolean isLoggedIn(Long userid) {
        var szerkesztoOpt = szerkesztoRepository.findById(userid);
        if (szerkesztoOpt.isPresent()) {
            var sz = szerkesztoOpt.get().getToken();
            return !Objects.equals(sz, "");
        }
        return false;
    }
}
