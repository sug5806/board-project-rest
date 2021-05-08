package hose.boardrestapi.repository.post.order;

import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Component;

import static hose.boardrestapi.entity.post.QPost.post;

@Component("create")
public class OrderCreate implements OrderTypeStrategy {
    @Override
    public OrderSpecifier<?> getOrder() {
        return post.date.createdAt.desc();
    }
}
