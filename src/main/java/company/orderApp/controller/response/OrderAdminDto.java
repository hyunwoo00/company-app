package company.orderApp.controller.response;


import company.orderApp.domain.Address;
import company.orderApp.domain.order.Order;
import company.orderApp.domain.order.Receipt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderAdminDto {
    private String storeName;
    private String roadAddress;
    private int zoneCode;
    private String detail;
    private String request;
    private String phoneNumber;
    private Receipt receipt;
    private LocalDateTime orderDate;
    private List<OrderItemAdminDto> orderItems;

    public OrderAdminDto(Order o) {
        storeName = o.getDelivery().getAddress().getStoreName();
        roadAddress = o.getDelivery().getAddress().getRoadAddress();
        zoneCode = o.getDelivery().getAddress().getZoneCode();
        detail = o.getDelivery().getAddress().getDetail();
        phoneNumber = o.getPhoneNumber();
        request = o.getRequest();
        receipt = o.getReceipt();
        orderDate = o.getOrderDate();
        orderItems = o.getOrderItems().stream()
                .map(OrderItemAdminDto::new)
                .toList();
    }
}
