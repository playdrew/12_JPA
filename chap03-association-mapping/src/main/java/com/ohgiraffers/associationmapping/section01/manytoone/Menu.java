package com.ohgiraffers.associationmapping.section01.manytoone;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.naming.Name;

@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Menu {
    
    @Id
    @Column(name = "menu_code")
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private int menuCode;
    
    @Column(name = "menu_name")
    private String menuName;
    
    @Column(name = "menu_price")
    private int menuPrice;
    
    /* comment.
    *   [영속성 전이]
    *   특정 엔티티를 영속화(관리) 할 때 , 연관관계에 있는
    *   엔티티도 같이 영속화 한다는 의미이다.
    *   이 클래스에 예를 들면
    *   1번 메뉴를 영속화 할 때, 포함하는 Category 엔티티도
    *   같이 영속화 한다는 의미이다.
    * */

    // 엔티티는 특별해야함. 값 수정을 방지하기 위해서 setter 생략 setter 상관없지만 비추천
    
    @ManyToOne(cascade = CascadeType.PERSIST) // persist 영속화 1번메뉴조회 카테고리클래스영속화 관리하겠다는 것
    @JoinColumn(name = "category_code")
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;
}
