package hose.boardrestapi.controller;

import hose.boardrestapi.dto.LoginDTO;
import hose.boardrestapi.dto.TokenDTO;
import hose.boardrestapi.dto.UserDTO;
import hose.boardrestapi.entity.User;
import hose.boardrestapi.jwt.TokenProvider;
import hose.boardrestapi.service.UserService;
import hose.boardrestapi.util.response.sucess.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "유저 관련 API")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.createUser(userDTO);

        return SuccessResponse.success(UserDTO.builder()
                .id(user.getId())
                .build());
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<TokenDTO>> login(@RequestBody @Valid LoginDTO loginDTO, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        Cookie myCookie = new Cookie("cookie", token);

        myCookie.setHttpOnly(true);
        myCookie.setMaxAge(300);
        response.addCookie(myCookie);

        HttpHeaders httpHeaders = new HttpHeaders();

        SuccessResponse<TokenDTO> successResponse = SuccessResponse.success(TokenDTO.builder().token(token).build());

        return new ResponseEntity<>(successResponse, httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "로그아웃")
    @GetMapping("/logout")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SuccessResponse<String>> logout() {
        HttpHeaders httpHeaders = new HttpHeaders();

        SuccessResponse<String> successResponse = SuccessResponse.success(null);

        return new ResponseEntity<>(successResponse, httpHeaders, HttpStatus.OK);
    }
}
