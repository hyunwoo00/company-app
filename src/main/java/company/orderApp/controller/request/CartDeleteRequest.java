package company.orderApp.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDeleteRequest {
    private Long userId;
    private Long itemId;
}
