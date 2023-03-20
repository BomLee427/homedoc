package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findAllByDeletedAtNull(Pageable pageable);

    Optional<Member> findByIdAndDeletedAtNull(Long memberId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndDeletedAtNull(String email);

    @Query("select m, ma, a from Member m " +
            "join fetch m.memberAuthorities ma " +
            "join fetch ma.authority a " +
            "where m.id = :id " +
            "and m.deletedAt IS null " +
            "and ma.deletedAt IS null")
    Optional<Member> findByIdWithAuthorities(@Param("id")Long id);

    @Query("select m, ma, a from Member m " +
            "join fetch m.memberAuthorities ma " +
            "join fetch ma.authority a " +
            "where m.email = :email " +
            "and m.deletedAt IS null " +
            "and ma.deletedAt IS null")
    Optional<Member> findByEmailWithAuthorities(@Param("email")String email);

}
