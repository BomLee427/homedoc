package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.measure.Fasted;
import bom.proj.homedoc.domain.measure.Glucose;
import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Meal;
import bom.proj.homedoc.util.validation.EnumValue;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static bom.proj.homedoc.domain.EnumNullCheck.*;

@Getter
public class GlucoseCreateRequestDto {

    @NotNull
    @EnumValue(enumClass = Manual.class)
    private String manual;

    @NotNull
    @EnumValue(enumClass = Meal.class)
    private String meal;

    @NotNull
    @EnumValue(enumClass = Fasted.class)
    private String fasted;

    @NotNull
    private Double value;

    @Size(max = 200, message = "MAX_LENGTH_IS_200")
    private String memo;

    public Glucose toEntity(Member member) {
        return Glucose.builder()
                .member(member)
                .manual(valueOfOrNull(Manual.class, manual))
                .fasted(valueOfOrNull(Fasted.class, fasted))
                .meal(valueOfOrNull(Meal.class, meal))
                .value(value)
                .memo(memo)
                .build();
    }
}
