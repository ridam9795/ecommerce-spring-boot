
package com.masai.service;

import com.masai.models.*;
import com.masai.repository.CartDao;
import com.masai.repository.CustomerDao;
import com.masai.repository.ProductDao;
import com.masai.repository.SessionDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartDao cartDao;

    @Mock
    private SessionDao sessionDao;

    @Mock
    private CartItemService cartItemService;


    @Mock
    private CustomerDao customerDao;

    @Mock
    private LoginLogoutService loginService;


    @Mock
    private ProductDao productDao;

    @InjectMocks
    CartServiceImpl cartService;

    private String token;
    private int userId;
    private int sessionId;
    private int cartId;
    private int productId;
    private UserSession userSession;
    private Customer customer;
    private Cart cart;
    @BeforeEach
    public void setup(){
        token="customerToken";
        userId=1234567890;
        sessionId=1234;
        cartId=353242;
        productId=523532;

        cart=new Cart();
        cart.setCartId(cartId);
        userSession=UserSession.builder()
                .userId(userId).sessionId(sessionId).token(token)
                .sessionStartTime(LocalDateTime.MIN).sessionEndTime(LocalDateTime.MAX).build();
        customer=Customer.builder().customerId(1241424).firstName("varun")
                .lastName("mishra").mobileNo("12345678765").emailId("varun@test.com")
                .customerCart(cart).build();
        cart.setCustomer(customer);
    }

    @Test
    public void addProductToCartTest_whenCartIsEmpty(){

        given(sessionDao.findByToken(token)).willReturn(Optional.ofNullable(userSession));
        given(customerDao.findById(userId)).willReturn(Optional.ofNullable(customer));
        Product product=Product.builder().productId(productId).productName("Dove").description("it's a shampoo")
                .price(80.0).manufacturer("hrg").quantity(10).build();
        CartItem newCartItem=new CartItem(423213,product,2);
        List<CartItem> cartItemList=new ArrayList<>();
        cartItemList.add(newCartItem);
       CartDTO cartDTO=new CartDTO(newCartItem.getCartProduct().getProductId(),newCartItem.getCartProduct().getProductName(),newCartItem.getCartProduct().getPrice(),newCartItem.getCartItemQuantity());
        given(cartItemService.createItemforCart(cartDTO)).willReturn(newCartItem);
        given(cartDao.save(any(Cart.class))).willReturn(cart);
        Cart expectedCart=new Cart(cartId,cartItemList,80.0,customer);
        Cart actualCart=cartService.addProductToCart(cartDTO,token);
 //       System.out.println(expectedCart);
   //     System.out.println(actualCart);
        Assertions.assertEquals(expectedCart,actualCart);



    }


}
