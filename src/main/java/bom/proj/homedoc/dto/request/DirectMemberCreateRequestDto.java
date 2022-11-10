package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.Member;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
public class DirectMemberCreateRequestDto {
    @Email(message = "NOT_VALID_EMAIL")
    @NotBlank(message = "EMAIL_IS_MANDATORY")
    private String email;

    public Member toEntity() {
        return Member.createDirectMember(this.email);
    }
}
