package bom.proj.homedoc.domain.measure;

import bom.proj.homedoc.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PRE")
@Getter
public class Pressure extends Measurement {

    @Builder
    private Pressure(Member member, Manual manual, Normality normality, String memo, Integer diastolic, Integer systolic) {
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.setMeasurement(member, manual, normality, memo);
    }

    private Integer diastolic;
    private Integer systolic;

    public void updateDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public void updateSystolic(Integer systolic) {
        this.systolic = systolic;
    }

}