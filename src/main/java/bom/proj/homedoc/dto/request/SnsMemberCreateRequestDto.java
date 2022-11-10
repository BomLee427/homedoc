package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.EnumNullCheck;
import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.OauthType;
import bom.proj.homedoc.validation.EnumValue;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

import static bom.proj.homedoc.domain.EnumNullCheck.valueOfOrNull;

@Getter
public class SnsMemberCreateRequestDto {

    @NotBlank(message = "OAUTH_TYPE_IS_MANDATORY")
    @EnumValue(enumClass = OauthType.class)
    private String oauthType;
    @NotBlank(message = "OAUTH_ID_IS_MANDATORY")
    private String oauthId;

    public Member toEntity() {
        return Member.createSnsMember(valueOfOrNull(OauthType.class, oauthType), oauthId);
    }
}
