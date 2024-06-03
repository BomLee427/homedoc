package bom.proj.homedoc.controller;

import bom.proj.homedoc.dto.request.HealthProfileUpdateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponse;
import bom.proj.homedoc.dto.response.HealthProfileResponseDto;
import bom.proj.homedoc.exception.NotAuthenticatedException;
import bom.proj.homedoc.service.HealthProfileService;
import bom.proj.homedoc.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public CommonResponse<HealthProfileResponseDto> getHealthProfileV1() {

        Long memberId = SecurityUtil.getCurrentUserPK().orElseThrow(NotAuthenticatedException::new);
        return CommonResponse.<HealthProfileResponseDto>builder()
                .data(healthProfileService.getHealthProfile(memberId))
                .build();
    }

    /**
     * 자신의 건강정보 수정
     */
    @PutMapping("/my")
    public CommonResponse<HealthProfileResponseDto> updateHealthProfileV1(
            @Valid @RequestBody final HealthProfileUpdateRequestDto dto
    ) {

        Long memberId = SecurityUtil.getCurrentUserPK().orElseThrow(NotAuthenticatedException::new);
        return CommonResponse.<HealthProfileResponseDto>builder()
                .data(healthProfileService.updateHealthProfile(memberId, dto))
                .build();
    }
}
