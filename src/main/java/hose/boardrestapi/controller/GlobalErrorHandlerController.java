package hose.boardrestapi.controller;

import hose.boardrestapi.common.custom_exception.PostNotFound;
import hose.boardrestapi.util.ResponseUtil;
import hose.boardrestapi.util.error.CustomError;
import hose.boardrestapi.util.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandlerController {

    @ExceptionHandler(PostNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseUtil> postNotFound(PostNotFound postNotFound) {

        return ResponseUtil.fail(postNotFound.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValid(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("bad_request")
                .build();

        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();

        for (ObjectError error : allErrors) {
            CustomError customError = CustomError.builder()
                    .field(((FieldError) error).getField())
                    .message(error.getDefaultMessage())
                    .build();
            errorResponse.addError(customError);
        }

        return errorResponse;
    }
}
