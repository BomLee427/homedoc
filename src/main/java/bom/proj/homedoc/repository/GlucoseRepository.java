package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.measure.Glucose;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlucoseRepository extends JpaRepository<Glucose, Long> {
    @EntityGraph(attributePaths = {"member"})
    Optional<Glucose> findByIdAndDeletedAtNull(Long id);
}
