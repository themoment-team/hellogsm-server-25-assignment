package team.themoment.hellogsmassignment.domain.order.repo.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmassignment.domain.order.entity.Order;
import team.themoment.hellogsmassignment.domain.order.entity.type.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomOrderRepository {
    Optional<Order> findByIdWithDetails(Long orderId);
    Page<Order> searchOrders(OrderStatus status, BigDecimal minPrice, BigDecimal maxPrice, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
