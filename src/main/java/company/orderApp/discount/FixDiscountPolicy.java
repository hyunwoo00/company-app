package company.orderApp.discount;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountPrice = 1000;

    /**
     * @param amount : 제품 수량
     * @return 할인되는 가격
     */
    @Override
    public int discount(int amount, int startAmount) {
        if(amount > 10){
            return discountPrice * 10;
        }
        else if(amount >= startAmount){
            return discountPrice * amount;
        }
        return 0;
    }
}
