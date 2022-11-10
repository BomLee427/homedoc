package bom.proj.homedoc.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PressureStatisticResponseDto {

    @Builder
    public PressureStatisticResponseDto(Long memberId, Double diastolicAverage, Integer diastolicMax, Integer diastolicMin, Double systolicAverage, Integer systolicMax, Integer systolicMin) {
        this.memberId = memberId;
        this.diastolicAverage = diastolicAverage;
        this.diastolicMax = diastolicMax;
        this.diastolicMin = diastolicMin;
        this.systolicAverage = systolicAverage;
        this.systolicMax = systolicMax;
        this.systolicMin = systolicMin;
    }

    private Long memberId;

    private Double diastolicAverage;
    private Integer diastolicMax;
    private Integer diastolicMin;

    private Double systolicAverage;
    private Integer systolicMax;
    private Integer systolicMin;
}
