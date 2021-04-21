package hose.boardrestapi.util.error;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String message;
    private List<CustomError> errors = new ArrayList<>();

    public List<CustomError> addError(CustomError error) {
        this.errors.add(error);
        return this.errors;
    }
}
