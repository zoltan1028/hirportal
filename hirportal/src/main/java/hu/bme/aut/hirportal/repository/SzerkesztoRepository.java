package hu.bme.aut.hirportal.repository;

import hu.bme.aut.hirportal.model.Szerkeszto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SzerkesztoRepository extends JpaRepository<Szerkeszto, Long> {
    abstract Szerkeszto findByFelhasznalonev(String felhasznalonev);
    abstract Szerkeszto findByToken(String token);
}
