package hu.bme.aut.hirportal.repository;

import hu.bme.aut.hirportal.model.Kategoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KategoriaRepository extends JpaRepository<Kategoria, Long> {
    abstract void deleteByNev(String katnev);
    abstract Optional<Kategoria> findByNev(String katnev);

}
