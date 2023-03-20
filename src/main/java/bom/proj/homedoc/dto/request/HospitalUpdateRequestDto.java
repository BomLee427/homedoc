package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.hospital.Department;
import bom.proj.homedoc.util.validation.EnumValue;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class HospitalUpdateRequestDto {

    @Size(max = 255)
    private String name;

    @EnumValue(enumClass = Department.class)
    private String department;

    @Size(max = 125)
    private String city;

    @Size(max = 255)
    private String street;

    @Size(max = 16)
    private String zipCode;

    @Size(max = 16)
    private String phoneNumber;
}
