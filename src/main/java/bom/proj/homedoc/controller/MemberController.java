package bom.proj.homedoc.controller;

import bom.proj.homedoc.dto.request.MemberCreateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponse;
import bom.proj.homedoc.dto.response.MemberResponseDto;
import bom.proj.homedoc.dto.request.MemberUpdateRequestDto;
import bom.proj.homedoc.exception.NotAuthenticatedException;
import bom.proj.homedoc.service.MemberService;
import bom.proj.homedoc.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MemberController {

    private final MemberService memberService;

    /**
     * 이메일 회원가입
     * TODO: 매개변수에서 BindingResult 빼자마자 예외가 잘 터진다...왜인지 알아볼것
     */
    @PostMapping("/member")
    public CommonResponse<Map<String, Long>> directJoinV1(
            @Valid @RequestBody final MemberCreateRequestDto dto
    ) {
        return CommonResponse.<Map<String, Long>>builder()
                .data(Map.of("id", memberService.directJoin(dto)))
                .build();
    }

    /**
     * 내 정보 조회
     */
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/myinfo")
    public CommonResponse<MemberResponseDto> getMyInfoV1() {
        Long memberId = SecurityUtil.getCurrentUserPK().orElseThrow(NotAuthenticatedException::new);
        return CommonResponse.<MemberResponseDto>builder()
                .data(memberService.getMemberById(memberId))
                .build();
    }

    /**
     * 내 정보 수정
     */
    @PreAuthorize("hasAnyRole('USER')")
    @PatchMapping("/myinfo")
    public CommonResponse<MemberResponseDto> updateMyInfoV1(@Valid @RequestBody final MemberUpdateRequestDto dto) {
        Long memberId = SecurityUtil.getCurrentUserPK().orElseThrow(NotAuthenticatedException::new);
        return CommonResponse.<MemberResponseDto>builder()
                .data(memberService.defaultInfoUpdate(memberId, dto))
                .build();
    }

    /**
     * 회원 탈퇴
     */
    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/member")
    public CommonResponse<Map<String, Long>> resignV1() {
        Long memberId = SecurityUtil.getCurrentUserPK().orElseThrow(NotAuthenticatedException::new);
        return CommonResponse.<Map<String, Long>>builder()
                .data(Map.of("id", memberService.resign(memberId)))
                .build();
    }
}
