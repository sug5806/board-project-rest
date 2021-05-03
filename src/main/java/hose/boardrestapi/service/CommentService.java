package hose.boardrestapi.service;

import hose.boardrestapi.dto.CommentDTO;
import hose.boardrestapi.entity.Comment;
import hose.boardrestapi.entity.User;
import hose.boardrestapi.entity.common.Date;
import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.repository.CommentRepository;
import hose.boardrestapi.repository.UserRepository;
import hose.boardrestapi.repository.post.PostRepository;
import hose.boardrestapi.util.custom_exception.PostNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentDTO createComment(Long postId, CommentDTO commentDTO, String email) {
        Optional<Post> byId = postRepository.findById(postId);

        Post post = byId.orElseThrow(() -> new PostNotFound("게시물이 삭제되었거나 존재하지 않습니다."));

        Optional<User> byEmail = userRepository.findByEmail(email);

        User user = byEmail.orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .contents(commentDTO.getContents())
                .date(Date.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .build();

        comment.mappingPostAndUser(post, user);

        Comment saveComment = commentRepository.save(comment);

        return CommentDTO.convertToCommentDto(saveComment);
    }
}
