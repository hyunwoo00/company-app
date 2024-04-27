package company.orderApp.repository;

import company.orderApp.domain.order.Order;
import company.orderApp.domain.order.OrderSearch;
import company.orderApp.domain.order.OrderStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(em.find(Order.class, id));
    }

    public void save(Order order) {em.persist(order);}


    /**
     * 최근 3개월 내의 회원 주문 내역 조회
     */
    public List<Order> findRecentAllByUser(Long userId, int offset, int limit) {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        return em.createQuery("select o from Order o" +
                " join fetch o.delivery d" +
                " where o.user.id = :userId and" +
                " o.orderDate >= :threeMonthsAgo" +
                " order by o.orderDate desc", Order.class)
                .setParameter("userId", userId)
                .setParameter("threeMonthsAgo", threeMonthsAgo)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 회원들의 당일 주문 내역 조회
     */

    public List<Order> findAllByDate(LocalDateTime date, int offset, int limit) {

        LocalDateTime startOfDay = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = date.withHour(23).withMinute(59).withSecond(59);

        return em.createQuery("select o from Order o" +
                        " join fetch o.delivery d" +
                        " where o.orderDate BETWEEN :startOfDay AND :endOfDay" +
                        " order by o.orderDate desc", Order.class)
                .setParameter("startOfDay", startOfDay)
                .setParameter("endOfDay", endOfDay)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }




}
