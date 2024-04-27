package company.orderApp.controller.response;


import company.orderApp.domain.order.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemAdminDto {
    private String name;
    private int count;


    public OrderItemAdminDto(OrderItem orderItem) {
        name = orderItem.getItem().getName();
        count = orderItem.getCount();
    }
}
