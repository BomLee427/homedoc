package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Pressure;
import bom.proj.homedoc.util.validation.EnumValue;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static bom.proj.homedoc.domain.EnumNullCheck.*;

@Getter
public class PressureCreateRequestDto {

    @NotNull
    @EnumValue(enumClass = Manual.class)
    private String manual;

    @NotNull
    private Integer diastolic;

    @NotNull
    private Integer systolic;

    @Size(max = 200)
    private String memo;

    public Pressure toEntity(Member member) {
        return Pressure.builder()
                .member(member)
                .manual(valueOfOrNull(Manual.class, manual))
                .memo(memo)
                .diastolic(diastolic)
                .systolic(systolic)
                .build();
    }
}
