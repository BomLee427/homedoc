package bom.proj.homedoc.controller;

import bom.proj.homedoc.domain.measure.Manual;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static bom.proj.homedoc.domain.EnumNullCheck.valueOfOrNull;
import static bom.proj.homedoc.util.SecurityUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/measure/pressure")
@PreAuthorize("hasAnyRole('USER')")
public class PressureController {

    private final PressureService pressureService;

    /**
     * 혈압 측정기록 조회(페이징)
     */
    @GetMapping("")
    public ResponseEntity<CommonResponse<List<PressureResponseDto>>> getPressureListV1(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int size,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(value = "manual", required = false) String manualString
    ) {

        Pageable pageable = PageRequest.of(page, size);

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atStartOfDay() : null;

        Manual manual = valueOfOrNull(Manual.class, manualString);

        PressureSearch pressureSearch = PressureSearch.builder()
                .startDate(startDateTime)
                .endDate(endDateTime)
                .manual(manual)
                .build();

        return ResponseEntity.ok(CommonResponse.getResponse(pressureService.getPressureList(getCurrentUserPK().orElse(null), pressureSearch, pageable)));
    }

    /**
     * 혈압 통계 조회
     */
    @GetMapping("/statistic")
    public ResponseEntity<CommonResponse<PressureStatisticResponseDto>> getPressureStatisticV1(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(value = "manual", required = false) String manualString
    ) {

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atStartOfDay() : null;

        Manual manual = valueOfOrNull(Manual.class, manualString);

        PressureSearch pressureSearch = PressureSearch.builder()
                .startDate(startDateTime)
                .endDate(endDateTime)
                .manual(manual)
                .build();

        return ResponseEntity.ok(CommonResponse.getResponse(pressureService.getPressureStatistic(getCurrentUserPK().orElse(null), pressureSearch)));
    }

    /**
     * 특정 혈압 측정기록 상세 조회
     */
    @GetMapping("/{measurementId}")
    public ResponseEntity<CommonResponse<PressureResponseDto>> getPressureV1(
            @PathVariable Long measurementId
    ) {
        return ResponseEntity.ok(CommonResponse.getResponse(pressureService.getPressure(getCurrentUserPK().orElse(null), measurementId)));
    }

    /**
     * 혈압 측정
     */
    @PostMapping("")
    public ResponseEntity<CommonResponse<Map<String, Long>>> createPressureV1(
            @RequestBody final PressureCreateRequestDto dto
    ) {
        return ResponseEntity.ok(CommonResponse.getResponse(Map.of("id", pressureService.createPressure(getCurrentUserPK().orElse(null), dto))));
    }

    /**
     * 혈압 측정기록 수정
     */
    @PatchMapping("/{measurementId}")
    public ResponseEntity<CommonResponse<PressureResponseDto>> updatePressureV1(
            @PathVariable Long measurementId,
            @Validated @RequestBody final PressureUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(CommonResponse.getResponse(pressureService.updatePressure(getCurrentUserPK().orElse(null), measurementId, dto)));
    }

    /**
     * 혈압 측정기록 삭제
     */
    @DeleteMapping("/{measurementId}")
    public ResponseEntity<CommonResponse<Map<String, Long>>> deletePressureV1(
            @PathVariable Long measurementId
    ) {
        return ResponseEntity.ok(CommonResponse.getResponse(Map.of("id", pressureService.deletePressure(getCurrentUserPK().orElse(null), measurementId))));
    }
}
