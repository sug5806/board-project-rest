package hose.boardrestapi.service;

import hose.boardrestapi.common.custom_exception.PostNotFound;
import hose.boardrestapi.dto.PostDTO;
import hose.boardrestapi.entity.Post;
import hose.boardrestapi.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostServiceTotalTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    public PostDTO initPostDTO() {
        return PostDTO.builder()
                .title("title")
                .contents("contents")
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
        PostDTO postDTO = initPostDTO();

        // when
        PostDTO post1 = postService.createPost(postDTO);

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
