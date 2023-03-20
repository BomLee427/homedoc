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
    public HealthProfile(Member member, Sex sex, int age, Double height, Double weight, GlucoseRisk glucoseRisk, GlucoseCriteria glucoseCriteria, PressureRisk pressureRisk, PressureCriteria pressureCriteria) {
        this.member = member;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.glucoseRisk = glucoseRisk;
        this.glucoseCriteria = glucoseCriteria;
        this.pressureRisk = pressureRisk;
        this.pressureCriteria = pressureCriteria;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @MapsId
    private Member member;

    @Enumerated(EnumType.STRING)
    private Sex sex; //MALE, FEMALE, ETC
    private Integer age;

    private Double height;
    private Double weight;

    @Enumerated(EnumType.STRING)
    private GlucoseRisk glucoseRisk; //VERY_HIGH, HIGH, NORMAL

    @Embedded
    private GlucoseCriteria glucoseCriteria;

    @Enumerated(EnumType.STRING)
    private PressureRisk pressureRisk; //VERY_HIGH, HIGH, NORMAL

    @Embedded
    private PressureCriteria pressureCriteria;

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

    public void updateGlucoseCriteria(GlucoseCriteria glucoseCriteria) {
        this.glucoseCriteria = glucoseCriteria;
    }

    public void updatePressureRisk(PressureRisk pressureRisk) {
        this.pressureRisk = pressureRisk;
    }

    public void updatePressureCriteria(PressureCriteria pressureCriteria) {
        this.pressureCriteria = pressureCriteria;
    }

    public void deleteHealthProfile() {
        super.delete();
    }
}
