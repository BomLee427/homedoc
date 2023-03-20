package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.authority.CustomUser;
import bom.proj.homedoc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.trace("loadUserByUsername called");
        return memberRepository.findByEmailWithAuthorities(email)
                .map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException("이메일이 \"" + email + "\"인 회원을 찾을 수 없습니다."));
    }

    //TODO: UserDetails 객체를 implement해서 Member객체를 만드는 방법과 아래처럼 따로 만드는 방법 선택의 기준 찾아보기
    private CustomUser createUser(Member member) {
        log.trace("createUser called");
        List<GrantedAuthority> grantedAuthorities = member.getMemberAuthorities()
                .stream().map(authority -> new SimpleGrantedAuthority(authority.toAuthority().getAuthorityName()))
                .collect(Collectors.toList());
        return new CustomUser(member.getEmail(), member.getPassword(), grantedAuthorities, member.getId());
    }
}


