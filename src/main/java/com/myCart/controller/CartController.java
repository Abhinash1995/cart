package com.myCart.controller;

import com.myCart.model.Cart;
import com.myCart.model.Product;
import com.myCart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("addToCart/{productId}/{qty}/{userId}")
    public ResponseEntity<Cart> addCart(@PathVariable Long productId, @PathVariable Integer qty, @PathVariable String userId){
        //we can get the user id of logged in user from security context if we implement security
        return status(HttpStatus.CREATED).body(cartService.addToCart(productId, qty, userId));
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<Product>> getAllCartItem(@PathVariable String userId){
        //we can get the user id of logged in user from security context
        return status(HttpStatus.OK).body(cartService.getAllCartDetails(userId));
    }

    @DeleteMapping("/cart/{productId}/{userId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId, @PathVariable String userId){
        return status(HttpStatus.CREATED).body(cartService.removeFromCart(productId, userId));
    }




}
