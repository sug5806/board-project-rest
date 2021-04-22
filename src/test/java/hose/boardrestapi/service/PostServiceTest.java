package hose.boardrestapi.service;

import hose.boardrestapi.dto.PostDTO;
import hose.boardrestapi.entity.Post;
import hose.boardrestapi.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
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
    @DisplayName("게시물 등록하기 서비스 테스트")
    public void createPost() throws Exception {
        //given
        PostDTO postDTO = initPostDTO();
        Post post = initPost(postDTO);

        given(postRepository.save(post)).willReturn(post);

        //when
        Post savePost = postRepository.save(post);

        //then
        assertEquals(savePost.getTitle(), post.getTitle());
        assertEquals(savePost.getContents(), post.getContents());
    }

    @Test
    public void 제목_업데이트() throws Exception {
        // given
        PostDTO postDTO = PostDTO.builder()
                .title("title update")
                .contents("contents")
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("title update")
                .contents("contents")
                .build();

        postService.updatePost(1L, postDTO);
        given(postRepository.save(post)).willReturn(post);
        // when


        // then
    }
}