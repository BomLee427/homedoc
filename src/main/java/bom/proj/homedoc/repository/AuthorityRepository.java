package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.authority.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthorityNameAndDeletedAtNull(String authorityName);

    List<Authority> findByAuthorityNameIn(List<String> authorities);
}
