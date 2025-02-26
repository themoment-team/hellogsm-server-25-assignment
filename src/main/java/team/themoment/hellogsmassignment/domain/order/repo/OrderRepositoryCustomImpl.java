package team.themoment.hellogsmassignment.domain.order.repo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmassignment.domain.order.entity.Order;
import team.themoment.hellogsmassignment.domain.order.entity.type.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static team.themoment.hellogsmassignment.domain.order.entity.QOrder.order;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Order> searchOrders(OrderStatus status, BigDecimal minPrice, BigDecimal maxPrice, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        List<Order> orders = queryFactory.selectFrom(order)
                .leftJoin(order.member).fetchJoin()
                .where(
                        eqStatus(status),
                        goeMinPrice(minPrice),
                        loeMaxPrice(maxPrice),
                        goeStartDate(startDate),
                        loeEndDate(endDate)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(order.count())
                .from(order)
                .where(
                        eqStatus(status),
                        goeMinPrice(minPrice),
                        loeMaxPrice(maxPrice),
                        goeStartDate(startDate),
                        loeEndDate(endDate)
                )
                .fetchOne();

        return new PageImpl<>(orders, pageable, total != null ? total : 0);
    }


/*    @Override
    public int countSearchOrder(OrderStatus status, BigDecimal minPrice, BigDecimal maxPrice, LocalDateTime startDate, LocalDateTime endDate) {
        Long count = queryFactory.select(order.count())
                .from(order)
                .where(
                        eqStatus(status),
                        goeMinPrice(minPrice),
                        loeMaxPrice(maxPrice),
                        goeStartDate(startDate),
                        loeEndDate(endDate)
                )
                .fetchOne();

        return count != null ? count.intValue() : 0;
    }*/

    private BooleanExpression eqStatus(OrderStatus status) {
        return status != null ? order.status.eq(status) : null;
    }

    private BooleanExpression goeMinPrice(BigDecimal minPrice) {
        return minPrice != null ? order.totalPrice.goe(minPrice) : null;
    }

    private BooleanExpression loeMaxPrice(BigDecimal maxPrice) {
        return maxPrice != null ? order.totalPrice.loe(maxPrice) : null;
    }

    private BooleanExpression goeStartDate(LocalDateTime startDate) {
        return startDate != null ? order.createdTime.goe(startDate) : null;
    }

    private BooleanExpression loeEndDate(LocalDateTime endDate) {
        return endDate != null ? order.createdTime.loe(endDate) : null;
    }
}