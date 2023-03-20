package bom.proj.homedoc.dto.response;

import bom.proj.homedoc.domain.Address;
import bom.proj.homedoc.domain.hospital.Department;
import bom.proj.homedoc.domain.hospital.Hospital;
import lombok.Getter;


@Getter
public class HospitalResponseDto {
    private Long id;
    private String hashCode;
    private String name;
    private Department department; //ENT, EYE, INTERNAL, SURGERY, DENTAL
    private Address address;
    private String phoneNumber;

    public static HospitalResponseDto fromEntity(Hospital hospital) {
        HospitalResponseDto dto = new HospitalResponseDto();

        dto.id = hospital.getId();
        dto.hashCode = hospital.getHashCode();
        dto.name = hospital.getName();
        dto.department = hospital.getDepartment();
        dto.address = hospital.getAddress();
        dto.phoneNumber = hospital.getPhoneNumber();

        return dto;
    }
}
