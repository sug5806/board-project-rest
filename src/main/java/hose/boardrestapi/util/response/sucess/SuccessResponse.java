package hose.boardrestapi.util.response.sucess;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    private String message;
    private T data;

    public static <T> SuccessResponse success(T data) {

        SuccessResponse responseUtil = SuccessResponse.builder()
                .message("success")
                .data(data)
                .build();

        return responseUtil;

    }
}
