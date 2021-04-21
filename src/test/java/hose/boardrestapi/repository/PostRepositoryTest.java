package hose.boardrestapi.repository;

import hose.boardrestapi.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    public Post initPost() {
        return Post.builder()
                .title("title")
                .contents("contents")
                .createAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("상품 등록 레포 테스트")
    public void createPost() throws Exception {
        // given
        Post post = initPost();

        // when
        Post save = postRepository.save(post);

        // then
        assertEquals(post.getTitle(), save.getTitle());
        assertEquals(post.getContents(), save.getContents());
        assertEquals(1, save.getId());
    }
}