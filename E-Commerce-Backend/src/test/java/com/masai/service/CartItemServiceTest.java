package com.masai.service;

import com.masai.models.*;
import com.masai.repository.ProductDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {

    @Mock
    ProductDao productDao;

    @InjectMocks
    CartItemServiceImpl cartItemService;

    private CartDTO cartDTO;

    private Product product;
    private Cart cart;
    @BeforeEach
    public void setup(){
        int cartId=353242;

        cart=new Cart();
        cart.setCartId(cartId);
        cart.setCartTotal(0.0);

        product=Product.builder().productId(123456).productName("Dove shampoo")
                .price(2000.0).quantity(10)
                .status(ProductStatus.AVAILABLE).build();
        cartDTO=CartDTO.builder().productId(123456).build();
        CartItem cartItem=CartItem.builder().cartProduct(product).cartItemQuantity(1).build();
        cart.setCartItems(List.of(cartItem));
    }

    @Test
    public void createItemforCartTest(){
        given(productDao.findById(cartDTO.getProductId())).willReturn(Optional.of(product));
        CartItem actualCartItem=cartItemService.createItemforCart(cartDTO);
        CartItem expectedItems=cart.getCartItems().stream().filter(item->item.getCartProduct()==product).findFirst().get();
        Assertions.assertEquals(expectedItems.getCartProduct(),actualCartItem.getCartProduct());
        Assertions.assertEquals(expectedItems.getCartItemQuantity(),actualCartItem.getCartItemQuantity());
    }


}
