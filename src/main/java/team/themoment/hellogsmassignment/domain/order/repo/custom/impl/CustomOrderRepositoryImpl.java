package team.themoment.hellogsmassignment.domain.order.repo.custom.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmassignment.domain.order.entity.Order;
import team.themoment.hellogsmassignment.domain.order.entity.type.OrderStatus;
import team.themoment.hellogsmassignment.domain.order.repo.custom.CustomOrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static team.themoment.hellogsmassignment.domain.member.entity.QMember.member;
import static team.themoment.hellogsmassignment.domain.order.entity.QOrder.order;
import static team.themoment.hellogsmassignment.domain.order.entity.QOrderItem.orderItem;
import static team.themoment.hellogsmassignment.domain.product.entity.QProduct.product;

@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<Order> findByIdWithDetails(Long orderId) {
        Order fetchOrder = queryFactory.selectFrom(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.product,product).fetchJoin()
                .join(order.member, member).fetchJoin()
                .where(order.id.eq(orderId))
                .fetchOne();
        return Optional.ofNullable(fetchOrder);
    }

    @Override
    public Page<Order> searchOrders(OrderStatus status, BigDecimal minPrice, BigDecimal maxPrice, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder();
        if(status != null){searchCondition.and(order.status.eq(status));}
        if(minPrice != null){searchCondition.and(order.totalPrice.goe(minPrice));}
        if(maxPrice != null){searchCondition.and(order.totalPrice.loe(maxPrice));}
        if(startDate != null){searchCondition.and(order.createdTime.goe(startDate));}
        if(endDate != null){searchCondition.and(order.createdTime.loe(endDate));}

        List<Order> orders = queryFactory.selectFrom(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.product,product).fetchJoin()
                .join(order.member, member).fetchJoin()
                .where(searchCondition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long count = queryFactory.select(order.count())
                .from(order)
                .where(searchCondition)
                .fetchOne();
        return new PageImpl<>(orders, pageable, count);
    }
}
