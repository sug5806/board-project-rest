package hose.boardrestapi.controller;

import hose.boardrestapi.util.response.sucess.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<String> main() {
        return SuccessResponse.success(null);
    }
}
