package hu.bme.aut.hirportal.auth;

import hu.bme.aut.hirportal.repository.SzerkesztoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Hash {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public String hashPassword(String password) {
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
    public boolean isHashOk(String rawPw, String hashedPassword) {
        boolean isPasswordMatch = passwordEncoder.matches(rawPw, hashedPassword);
        return isPasswordMatch;
    }
}
