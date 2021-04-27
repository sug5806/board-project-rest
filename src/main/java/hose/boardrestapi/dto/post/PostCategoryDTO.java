package hose.boardrestapi.dto.post;

import hose.boardrestapi.entity.post.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCategoryDTO {
    private Long id;
    private String name;
    private String value;

    public static PostCategoryDTO ConvertToPostCategoryDTO(PostCategory postCategory) {
        return PostCategoryDTO.builder()
                .id(postCategory.getId())
                .name(postCategory.getName())
                .value(postCategory.getValue())
                .build();
    }
}
