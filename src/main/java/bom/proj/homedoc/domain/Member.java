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

    @Embedded
    private OauthInfo oauthInfo;

    private String name;

    private String refreshToken;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberHospital> memberHospitals;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberAuthority> memberAuthorities;

    @Builder(builderMethodName = "defaultBuilder")
    private Member(String email, String password, String name) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Builder(builderMethodName = "oauthBuilder")
    private Member(OauthType oauthType, String oauthId, String name) {
        this.oauthInfo = OauthInfo.of(oauthType, oauthId);
        this.name = name;
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
        this.oauthInfo = OauthInfo.of(oauthType, oauthId);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void resign() {
        super.delete();
    }

}
