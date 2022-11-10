package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.JoinType;
import bom.proj.homedoc.domain.OauthType;
import bom.proj.homedoc.dto.request.SnsUpdateRequestDto;
import bom.proj.homedoc.dto.request.MemberUpdateRequestDto;
import bom.proj.homedoc.dto.response.MemberResponseDto;
import bom.proj.homedoc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.stream.Collectors;

import static bom.proj.homedoc.domain.BaseAuditingEntity.filterSoftDeleted;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService extends CommonService {

    private final MemberRepository memberRepository;

    /**
     * 전체 회원 조회
     */
    public List<MemberResponseDto> getMembers(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return memberRepository.findAllByDeletedAtNull(pageRequest)
                .stream().map(MemberResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 개별 회원 조회
     */
    public MemberResponseDto getMemberById(Long id) {
            Member member = filterSoftDeleted(memberRepository.findById(id).orElse(null));
            return MemberResponseDto.fromEntity(member);
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
            if (member.getJoinType() == JoinType.DIRECT) {
                validateEmailDuplication(member.getEmail());
            } else {
                validateOauthDuplication(member.getOauthType(), member.getOauthId());
            }
            return memberRepository.save(member).getId();
    }

    /**
     * 기본정보 수정
     */
    public MemberResponseDto defaultInfoUpdate(Long id, MemberUpdateRequestDto requestDto) {
        Member member = filterSoftDeleted(memberRepository.findById(id).orElse(null));
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
     * SNS 회원가입 정보 수정
     */
    public MemberResponseDto snsUpdate(Long id, SnsUpdateRequestDto dto) {
        validateOauthDuplication(OauthType.valueOf(dto.getOauthType()), dto.getOauthId());
        Member member = filterSoftDeleted(memberRepository.findById(id).orElse(null));
        member.updateOauth(OauthType.valueOf(dto.getOauthType()), dto.getOauthId());
        return MemberResponseDto.fromEntity(member);
    }

    /**
     * 회원 탈퇴
     */
    public Long resign(Long id) {
            Member member = filterSoftDeleted(memberRepository.findById(id).orElse(null));
            member.resign();
            return member.getId();
    }

    /**
     * 이메일 중복 검증
     */
    private void validateEmailDuplication(String email) throws DuplicateKeyException {
        Member findMember = memberRepository.findByEmail(email).orElse(null);
        if (findMember != null && !findMember.isSoftDeleted()) {
            throw new DuplicateKeyException("RESOURCE_DUPLICATION");
        }
    }

    /**
     * Oauth 중복 검증
     */
    private void validateOauthDuplication(OauthType oauthType, String oauthId) throws DuplicateKeyException {
        Member findMember = memberRepository.findByOauthTypeAndOauthId(oauthType, oauthId)
                .orElse(null);
        if (findMember != null && !findMember.isSoftDeleted()) {
            throw new DuplicateKeyException("RESOURCE_DUPLICATION");
        }
    }

}
