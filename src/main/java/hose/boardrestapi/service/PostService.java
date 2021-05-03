package hose.boardrestapi.service;

import hose.boardrestapi.dto.CommentDTO;
import hose.boardrestapi.dto.SearchDTO;
import hose.boardrestapi.dto.UserDTO;
import hose.boardrestapi.dto.post.PostCategoryDTO;
import hose.boardrestapi.dto.post.PostDTO;
import hose.boardrestapi.entity.User;
import hose.boardrestapi.entity.common.Date;
import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.entity.post.PostCategory;
import hose.boardrestapi.entity.post.PostLike;
import hose.boardrestapi.repository.UserRepository;
import hose.boardrestapi.repository.post.PostCategoryRepository;
import hose.boardrestapi.repository.post.PostLikeRepository;
import hose.boardrestapi.repository.post.PostRepository;
import hose.boardrestapi.util.custom_exception.PostNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostLikeRepository postLikeRepository;

    public PostDTO getPost(Long postId) {
        Post findPost = getPostInService(postId);

        findPost.addViewCount();
        List<CommentDTO> commentDTOS = CommentDTO.convertToCommentDtoList(findPost.getCommentList());

        return PostDTO.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .contents(findPost.getContents())
                .category(findPost.getCategory().getName())
                .viewCount(findPost.getViewCount())
                .likeCount(findPost.getLikeCount())
                .commentCount((long) commentDTOS.size())
                .user(UserDTO.convertToUserDTO(findPost.getUser()))
                .commentList(commentDTOS)
                .build();
    }

    private Post getPostInService(Long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        return byId.orElseThrow(() -> new PostNotFound("해당 포스트가 존재하지 않습니다."));
    }

    public PostDTO createPost(PostDTO postDTO, String email) {
        User user = getUserInService(email);

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .contents(postDTO.getContents())
                .date(Date.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .build();

        post.mappingCategory(postCategoryRepository.findByName(postDTO.getCategory()));
        post.mappingUser(user);

        Post savePost = postRepository.save(post);

        return PostDTO.builder()
                .id(savePost.getId())
                .build();
    }

    private User getUserInService(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(() -> new UsernameNotFoundException("게시글 작성 권한이 없습니다."));
        return user;
    }

    public PostDTO updatePost(Long postId, PostDTO postDTO, String email) {
        Post post = getPostInService(postId);

        if (post.getUser().getEmail().equals(email)) {
            post.changeTitle(postDTO.getTitle());
            post.changeContents(postDTO.getContents());
            post.getDate().changeUpdatedAt(LocalDateTime.now());

            return PostDTO.builder()
                    .id(post.getId())
                    .build();
        } else {
            throw new AuthorizationServiceException("권한이 없습니다.");
        }
    }

    public void deletePost(Long postId, String email) {
        Post post = getPostInService(postId);

        if (post.getUser().getEmail().equals(email)) {
            postRepository.delete(post);
        } else {
            throw new AuthorizationServiceException("권한이 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<PostCategoryDTO> postCategoryList() {
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();

        Stream<PostCategory> stream = postCategoryList.stream();

        return stream.map(PostCategoryDTO::convertToPostCategoryDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getPostList(SearchDTO searchDTO) {
        Stream<Post> stream = postRepository.postListQueryDSL(searchDTO).stream();

        return stream.map(PostDTO::convertToPostDTO).collect(Collectors.toList());
    }

    public void postLike(Long postId, String email) {
        Post post = getPostInService(postId);
        User user = getUserInService(email);
        Optional<PostLike> byPostAndUser = postLikeRepository.findByPostAndUser(post, user);

        byPostAndUser.ifPresentOrElse(
                postLike -> {
                    postLikeRepository.delete(postLike);
                    post.discountLike(postLike);
                },
                () -> {
                    PostLike postLike = PostLike.builder().build();

                    postLike.mappingPost(post);
                    postLike.mappingUser(user);
                    post.updateLikeCount();

                    postLikeRepository.save(postLike);
                }
        );
    }
}
