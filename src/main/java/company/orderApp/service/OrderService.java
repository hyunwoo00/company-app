package company.orderApp.service;


import company.orderApp.discount.DiscountPolicy;
import company.orderApp.domain.Cart;
import company.orderApp.domain.Delivery;
import company.orderApp.domain.User;
import company.orderApp.domain.order.Order;
import company.orderApp.domain.order.OrderItem;
import company.orderApp.domain.order.Receipt;
import company.orderApp.repository.CartRepository;
import company.orderApp.repository.OrderRepository;
import company.orderApp.repository.UserRepository;
import company.orderApp.service.exception.NonExistentCartException;
import company.orderApp.service.exception.NonExistentOrderException;
import company.orderApp.service.exception.NonExistentUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final DiscountPolicy discountPolicy;
    private final CartRepository cartRepository;

    /**
     * 장바구니로 주문하기
     */
    @Transactional
    public Long orderByCart(Long userId, Long cartId, Delivery delivery, String phoneNumber, Receipt receipt, String request) {
        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NonExistentUserException("존재하지 않는 회원입니다."));
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NonExistentCartException("존재하지 않는 장바구니입니다."));

        cart.setOrdered();

        Order order = Order.createOrderByCart(user, delivery, cart, phoneNumber, receipt, request);
        orderRepository.save(order);

        return order.getId();

    }


    /**
     * 단독 상품 주문하기
     */
    @Transactional
    public Long orderByItem(Long userId, Delivery delivery, OrderItem orderItem, String phoneNumber, Receipt receipt, String request) {
        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NonExistentUserException("존재하지 않는 회원입니다."));

        orderItem.setDiscountPrice(discountPolicy.discount(orderItem.getCount(), orderItem.getItem().getMinimumQuantityForDiscount()));
        Order order = Order.createOrderByOrderItem(user, delivery, orderItem, phoneNumber, receipt, request);

        orderRepository.save(order);

        return order.getId();
    }


    /**
     * 주문 취소하기
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //엔티티 조회
        Order order = findOrderById(orderId);

        //주문 취소
        order.cancel();
    }


    /**
     * 주문 전체 가격 조회하기
     */
    public int getOrderTotalPrice(Order order) {

        return order.getOrderItems().stream()
                .mapToInt(element -> element.getTotalPrice() - discountPolicy.discount(element.getCount(), element.getItem().getMinimumQuantityForDiscount()))
                .sum();
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NonExistentOrderException("존재하지 않는 주문입니다."));
    }

}
