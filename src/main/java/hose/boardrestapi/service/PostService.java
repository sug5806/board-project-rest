package hose.boardrestapi.service;

import hose.boardrestapi.common.custom_exception.PostNotFound;
import hose.boardrestapi.dto.PostDTO;
import hose.boardrestapi.entity.Post;
import hose.boardrestapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostDTO getPost(Long postId) {
        Optional<Post> byId = postRepository.findById(postId);

        Post findPost = byId.orElseThrow(() -> new PostNotFound("해당 포스트가 존재하지 않습니다."));

        return PostDTO.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .contents(findPost.getContents())
                .build();
    }

    public PostDTO createPost(PostDTO postDTO) {
        Post post = Post.builder()
                .title(postDTO.getTitle())
                .contents(postDTO.getContents())
                .createAt(LocalDateTime.now())
                .build();

        Post savePost = postRepository.save(post);

        return PostDTO.builder()
                .id(savePost.getId())
                .build();
    }

    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.orElseThrow(() -> new PostNotFound("해당 포스트가 존재하지 않습니다."));

        post.changeTitle(postDTO.getTitle());
        post.changeContents(postDTO.getContents());

        return PostDTO.builder()
                .id(post.getId())
                .build();
    }

    public void deletePost(Long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.orElseThrow(() -> new PostNotFound("해당 포스트가 존재하지 않습니다."));

        postRepository.delete(post);
    }
}
