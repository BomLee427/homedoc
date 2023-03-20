package bom.proj.homedoc.dto.response;

import bom.proj.homedoc.domain.Member;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AdminMemberResponseDto {
    private Long memberId;
    private String name;
    private String email;
    private List<String> authorities;
    public static AdminMemberResponseDto fromEntity (Member member) {
        AdminMemberResponseDto dto = new AdminMemberResponseDto();
        dto.memberId = member.getId();
        dto.name = member.getName();
        dto.email = member.getEmail();
        dto.authorities = member.getMemberAuthorities().stream().map(Object::toString).collect(Collectors.toList());

        return dto;
    }
}
