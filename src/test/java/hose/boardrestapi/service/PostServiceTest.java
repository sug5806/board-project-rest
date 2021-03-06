package hose.boardrestapi.service;

import hose.boardrestapi.dto.post.PostDTO;
import hose.boardrestapi.entity.common.Date;
import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.repository.post.PostRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
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
                .date(Date.builder()
                        .createdAt(LocalDateTime.now())
                        .build()
                )
                .build();
    }

//    @Test
//    @DisplayName("게시물 등록하기 서비스 테스트")
//    public void createPost() throws Exception {
//        //given
//        PostDTO postDTO = initPostDTO();
//        Post post = initPost(postDTO);
//
////        given(postRepository.save(post)).willReturn(post);
//
//        //when
//        PostDTO post1 = postService.createPost(postDTO);
////        Post savePost = postRepository.save(post);
//
//        //then
//        assertEquals(post1.getId(), 1L);
//    }
}