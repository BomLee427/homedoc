package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.healthprofile.GlucoseRisk;
import bom.proj.homedoc.domain.healthprofile.PressureRisk;
import bom.proj.homedoc.domain.healthprofile.Sex;
import bom.proj.homedoc.validation.EnumValue;
import lombok.Getter;

@Getter
public class HealthProfileUpdateRequestDto {
    @EnumValue(enumClass = Sex.class)
    private String sex;

    private int age;

    private Double height;

    private Double weight;

    @EnumValue(enumClass = GlucoseRisk.class)
    private String glucoseRisk;

    @EnumValue(enumClass = PressureRisk.class)
    private String pressureRisk;
}
