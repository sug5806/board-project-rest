package hose.boardrestapi.entity.post;

import hose.boardrestapi.entity.Comment;
import hose.boardrestapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String contents;

    private LocalDateTime createAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory category;

    @Builder.Default
    private Long viewCount = 0L;

    public void addViewCount() {
        this.viewCount += 1;
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_user_post"))
    private User user;

    @OneToMany(fetch = LAZY, mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();


////////////////////////////////////////////////////////////////////////////////////////////////

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

    public void mappingUser(User user) {
        this.user = user;
        user.mappingPost(this);
    }

    public void mappingComment(Comment comment) {
        this.commentList.add(comment);
    }
}
