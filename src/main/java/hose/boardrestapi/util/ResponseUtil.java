package hose.boardrestapi.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUtil<T> {
    private String message;
    private T data;

    private static HttpHeaders initHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }

    public static ResponseEntity<ResponseUtil> fail(String message, HttpStatus httpStatus) {
        HttpHeaders headers = initHeader();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        ResponseUtil responseUtil = ResponseUtil.builder()
                .message(message)
                .build();

        return new ResponseEntity<>(responseUtil, headers, httpStatus);
    }

    public static <T> ResponseEntity<ResponseUtil> success(T data, HttpStatus httpStatus) {
        HttpHeaders headers = initHeader();

        ResponseUtil responseUtil = ResponseUtil.builder()
                .message("success")
                .data(data)
                .build();

        return new ResponseEntity<>(responseUtil, headers, httpStatus);

    }
}
