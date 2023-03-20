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
    public Glucose(Member member, Manual manual, Normality normality, String memo, Meal meal, Fasted fasted, Double value) {
        this.meal = meal;
        this.fasted = fasted;
        this.value = value;
        this.setMeasurement(member, manual, normality, memo);
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "glucose_meal")
    private Meal meal; //BREAKFAST, LUNCH, DINNER
    @Enumerated(EnumType.STRING)
    @Column(name = "glucose_fasted")
    private Fasted fasted; //FASTED, AFTER_MEAL
    @Column(name = "glucose_value")
    private Double value;

    public void updateMeal(Meal meal) {
        this.meal = meal;
    }

    public void updateFasted(Fasted fasted) {
        this.fasted = fasted;
    }

    public void updateValue(Double value) {
        this.value = value;
    }
}
