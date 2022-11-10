package bom.proj.homedoc.dto.response;

import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Normality;
import bom.proj.homedoc.domain.measure.Pressure;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class PressureResponseDto {
    private Long measurementId;
    private Long memberId;
    private Manual manual;
    private Normality normality;
    private String memo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime measuredAt;
    private Integer diastolic;
    private Integer systolic;

    public static PressureResponseDto fromEntity(Pressure pressure) {
        PressureResponseDto dto = new PressureResponseDto();
        dto.measurementId = pressure.getId();
        dto.memberId = pressure.getMember().getId();
        dto.manual = pressure.getManual();
        dto.normality = pressure.getNormality();
        dto.memo = pressure.getMemo();
        dto.measuredAt = pressure.getMeasuredAt();
        dto.diastolic = pressure.getDiastolic();
        dto.systolic = pressure.getSystolic();
        return dto;
    }
}
