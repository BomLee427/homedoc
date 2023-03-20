package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.authority.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepositoryCustom {
    List<Authority> findMemberAuthority(String email);
}
