package bom.proj.homedoc.domain.measure;

import bom.proj.homedoc.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("GLU")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Glucose extends Measurement {

    @Builder
    private Glucose(Member member, Manual manual, Normality normality, String memo, Meal glucoseMeal, Fasted glucoseFasted, Double glucoseValue) {
        this.glucoseMeal = glucoseMeal;
        this.glucoseFasted = glucoseFasted;
        this.glucoseValue = glucoseValue;
        this.setMeasurement(member, manual, normality, memo);
    }

    @Enumerated(EnumType.STRING)
    private Meal glucoseMeal; //BREAKFAST, LUNCH, DINNER
    @Enumerated(EnumType.STRING)
    private Fasted glucoseFasted; //FASTED, AFTER_MEAL
    private Double glucoseValue;

    public void updateMeal(Meal meal) {
        this.glucoseMeal = meal;
    }

    public void updateFasted(Fasted fasted) {
        this.glucoseFasted = fasted;
    }

    public void updateValue(Double value) {
        this.glucoseValue = value;
    }
}
