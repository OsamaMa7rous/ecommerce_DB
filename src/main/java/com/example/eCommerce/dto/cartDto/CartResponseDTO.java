package com.example.eCommerce.dto.cartDto;

import com.example.eCommerce.dto.cartItemDto.CartItemResponseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class CartResponseDTO {
private  Long id;
private  Long userID;
private  String username;
private List<CartItemResponseDTO> cartItemResponseDTOList;
private double totalPrice;
}
