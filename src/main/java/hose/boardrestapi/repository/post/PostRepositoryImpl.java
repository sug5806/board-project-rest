package hose.boardrestapi.repository.post;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hose.boardrestapi.dto.SearchDTO;
import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.entity.post.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static hose.boardrestapi.entity.QUser.user;
import static hose.boardrestapi.entity.post.QPost.post;
import static hose.boardrestapi.entity.post.QPostCategory.postCategory;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> postListQueryDSL(SearchDTO searchDTO) {
        BooleanExpression postCategoryQuery = getPostCategoryQuery(searchDTO.getCategory());

        List<Post> postList = jpaQueryFactory
                .selectFrom(post)
                .where(postCategoryQuery)
                .join(post.user, user).fetchJoin()
                .join(post.category, postCategory).fetchJoin()
                .fetch();

        return postList;
    }

    private BooleanExpression getPostCategoryQuery(String category) {
        if (StringUtils.hasLength(category)) {
            PostCategory findPostCategory = jpaQueryFactory
                    .selectFrom(postCategory)
                    .where(postCategory.name.eq(category))
                    .fetchOne();

            return post.category.eq(findPostCategory);
        }

        return null;
    }
}
