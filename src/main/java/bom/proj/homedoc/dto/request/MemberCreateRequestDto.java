package bom.proj.homedoc.dto.request;

import bom.proj.homedoc.domain.Member;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class MemberCreateRequestDto {
    @Email(message = "NOT_VALID_EMAIL")
    @NotBlank(message = "EMAIL_IS_MANDATORY")
    private String email;

    @NotBlank(message = "PASSWORD_IS_MANDATORY")
    @Size(min = 3, max = 32)
    private String password;

    public Member toEntity(String encodedPassword) {
        return Member.createMember(this.email, encodedPassword);
    }
}
