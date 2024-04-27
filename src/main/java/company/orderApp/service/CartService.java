package company.orderApp.service;


import company.orderApp.discount.DiscountPolicy;
import company.orderApp.domain.Cart;
import company.orderApp.domain.User;
import company.orderApp.domain.order.OrderItem;
import company.orderApp.repository.CartRepository;
import company.orderApp.repository.ItemRepository;
import company.orderApp.repository.UserRepository;
import company.orderApp.service.exception.NonExistentCartException;
import company.orderApp.service.exception.NonExistentOrderItemException;
import company.orderApp.service.exception.NonExistentUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final DiscountPolicy discountPolicy;

    /**
     * 상품 담기
     */
    @Transactional
    public void addItem(Long userId, OrderItem orderItem) {

        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NonExistentUserException("존재하지 않는 회원입니다."));


        Optional<Cart> optionalCart = findCurrentCart(user);
        //상품을 장바구니에 처음 담는 경우.
        if (optionalCart.isEmpty()) {
            //장바구니 생성.
            Cart newCart = Cart.createCart(user);
            //상품 담기.
            newCart.addOrderItem(orderItem);
            //장바구니 저장.
            cartRepository.save(newCart);
        }
        //장바구니에 상품이 존재하는 경우
        optionalCart.ifPresent( cart -> {

            Optional<OrderItem> findOrderItem = cart.getOrderItems().stream()
                    .filter(value -> value.getItem().getItemNumber() == orderItem.getItem().getItemNumber())
                    .findFirst();

            findOrderItem.ifPresentOrElse(
                    //장바구니에 담으려는 상품이 존재하는 상품의 제품번호가 같을 경우
                    element -> {
                        element.setCount(element.getCount() + orderItem.getCount());
                        element.setDiscountPrice(discountPolicy.discount(element.getCount() + orderItem.getCount(), orderItem.getItem().getMinimumQuantityForDiscount()));
                    },

                    //장바구니에 새로운 상품을 담는 경우
                    () -> //상품 담기 후 dirty checking.
                            cart.addOrderItem(orderItem)
            );

        });



    }

    /**
     * 현재 장바구니 조회
     */
    public Optional<Cart> findCurrentCart(User user) {
        return user.getCarts().stream().filter(e -> !e.isOrdered()).findFirst();
    }


    /**
     * 상품 제외하기
     */
    @Transactional
    public void removeItem(Long userId, Long itemId) {

        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NonExistentUserException("존재하지 않는 회원입니다."));

        Optional<Cart> optionalCart = findCurrentCart(user);

        if(optionalCart.isEmpty()){
            throw new NonExistentCartException("장바구니가 존재하지 않습니다.");
        }

        optionalCart.ifPresent( cart -> {
            OrderItem orderItem = cart.getOrderItems().stream()
                    .filter(oi -> Objects.equals(oi.getItem().getId(), itemId))
                    .findFirst()
                    .orElseThrow();

            cart.removeOrderItem(orderItem);
        });


    }

    /**
     * 주문 수량 변경
     */
    @Transactional
    public void changeItemCount(Long userId, Long itemId, int count) {
        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NonExistentUserException("존재하지 않는 회원입니다."));

        Optional<Cart> optionalCart = findCurrentCart(user);

        if(optionalCart.isEmpty()) throw new NonExistentCartException("장바구니가 존재하지 않습니다.");

        optionalCart.ifPresent(cart -> {
            OrderItem orderItem = cart.getOrderItems().stream()
                    .filter(e -> Objects.equals(e.getItem().getId(), itemId))
                    .findFirst()
                    .orElseThrow(() -> new NonExistentOrderItemException("존재하지 않는 주문상품입니다."));

            orderItem.setCount(count);
            orderItem.setDiscountPrice(discountPolicy.discount(count, orderItem.getItem().getMinimumQuantityForDiscount()));
        });

    }
}
