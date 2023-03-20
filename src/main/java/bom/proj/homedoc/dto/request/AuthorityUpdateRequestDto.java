package bom.proj.homedoc.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class AuthorityUpdateRequestDto {

    @NotNull
    @Size(min = 1)
    List<String> authorityList;
}
