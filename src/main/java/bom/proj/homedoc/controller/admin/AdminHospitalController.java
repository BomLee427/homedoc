package bom.proj.homedoc.controller.admin;

import bom.proj.homedoc.domain.EnumNullCheck;
import bom.proj.homedoc.domain.hospital.Department;
import bom.proj.homedoc.dto.request.HospitalCreateRequestDto;
import bom.proj.homedoc.dto.request.HospitalUpdateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponseDto;
import bom.proj.homedoc.dto.response.HospitalResponseDto;
import bom.proj.homedoc.search.HospitalSearch;
import bom.proj.homedoc.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
//TODO: Spring Security 내용 정리

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/hospital")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminHospitalController {

    private final HospitalService hospitalService;

    // 제휴병원 검색
    @GetMapping("")
    public ResponseEntity<CommonResponseDto<List<HospitalResponseDto>>> getHospitalListV1(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        HospitalSearch hospitalSearch = HospitalSearch.builder()
                .name(name)
                .department(EnumNullCheck.valueOfOrNull(Department.class, department))
                .city(city)
                .build();

        return ResponseEntity.ok(CommonResponseDto.getResponse(hospitalService.getHospitalList(hospitalSearch, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDto<HospitalResponseDto>> getHospitalV1(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(hospitalService.getHospital(id)));
    }

    // 제휴병원 등록
    @PostMapping("")
    public ResponseEntity<CommonResponseDto<Map<String, Long>>> registerHospitalV1(
            @Validated @RequestBody final HospitalCreateRequestDto dto
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(Map.of("id", hospitalService.registerHospital(dto))));
    }

    // 제휴병원 수정
    @PutMapping("/{hospitalId}")
    public ResponseEntity<CommonResponseDto<HospitalResponseDto>> updateHospitalV1(
            @PathVariable Long hospitalId,
            @Validated @RequestBody final HospitalUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(hospitalService.updateHospital(hospitalId, dto)));
    }

    // 제휴병원 삭제
    @DeleteMapping("/{hospitalId}")
    public ResponseEntity<CommonResponseDto<Map<String, Long>>> deleteHospitalV1(
            @PathVariable Long hospitalId
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(Map.of("id", hospitalService.deleteHospital(hospitalId))));
    }

}
