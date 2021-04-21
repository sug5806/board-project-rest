package hose.boardrestapi.service;

import hose.boardrestapi.dto.PostDTO;
import hose.boardrestapi.entity.Post;
import hose.boardrestapi.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
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
}