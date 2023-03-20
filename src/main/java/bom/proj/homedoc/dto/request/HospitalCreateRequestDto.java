package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.Address;
import bom.proj.homedoc.domain.hospital.Department;
import bom.proj.homedoc.domain.hospital.Hospital;
import bom.proj.homedoc.util.validation.EnumValue;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static bom.proj.homedoc.domain.EnumNullCheck.valueOfOrNull;

@Getter
public class HospitalCreateRequestDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @EnumValue(enumClass = Department.class)
    private String department;

    @NotBlank
    @Size(max = 255)
    private String city;

    @NotBlank
    @Size(max = 255)
    private String street;

    @NotBlank
    @Size(max = 8)
    private String zipCode;

    @NotBlank
    @Size(max = 16)
    private String phoneNumber;

    public Hospital toEntity() {
        return Hospital.createHospital(name, valueOfOrNull(Department.class, department), Address.createAddress(city, street, zipCode), phoneNumber);
    }
}
