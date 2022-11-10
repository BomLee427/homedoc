package bom.proj.homedoc.dto.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class PressureUpdateRequestDto {
    private Integer diastolic;
    private Integer systolic;

    @Size(max = 200)
    private String memo;
}
