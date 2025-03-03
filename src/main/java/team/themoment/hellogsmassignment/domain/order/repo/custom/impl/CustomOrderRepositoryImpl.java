package team.themoment.hellogsmassignment.domain.order.repo.custom.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.themoment.hellogsmassignment.domain.order.entity.Order;
import team.themoment.hellogsmassignment.domain.order.entity.type.OrderStatus;
import team.themoment.hellogsmassignment.domain.order.repo.custom.repo.CustomOrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static team.themoment.hellogsmassignment.domain.member.entity.QMember.member;
import static team.themoment.hellogsmassignment.domain.order.entity.QOrder.order;
import static team.themoment.hellogsmassignment.domain.order.entity.QOrderItem.orderItem;
import static team.themoment.hellogsmassignment.domain.product.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Order> findByOrderId(Long orderId) {
        return Optional.ofNullable(queryFactory.selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.product, product).fetchJoin()
                .where(order.id.eq(orderId))
                .fetchOne());
    }

    @Override
    public Page<Order> customSearchOrders(
            OrderStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        if (status != null) builder.and(order.status.eq(status));
        if (minPrice != null) builder.and(order.totalPrice.goe(minPrice));
        if (maxPrice != null) builder.and(order.totalPrice.loe(maxPrice));
        if (startDate != null) builder.and(order.createdTime.goe(startDate));
        if (endDate != null) builder.and(order.createdTime.loe(endDate));

        List<Order> orders = queryFactory.selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.product,product).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long countOrders = queryFactory.select(order.count())
                .from(order)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(orders, pageable, countOrders);
    }
}
