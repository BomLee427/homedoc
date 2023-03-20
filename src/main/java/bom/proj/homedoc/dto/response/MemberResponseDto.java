package bom.proj.homedoc.dto.response;

import bom.proj.homedoc.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long memberId;
    private String name;
    private String email;
    public static MemberResponseDto fromEntity (Member member) {
        MemberResponseDto dto = new MemberResponseDto();
        dto.memberId = member.getId();
        dto.name = member.getName();
        dto.email = member.getEmail();

        return dto;
    }
}
