package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.healthprofile.HealthProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthProfileRepository extends JpaRepository<HealthProfile, Long> {
    @EntityGraph(attributePaths = {"member"})
    Optional<HealthProfile> findOneByMemberIdAndDeletedAtNull(Long memberId);

    @EntityGraph(attributePaths = {"member"})
    Optional<HealthProfile> findByIdAndDeletedAtNull(Long id);
}
