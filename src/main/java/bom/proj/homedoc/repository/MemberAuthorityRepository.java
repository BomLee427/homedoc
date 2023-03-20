package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.authority.MemberAuthority;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long> {

    @EntityGraph(attributePaths = {"authority"})
    List<MemberAuthority> findAllByMemberIdAndDeletedAtNull(Long memberId);

    @Query("select ma from MemberAuthority ma join fetch ma.authority a join fetch ma.member m where ma.member.id = :memberId and ma.deletedAt IS null")
    List<MemberAuthority> findAllWithMemberByMemberId(@Param(value = "memberId")Long memberId);
}
