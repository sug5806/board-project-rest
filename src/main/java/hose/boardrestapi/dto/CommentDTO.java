package hose.boardrestapi.dto;

import hose.boardrestapi.entity.Comment;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class CommentDTO {
    private Long id;

    @NotBlank
    @Length(max = 100)
    private String contents;
    private UserDTO user;

    public static CommentDTO convertToCommentDto(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .contents(comment.getContents())
                .user(UserDTO.convertToUserDTO(comment.getUser()))
//                .user(UserDTO.builder()
//                        .id(comment.getUser().getId())
//                        .email(comment.getUser().getEmail())
//                        .nickname(comment.getUser().getNickname())
//                        .build())
                .build();
    }

    public static List<CommentDTO> convertToCommentDtoList(List<Comment> commentList) {
        Stream<Comment> stream = commentList.stream();

        return stream.map(CommentDTO::convertToCommentDto).collect(Collectors.toList());

    }
}