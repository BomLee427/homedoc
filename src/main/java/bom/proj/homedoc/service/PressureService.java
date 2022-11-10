package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Pressure;
import bom.proj.homedoc.dto.request.PressureCreateRequestDto;
import bom.proj.homedoc.dto.request.PressureUpdateRequestDto;
import bom.proj.homedoc.dto.response.PressureResponseDto;
import bom.proj.homedoc.dto.response.PressureStatisticResponseDto;
import bom.proj.homedoc.repository.MemberRepository;
import bom.proj.homedoc.repository.PressureRepoImpl;
import bom.proj.homedoc.repository.PressureRepository;
import bom.proj.homedoc.search.PressureSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bom.proj.homedoc.domain.BaseAuditingEntity.filterSoftDeleted;

@Service
@Transactional
@RequiredArgsConstructor
public class PressureService {

    private final PressureRepoImpl pressureCustomRepo;
    private final MemberRepository memberRepository;
    private final PressureRepository pressureRepository;

    public List<PressureResponseDto> getPressureList(Long memberId, PressureSearch pressureSearch, Pageable pageable) {

//        List<Pressure> pressureList = getPressures(memberId, pressureSearch, pageRequest);
        Page<Pressure> pressureList = getPressureQdsl(memberId, pressureSearch, pageable);

        return pressureList.stream().map(PressureResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public PressureResponseDto getPressure(Long measurementId) {
        Pressure pressure = filterSoftDeleted(pressureRepository.findById(measurementId).orElse(null));
        return PressureResponseDto.fromEntity(pressure);
    }

    public PressureStatisticResponseDto getPressureStatistic(Long memberId, PressureSearch pressureSearch) throws ArithmeticException {

        List<Pressure> pressureList = getPressures(memberId, pressureSearch, null);

        Double diastolicAverage = null;
        Integer diastolicMax = null;
        Integer diastolicMin = null;
        Double systolicAverage = null;
        Integer systolicMax = null;
        Integer systolicMin = null;

        if (pressureList.size() > 0) {
            diastolicAverage = pressureList.stream()
                    .mapToInt(Pressure::getDiastolic)
                    .average()
                    .orElseThrow(() -> new ArithmeticException("이완기 혈압 최대값을 구할 수 없습니다."));
            //TODO: 0이면 안될거같은...그렇다고 아묻따 익셉션 던지는것도 아닌거같은...

            diastolicMax = pressureList.stream()
                    .mapToInt(Pressure::getDiastolic)
                    .max()
                    .orElseThrow(() -> new ArithmeticException("이완기 혈압 최대값을 구할 수 없습니다."));

            diastolicMin = pressureList.stream()
                    .mapToInt(Pressure::getDiastolic)
                    .min()
                    .orElseThrow(() -> new ArithmeticException("이완기 혈압 최소값을 구할 수 없습니다."));


            systolicAverage = pressureList.stream()
                    .mapToInt(Pressure::getSystolic)
                    .average()
                    .orElseThrow(() -> new ArithmeticException("수축기 혈압 평균값을 구할 수 없습니다."));

            systolicMax = pressureList.stream()
                    .mapToInt(Pressure::getSystolic)
                    .max()
                    .orElseThrow(() -> new ArithmeticException("수축기 혈압 최대값을 구할 수 없습니다."));

            systolicMin = pressureList.stream()
                    .mapToInt(Pressure::getSystolic)
                    .min()
                    .orElseThrow(() -> new ArithmeticException("수축기 혈압 최소값을 구할 수 없습니다."));
        }

        return PressureStatisticResponseDto.builder()
                .diastolicAverage(diastolicAverage)
                .diastolicMax(diastolicMax)
                .diastolicMin(diastolicMin)
                .systolicAverage(systolicAverage)
                .systolicMax(systolicMax)
                .systolicMin(systolicMin)
                .build();
    }

    public Long createPressure (Long memberId, PressureCreateRequestDto dto) {
        Member member = filterSoftDeleted(memberRepository.findById(memberId).orElse(null));
        return pressureRepository.save(dto.toEntity(member)).getId();
    }

    public PressureResponseDto updatePressure(Long measurementId, PressureUpdateRequestDto dto) {
        Pressure pressure = filterSoftDeleted(pressureRepository.findById(measurementId).orElse(null));
        if (pressure.getManual() == Manual.MANUAL) {
            if (dto.getDiastolic() != null) pressure.updateDiastolic(dto.getDiastolic());
            if (dto.getSystolic() != null) pressure.updateSystolic(dto.getSystolic());
        }
        if (dto.getMemo() != null) pressure.updateMemo(dto.getMemo());

        return PressureResponseDto.fromEntity(pressure);
    }

    public Long deletePressure(Long measurementId) {
        Pressure pressure = filterSoftDeleted(pressureRepository.findById(measurementId).orElse(null));
        pressure.deleteMeasurement();
        return pressure.getId();
    }

    private List<Pressure> getPressures(Long memberId, PressureSearch pressureSearch, PageRequest pageRequest) {

        List<Pressure> pressureList;

        if (pageRequest != null) {
            if (pressureSearch.getStartDate() != null) {
                if (pressureSearch.getEndDate() != null) {
                    pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNullAndMeasuredAtBetween(memberId, pageRequest, pressureSearch.getStartDate(), pressureSearch.getEndDate()).toList();
                } else {
                    pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNullAndMeasuredAtAfter(memberId, pageRequest, pressureSearch.getStartDate()).toList();
                }
            } else if (pressureSearch.getEndDate() != null) {
                pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNullAndMeasuredAtBefore(memberId, pageRequest, pressureSearch.getEndDate()).toList();
            } else {
                pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNull(memberId, pageRequest).toList();
            }
        } else {
            if (pressureSearch.getStartDate() != null) {
                if (pressureSearch.getEndDate() != null) {
                    pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNullAndMeasuredAtBetween(memberId, pressureSearch.getStartDate(), pressureSearch.getEndDate());
                } else {
                    pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNullAndMeasuredAtAfter(memberId, pressureSearch.getStartDate());
                }
            } else if (pressureSearch.getEndDate() != null) {
                pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNullAndMeasuredAtBefore(memberId, pressureSearch.getEndDate());
            } else {
                pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNull(memberId);
            }
        }
        return pressureList;
    }

    private List<Pressure> getPressureQdsl(Long memberId, PressureSearch pressureSearch) {
        return pressureCustomRepo.findAll(memberId, pressureSearch);
    }

    private Page<Pressure> getPressureQdsl(Long memberId, PressureSearch pressureSearch, Pageable pageable) {
        return pressureCustomRepo.findAll(memberId, pressureSearch, pageable);
    }

}
