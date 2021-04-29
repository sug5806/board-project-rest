package hose.boardrestapi.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import hose.boardrestapi.dto.UserDTO;
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
}
