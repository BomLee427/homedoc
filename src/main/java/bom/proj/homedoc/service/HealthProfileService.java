package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.healthprofile.GlucoseRisk;
import bom.proj.homedoc.domain.healthprofile.HealthProfile;
import bom.proj.homedoc.domain.healthprofile.PressureRisk;
import bom.proj.homedoc.domain.healthprofile.Sex;
import bom.proj.homedoc.dto.request.HealthProfileCreateRequestDto;
import bom.proj.homedoc.dto.request.HealthProfileUpdateRequestDto;
import bom.proj.homedoc.dto.response.HealthProfileResponseDto;
import bom.proj.homedoc.repository.HealthProfileRepository;
import bom.proj.homedoc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static bom.proj.homedoc.domain.BaseAuditingEntity.filterSoftDeleted;
import static bom.proj.homedoc.domain.EnumNullCheck.*;

@Service
@Transactional
@RequiredArgsConstructor
public class HealthProfileService extends CommonService {

    private final MemberRepository memberRepository;
    private final HealthProfileRepository healthProfileRepository;

    public HealthProfileResponseDto getHealthProfile(Long memberId) {
        HealthProfile healthProfile = filterSoftDeleted(healthProfileRepository.findByMemberId(memberId).orElse(null));
        return HealthProfileResponseDto.fromEntity(healthProfile);
    }

    public Long createHealthProfile(Long memberId, HealthProfileCreateRequestDto dto) {
        // 존재하는 회원인지 체크
        Member member = filterSoftDeleted(memberRepository.findById(memberId).orElse(null));
        // 건강정보가 이미 있는지 체크
        HealthProfile findHealthProfile = healthProfileRepository.findByMemberId(memberId).orElse(null);
        if (findHealthProfile != null && findHealthProfile.getDeletedAt() != null) {
            throw new DuplicateKeyException("이미 건강정보가 존재합니다.");
        }
        HealthProfile healthProfile = dto.toEntity(member);
        healthProfileRepository.save(healthProfile);
        return healthProfile.getId();
    }

    public HealthProfileResponseDto updateHealthProfile(Long id, HealthProfileUpdateRequestDto dto) {
        HealthProfile healthProfile = filterSoftDeleted(healthProfileRepository.findById(id).orElse(null));
        healthProfile.updateSex(valueOfOrNull(Sex.class, dto.getSex()));
        healthProfile.updateAge(dto.getAge());
        healthProfile.updateHeight(dto.getHeight());
        healthProfile.updateWeight(dto.getWeight());
        healthProfile.updateGlucoseRisk(valueOfOrNull(GlucoseRisk.class, dto.getGlucoseRisk()));
        healthProfile.updatePressureRisk(valueOfOrNull(PressureRisk.class, dto.getPressureRisk()));

        return HealthProfileResponseDto.fromEntity(healthProfile);
    }

    public Long deleteHealthProfile(Long id) {
        HealthProfile healthProfile = filterSoftDeleted(healthProfileRepository.findById(id).orElse(null));
        healthProfile.deleteHealthProfile();
        return healthProfile.getId();
    }
}
