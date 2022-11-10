package bom.proj.homedoc.controller;

import bom.proj.homedoc.dto.request.GlucoseCreateRequestDto;
import bom.proj.homedoc.dto.request.GlucoseUpdateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponse;
import bom.proj.homedoc.dto.response.GlucoseResponseDto;
import bom.proj.homedoc.service.GlucoseService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/measure/glucose")
public class GlucoseController {

    private final GlucoseService glucoseService;

    @GetMapping("")
    public CommonResponse<List<GlucoseResponseDto>> getGlucoseListV1(
            @RequestParam(value = "memberId") Long memberId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int size
    ) {
        return CommonResponse.getResponse(glucoseService.getGlucoseList(memberId, page, size));
    }

    @GetMapping("/{measurementId}")
    public CommonResponse<GlucoseResponseDto> getGlucoseV1(
            @PathVariable Long measurementId
    ) {
        return CommonResponse.getResponse(glucoseService.getGlucose(measurementId));
    }

    @PostMapping("")
    public CommonResponse<Map<String, Long>> createGlucoseV1(
            @Validated @RequestBody final GlucoseCreateRequestDto dto,
            @RequestParam(value = "memberId") Long memberId
    ) {
        return CommonResponse.getResponse(Map.of("id", glucoseService.createGlucose(memberId, dto)));
    }

    @PatchMapping("/{measurementId}")
    public CommonResponse<GlucoseResponseDto> updateGlucoseV1(
            @PathVariable Long measurementId,
            @Validated @RequestBody final GlucoseUpdateRequestDto dto
    ) {
        return CommonResponse.getResponse(glucoseService.updateGlucose(measurementId, dto));
    }

    @DeleteMapping("/{measurementId}")
    public CommonResponse<Map<String, Long>> deleteGlucoseV1(
            @PathVariable Long measurementId
    ) {
        return CommonResponse.getResponse(Map.of("id", glucoseService.deleteGlucose(measurementId)));
    }
}
