package company.orderApp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import company.orderApp.domain.order.Order;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    public void setOrder(Order order) {
        this.order = order;
    }
    private void setAddress(Address address) {this.address = address;}

    //==생성 메서드==//
    public static Delivery createDelivery(Address address) {
        Delivery delivery = new Delivery();
        delivery.setAddress(address);

        return delivery;
    }

    //==비즈니스 로직==//
    public void changeAddress(Address address) {
        setAddress(address);
    }

}
