package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.measure.Glucose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlucoseRepository extends JpaRepository<Glucose, Long> {
    @EntityGraph(attributePaths = {"member"})
    Page<Glucose> findAllByMemberIdAndDeletedAtNull(Long memberId, PageRequest pageRequest);

    @EntityGraph(attributePaths = {"member"})
    Optional<Glucose> findById(Long id);
}
