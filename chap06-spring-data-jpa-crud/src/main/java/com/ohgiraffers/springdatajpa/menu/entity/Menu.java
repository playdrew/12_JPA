package com.ohgiraffers.springdatajpa.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
//@Setter // 지양
@ToString
// setter 제외하고 셋팅
// @Builder(toBuilder = true) // update 를 위한
// 빌더가 실제로 어떻게 동작하는지 직접 사용
public class Menu {

    // 테이블과 일치하는 필드 작성
    @Id
    @Column(name = "menu_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

//    public void setMenuName(String menuName){
//        this.menuName = menuName;
//    }

    /* 3. builder 패턴 직접 구현 */
    public Menu menuName(String var){
        this.menuName = var;
        return this;
    }

    public Menu builder(){
        return new Menu(menuCode,menuName,menuPrice,categoryCode,orderableStatus);
    }

}
