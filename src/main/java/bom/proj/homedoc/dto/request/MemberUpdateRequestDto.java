package bom.proj.homedoc.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class MemberUpdateRequestDto {
    private String name;

    @Email
    private String email;
}
