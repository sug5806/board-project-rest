package hose.boardrestapi.util.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String message;
    @Builder.Default
    private List<CustomError> errors = new ArrayList<>();

    public List<CustomError> addError(CustomError error) {
        this.errors.add(error);
        return this.errors;
    }
}
