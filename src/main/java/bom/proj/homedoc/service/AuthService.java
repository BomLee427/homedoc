package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.dto.request.SigninRequestDto;
import bom.proj.homedoc.exception.NoResourceFoundException;
import bom.proj.homedoc.jwt.TokenDto;
import bom.proj.homedoc.jwt.TokenProvider;
import bom.proj.homedoc.repository.MemberRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;

    public TokenDto getToken(SigninRequestDto dto) {

        log.trace("getToken called");
        // AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // Token을 통해 인증된 Authentication 객체 획득
        log.trace("authenticationManagerBuilder called");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // TODO: 이 부분 정확하게 이해하기
        // Security context에 Authentication 객체 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 토큰 셋 발급
        log.trace("token set created");
        TokenDto tokenSet = createTokenSetByAuthentication(authentication);

        // member 엔티티 영속화
        log.trace("member entity persist");
        Member member = memberRepository.findByEmailAndDeletedAtNull(authentication.getName())
                .orElseThrow(NoResourceFoundException::new);

        // refresh token 업데이트
        log.trace("refreshToken updated");
        member.updateRefreshToken(tokenSet.getRefreshToken());

        return tokenSet;
    }

    //TODO: 예외처리 형태...이게 제일 예쁜건지 고민해보기
    public TokenDto tokenRefresh(String refreshToken) throws JwtException {
        // 토큰의 유효성 체크
        if (!tokenProvider.validateToken(refreshToken)) {
            log.warn("Invalid refresh token excepted");
            throw new JwtException("INVALID_TOKEN");
            //TODO: 추가 보안처리 필요한 부분인지 생각
        }

        //MEMO: Spring Security의 loadUserByName 과정 중 member select 쿼리를 한번 날리게 되는데 이때 Member 엔티티를 컨텍스트화할 방법이 있을까?
        //MEMO: 그렇지 않으면 아래 두 줄에서 쿼리를 무조건 두 번 해야함. 로그인시에도 refresh 토큰 업데이트를 위해 select 후 update해야 하고
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        Member member = memberRepository.findByEmailAndDeletedAtNull(authentication.getName())
                .orElseThrow(NoResourceFoundException::new);

        if (!refreshToken.equals(member.getRefreshToken())) {
            log.warn("Not matched refresh token");
            throw new JwtException("INVALID_TOKEN");
            //TODO: 추가 보안처리 필요한 부분인지 생각 (토큰 탈취 시도가 있었다거나 하는 상황을 가정)
        }

        // 토큰 셋 발급, 엔티티에 ref token 업데이트
        TokenDto tokenSet = createTokenSetByAuthentication(authentication);
        member.updateRefreshToken(tokenSet.getRefreshToken());

        return tokenSet;
    }

    private TokenDto createTokenSetByAuthentication(Authentication authentication) {
        return TokenDto.builder()
                .accessToken(tokenProvider.getAccessToken(authentication))
                .refreshToken(tokenProvider.getRefreshToken(authentication))
                .build();
    }
}
