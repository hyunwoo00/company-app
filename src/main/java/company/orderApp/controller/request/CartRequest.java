package company.orderApp.controller.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartRequest {
    private Long userId;
    private Long itemId;
    private int count;
}
