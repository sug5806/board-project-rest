package hose.boardrestapi.repository.post;

import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.entity.post.PostCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    @EntityGraph(attributePaths = {"category", "user", "commentList.user"})
    @Override
    Optional<Post> findById(Long postId);

    @EntityGraph(attributePaths = {"user", "category"})
    @Override
    List<Post> findAll(Sort sort);

    @EntityGraph(attributePaths = {"user", "category"})
    List<Post> findAllByCategory(PostCategory postCategory, Sort sort);
}