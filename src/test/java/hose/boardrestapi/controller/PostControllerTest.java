package hose.boardrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hose.boardrestapi.dto.PostDTO;
import hose.boardrestapi.service.PostService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    public PostDTO initPostDTO() {
        return PostDTO.builder()
                .title("title")
                .contents("contents")
                .build();
    }

    @Test
    @DisplayName("게시물 등록 테스트")
    @Order(1)
    public void createPost() throws Exception {
        // given
        PostDTO postDTO = initPostDTO();

        //when
        ResultActions resultActions = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)));

        //then
        resultActions
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data").hasJsonPath())
                .andExpect(jsonPath("data.id").exists())
                .andExpect(jsonPath("message").value("success"))
                .andExpect(jsonPath("data.id").value(1))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 가져오기 테스트 - 성공")
    @Order(2)
    public void getPostSuccess() throws Exception {
        // given
        PostDTO postDTO = initPostDTO();
        postService.createPost(postDTO);

        // when
        ResultActions perform = mockMvc.perform(get("/post/{id}", 1));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("success"))
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.id").exists())
                .andExpect(jsonPath("data.title").exists())
                .andExpect(jsonPath("data.title").value("title"))
                .andExpect(jsonPath("data.contents").exists())
                .andExpect(jsonPath("data.contents").value("contents"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 가져오기 테스트 - 실패")
    @Order(3)
    public void getPostFail() throws Exception {
        mockMvc.perform(get("/post/{id}", 9999999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("해당 포스트가 존재하지 않습니다."))
                .andDo(print());
    }
}