package com.ohgiraffers.mapping.section03.idclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CartCompositeKey   {

    // 이 두가지 필드를 복합키 아이디로서 사용하고 싶다.
    private int cartOwner; // 카트주인
    private int addedBook; // 추가 된 책


}
