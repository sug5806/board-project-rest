package hose.boardrestapi.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hose.boardrestapi.util.response.error.CustomError;
import hose.boardrestapi.util.response.error.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //필요한 권한이 없이 접근하려 할때 403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("forbidden")
                    .build();

            errorResponse.addError(CustomError.builder()
                    .message("토큰이 유효하지 않습니다. 다시 로그인을 해주세요.")
                    .build());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, errorResponse);
            outputStream.flush();
        }
    }
}