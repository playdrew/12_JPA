package com.ohgiraffers.mapping.section03.idclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;

    // 테스트코드에서 전달받은 cartDTO 를 엔티티화 시킴
    @Transactional
    public void addItemToCart(CartDTO cart) {

        Cart newCart = new Cart(
                cart.getCartOwnerMemberNo(),
                cart.getAddedBookNo(),
                cart.getQuantity()
        );

        // 이렇게 엔티티화시킨 것을 하나의 로우를 만들어낸 것들 디비반영해달라고 메니저에게 보냄
        repository.save(newCart);
    }
}
