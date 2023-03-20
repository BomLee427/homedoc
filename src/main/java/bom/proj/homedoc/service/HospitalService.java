package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Address;
import bom.proj.homedoc.domain.hospital.Department;
import bom.proj.homedoc.domain.hospital.Hospital;
import bom.proj.homedoc.dto.request.HospitalCreateRequestDto;
import bom.proj.homedoc.dto.request.HospitalUpdateRequestDto;
import bom.proj.homedoc.dto.response.HospitalResponseDto;
import bom.proj.homedoc.exception.NoResourceFoundException;
import bom.proj.homedoc.repository.HospitalRepoImpl;
import bom.proj.homedoc.repository.HospitalRepository;
import bom.proj.homedoc.search.HospitalSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bom.proj.homedoc.domain.EnumNullCheck.valueOfOrNull;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalRepoImpl hospitalRepoImpl;

    public List<HospitalResponseDto> getHospitalList(HospitalSearch hospitalSearch, Pageable pageable) {
        return hospitalRepoImpl.findAll(hospitalSearch, pageable).stream().map(HospitalResponseDto::fromEntity).collect(Collectors.toList());
    }

    public HospitalResponseDto getHospital(Long id) {
        Hospital hospital = hospitalRepository.findByIdAndDeletedAtNull(id).orElseThrow(NoResourceFoundException::new);
        return HospitalResponseDto.fromEntity(hospital);
    }

    public Long registerHospital(HospitalCreateRequestDto dto) {
        Hospital hospital = dto.toEntity();
        hospitalRepository.save(hospital);
        return hospital.getId();
    }

    public HospitalResponseDto updateHospital(Long id, HospitalUpdateRequestDto dto) {
        Hospital hospital = hospitalRepository.findByIdAndDeletedAtNull(id)
                .orElseThrow(NoResourceFoundException::new);
        if (dto.getName() != null) hospital.updateName(dto.getName());
        if (dto.getDepartment() != null) hospital.updateDepartment(valueOfOrNull(Department.class, dto.getDepartment()));
        if (dto.getCity() != null && dto.getStreet() != null && dto.getZipCode() != null) {
            Address newAddress = Address.createAddress(dto.getCity(), dto.getStreet(), dto.getZipCode());
            hospital.updateAddress(newAddress);
        }
        if (dto.getPhoneNumber() != null) hospital.updatePhoneNumber(dto.getPhoneNumber());
        return HospitalResponseDto.fromEntity(hospital);
    }

    public Long deleteHospital(Long id) {
        Hospital hospital = hospitalRepository.findByIdAndDeletedAtNull(id)
                .orElseThrow(NoResourceFoundException::new);
        hospital.deleteHospital();
        return hospital.getId();
    }
}
