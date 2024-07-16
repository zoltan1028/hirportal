package hu.bme.aut.hirportal.repository;

import hu.bme.aut.hirportal.model.Hir;
import hu.bme.aut.hirportal.model.HirFooldal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
public interface HirRepository extends JpaRepository<Hir, Long> {
}
