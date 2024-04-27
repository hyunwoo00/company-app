package company.orderApp.repository;

import company.orderApp.domain.Cart;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final EntityManager em;

    public void save(Cart cart) {
        em.persist(cart);
    }

    public Cart findByUserId(Long userId) {
        return em.createQuery("select c from Cart c" +
                " join fetch c.orderItems oi"+
                " where c.user.id = :userId", Cart.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public Optional<Cart> findById(Long cartId) {
        Cart cart = em.find(Cart.class, cartId);

        return Optional.ofNullable(cart);
    }
}
