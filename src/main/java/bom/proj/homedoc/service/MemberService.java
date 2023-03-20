package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.authority.MemberAuthority;
import bom.proj.homedoc.domain.healthprofile.HealthProfile;
import bom.proj.homedoc.domain.hospital.MemberHospital;
import bom.proj.homedoc.dto.request.MemberCreateRequestDto;
import bom.proj.homedoc.dto.request.MemberUpdateRequestDto;
import bom.proj.homedoc.dto.response.AdminMemberResponseDto;
import bom.proj.homedoc.dto.response.MemberResponseDto;
import bom.proj.homedoc.exception.NoResourceFoundException;
import bom.proj.homedoc.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final HealthProfileRepository healthProfileRepository;
    private final AuthorityRepository authorityRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final HealthProfileService healthProfileService;
    private final MemberHospitalRepository memberHospitalRepository;

    /**
     * 전체 회원 조회
     */
    @Transactional(readOnly = true)
    public List<MemberResponseDto> getMembers(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return memberRepository.findAllByDeletedAtNull(pageRequest)
                .stream().map(MemberResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 개별 회원 조회(id)
     */
    @Transactional(readOnly = true)
    public MemberResponseDto getMemberById(Long id) {
        return MemberResponseDto.fromEntity(memberRepository.findByIdAndDeletedAtNull(id).orElseThrow(NoResourceFoundException::new));
    }

    /**
     * 개별 회원의 권한정보까지 조회(id)
     */
    @Transactional(readOnly = true)
    public AdminMemberResponseDto getMemberByIdWithRole(Long id) {
        return AdminMemberResponseDto.fromEntity(memberRepository.findByIdWithAuthorities(id).orElseThrow(NoResourceFoundException::new));
    }

    /**
     * 회원가입
     */
    public Long directJoin(MemberCreateRequestDto dto) {

        log.info("directJoin called");
        String encoded = passwordEncoder.encode(dto.getPassword());
        Member member = dto.toEntity(encoded);

        log.info("Email validation");
        validateEmailDuplication(member.getEmail());

        log.info("Member persisted");
        Long memberId = memberRepository.save(member).getId();

        log.info("Member authorities persisted");
        memberAuthorityRepository.save(MemberAuthority.addAuthority(member, authorityRepository.findByAuthorityNameAndDeletedAtNull("ROLE_USER").orElse(null)));

        log.info("HealthProfile persisted");
        healthProfileService.createHealthProfile(member);

        return memberId;
    }

    /**
     * 기본정보 수정(patch)
     */
    public MemberResponseDto defaultInfoUpdate(Long memberId, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findByIdAndDeletedAtNull(memberId)
                .orElseThrow(NoResourceFoundException::new);
        if (requestDto.getName() != null) {
            member.updateName(requestDto.getName());
        }
        if (requestDto.getEmail() != null) {
            validateEmailDuplication(requestDto.getEmail());
            member.updateEmail(requestDto.getEmail());
        }
        return MemberResponseDto.fromEntity(member);
    }


    /**
     * 회원 탈퇴
     */
    // TODO: 측정정보는 어떻게 할것인지 결정하기
    public Long resign(Long id) {
            HealthProfile healthProfile = healthProfileRepository.findByIdAndDeletedAtNull(id)
                    .orElseThrow(NoResourceFoundException::new); // HealthProfile과 Member 함께 불러옴(EntityGraph 사용)

            healthProfile.getMember().resign(); //회원정보
            healthProfile.deleteHealthProfile(); //건강정보
            memberAuthorityRepository.findAllByMemberIdAndDeletedAtNull(id).forEach(MemberAuthority::delete); // 권한정보
            memberHospitalRepository.findByMemberIdAndDeletedAtNull(id).forEach(MemberHospital::unJoin); // 병원연결정보

            return healthProfile.getMember().getId();
    }

    /**
     * 이메일 중복 검증
     */
    private void validateEmailDuplication(String email) throws DuplicateKeyException {
        memberRepository.findByEmailAndDeletedAtNull(email)
                .ifPresent(m -> { throw new DuplicateKeyException("RESOURCE_DUPLICATION"); });
    }

}
