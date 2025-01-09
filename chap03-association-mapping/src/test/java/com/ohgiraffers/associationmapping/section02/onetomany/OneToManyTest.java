package com.ohgiraffers.associationmapping.section02.onetomany;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OneToManyTest {

    @Autowired
    private OneToManyService service;

    @DisplayName("1:N 연관관계 객체 그래프 탐색 조회")
    @Test
    void oneToManyFind(){

        // 전에는 메뉴코드였는데 이번엔 카테고리코드
        int categoryCode = 10;

        Category category = service.findCategory(categoryCode);

        System.out.println(category.getMenuList());

        Assertions.assertNotNull(category);
    }

    /* comment.
    *   DML 구문을 사용할 때 @Transactional 어노테이션을 사용하는데
    *   이번에는 조회임에도 불구하고 사용하는 이유는
    *   EX) 10 카테고리를 갖는 menu 가 10개면 select 그만큼 해야한다.
    *   1개의 엔티티가 여러 개의 엔티티를 로드 해야하는 상황은
    *   성능상의 이슈가 발생 할 수 있다.
    *   따라서 일단 카테고리만 조회 후 필요 시에 로드를 하게 된다.
    *   @OneToMany(fetch = FetchType.lazy) 기본 설정
    *   지연 로딩이라고 하는 것인데, 카테고리 조회시 연관 된 메뉴들은
    *   부르기전까지 조회하지 않고 부를때 로딩을 하는 것이다.
    *   @ManyToOne(fetch = FetchType.EAGER)
    *   1개의 엔티티를 조회할 때 1개만 조회하면 되므로 즉시 로딩(이른 로딩)
    *   이 디폴트 값이다.
    * */

    // fetch 가 낚아채는 것 카테고리하나를 find 조회할때 카테고리를 낚아채야합
}