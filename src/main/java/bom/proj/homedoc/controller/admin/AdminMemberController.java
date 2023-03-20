package bom.proj.homedoc.controller.admin;

import bom.proj.homedoc.dto.request.MemberUpdateRequestDto;
import bom.proj.homedoc.dto.response.AdminMemberResponseDto;
import bom.proj.homedoc.dto.response.CommonResponseDto;
import bom.proj.homedoc.dto.response.MemberResponseDto;
import bom.proj.homedoc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/member")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminMemberController {

    private final MemberService memberService;

    /**
     * 회원 전체 조회(페이징)
     */
    @GetMapping("")
    public ResponseEntity<CommonResponseDto<List<MemberResponseDto>>> getMembersV1(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int size
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(memberService.getMembers(page, size)));

    }

    /**
     * 특정 회원 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDto<AdminMemberResponseDto>> getMemberV1(@PathVariable Long id) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(memberService.getMemberByIdWithRole(id)));
    }

    /**
     * 특정 회원 정보 수정
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponseDto<MemberResponseDto>> updateMemberInfoV1(
            @PathVariable Long id,
            @Validated @RequestBody final MemberUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(memberService.defaultInfoUpdate(id, dto)));
    }

    /**
     * 특정 회원 탈퇴
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponseDto<Map<String, Long>>> deleteMemberInfoV1(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(Map.of("id", memberService.resign(id))));
    }

}
