package bom.proj.homedoc.domain;

import bom.proj.homedoc.domain.authority.MemberAuthority;
import bom.proj.homedoc.domain.hospital.MemberHospital;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseAuditingEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private OauthType oauthType;

    private String oauthId;

    private String name;

    private String refreshToken;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberHospital> memberHospitals;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberAuthority> memberAuthorities;

    private Member(String email, String password, String name) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    private Member(OauthType oauthType, String oauthId, String name) {
        this.oauthType = oauthType;
        this.oauthId = oauthId;
        this.name = name;
    }

    public static Member createDirectMember(String email, String password, String name) {
        return new Member(email, password, name);
    }

    public static Member createSnsMember(OauthType oauthType, String oauthId, String name) {
        return new Member(oauthType, oauthId, name);
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateOauthInfo(OauthType oauthType, String oauthId) {
        this.oauthType = oauthType;
        this.oauthId = oauthId;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void resign() {
        super.delete();
    }

}
