package hose.boardrestapi.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import hose.boardrestapi.dto.UserDTO;
import hose.boardrestapi.entity.post.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
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

    public static PostDTO convertToPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .createdAt(post.getCreateAt().toString())
                .viewCount(post.getViewCount())
                .user(UserDTO.convertToUserDTO(post.getUser()))
                .category(PostCategoryDTO.convertToPostCategoryDTO(post.getCategory()).getName())
                .build();
    }
}
