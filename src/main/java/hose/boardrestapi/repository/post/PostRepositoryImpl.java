package hose.boardrestapi.repository.post;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hose.boardrestapi.dto.SearchDTO;
import hose.boardrestapi.entity.post.Post;
import hose.boardrestapi.entity.post.PostCategory;
import hose.boardrestapi.repository.post.order.OrderTypeStrategy;
import hose.boardrestapi.repository.post.search.SearchTypeStrategy;
import hose.boardrestapi.util.enumerate.OrderType;
import hose.boardrestapi.util.enumerate.SearchType;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import static hose.boardrestapi.entity.QUser.user;
import static hose.boardrestapi.entity.post.QPost.post;
import static hose.boardrestapi.entity.post.QPostCategory.postCategory;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final Map<String, OrderTypeStrategy> orderTypeStrategyMap;
    private final Map<String, SearchTypeStrategy> searchTypeStrategyMap;

    @Override
    public List<Post> postListQueryDSL(SearchDTO searchDTO) {
        BooleanExpression postCategoryQuery = postCategoryQuery(searchDTO.getCategory());
        BooleanExpression postSearchQuery = postSearchQuery(searchDTO);

        return jpaQueryFactory
                .selectFrom(post)
                .where(postCategoryQuery, postSearchQuery)
                .join(post.user, user).fetchJoin()
                .join(post.category, postCategory).fetchJoin()
                .orderBy(post.date.createdAt.desc())
                .fetch();
    }

    @Override
    public Page<Post> postListPagingQueryDSL(SearchDTO searchDTO, Pageable pageable) {
        BooleanExpression postCategoryQuery = postCategoryQuery(searchDTO.getCategory());
        BooleanExpression postSearchQuery = postSearchQuery(searchDTO);
        OrderSpecifier<?> order = sortingCondition(pageable.getSort());

        List<Post> postList = jpaQueryFactory
                .selectFrom(post)
                .where(postCategoryQuery, postSearchQuery)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(order)
                .fetch();

        long count = jpaQueryFactory
                .selectFrom(post)
                .where(postCategoryQuery, postSearchQuery)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(order)
                .fetchCount();


        return new PageImpl<>(postList, pageable, count);

    }

    private BooleanExpression postCategoryQuery(String category) {
        if (StringUtils.hasLength(category)) {
            return post.category.eq(getPostCategory(category));
        }


        return null;
    }

    private BooleanExpression postSearchQuery(SearchDTO searchDTO) {
        SearchType searchType = SearchType.convertToType(searchDTO.getSearchType());

        if (Strings.hasText(searchDTO.getQuery())) {
            SearchTypeStrategy searchTypeStrategy = searchTypeStrategyMap.get(searchType.getType());

            return searchTypeStrategy.search(searchDTO.getQuery());
        }

        return null;

    }

    private PostCategory getPostCategory(String category) {
        return jpaQueryFactory
                .selectFrom(postCategory)
                .where(postCategory.name.eq(category))
                .fetchOne();
    }

    private OrderSpecifier<?> sortingCondition(Sort sort) {
        OrderSpecifier<?> orderBy = null;

        if (!sort.isEmpty()) {
            List<Sort.Order> orders = sort.toList();
            Sort.Order order = orders.get(0);
            OrderType orderType = OrderType.convertToType(order.getProperty());

            OrderTypeStrategy orderTypeStrategy = orderTypeStrategyMap.get(orderType.getType());

            orderBy = orderTypeStrategy.getOrder();
        }

        return orderBy;
    }
}
