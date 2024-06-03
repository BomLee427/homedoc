package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.measure.*;
import bom.proj.homedoc.dto.request.GlucoseCreateRequestDto;
import bom.proj.homedoc.dto.request.GlucoseUpdateRequestDto;
import bom.proj.homedoc.dto.response.GlucoseResponseDto;
import bom.proj.homedoc.dto.response.GlucoseStatisticResponseDto;
import bom.proj.homedoc.exception.NoResourceFoundException;
import bom.proj.homedoc.repository.GlucoseRepoImpl;
import bom.proj.homedoc.repository.GlucoseRepository;
import bom.proj.homedoc.repository.MemberRepository;
import bom.proj.homedoc.search.GlucoseSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bom.proj.homedoc.domain.EnumNullCheck.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GlucoseService {

    private final MemberRepository memberRepository;
    private final GlucoseRepository glucoseRepository;
    private final GlucoseRepoImpl glucoseRepoImpl;

    @Transactional(readOnly = true)
    public List<GlucoseResponseDto> getGlucoseList(Long memberId, GlucoseSearch glucoseSearch, Pageable pageable) {
        return getGlucoses(memberId, glucoseSearch, pageable)
                .stream().map(GlucoseResponseDto::fromEntity).collect(Collectors.toList());
    }

    public GlucoseResponseDto getGlucose(Long memberId, Long glucoseId) {
        Glucose glucose = glucoseRepository.findByIdAndDeletedAtNull(glucoseId)
                .filter(g -> g.isValidAuthor(memberId))
                .orElseThrow(NoResourceFoundException::new);
        return GlucoseResponseDto.fromEntity(glucose);
    }

    public GlucoseStatisticResponseDto getGlucoseStatistic(Long memberId, GlucoseSearch glucoseSearch) throws ArithmeticException {

        List<Glucose> glucoseList = getGlucoses(memberId, glucoseSearch);

        Double average = null;
        Double max = null;
        Double min = null;

        if (glucoseList.size() > 0) {
            average = glucoseList.stream()
                    .mapToDouble(Glucose::getValue)
                    .average()
                    .orElseThrow(() -> new ArithmeticException("이완기 혈압 평균값을 구할 수 없습니다."));
            //TODO: 0이면?
            //TODO: 예외를 너무 성의없이 던지는 것은 아닌지

            max = glucoseList.stream()
                    .mapToDouble(Glucose::getValue)
                    .max()
                    .orElseThrow(() -> new ArithmeticException("이완기 혈압 최대값을 구할 수 없습니다."));

            min = glucoseList.stream()
                    .mapToDouble(Glucose::getValue)
                    .min()
                    .orElseThrow(() -> new ArithmeticException("이완기 혈압 최소값을 구할 수 없습니다."));
        }

        return GlucoseStatisticResponseDto.builder()
                .memberId(memberId)
                .average(average)
                .max(max)
                .min(min)
                .meal(glucoseSearch.getMeal())
                .fasted(glucoseSearch.getFasted())
                .build();
    }

    public Long createGlucose(Long memberId, GlucoseCreateRequestDto dto) {
        Member member = memberRepository.findByIdAndDeletedAtNull(memberId)
                .orElseThrow(NoResourceFoundException::new);
        Glucose glucose = dto.toEntity(member);
        glucoseRepository.save(glucose);
        return glucose.getId();
    }

    public GlucoseResponseDto updateGlucose(Long memberId, Long measurementId, GlucoseUpdateRequestDto dto) {
        Glucose glucose = glucoseRepository.findByIdAndDeletedAtNull(measurementId).orElseThrow(NoResourceFoundException::new);
        if (dto.getMeal() != null) glucose.updateMeal(valueOfOrNull(Meal.class, dto.getMeal()));
        if (dto.getFasted() != null) glucose.updateFasted(valueOfOrNull(Fasted.class, dto.getFasted()));
        if (dto.getValue() != null && glucose.getManual() != Manual.MANUAL) glucose.updateValue(dto.getValue());
        if (dto.getMemo() != null) glucose.updateMemo(dto.getMemo());
        return GlucoseResponseDto.fromEntity(glucose);
    }

    public Long deleteGlucose(Long memberId, Long measurementId) {
        Glucose glucose = glucoseRepository.findByIdAndDeletedAtNull(measurementId)
                .filter(g -> g.isValidAuthor(memberId))
                .orElseThrow(NoResourceFoundException::new);
        glucose.deleteMeasurement();
        return glucose.getId();
    }

    private List<Glucose> getGlucoses(Long memberId, GlucoseSearch glucoseSearch) {
        return glucoseRepoImpl.findAll(memberId, glucoseSearch);
    }

    private Page<Glucose> getGlucoses(Long memberId, GlucoseSearch glucoseSearch, Pageable pageable) {
        return glucoseRepoImpl.findAll(memberId, glucoseSearch, pageable);
    }


}
