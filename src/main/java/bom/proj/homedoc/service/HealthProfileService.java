package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.healthprofile.*;
import bom.proj.homedoc.dto.request.HealthProfileUpdateRequestDto;
import bom.proj.homedoc.dto.response.HealthProfileResponseDto;
import bom.proj.homedoc.exception.NoResourceFoundException;
import bom.proj.homedoc.repository.HealthProfileRepository;
import bom.proj.homedoc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static bom.proj.homedoc.domain.EnumNullCheck.*;

@Service
@Transactional
@RequiredArgsConstructor
public class HealthProfileService {

    private final MemberRepository memberRepository;
    private final HealthProfileRepository healthProfileRepository;

    /**
     * 건강정보 조회
     */
    @Transactional(readOnly = true)
    public HealthProfileResponseDto getHealthProfile(Long memberId) {
        HealthProfile healthProfile = healthProfileRepository.findOneByMemberIdAndDeletedAtNull(memberId)
                .orElseThrow(NoResourceFoundException::new);
        return HealthProfileResponseDto.fromEntity(healthProfile);
    }

    /**
     * 건강정보 생성(회원가입 시 1회 호출)
     */
    //MEMO: insert나 update를 할때 중복체크나 엔티티 영속화를 위해 반드시 select가 선행되고 있는데...
    // 애플리케이션 레벨에서의 예외처리를 위해서라면 필요한 절차긴 하겠지만...? jpa를 사용하지 않는다고 가정해도 select 쿼리는 반드시 한번 나가야하긴해
    // 안그러면 DB단에서의 PK 오류같은걸로 잡아내야하는데...아무래도 위험하지
    public void createHealthProfile(Member member) {
        // 건강정보가 이미 있는지 체크
        healthProfileRepository.findOneByMemberIdAndDeletedAtNull(member.getId()).ifPresent(p -> {
                    throw new DuplicateKeyException("HEALTH_PROFILE_IS_EXIST");
                });
        HealthProfile healthProfile = HealthProfile.builder().member(member).build();
        healthProfileRepository.save(healthProfile);
    }

    /**
     * 건강정보 수정(PUT)
     */
    public HealthProfileResponseDto updateHealthProfile(Long id, HealthProfileUpdateRequestDto dto) {
        HealthProfile healthProfile = healthProfileRepository.findByIdAndDeletedAtNull(id)
                .orElseThrow(NoResourceFoundException::new);
        healthProfile.updateSex(valueOfOrNull(Sex.class, dto.getSex()));
        healthProfile.updateAge(dto.getAge());
        healthProfile.updateHeight(dto.getHeight());
        healthProfile.updateWeight(dto.getWeight());
        healthProfile.updateGlucoseRisk(valueOfOrNull(GlucoseRisk.class, dto.getGlucoseRisk()));
        healthProfile.updatePressureRisk(valueOfOrNull(PressureRisk.class, dto.getPressureRisk()));

        return HealthProfileResponseDto.fromEntity(healthProfile);
    }

    public Long deleteHealthProfile(Long id) {
        HealthProfile healthProfile = healthProfileRepository.findByIdAndDeletedAtNull(id)
                .orElseThrow(NoResourceFoundException::new);
        healthProfile.deleteHealthProfile();
        return healthProfile.getId();
    }

    public Long deleteHealthProfileByMemberId(Long memberId) {
        HealthProfile healthProfile = healthProfileRepository.findOneByMemberIdAndDeletedAtNull(memberId)
                .orElseThrow(NoResourceFoundException::new);
        healthProfile.deleteHealthProfile();
        return healthProfile.getId();
    }
}
