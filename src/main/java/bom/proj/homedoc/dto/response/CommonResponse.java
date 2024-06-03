package bom.proj.homedoc.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommonResponse<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();
    private String message;
    private T data;

    @Deprecated
    public CommonResponse() { }

    @Builder
    public CommonResponse(String message, T data) {
        this.message = message == null ? "SUCCESS" : message;
        this.data = data;
    }

    @Deprecated
    public static <T> CommonResponse<T> getResponse() {
        CommonResponse<T> response = new CommonResponse<>();
        response.message = "SUCCESS";
        return response;
    }

    @Deprecated
    public static <T> CommonResponse<T> getResponse(T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.message = "SUCCESS";
        response.data = data;
        return response;
    }

    @Deprecated
    public static <T> CommonResponse<T> getResponse(String message, T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.message = message;
        response.data = data;
        return response;
    }

}
