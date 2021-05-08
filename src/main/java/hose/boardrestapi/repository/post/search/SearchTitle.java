package hose.boardrestapi.repository.post.search;

import com.querydsl.core.types.dsl.BooleanExpression;
import hose.boardrestapi.entity.post.QPost;
import org.springframework.stereotype.Component;

@Component("title")
public class SearchTitle implements SearchTypeStrategy {
    @Override
    public BooleanExpression search(String query) {
        return QPost.post.title.contains(query);
    }
}
