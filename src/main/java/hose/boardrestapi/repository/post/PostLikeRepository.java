package hose.boardrestapi.repository.post;

import hose.boardrestapi.entity.User;
import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.entity.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndUser(Post post, User user);
}
