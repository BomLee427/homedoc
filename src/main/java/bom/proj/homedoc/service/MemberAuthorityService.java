package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.authority.Authority;
import bom.proj.homedoc.domain.authority.MemberAuthority;
import bom.proj.homedoc.dto.response.MemberAuthorityResponseDto;
import bom.proj.homedoc.exception.NoResourceFoundException;
import bom.proj.homedoc.repository.AuthorityRepository;
import bom.proj.homedoc.repository.MemberAuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberAuthorityService {

    private final AuthorityRepository authorityRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;

    /**
     * 권한 추가
     */
    public MemberAuthorityResponseDto addAuthority(Long memberId, List<String> authorityStrings) {
        // 멤버 구하기 위해 MemberAuthority 먼저 쿼리
        List<MemberAuthority> memberAuthorities = memberAuthorityRepository.findAllWithMemberByMemberId(memberId);
        Member member = memberAuthorities.get(0).getMember();

        // 권한 엔티티 생성
        List<Authority> newAuthorities = authorityRepository.findByAuthorityNameIn(authorityStrings);
        if (newAuthorities.size() < 1) {
            throw new NoResourceFoundException();
        }

        // 중복 제거
        newAuthorities = newAuthorities.stream().filter(a ->
                !(memberAuthorities.stream().map(ma -> ma.getAuthority()).collect(Collectors.toList()).contains(a))
        ).collect(Collectors.toList());

        // 엔티티 생성
        List<MemberAuthority> newMemberAuthorities = newAuthorities.stream().map(a -> MemberAuthority.addAuthority(member, a)).collect(Collectors.toList());
        memberAuthorityRepository.saveAll(newMemberAuthorities);

        return MemberAuthorityResponseDto.createDto(
                memberId, newMemberAuthorities.stream().map(a -> a.getAuthority().getAuthorityName()).collect(Collectors.toList())
        );
    }

    /**
     * 권한 삭제
     */
    public MemberAuthorityResponseDto removeAuthority(Long memberId, List<String> authorityStrings) {
        // 해당 멤버의 멤버권한 엔티티 가져오기
        List<MemberAuthority> memberAuthorities = memberAuthorityRepository.findAllByMemberIdAndDeletedAtNull(memberId);

        // 권한 문자열을 엔티티화
        List<Authority> removeAuthorities = authorityRepository.findByAuthorityNameIn(authorityStrings);
        if (removeAuthorities.size() < 1) {
            throw new NoResourceFoundException();
        }

        // 멤버권한 엔티티의 권한과 일치하는 권한만 삭제 처리
        memberAuthorities.stream()
                .filter(a -> removeAuthorities.contains(a.getAuthority())).forEach(a -> a.delete());

        //
        return MemberAuthorityResponseDto.createDto(
                memberId, memberAuthorities.stream().map(a -> a.getAuthority().getAuthorityName()).collect(Collectors.toList())
        );
    }
}
