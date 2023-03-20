package bom.proj.homedoc.domain.authority;

import bom.proj.homedoc.domain.BaseAuditingEntity;
import bom.proj.homedoc.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuthority extends BaseAuditingEntity {

    private MemberAuthority(Member member, Authority authority) {
        this.member = member;
        this.authority = authority;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_authority_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id")
    private Authority authority;


    /**
     * 권한 부여
     */
    public static MemberAuthority addAuthority(Member member, Authority authority) {
        return new MemberAuthority(member, authority);
    }

    @Override
    public String toString() {
        return this.authority.getAuthorityName();
    }

    public Authority toAuthority() {
        return this.authority;
    }

    public void delete() {
        super.delete();
    }
}
