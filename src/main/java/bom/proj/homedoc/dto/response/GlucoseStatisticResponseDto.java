package bom.proj.homedoc.dto.response;

import bom.proj.homedoc.domain.measure.Fasted;
import bom.proj.homedoc.domain.measure.Meal;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GlucoseStatisticResponseDto {

    @Builder
    public GlucoseStatisticResponseDto(Long memberId, Meal meal, Fasted fasted, Double average, Double max, Double min) {
        this.memberId = memberId;
        this.meal = meal;
        this.fasted = fasted;
        this.average = average;
        this.max = max;
        this.min = min;
    }

    private Long memberId;

    private Meal meal;
    private Fasted fasted;

    private Double average;
    private Double max;
    private Double min;
}
