package company.orderApp.discount;

public interface DiscountPolicy {

    int discount(int amount, int startAmount);
}
