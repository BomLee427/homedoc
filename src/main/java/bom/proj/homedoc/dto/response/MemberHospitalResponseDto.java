package bom.proj.homedoc.dto.response;

import bom.proj.homedoc.domain.Address;
import bom.proj.homedoc.domain.hospital.Department;
import bom.proj.homedoc.dto.query.MemberHospitalQueryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MemberHospitalResponseDto {
    private Long id;
    private Long hospitalId;
    private String hashCode;
    private String name;
    private Department department; //ENT, EYE, INTERNAL, SURGERY, DENTAL
    private Address address;
    private String phoneNumber;

    public static MemberHospitalResponseDto fromQueryDto(MemberHospitalQueryDto queryDto) {
        MemberHospitalResponseDto newDto = new MemberHospitalResponseDto();
        newDto.id = queryDto.getId();
        newDto.hospitalId = queryDto.getHospitalId();
        newDto.hashCode = queryDto.getHashCode();
        newDto.name = queryDto.getName();
        newDto.department = queryDto.getDepartment();
        newDto.address = queryDto.getAddress();
        newDto.phoneNumber = queryDto.getPhoneNumber();

        return newDto;
    }
}
