package hose.boardrestapi.entity;

import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.entity.post.PostLike;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private LocalDateTime createdBy;

    @Builder.Default
    private String role = "ROLE_USER";

    private String authority;

    @Builder.Default
    private boolean enabled = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void encryptPassword(String password) {

        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void mappingPost(Post post) {
        postList.add(post);
    }

    public void mappingComment(Comment comment) {
        commentList.add(comment);
    }

    public void mappingPostLike(PostLike postLike) {
        this.postLikeList.add(postLike);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> auth = new HashSet<>();
        auth.add(new SimpleGrantedAuthority(authority));

        return auth;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
