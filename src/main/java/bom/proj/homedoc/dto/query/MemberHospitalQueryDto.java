package bom.proj.homedoc.dto.query;

import bom.proj.homedoc.domain.Address;
import bom.proj.homedoc.domain.hospital.Department;
import lombok.Getter;

@Getter
public class MemberHospitalQueryDto {
    private Long id;
    private Long hospitalId;
    private String hashCode;
    private String name;
    private Department department; //ENT, EYE, INTERNAL, SURGERY, DENTAL
    private Address address;
    private String phoneNumber;
}
