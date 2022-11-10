package bom.proj.homedoc.domain.healthprofile;

import bom.proj.homedoc.domain.BaseAuditingEntity;
import bom.proj.homedoc.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member_health")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthProfile extends BaseAuditingEntity {

    @Builder
    public HealthProfile(Member member, Sex sex, int age, Double height, Double weight, GlucoseRisk glucoseRisk, PressureRisk pressureRisk) {
        this.member = member;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.glucoseRisk = glucoseRisk;
        this.pressureRisk = pressureRisk;
    }


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Sex sex; //MALE, FEMALE, ETC
    private int age;

    private Double height;
    private Double weight;

    @Enumerated(EnumType.STRING)
    private GlucoseRisk glucoseRisk; //VERY_HIGH, HIGH, NORMAL

    @Enumerated(EnumType.STRING)
    private PressureRisk pressureRisk; //VERY_HIGH, HIGH, NORMAL


    public void updateSex(Sex sex) {
        this.sex = sex;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void updateHeight(Double height) {
        this.height = height;
    }

    public void updateWeight(Double weight) {
        this.weight = weight;
    }

    public void updateGlucoseRisk(GlucoseRisk glucoseRisk) {
        this.glucoseRisk = glucoseRisk;
    }

    public void updatePressureRisk(PressureRisk pressureRisk) {
        this.pressureRisk = pressureRisk;
    }

    public void deleteHealthProfile() {
        super.delete();
    }
}
