package company.orderApp.domain.order;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    private String username; // 실명
    private OrderStatus orderStatus; // 주문 상태[ORDER,CANCEL,CONFIRM]
}
