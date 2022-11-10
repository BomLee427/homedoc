package bom.proj.homedoc.controller;

import bom.proj.homedoc.dto.request.SnsUpdateRequestDto;
import bom.proj.homedoc.dto.request.DirectMemberCreateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponse;
import bom.proj.homedoc.dto.response.MemberResponseDto;
import bom.proj.homedoc.dto.request.MemberUpdateRequestDto;
import bom.proj.homedoc.dto.request.SnsMemberCreateRequestDto;
import bom.proj.homedoc.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 전체 조회(페이징)
     */
    @GetMapping("")
    public CommonResponse<List<MemberResponseDto>> getMembersV1(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int size
    ) {
        return CommonResponse.getResponse(memberService.getMembers(page, size));

    }

    /**
     * 특정 회원 조회
     */
    @GetMapping("/{id}")
    public CommonResponse<MemberResponseDto> getMemberV1(@PathVariable Long id) {
        log.info("controller");
        return CommonResponse.getResponse(memberService.getMemberById(id));
    }

    /**
     * 이메일 회원가입
     * TODO: 매개변수에서 BindingResult 빼자마자 예외가 잘 터진다...왜인지 알아볼것
     */
    @PostMapping("/direct")
    public CommonResponse<Map<String, Long>> directJoinV1(
            @Validated @RequestBody final DirectMemberCreateRequestDto dto
    ) {
        return CommonResponse.getResponse(Map.of("id", memberService.join(dto.toEntity())));
    }

    /**
     * SNS 회원가입
     */
    @PostMapping("/sns")
    public CommonResponse<Map<String, Long>> snsJoinV1(
            @Validated @RequestBody final SnsMemberCreateRequestDto dto) {
        return CommonResponse.getResponse(Map.of("id", memberService.join(dto.toEntity())));
    }

    @PatchMapping("/{id}")
    public CommonResponse<MemberResponseDto> updateMemberInfoV1(
            @PathVariable Long id,
            @Validated @RequestBody final MemberUpdateRequestDto dto
    ) {
        return CommonResponse.getResponse(memberService.defaultInfoUpdate(id, dto));
    }

    @PatchMapping("/sns/{id}")
    public CommonResponse<MemberResponseDto> updateSnsV1(
            @PathVariable Long id,
            @Validated @RequestBody final SnsUpdateRequestDto dto
    ) {
        return CommonResponse.getResponse(memberService.snsUpdate(id, dto));
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Map<String, Long>> resignV1(@PathVariable Long id) {
        return CommonResponse.getResponse(Map.of("id", memberService.resign(id)));
    }
}
