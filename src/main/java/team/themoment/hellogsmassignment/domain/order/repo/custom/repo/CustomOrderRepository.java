package team.themoment.hellogsmassignment.domain.order.repo.custom.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmassignment.domain.order.entity.Order;
import team.themoment.hellogsmassignment.domain.order.entity.type.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomOrderRepository {
    Optional<Order> findByOrderId(Long orderId);

    Page<Order> customSearchOrders(
            OrderStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
}
