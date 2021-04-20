package hose.boardrestapi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("게시물 가져오기 테스트")
    public void getPost() throws Exception {

        String hello = "hello";

        mockMvc.perform(MockMvcRequestBuilders.get("/post/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

}