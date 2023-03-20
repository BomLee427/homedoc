package bom.proj.homedoc.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResponseDto<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();
    private String message;
    private T data;

    public static <T> CommonResponseDto<T> getResponse() {
        CommonResponseDto<T> response = new CommonResponseDto<>();
        response.message = "SUCCESS";
        return response;
    }

    public static <T> CommonResponseDto<T> getResponse(T data) {
        CommonResponseDto<T> response = new CommonResponseDto<>();
        response.message = "SUCCESS";
        response.data = data;
        return response;
    }

    public static <T> CommonResponseDto<T> getResponse(String message, T data) {
        CommonResponseDto<T> response = new CommonResponseDto<>();
        response.message = message;
        response.data = data;
        return response;
    }
}
