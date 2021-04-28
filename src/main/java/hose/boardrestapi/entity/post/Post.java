package hose.boardrestapi.entity.post;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory category;

    @Builder.Default
    private Long viewCount = 0L;

    public void addViewCount() {
        this.viewCount += 1;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContents(String contents) {
        this.contents = contents;
    }

    public void mappingCategory(PostCategory postCategory) {
        this.category = postCategory;
        postCategory.mappingPost(this);
    }
}
