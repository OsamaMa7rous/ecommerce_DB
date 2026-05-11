package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.CartMapper;
import com.example.eCommerce.entity.Cart;
import com.example.eCommerce.dto.cartDto.CartResponseDTO;
import com.example.eCommerce.entity.CartItem;
import com.example.eCommerce.entity.User;
import com.example.eCommerce.repository.CartItemRepo;
import com.example.eCommerce.repository.CartRepo;
import com.example.eCommerce.repository.ProductRepo;
import com.example.eCommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final CartItemRepo cartItemRepo;
    private final CartMapper cartMapper;

    public CartResponseDTO addCart(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
      Cart cart = new Cart();
        cart.setUser(user);
        Cart save = cartRepo.save(cart);
        List<CartItem> items = save.getCartItems();

      return cartMapper.toCartDto(save);
    }

    public String deleteCart(Long id) {
        Cart cartNotFound = cartRepo.findById(id).orElseThrow(() -> new RuntimeException("cart not found"));
        User user = cartNotFound.getUser();
        if(user != null) {user.setCart(null);}
        cartRepo.delete(cartNotFound);
        return "cart successfully deleted";
    }

    public CartResponseDTO getCartById(Long id){
        return cartMapper.toCartDto(cartRepo.findById(id).orElseThrow(() -> new RuntimeException("cart not found")));
    }

    public List<CartResponseDTO> getAllCart() {
        List<Cart> all = cartRepo.findAll();
        return all.stream().map(cartMapper::toCartDto).toList();

    }

}
