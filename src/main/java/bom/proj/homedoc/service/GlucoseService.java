package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.measure.Fasted;
import bom.proj.homedoc.domain.measure.Glucose;
import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Meal;
import bom.proj.homedoc.dto.request.GlucoseCreateRequestDto;
import bom.proj.homedoc.dto.request.GlucoseUpdateRequestDto;
import bom.proj.homedoc.dto.request.PressureCreateRequestDto;
import bom.proj.homedoc.dto.request.PressureUpdateRequestDto;
import bom.proj.homedoc.dto.response.GlucoseResponseDto;
import bom.proj.homedoc.dto.response.PressureResponseDto;
import bom.proj.homedoc.repository.GlucoseRepository;
import bom.proj.homedoc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bom.proj.homedoc.domain.BaseAuditingEntity.filterSoftDeleted;
import static bom.proj.homedoc.domain.EnumNullCheck.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GlucoseService extends CommonService {

    private final MemberRepository memberRepository;
    private final GlucoseRepository glucoseRepository;

    public List<GlucoseResponseDto> getGlucoseList(Long memberId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return glucoseRepository.findAllByMemberIdAndDeletedAtNull(memberId, pageRequest)
                .stream().map(GlucoseResponseDto::fromEntity).collect(Collectors.toList());
    }

    public GlucoseResponseDto getGlucose(Long glucoseId) {
        Glucose glucose = filterSoftDeleted(glucoseRepository.findById(glucoseId).orElse(null));
        return GlucoseResponseDto.fromEntity(glucose);
    }

    public Long createGlucose(Long memberId, GlucoseCreateRequestDto dto) {
        Member member = filterSoftDeleted(memberRepository.findById(memberId).orElse(null));
        Glucose glucose = dto.toEntity(member);
        glucoseRepository.save(glucose);
        return glucose.getId();
    }

    public GlucoseResponseDto updateGlucose(Long measurementId, GlucoseUpdateRequestDto dto) {
        Glucose glucose = filterSoftDeleted(glucoseRepository.findById(measurementId).orElse(null));
        if (dto.getMeal() != null) glucose.updateMeal(valueOfOrNull(Meal.class, dto.getMeal()));
        if (dto.getFasted() != null) glucose.updateFasted(valueOfOrNull(Fasted.class, dto.getFasted()));
        if (dto.getValue() != null && glucose.getManual() != Manual.MANUAL) glucose.updateValue(dto.getValue());
        if (dto.getMemo() != null) glucose.updateMemo(dto.getMemo());
        return GlucoseResponseDto.fromEntity(glucose);
    }

    public Long deleteGlucose(Long measurementId) {
        Glucose glucose = filterSoftDeleted(glucoseRepository.findById(measurementId).orElse(null));
        glucose.deleteMeasurement();
        return glucose.getId();
    }
}
