package com.ohgiraffers.mapping.section03.idclass;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class CartDTO {

    private int cartOwnerMemberNo;
    private int addedBookNo;
    private int quantity;

}
