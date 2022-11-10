package bom.proj.homedoc.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Getter
public class ErrorResponse extends RestResponse {
    private String debugMessage;
}
