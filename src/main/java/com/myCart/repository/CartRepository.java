package com.myCart.repository;

import com.myCart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(String userId);

    @Modifying
    @Query(value = "delete from Cart where productId = :productId and userId = :userId")
    public void deleteFromCart(Long productId, String userId);

}
