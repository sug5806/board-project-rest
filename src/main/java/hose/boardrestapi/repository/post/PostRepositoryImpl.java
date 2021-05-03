package hose.boardrestapi.repository.post;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hose.boardrestapi.dto.SearchDTO;
import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.entity.post.PostCategory;
import hose.boardrestapi.util.enumerate.SearchType;
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
        BooleanExpression postCategoryQuery = postCategoryQuery(searchDTO.getCategory());
        BooleanExpression postSearchQuery = postSearchQuery(searchDTO);

        return jpaQueryFactory
                .selectFrom(post)
                .where(postCategoryQuery, postSearchQuery)
                .join(post.user, user).fetchJoin()
                .join(post.category, postCategory).fetchJoin()
                .fetch();
    }

    private BooleanExpression postCategoryQuery(String category) {
        if (StringUtils.hasLength(category)) {
            return post.category.eq(getPostCategory(category));
        }

        return null;
    }

    private BooleanExpression postSearchQuery(SearchDTO searchDTO) {
        SearchType searchType = SearchType.convertToType(searchDTO.getSearchType());

        if (searchType == SearchType.USER) {
            return user.nickname.eq(searchDTO.getQuery());
        }
        return post.title.contains(searchDTO.getQuery());

    }

    private PostCategory getPostCategory(String category) {
        return jpaQueryFactory
                .selectFrom(postCategory)
                .where(postCategory.name.eq(category))
                .fetchOne();
    }
}
