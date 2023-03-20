package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.authority.Authority;
import bom.proj.homedoc.domain.authority.QAuthority;
import bom.proj.homedoc.domain.authority.QMemberAuthority;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorityRepoImpl implements AuthorityRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMemberAuthority qMemberAuthority = QMemberAuthority.memberAuthority;
    private final QAuthority qAuthority = QAuthority.authority;


    @Override
    public List<Authority> findMemberAuthority(String email) {
        return jpaQueryFactory.selectFrom(qAuthority)
                .join(qMemberAuthority)
                .on(qMemberAuthority.authority.id.eq(qAuthority.id))
                .where(qMemberAuthority.member.email.eq(email))
                .fetch();
    }

}
