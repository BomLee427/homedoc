package bom.proj.homedoc.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Getter
@ResponseStatus(HttpStatus.OK)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResponse<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();
    private String message;
    private T data;

    public static <T> CommonResponse<T> getResponse() {
        CommonResponse response = new CommonResponse();
        response.message = "SUCCESS";
        return response;
    }

    public static <T> CommonResponse<T> getResponse(T data) {
        CommonResponse response = new CommonResponse();
        response.message = "SUCCESS";
        response.data = data;
        return response;
    }

    public static <T> CommonResponse<T> getResponse(String message, T data) {
        CommonResponse response = new CommonResponse();
        response.message = message;
        response.data = data;
        return response;
    }
}
