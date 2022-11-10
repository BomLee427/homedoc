package bom.proj.homedoc.dto.response;

import bom.proj.homedoc.domain.measure.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GlucoseResponseDto {
    private Long measurementId;
    private Long memberId;
    private Manual manual;
    private Normality normality;
    private String memo;
    private LocalDateTime measuredAt;

    private Fasted fasted;
    private Meal meal;
    private Double value;

    public static GlucoseResponseDto fromEntity(Glucose glucose) {
        GlucoseResponseDto dto = new GlucoseResponseDto();
        dto.measurementId = glucose.getId();
        dto.memberId = glucose.getMember().getId();
        dto.manual = glucose.getManual();
        dto.normality = glucose.getNormality();
        dto.memo = glucose.getMemo();
        dto.measuredAt = glucose.getMeasuredAt();

        dto.fasted = glucose.getGlucoseFasted();
        dto.meal = glucose.getGlucoseMeal();
        dto.value = glucose.getGlucoseValue();

        return dto;
    }
}
