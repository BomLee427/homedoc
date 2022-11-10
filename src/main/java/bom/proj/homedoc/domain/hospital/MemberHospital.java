package bom.proj.homedoc.domain.hospital;

import bom.proj.homedoc.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberHospital {

    private MemberHospital(Member member, Hospital hospital) {
        this.member = member;
        this.hospital = hospital;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_hospital_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    /**
     * 병원 등록
     */
    public static MemberHospital joinHospital(Member member, Hospital hospital) {
        return new MemberHospital(member, hospital);
    }
}
