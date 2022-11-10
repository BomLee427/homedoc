package bom.proj.homedoc.dto.response;

import bom.proj.homedoc.domain.healthprofile.GlucoseRisk;
import bom.proj.homedoc.domain.healthprofile.HealthProfile;
import bom.proj.homedoc.domain.healthprofile.PressureRisk;
import bom.proj.homedoc.domain.healthprofile.Sex;
import lombok.Getter;

@Getter
public class HealthProfileResponseDto {
    private Long healthProfileId;
    private Long memberId;
    private Sex sex;
    private Double height;
    private Double weight;
    private GlucoseRisk glucoseRisk;
    private PressureRisk pressureRisk;
    public static HealthProfileResponseDto fromEntity(HealthProfile healthProfile) {
        HealthProfileResponseDto dto = new HealthProfileResponseDto();
        dto.healthProfileId = healthProfile.getId();
        dto.memberId = healthProfile.getMember().getId();
        dto.sex = healthProfile.getSex() != null ? healthProfile.getSex() : null;
        dto.height = healthProfile.getHeight();
        dto.weight = healthProfile.getWeight();
        dto.glucoseRisk = healthProfile.getGlucoseRisk() != null ? healthProfile.getGlucoseRisk() : null;
        dto.pressureRisk = healthProfile.getPressureRisk() != null ? healthProfile.getPressureRisk() : null;

        return dto;
    }
}
