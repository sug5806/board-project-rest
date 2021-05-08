package hose.boardrestapi.repository.post.order;

import com.querydsl.core.types.OrderSpecifier;

public interface OrderTypeStrategy {
    OrderSpecifier<?> getOrder();
}
