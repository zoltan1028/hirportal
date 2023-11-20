package hu.bme.aut.hirportal.repository;

import hu.bme.aut.hirportal.model.Hir;
import hu.bme.aut.hirportal.model.HirFooldal;
import hu.bme.aut.hirportal.model.Szerkeszto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HirFooldalRepository extends JpaRepository<HirFooldal, Long> {
    abstract HirFooldal findByHir(Hir hir);
}
