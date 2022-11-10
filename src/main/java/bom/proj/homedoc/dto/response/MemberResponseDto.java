package bom.proj.homedoc.dto.response;

import bom.proj.homedoc.domain.JoinType;
import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.OauthType;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long memberId;
    private String name;
    private String email;
    private JoinType joinType;
    private OauthType oauthType;

    public static MemberResponseDto fromEntity (Member member) {
        MemberResponseDto dto = new MemberResponseDto();
        dto.memberId = member.getId();
        dto.name = member.getName();
        dto.email = member.getEmail();
        dto.joinType = member.getJoinType();
        dto.oauthType = member.getOauthType();

        return dto;
    }
}
