package com.ohgiraffers.springdatajpacrud.menu.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuDTO {

    private int menuCode;
    private String menuName;
    private int menuPrice;
    private CategoryDTO categoryDTO;
    private String orderableStatus;
}