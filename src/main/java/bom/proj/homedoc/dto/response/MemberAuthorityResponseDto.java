package bom.proj.homedoc.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class MemberAuthorityResponseDto {
    private Long memberId;
    private List<String> authorities;

    public static MemberAuthorityResponseDto createDto(Long memberId, List<String> authorities) {
        MemberAuthorityResponseDto dto = new MemberAuthorityResponseDto();
        dto.memberId = memberId;
        dto.authorities = authorities;
        return dto;
    }
}
