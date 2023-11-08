package hu.bme.aut.hirportal.repository;

import hu.bme.aut.hirportal.model.Hir;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HirRepository extends JpaRepository<Hir, Long> {
}
