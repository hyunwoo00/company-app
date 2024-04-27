package company.orderApp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import company.orderApp.domain.order.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isOrdered;


    //==생성 메서드==//
    public static Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.isOrdered = false;

        return cart;
    }

    //==연관 관계 메서드==//
    public void setUser(User user) {
        this.user =user;
        user.getCarts().add(this);
    }
    //==비즈니스 로직==//
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setCart(this);
    }

    public void setOrdered(){
        this.isOrdered = true;
    }

    public void removeOrderItem(OrderItem orderItem) {

        this.orderItems.remove(orderItem);
        orderItem.setCart(null);
    }

}
