package bom.proj.homedoc.controller;

import bom.proj.homedoc.dto.request.HealthProfileCreateRequestDto;
import bom.proj.homedoc.dto.request.HealthProfileUpdateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponse;
import bom.proj.homedoc.dto.response.HealthProfileResponseDto;
import bom.proj.homedoc.service.HealthProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/health-profile")
public class HealthProfileController {

    private final HealthProfileService healthProfileService;

    /**
     * 특정 회원의 건강정보 조회
     */
    @GetMapping("/{memberId}")
    public CommonResponse<HealthProfileResponseDto> getHealthProfileV1(
            @PathVariable Long memberId
    ) {
        return CommonResponse.getResponse(healthProfileService.getHealthProfile(memberId));
    }

    /**
     * 건강정보 생성
     */
    @PostMapping("/{memberId}")
    public CommonResponse<Map<String, Long>> createHealthProfileV1(
            @PathVariable Long memberId,
            @Validated @RequestBody final HealthProfileCreateRequestDto dto
    ) {
        return CommonResponse.getResponse(Map.of("id", healthProfileService.createHealthProfile(memberId, dto)));
    }

    /**
     * 건강정보 수정(PUT)
     */
    @PutMapping("/{profileId}")
    public CommonResponse<HealthProfileResponseDto> updateHealthProfileV1(
            @PathVariable Long profileId,
            @Validated @RequestBody final HealthProfileUpdateRequestDto dto
    ) {
        return CommonResponse.getResponse(healthProfileService.updateHealthProfile(profileId, dto));
    }

    /**
     * 건강정보 삭제
     */
    @DeleteMapping("/{profileId}")
    public CommonResponse<Map<String, Long>> deleteHealthProfileV1 (
            @PathVariable Long profileId
    ) {
        return CommonResponse.getResponse(Map.of("id", healthProfileService.deleteHealthProfile(profileId)));
    }
}
