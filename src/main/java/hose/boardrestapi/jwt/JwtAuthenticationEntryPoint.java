package hose.boardrestapi.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hose.boardrestapi.util.response.error.CustomError;
import hose.boardrestapi.util.response.error.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("unauthorized")
                    .build();

            errorResponse.addError(CustomError.builder()
                    .message("로그인이 필요한 서비스입니다.")
                    .build());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, errorResponse);
            outputStream.flush();
        }
    }
}