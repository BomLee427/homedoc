package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Normality;
import bom.proj.homedoc.domain.measure.Pressure;
import bom.proj.homedoc.validation.EnumValue;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static bom.proj.homedoc.domain.EnumNullCheck.*;

@Getter
public class PressureCreateRequestDto {

    @NotNull
    @EnumValue(enumClass = Manual.class)
    private String manual;

    @EnumValue(enumClass = Normality.class)
    private String normality;

    @Size(max = 200)
    private String memo;

    @NotNull
    private Integer diastolic;

    @NotNull
    private Integer systolic;

    public Pressure toEntity(Member member) {
        return Pressure.builder()
                .member(member)
                .manual(valueOfOrNull(Manual.class, manual))
                .normality(valueOfOrNull(Normality.class, normality))
                .memo(memo)
                .diastolic(diastolic)
                .systolic(systolic)
                .build();
    }
}
