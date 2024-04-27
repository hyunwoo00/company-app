package company.orderApp.controller.response;


import company.orderApp.domain.order.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private Long id; //itemId
    private String imgPath;
    private String name;
    private int count;
    private int discountPrice;
    private int price;


    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getItem().getId();
        this.name = orderItem.getItem().getName();
        this.price = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
        this.imgPath = orderItem.getItem().getImgPath();
        this.discountPrice = orderItem.getDiscountPrice();
    }
}
