package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.healthprofile.GlucoseRisk;
import bom.proj.homedoc.domain.healthprofile.HealthProfile;
import bom.proj.homedoc.domain.healthprofile.PressureRisk;
import bom.proj.homedoc.domain.healthprofile.Sex;
import bom.proj.homedoc.validation.EnumValue;
import lombok.*;

import static bom.proj.homedoc.domain.EnumNullCheck.*;

@Getter
public class HealthProfileCreateRequestDto {
    @EnumValue(enumClass = Sex.class)
    private String sex;

    private int age;

    private Double height;

    private Double weight;

    @EnumValue(enumClass = GlucoseRisk.class)
    private String glucoseRisk;

    @EnumValue(enumClass = PressureRisk.class)
    private String pressureRisk;

    public HealthProfile toEntity(Member member) {
        HealthProfile.HealthProfileBuilder builder = HealthProfile.builder()
                .age(age)
                .height(height)
                .weight(weight);
        if (member != null)     builder.member(member);
        if (sex != null)        builder.sex(valueOfOrNull(Sex.class, sex));
        if (glucoseRisk != null) builder.glucoseRisk(valueOfOrNull(GlucoseRisk.class, glucoseRisk));
        if (pressureRisk != null) builder.pressureRisk(valueOfOrNull(PressureRisk.class, pressureRisk));
        return builder.build();
    }

}
