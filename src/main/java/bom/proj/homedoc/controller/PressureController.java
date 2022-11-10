package bom.proj.homedoc.controller;

import bom.proj.homedoc.dto.request.PressureCreateRequestDto;
import bom.proj.homedoc.dto.request.PressureUpdateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponse;
import bom.proj.homedoc.dto.response.PressureResponseDto;
import bom.proj.homedoc.dto.response.PressureStatisticResponseDto;
import bom.proj.homedoc.search.PressureSearch;
import bom.proj.homedoc.service.PressureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/measure/pressure")
public class PressureController {

    private final PressureService pressureService;

    @GetMapping("")
    public CommonResponse<List<PressureResponseDto>> getPressureListV1(
            @RequestParam(value = "memberId") Long memberId, //TODO: 인증 구현 후 빼기
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int size,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {

        Pageable pageable = PageRequest.of(page, size);

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atStartOfDay() : null;

        PressureSearch pressureSearch = PressureSearch.builder()
                .startDate(startDateTime)
                .endDate(endDateTime)
                .build();

        return CommonResponse.getResponse(pressureService.getPressureList(memberId, pressureSearch, pageable));
    }

    @GetMapping("/statistic")
    public CommonResponse<PressureStatisticResponseDto> getPressureStatisticV1(
            @RequestParam(value = "memberId") Long memberId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atStartOfDay() : null;

        PressureSearch pressureSearch = PressureSearch.builder()
                .startDate(startDateTime)
                .endDate(endDateTime)
                .build();

        return CommonResponse.getResponse(pressureService.getPressureStatistic(memberId, pressureSearch));
    }

    @GetMapping("/{measurementId}")
    public CommonResponse<PressureResponseDto> getPressureV1(
            @PathVariable Long measurementId
    ) {
        return CommonResponse.getResponse(pressureService.getPressure(measurementId));
    }

    @PostMapping("")
    public CommonResponse<Map<String, Long>> createPressureV1(
            @RequestParam(value = "memberId") Long memberId,
            @RequestBody final PressureCreateRequestDto dto
    ) {
        return CommonResponse.getResponse(Map.of("id", pressureService.createPressure(memberId, dto)));
    }

    @PatchMapping("/{measurementId}")
    public CommonResponse<PressureResponseDto> updatePressureV1(
            @PathVariable Long measurementId,
            @Validated final PressureUpdateRequestDto dto
    ) {
        return CommonResponse.getResponse(pressureService.updatePressure(measurementId, dto));
    }

    @DeleteMapping("/{measurementId}")
    public CommonResponse<Map<String, Long>> deletePressureV1(
            @PathVariable Long measurementId
    ) {
        return CommonResponse.getResponse(Map.of("id", pressureService.deletePressure(measurementId)));
    }
}
