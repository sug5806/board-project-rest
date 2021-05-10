package hose.boardrestapi.service;

import hose.boardrestapi.dto.SignupDTO;
import hose.boardrestapi.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTotalTest {
    @Autowired
    private UserService userService;

    public SignupDTO initSignupDTO() {
        return SignupDTO.builder()
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .build();
    }

    @Test
    @Order(1)
    public void 유저_생성() throws Exception {
        //given
        SignupDTO signupDTO = initSignupDTO();

        // when
        User user = userService.createUser(signupDTO);

        // then
        assertThat(user.getEmail()).isEqualTo(signupDTO.getEmail());
        assertThat(user.getNickname()).isEqualTo(signupDTO.getNickname());
    }
}
