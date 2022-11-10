package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.OauthType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findAllByDeletedAtNull(PageRequest pageRequest);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByOauthTypeAndOauthId(OauthType oauthType, String oauthId);
}
