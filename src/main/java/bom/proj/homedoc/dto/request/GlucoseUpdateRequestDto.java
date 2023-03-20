package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.measure.Fasted;
import bom.proj.homedoc.domain.measure.Meal;
import bom.proj.homedoc.util.validation.EnumValue;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class GlucoseUpdateRequestDto {
    @NotNull
    @EnumValue(enumClass = Meal.class)
    private String meal;

    @NotNull
    @EnumValue(enumClass = Fasted.class)
    private String fasted;

    private Double value; //TODO: long으로 변경 검토

    @Size(max = 200)
    private String memo;
}
