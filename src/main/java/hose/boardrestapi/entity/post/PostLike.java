package hose.boardrestapi.entity.post;

import hose.boardrestapi.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_PostLike_User"))
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_PostLike_Post"))
    private Post post;

    public static boolean isVotedPost(Optional<PostLike> optionalPostLike) {
        return optionalPostLike.isPresent();
    }

    public void mappingUser(User user) {
        this.user = user;
        user.mappingPostLike(this);
    }

    public void mappingPost(Post post) {
        this.post = post;
        post.mappingPostLike(this);
    }

}