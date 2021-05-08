package hose.boardrestapi.repository.post.order;

import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Component;

import static hose.boardrestapi.entity.post.QPost.post;

@Component("popular")
public class OrderPopular implements OrderTypeStrategy {
    @Override
    public OrderSpecifier<?> getOrderType() {
        return post.likeCount.desc();
    }
}
