package bom.proj.homedoc.controller;

import bom.proj.homedoc.dto.request.SigninRequestDto;
import bom.proj.homedoc.dto.request.TokenRefreshRequestDto;
import bom.proj.homedoc.dto.response.CommonResponse;
import bom.proj.homedoc.dto.response.AuthorizeResponseDto;
import bom.proj.homedoc.jwt.TokenDto;
import bom.proj.homedoc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<CommonResponse<AuthorizeResponseDto>> signInV1(
            @Validated @RequestBody SigninRequestDto dto
    ) {
        TokenDto tokenDto = authService.getToken(dto);
        //TODO: 헤더 세팅 필요한지 확인
        return ResponseEntity.ok(CommonResponse.getResponse(AuthorizeResponseDto.builder()
                            .accessToken(tokenDto.getAccessToken())
                            .refreshToken(tokenDto.getRefreshToken()).build()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponse<AuthorizeResponseDto>> tokenRefreshV1(
            @Validated @RequestBody TokenRefreshRequestDto dto
    ) {
        // authService에 refresh 로직 위임
        TokenDto tokenDto = authService.tokenRefresh(dto.getRefreshToken());

        //TODO: 헤더 세팅 필요한지 확인
        return ResponseEntity.ok(CommonResponse.getResponse(AuthorizeResponseDto.builder()
                                .accessToken(tokenDto.getAccessToken())
                                .refreshToken(tokenDto.getRefreshToken()).build()));
    }
}
