package bom.proj.homedoc.domain;

import bom.proj.homedoc.domain.authority.MemberAuthority;
import bom.proj.homedoc.domain.healthprofile.HealthProfile;
import bom.proj.homedoc.domain.hospital.MemberHospital;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseAuditingEntity {

    private Member(String email, String password, String name) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;

    private String refreshToken;

    //MEMO: OneToOne 관계에서는 주인 쪽에서의 lazy 로딩만 정상 동작한다.
    //https://woodcock.tistory.com/23
    //https://1-7171771.tistory.com/143
    //MEMO: 단방향 연관관계로 변경함
//    @OneToOne(mappedBy = "member")
//    private HealthProfile healthProfile;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberHospital> memberHospitals;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberAuthority> memberAuthorities;

    public static Member createMember(String email, String password) {
        return new Member(email, password, initName());
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

    public void updatePassword(String password) {
        this.password = password;
    }

    public void resign() {
        super.delete();
    }

    private static String initName() {
        return "USER" + UUID.randomUUID();
    }

}
