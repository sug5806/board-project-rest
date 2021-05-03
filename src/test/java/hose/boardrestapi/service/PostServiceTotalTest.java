package hose.boardrestapi.service;

import hose.boardrestapi.dto.UserDTO;
import hose.boardrestapi.dto.post.PostDTO;
import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.entity.post.PostCategory;
import hose.boardrestapi.repository.post.PostCategoryRepository;
import hose.boardrestapi.repository.post.PostRepository;
import hose.boardrestapi.util.custom_exception.PostNotFound;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostServiceTotalTest {
    private static final String userEmail = "user@user.com";

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;
    private static Cookie cookie;

    @Autowired
    private PostCategoryRepository postCategoryRepository;
    @Autowired
    private UserService userService;

    @BeforeAll
    static void initCookie() {
        cookie = new Cookie("cookie", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQHVzZXIuY29tIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTYxOTY3OTcxM30.RQfAsaGr1cq2c6PWBSKCy1y9TbcDg3wl9SGakRRNzrb5wP4O00Fd8Y-rQ5wz3LjIEd8iMcrjR_mws_DN8HARbw");
    }

    void initPostCategory() {
        PostCategory build = PostCategory.builder()
                .name("free")
                .value("자유")
                .build();

        postCategoryRepository.save(build);
    }

    public PostDTO initPostDTO() {
        return PostDTO.builder()
                .title("title")
                .contents("contents")
                .category("free")
                .build();
    }

    public Post initPost(PostDTO postDTO) {
        return Post.builder()
                .title(postDTO.getTitle())
                .contents(postDTO.getContents())
                .createAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("게시물 등록하기 테스트")
    @Order(1)
    public void createPost() throws Exception {
        //given
        initPostCategory();
        PostDTO postDTO = initPostDTO();

        userService.createUser(UserDTO.builder()
                .email(userEmail)
                .password("1234")
                .nickname("nickname")
                .build());

        // when
        PostDTO post1 = postService.createPost(postDTO, userEmail);

        // then
        assertThat(post1.getId()).isEqualTo(1L);
    }

    @Test
    @Order(2)
    public void 게시물_조회() throws Exception {
        // when
        PostDTO post = postService.getPost(1L);

        // then
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getContents()).isEqualTo("contents");
    }

    @Test
    @Order(3)
    @Transactional
    public void 게시물_제목_업데이트() throws Exception {
        Post findPost = postRepository.findById(1L).get();

        findPost.changeTitle("title update");

        Post updatePost = postRepository.save(findPost);

        assertThat(updatePost.getId()).isEqualTo(findPost.getId());
        assertThat(updatePost.getTitle()).isEqualTo("title update");
        assertThat(updatePost.getContents()).isEqualTo("contents");
    }

    @Test
    @Order(4)
    @Transactional
    public void 게시물_내용_업데이트() throws Exception {
        Post findPost = postRepository.findById(1L).get();

        findPost.changeContents("contents update");

        Post updatePost = postRepository.save(findPost);

        assertThat(updatePost.getId()).isEqualTo(findPost.getId());
        assertThat(updatePost.getTitle()).isEqualTo("title");
        assertThat(updatePost.getContents()).isEqualTo("contents update");
    }

    @Test
    @Transactional
    public void 게시물_삭제_성공() throws Exception {
        Post post = postRepository.findById(1L).get();

        postRepository.delete(post);

        assertThatThrownBy(() -> {
            postService.getPost(1L);
        }).isInstanceOf(PostNotFound.class)
                .hasMessage("해당 포스트가 존재하지 않습니다.");
    }
}
