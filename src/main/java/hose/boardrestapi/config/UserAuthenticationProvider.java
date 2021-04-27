package hose.boardrestapi.config;

import hose.boardrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = userService.loadUserByUsername(email);

        if (!checkPassword(password, userDetails.getPassword()) || !userDetails.isEnabled()) {
            throw new BadCredentialsException("아이디나 비밀번호를 확인해주세요.");
        }

        if (userDetails == null) {
            throw new UsernameNotFoundException("계정이 존재하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean checkPassword(String loginPassword, String dbPassword) {
        return BCrypt.checkpw(loginPassword, dbPassword);
    }
}
