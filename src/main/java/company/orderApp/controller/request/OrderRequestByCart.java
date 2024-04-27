package company.orderApp.controller.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequestByCart {
    private Long userId;
    private String storeName;
    private String roadAddress;
    private int zoneCode;
    private String detail;
    private String phoneNumber;
    private String request;
    private String receipt;
}
