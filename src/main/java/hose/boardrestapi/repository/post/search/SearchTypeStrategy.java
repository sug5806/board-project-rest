package hose.boardrestapi.repository.post.search;

import com.querydsl.core.types.dsl.BooleanExpression;

public interface SearchTypeStrategy {
    BooleanExpression search(String query);
}
