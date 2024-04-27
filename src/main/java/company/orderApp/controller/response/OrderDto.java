package company.orderApp.controller.response;


import company.orderApp.domain.Delivery;
import company.orderApp.domain.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItems;
    private Delivery delivery;
    private LocalDateTime orderDate;

    public OrderDto(Order o) {
        this.id = o.getId();
        this.orderItems = o.getOrderItems().stream()
                .map(OrderItemDto::new)
                .toList();
        this.orderDate = o.getOrderDate();
        this.delivery = o.getDelivery();
    }
}
