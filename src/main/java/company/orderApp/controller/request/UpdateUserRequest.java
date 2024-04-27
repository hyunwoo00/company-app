package company.orderApp.controller.request;


import company.orderApp.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String phoneNumber;
    private Address address;
}
