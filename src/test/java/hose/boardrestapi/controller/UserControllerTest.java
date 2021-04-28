package hose.boardrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hose.boardrestapi.dto.UserDTO;
import hose.boardrestapi.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    public UserDTO initUserDTO() {
        return UserDTO.builder()
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .build();
    }

    @Test
    public void 유저_생성() throws Exception {
        // given
        UserDTO userDTO = initUserDTO();

        // when
        ResultActions perform = mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        // then
        perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("success"))
                .andExpect(jsonPath("data").hasJsonPath())
                .andExpect(jsonPath("data.id").exists())
                .andDo(print());
    }

    @Test
    public void 유저_생성_이메일_양식아님() throws Exception {
        // given
        UserDTO userDTO = initUserDTO();
        userDTO.setEmail("email");

        // when
        ResultActions perform = mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        // then
        perform
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("bad_request"))
                .andExpect(jsonPath("errors").hasJsonPath())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[*].field", containsInAnyOrder("email")))
                .andExpect(jsonPath("errors[*].message", containsInAnyOrder("이메일 양식이 아닙니다.")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void 유저_생성_이메일_없음() throws Exception {
        // given
        UserDTO userDTO = initUserDTO();
        userDTO.setEmail("");

        // when
        ResultActions perform = mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        // then
        perform
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("bad_request"))
                .andExpect(jsonPath("errors").hasJsonPath())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[*].field", containsInAnyOrder("email")))
                .andExpect(jsonPath("errors[*].message", containsInAnyOrder("이메일은 필수값 입니다.")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void 유저_생성_패스워드_없음() throws Exception {
        // given
        UserDTO userDTO = initUserDTO();
        userDTO.setPassword("");

        // when
        ResultActions perform = mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        // then
        perform
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("bad_request"))
                .andExpect(jsonPath("errors").hasJsonPath())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[*].field", containsInAnyOrder("password")))
                .andExpect(jsonPath("errors[*].message", containsInAnyOrder("비밀번호는 필수값 입니다.")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void 유저_생성_닉네임_없음() throws Exception {
        // given
        UserDTO userDTO = initUserDTO();
        userDTO.setNickname("");

        // when
        ResultActions perform = mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        // then
        perform
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("bad_request"))
                .andExpect(jsonPath("errors").hasJsonPath())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[*].field", containsInAnyOrder("nickname")))
                .andExpect(jsonPath("errors[*].message", containsInAnyOrder("닉네임은 필수값 입니다.")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}