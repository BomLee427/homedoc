package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.hospital.MemberHospital;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberHospitalRepository extends JpaRepository<MemberHospital, Long> {
    //MEMO: @EntityGraph는 left outer join, @Query(JPQL join fetch)는 inner join
    @EntityGraph(attributePaths = {"member", "hospital"})
    Optional<MemberHospital> findByIdAndDeletedAtNull(Long memberHospitalId);

    List<MemberHospital> findByMemberIdAndDeletedAtNull(Long memberId);

    @EntityGraph(attributePaths = {"member", "hospital"})
    Optional<MemberHospital> findByIdAndMemberIdAndDeletedAtNull(Long id, Long memberId);
}
