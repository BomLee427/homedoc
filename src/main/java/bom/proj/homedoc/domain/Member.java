package bom.proj.homedoc.domain;

import bom.proj.homedoc.domain.healthprofile.HealthProfile;
import bom.proj.homedoc.domain.hospital.MemberHospital;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "oauth_constraint",
                columnNames = {"oauth_type", "oauth_id"}
        )
})
public class Member extends BaseAuditingEntity {

    private Member(String email) {
        this.email = email;
        this.joinType = JoinType.DIRECT;
    }

    private Member(OauthType oauthType, String oauthId) {
        this.oauthType = oauthType;
        this.oauthId = oauthId;
        this.joinType = JoinType.SNS;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;


    @Enumerated(EnumType.STRING)
    @Column(name = "join_type")
    private JoinType joinType;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_type")
    private OauthType oauthType;
    @Column(name = "oauth_id")
    private String oauthId;

    @OneToOne(mappedBy = "member")
    private HealthProfile healthProfile;

    @OneToMany(mappedBy = "member")
    private List<MemberHospital> memberHospitals;

    public static Member createDirectMember(String email) {
        return new Member(email);
    }

    public static Member createSnsMember(OauthType oauthType, String oauthId) {
        return new Member(oauthType, oauthId);
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateOauth(OauthType oauthType, String oauthId) {
        this.oauthType = oauthType;
        this.oauthId = oauthId;
    }

    public void resign() {
        super.delete();
    }
}
