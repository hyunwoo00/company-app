package company.orderApp.domain;


import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    //가게 명
    private String storeName;
    //도로명 주소
    private String roadAddress;
    //우편 번호
    private int zoneCode;
    //상세 주소
    private String detail;
}
