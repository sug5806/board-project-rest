package hose.boardrestapi.service;

import hose.boardrestapi.dto.UserDTO;
import hose.boardrestapi.entity.User;
import hose.boardrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .nickname(userDTO.getNickname())
                .createdBy(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }
}
