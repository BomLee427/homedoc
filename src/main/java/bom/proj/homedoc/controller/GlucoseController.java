package bom.proj.homedoc.controller;

import bom.proj.homedoc.domain.measure.Fasted;
import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Meal;
import bom.proj.homedoc.dto.request.GlucoseCreateRequestDto;
import bom.proj.homedoc.dto.request.GlucoseUpdateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponseDto;
import bom.proj.homedoc.dto.response.GlucoseResponseDto;
import bom.proj.homedoc.dto.response.GlucoseStatisticResponseDto;
import bom.proj.homedoc.search.GlucoseSearch;
import bom.proj.homedoc.service.GlucoseService;
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

//TODO: validation 내용 정리

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/measure/glucose")
@PreAuthorize("hasAnyRole('USER')")
public class GlucoseController {

    private final GlucoseService glucoseService;

    /**
     * 자신의 혈당 측정기록 조회(페이징)
     */
    @GetMapping("")
    public ResponseEntity<CommonResponseDto<List<GlucoseResponseDto>>> getGlucoseListV1(
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

        GlucoseSearch glucoseSearch = GlucoseSearch.builder()
                .manual(manual)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .build();

        return ResponseEntity.ok(CommonResponseDto.getResponse(glucoseService.getGlucoseList(getCurrentUserPK().orElse(null), glucoseSearch, pageable)));
    }

    /**
     * 혈당 통계 조회
     */
    @GetMapping("/statistic")
    public ResponseEntity<CommonResponseDto<GlucoseStatisticResponseDto>> getGlucoseStatisticV1(
            @RequestParam(value = "meal") String meal,
            @RequestParam(value = "fasted") String fasted,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(value = "manual", required = false) String manualString
    ) {

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atStartOfDay() : null;

        Manual manual = valueOfOrNull(Manual.class, manualString);

        GlucoseSearch glucoseSearch = GlucoseSearch.builder()
                .meal(valueOfOrNull(Meal.class, meal))
                .fasted(valueOfOrNull(Fasted.class, fasted))
                .manual(manual)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .build();

        return ResponseEntity.ok(CommonResponseDto.getResponse(glucoseService.getGlucoseStatistic(getCurrentUserPK().orElse(null), glucoseSearch)));
    }

    /**
     * 자신의 특정 측정기록 상세조회
     */
    @GetMapping("/{measurementId}")
    public ResponseEntity<CommonResponseDto<GlucoseResponseDto>> getGlucoseV1(
            @PathVariable Long measurementId
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(glucoseService.getGlucose(getCurrentUserPK().orElse(null), measurementId)));
    }

    /**
     * 혈당 측정
     */
    @PostMapping("")
    public ResponseEntity<CommonResponseDto<Map<String, Long>>> createGlucoseV1(
            @Validated @RequestBody final GlucoseCreateRequestDto dto) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(Map.of("id", glucoseService.createGlucose(getCurrentUserPK().orElse(null), dto))));
    }

    /**
     * 혈당 측정기록 수정
     */
    @PatchMapping("/{measurementId}")
    public ResponseEntity<CommonResponseDto<GlucoseResponseDto>> updateGlucoseV1(
            @PathVariable Long measurementId,
            @Validated @RequestBody final GlucoseUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(glucoseService.updateGlucose(getCurrentUserPK().orElse(null), measurementId, dto)));
    }

    /**
     * 혈당 측정기록 삭제
     */
    @DeleteMapping("/{measurementId}")
    public ResponseEntity<CommonResponseDto<Map<String, Long>>> deleteGlucoseV1(
            @PathVariable Long measurementId
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(Map.of("id", glucoseService.deleteGlucose(getCurrentUserPK().orElse(null), measurementId))));
    }
}
