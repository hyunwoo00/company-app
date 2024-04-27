package company.orderApp.controller.response;


import company.orderApp.domain.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDto {
    private List<OrderItemDto> orderItems;


    public CartDto(Cart cart) {
        this.orderItems = cart.getOrderItems().stream()
                .map(OrderItemDto::new)
                .toList();
    }

    public CartDto() {
        this.orderItems = new ArrayList<>();
    }

}
