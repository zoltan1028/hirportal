package hu.bme.aut.hirportal.repository;

import hu.bme.aut.hirportal.model.Kategoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategoriaRepository extends JpaRepository<Kategoria, Long> {
    abstract void deleteByNev(String katnev);
    abstract Kategoria findByNev(String katnev);

}
