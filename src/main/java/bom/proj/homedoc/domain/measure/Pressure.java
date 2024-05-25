package bom.proj.homedoc.domain.measure;

import bom.proj.homedoc.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PRE")
@Getter
public class Pressure extends Measurement {

    @Builder
    public Pressure(Member member, Manual manual, Normality normality, String memo, Integer diastolic, Integer systolic) {
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.setMeasurement(member, manual, normality, memo);
    }

    @Column(name = "pressure_dias")
    private Integer diastolic;
    @Column(name = "pressure_sys")
    private Integer systolic;

    public void updateDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public void updateSystolic(Integer systolic) {
        this.systolic = systolic;
    }

}
