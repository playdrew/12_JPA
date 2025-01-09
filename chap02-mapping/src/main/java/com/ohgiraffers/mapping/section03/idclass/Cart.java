package com.ohgiraffers.mapping.section03.idclass;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_cart")
@IdClass(CartCompositeKey.class) // 복합키로 설정할 클래스 명시
public class Cart {

    //  CartCompositeKey 의 필드명과 아래의 필드명을 동일하게 해야함
    @Id
    @Column(name = "cart_owner")
    private int cartOwner;

    @Id
    @Column(name = "added_book")
    private int addedBook;

    @Column(name = "quantity")
    private int quantity; // 수량

    public Cart(){}

    public Cart(int cartOwner, int addedBook, int quantity) {
        this.cartOwner = cartOwner;
        this.addedBook = addedBook;
        this.quantity = quantity;
    }

}
