package hose.boardrestapi.controller;

import hose.boardrestapi.dto.UserDTO;
import hose.boardrestapi.entity.User;
import hose.boardrestapi.service.UserService;
import hose.boardrestapi.util.response.sucess.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.createUser(userDTO);

        return SuccessResponse.success(UserDTO.builder()
                .id(user.getId())
                .build());
    }
}
