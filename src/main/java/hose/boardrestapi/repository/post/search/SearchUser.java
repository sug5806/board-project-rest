package hose.boardrestapi.repository.post.search;

import com.querydsl.core.types.dsl.BooleanExpression;
import hose.boardrestapi.entity.QUser;
import org.springframework.stereotype.Component;

@Component("user")
public class SearchUser implements SearchTypeStrategy {
    @Override
    public BooleanExpression search(String query) {
        return QUser.user.nickname.eq(query);
    }
}
