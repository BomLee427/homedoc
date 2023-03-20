package bom.proj.homedoc.controller;

import bom.proj.homedoc.dto.request.HealthProfileUpdateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponseDto;
import bom.proj.homedoc.dto.response.HealthProfileResponseDto;
import bom.proj.homedoc.service.HealthProfileService;
import bom.proj.homedoc.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/healthprofile")
@PreAuthorize("hasAnyRole('USER')")
public class HealthProfileController {

    private final HealthProfileService healthProfileService;

    /**
     * 자신의 건강정보 조회
     */
    @GetMapping("/my")
    public ResponseEntity<CommonResponseDto<HealthProfileResponseDto>> getHealthProfileV1() {
        return ResponseEntity.ok(CommonResponseDto.getResponse(healthProfileService.getHealthProfile(SecurityUtil.getCurrentUserPK().orElse(null))));
    }

    /**
     * 자신의 건강정보 수정
     */
    @PutMapping("/my")
    public ResponseEntity<CommonResponseDto<HealthProfileResponseDto>> updateHealthProfileV1(
            @Validated @RequestBody final HealthProfileUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(healthProfileService.updateHealthProfile(SecurityUtil.getCurrentUserPK().orElse(null), dto)));
    }
}
