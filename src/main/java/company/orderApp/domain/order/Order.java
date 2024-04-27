package company.orderApp.domain.order;



import company.orderApp.domain.Address;
import company.orderApp.domain.Cart;
import company.orderApp.domain.Delivery;
import company.orderApp.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String phoneNumber;

    private String request;

    @Enumerated(EnumType.STRING)
    private Receipt receipt;

    /**
     * LAZY의 경우 Order를 가져올 때 실제 Member가 아닌 Proxy 객체를 가져옴.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


    //==생성 메서드==//
    public static Order createOrderByCart(User user, Delivery delivery, Cart cart, String phoneNumber, Receipt receipt, String request) {
        Order order = new Order();

        order.setUser(user);
        order.setDelivery(delivery);
        order.setPhoneNumber(phoneNumber);
        order.setReceipt(receipt);
        order.setRequest(request);

        List<OrderItem> cartOrderItems = cart.getOrderItems();

        for (OrderItem orderItem : cartOrderItems) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    public static Order createOrderByOrderItem(User user, Delivery delivery, OrderItem orderItem, String phoneNumber, Receipt receipt, String request) {
        Order order = new Order();

        order.setUser(user);
        order.setDelivery(delivery);
        order.setPhoneNumber(phoneNumber);
        order.setReceipt(receipt);
        order.setRequest(request);

        order.addOrderItem(orderItem);


        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //==연관 관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==setter==//
    private void setOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }


    //==비즈니스 로직==//

    /**
     * 주문 취소
     */
    public void cancel() {
        setOrderStatus(OrderStatus.CANCEL);
    }

    /**
     * 결제 확인
     */
    public void confirm() {setOrderStatus(OrderStatus.CONFIRM);}


}
