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

    //MEMO: Security Context는 애플리케이션 전역에서 참조가 가능한 컨텍스트라고 했는데, JPA 컨텍스트랑은 다르게 전역에서 이 메서드를 사용해도 괜찮다는 뜻일까?
    // -> 서비스단까지 인증 로직을 넘기게 되면 인증이 커버할 계층 범위가 넓어지고 단위테스트도 어려워질 것 같다. 컨트롤러 단에서 자를 것.
    // -> 대신 멤버아이디가 실제 엔티티의 PK와 같은지 검증하는 로직은 엔티티 단위까지 내려가야 하니까 서비스단으로.
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
