package bom.proj.homedoc.controller.admin;

import bom.proj.homedoc.dto.request.AuthorityUpdateRequestDto;
import bom.proj.homedoc.dto.response.CommonResponseDto;
import bom.proj.homedoc.dto.response.MemberAuthorityResponseDto;
import bom.proj.homedoc.service.MemberAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/memberAuthority")
@PreAuthorize("hasAnyRole('SUPER')")
public class MemberAuthorityController {

    private final MemberAuthorityService memberAuthorityService;

    /**
     * 권한 추가
     */
    @PostMapping("/{memberId}")
    public ResponseEntity<CommonResponseDto<MemberAuthorityResponseDto>> addAuthority(
            @Validated @RequestBody AuthorityUpdateRequestDto dto,
            @PathVariable Long memberId) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(memberAuthorityService.addAuthority(memberId, dto.getAuthorityList())));
    }

    /**
     * 권한 삭제
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<CommonResponseDto<MemberAuthorityResponseDto>> removeAuthority(
            @Validated @RequestBody AuthorityUpdateRequestDto dto,
            @PathVariable Long memberId) {
        return ResponseEntity.ok(CommonResponseDto.getResponse(memberAuthorityService.removeAuthority(memberId, dto.getAuthorityList())));
    }
}
