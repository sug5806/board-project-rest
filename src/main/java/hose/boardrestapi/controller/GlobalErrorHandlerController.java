package hose.boardrestapi.controller;

import hose.boardrestapi.common.custom_exception.PostNotFound;
import hose.boardrestapi.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandlerController {

    @ExceptionHandler(PostNotFound.class)
    public ResponseEntity<ResponseUtil> postNotFound(PostNotFound postNotFound) {
        return ResponseUtil.fail(postNotFound.getMessage(), HttpStatus.NOT_FOUND);
    }
}
