package hose.boardrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hose.boardrestapi.dto.SignupDTO;
import hose.boardrestapi.dto.post.PostDTO;
import hose.boardrestapi.entity.post.PostCategory;
import hose.boardrestapi.repository.post.PostCategoryRepository;
import hose.boardrestapi.service.PostService;
import hose.boardrestapi.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostControllerTest {
    private static final String userEmail = "user3@user.com";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    private static Cookie cookie;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @BeforeAll
    static void initCookie() {
        cookie = new Cookie("cookie", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyM0B1c2VyLmNvbSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2MjA2OTY3ODB9.IgZ2YqPol36KlbOEMQeKdH1JDPMXidhgxpn6Xu0DMBEKNJWQi0yV7CwCVUocA8ZBJApq4gqKY-BcPPkkSDRd1w");
    }

    public PostDTO initPostDTO() {
        return PostDTO.builder()
                .title("title")
                .contents("contents")
                .category("free")
                .build();
    }

    void initPostCategory() {
        PostCategory build = PostCategory.builder()
                .name("free")
                .value("??????")
                .build();

        postCategoryRepository.save(build);
    }

    @Test
    @DisplayName("????????? ?????? ?????????")
    @Order(1)
    public void createPost() throws Exception {
        // given
        initPostCategory();
        PostDTO postDTO = initPostDTO();

        userService.createUser(SignupDTO.builder()
                .email(userEmail)
                .password("1234")
                .nickname("nickname")
                .build());


        //when
        ResultActions resultActions = mockMvc.perform(post("/post")
                .cookie(cookie)
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
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("????????? ???????????? ????????? - ??????")
    @Order(2)
    @Transactional
    public void getPostSuccess() throws Exception {
        // given
        PostDTO postDTO = initPostDTO();
        postService.createPost(postDTO, userEmail);

        // when
        ResultActions perform = mockMvc.perform(get("/post/{id}", 1).cookie(cookie));

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
    @DisplayName("?????????????????? ????????? ????????????")
    @Order(3)
    public void getPostFail() throws Exception {
        mockMvc.perform(get("/post/{id}", 9999999).cookie(cookie))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("not_found"))
                .andExpect(jsonPath("errors[0]").exists())
                .andExpect(jsonPath("errors[0].field").doesNotExist())
                .andExpect(jsonPath("errors[0].message").exists())
                .andExpect(jsonPath("errors[0].message").value("?????? ???????????? ???????????? ????????????."))
                .andDo(print());
    }

    @Test
    public void ?????????_??????_????????????() throws Exception {
        // given
        PostDTO postDTO = PostDTO.builder()
                .contents("contents")
                .category("free")
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/post")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)));

        //then
        resultActions
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("errors[*].field", containsInAnyOrder("title")))
                .andExpect(jsonPath("errors[*].message", containsInAnyOrder("????????? ??????????????????.")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_????????????() throws Exception {
        // given
        PostDTO postDTO = PostDTO.builder()
                .title("title")
                .category("free")
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/post")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)));

        //then
        resultActions
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("errors[*].field", containsInAnyOrder("contents")))
                .andExpect(jsonPath("errors[*].message", containsInAnyOrder("????????? ??????????????????.")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_??????_????????????_????????????() throws Exception {
        // given
        PostDTO postDTO = PostDTO.builder()
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/post")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)));

        //then
        resultActions
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("errors").hasJsonPath())
                .andExpect(jsonPath("errors", hasSize(3)))
                .andExpect(jsonPath("errors[*].field", containsInAnyOrder("title", "contents", "category")))
                .andExpect(jsonPath("errors[*].message", containsInAnyOrder("????????? ??????????????????.", "????????? ??????????????????.", "??????????????? ??????????????????.")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_??????() throws Exception {
        // given
        Long targetPostId = 1L;
        PostDTO post = postService.getPost(targetPostId);

        PostDTO postDTO = PostDTO.builder()
                .title("title update")
                .contents(post.getContents())
                .category("free")
                .build();

        // when
        ResultActions perform = mockMvc.perform(put("/post/{id}", targetPostId)
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)));

        // then
        perform
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("success"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void ?????????_??????_??????() throws Exception {
        // given
        Long targetPostId = 1L;
        PostDTO post = postService.getPost(targetPostId);

        PostDTO postDTO = PostDTO.builder()
                .title(post.getTitle())
                .contents("contents update")
                .category("free")
                .build();


        // when
        ResultActions perform = mockMvc.perform(put("/post/{id}", targetPostId)
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)));

        // then
        perform
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("success"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_???_??????() throws Exception {
        // given
        Long targetPostId = 1L;
        PostDTO post = postService.getPost(targetPostId);

        PostDTO postDTO = PostDTO.builder()
                .title(post.getTitle())
                .title("title update")
                .contents("contents update")
                .category("free")
                .build();


        // when
        ResultActions perform = mockMvc.perform(put("/post/{id}", targetPostId)
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)));

        // then
        perform
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("success"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void ?????????_??????() throws Exception {
        // given
        Long targetPostId = 1L;

        // when
        ResultActions perform = mockMvc.perform(delete("/post/{id}", targetPostId).cookie(cookie));

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("success"))
                .andDo(print());
    }

}