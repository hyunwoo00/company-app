package company.orderApp.controller.request;

import company.orderApp.domain.Address;
import company.orderApp.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String storeName;
    @NotNull
    private String password;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String roadAddress;
    @NotNull
    private String businessNumber;
    @NotNull
    private String zoneCode;
    private String detail;


    public static User toEntity(SignUpRequest signUpRequest) {
        Address address = new Address(signUpRequest.getStoreName(),
                signUpRequest.getRoadAddress(),
                Integer.parseInt(signUpRequest.getZoneCode()),
                signUpRequest.getDetail());

        return User.createUser(signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                address,
                signUpRequest.getBusinessNumber(),
                signUpRequest.getPhoneNumber(),
                "USER");
    }
}
