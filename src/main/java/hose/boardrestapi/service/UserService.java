package hose.boardrestapi.service;

import hose.boardrestapi.dto.SignupDTO;
import hose.boardrestapi.entity.User;
import hose.boardrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public User createUser(SignupDTO signupDTO) {
        User user = User.builder()
                .email(signupDTO.getEmail())
                .nickname(signupDTO.getNickname())
                .createdBy(LocalDateTime.now())
                .enabled(true)
                .build();

        user.encryptPassword(signupDTO.getPassword());

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);

        User user = byEmail.orElseThrow(() -> new UsernameNotFoundException("아이디나 비밀번호가 틀립니다."));

        log.debug(String.valueOf(user.isEnabled()));

        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .authority(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }
}
