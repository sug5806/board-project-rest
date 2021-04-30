package hose.boardrestapi.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import hose.boardrestapi.dto.CommentDTO;
import hose.boardrestapi.dto.UserDTO;
import hose.boardrestapi.entity.post.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    private static final int SEC = 60;
    private static final int MIN = 60;
    private static final int HOUR = 24;
    private static final int DAY = 30;
    private static final int MONTH = 12;

    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    private Long viewCount;

    private String createdAt;

    @NotBlank(message = "카테고리를 선택해주세요.")
    private String category;

    private UserDTO user;

    private List<CommentDTO> commentList;

    public static PostDTO convertToPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .createdAt(formatTimeString(post.getDate().getCreatedAt()))
                .viewCount(post.getViewCount())
                .user(UserDTO.convertToUserDTO(post.getUser()))
                .category(PostCategoryDTO.convertToPostCategoryDTO(post.getCategory()).getName())
                .build();
    }

    private static String formatTimeString(LocalDateTime time) {
        String msg;
        LocalDateTime currentTime = LocalDateTime.now();

        long seconds = Duration.between(time, currentTime).getSeconds();

        if (seconds < SEC) {
            msg = seconds + "초 전";
        } else if (seconds / SEC < MIN) {
            msg = seconds / SEC + "분 전";
        } else if (seconds / (SEC * MIN) < HOUR) {
            msg = seconds / (SEC * MIN) + "시간 전";
        } else if (seconds / (SEC * MIN * HOUR) < DAY) {
            msg = seconds / (SEC * MIN * HOUR) + "일 전";
        } else if (seconds / (SEC * MIN * HOUR * MONTH) < MONTH) {
            msg = seconds / (SEC * MIN * HOUR * MONTH) + "개월 전";
        } else {
            msg = seconds / (SEC * MIN * HOUR * MONTH) + "년 전";
        }

        return msg;
    }
}
