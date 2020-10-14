package com.myCart.service;

import com.myCart.exception.ProductNotFoundException;
import com.myCart.model.Cart;
import com.myCart.model.Product;
import com.myCart.repository.CartRepository;
import com.myCart.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;


    public CartService(CartRepository cartRepository, ProductRepository productRepository){
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Cart addToCart(Long productId, Integer qty, String userId){
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()){
            throw new ProductNotFoundException("product not found with id" + productId);
        }

        Cart cart = new Cart();
        //we can get logged in user from security context
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQty(qty);
        return cartRepository.save(cart);
    }

    public List<Product> getAllCartDetails(String userId){
        //fetch product added in cart
        List<Cart> carts = cartRepository.findByUserId(userId);
        Set<Long> productIds = new HashSet<>(carts.size());

        //to map the qty to the product
        Map<Long, Integer> map = new HashMap<>();

        for (Cart cart : carts){
            productIds.add(cart.getProductId());
            map.put(cart.getProductId(),cart.getQty());
        }

        //get all product t=details which is added in cart
        List<Product> products =  productRepository.findByIdIn(productIds);
        //adding the qty of the product
        for (Product product : products) {
            product.setQty(map.get(product.getId()));
        }

        return products;
    }

    @Transactional
    public String removeFromCart(Long productId, String userId){
        cartRepository.deleteFromCart(productId, userId);
        return "Item Removed From cart successFully";
    }

}
