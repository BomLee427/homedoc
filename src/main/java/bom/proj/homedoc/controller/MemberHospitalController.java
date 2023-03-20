package bom.proj.homedoc.controller;

import bom.proj.homedoc.dto.response.CommonResponseDto;
import bom.proj.homedoc.dto.response.MemberHospitalResponseDto;
import bom.proj.homedoc.service.MemberHospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static bom.proj.homedoc.util.SecurityUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hospital")
@PreAuthorize("hasAnyRole('USER')")
public class MemberHospitalController {

    private final MemberHospitalService memberHospitalService;

    // 병원 목록(회원별)
    @GetMapping("")
    public ResponseEntity<CommonResponseDto<List<MemberHospitalResponseDto>>> getHospitalJoinListV1(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(CommonResponseDto.getResponse(memberHospitalService.getHospitalJoinList(getCurrentUserPK().orElse(null), pageable)));
    }

    // 병원 연결
    @PostMapping("")
    public ResponseEntity<CommonResponseDto<Map<String, Long>>> joinHospitalV1(
            @RequestParam(value = "hashCode") String hashCode
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(Map.of("id", memberHospitalService.joinHospital(getCurrentUserPK().orElse(null), hashCode))));
    }

    // 병원 연결 해제
    @DeleteMapping("/{memberHospitalId}")
    public ResponseEntity<CommonResponseDto<Map<String, Long>>> unJoinHospitalV1(
            @PathVariable(name = "memberHospitalId") Long memberHospitalId
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(Map.of("id", memberHospitalService.unJoinHospital(getCurrentUserPK().orElse(null), memberHospitalId))));
    }
}
