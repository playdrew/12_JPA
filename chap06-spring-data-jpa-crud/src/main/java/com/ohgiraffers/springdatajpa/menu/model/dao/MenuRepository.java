package com.ohgiraffers.springdatajpa.menu.model.dao;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/* comment.
*   Repository 란?
*   EntityManager 와 EntityManagerFactory, EntityTransaction 을
*   구현한 클래스 이다. 따라서 이제 우리는 미리 구현 된 클래스를 상속받아
*   더이상 매니저를 명시적으로 호출할 필요가 없다
*   JpaRepository < 사용할 엔티티, 해당 엔티티 식별자 > */

// Menu 는 엔티티를 만드는 클래스이름을 넣고 키값자료형타입(객체타입) int 지만 객체타입으로 넣어야 한다.
@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {

    // 정렬하지 않음
    // List<Menu> findByMenuPriceGreaterThan(int menuPrice); // 이런식으로 쿼리문 작성도 할 수 있습니다.
    // 이번엔 order by
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(int menuPrice); // 이런식으로 쿼리문 작성도 할 수 있습니다.
    // List<Menu> findByMenuPriceGreaterThanAndMenuCodeGreaterThan(int menuPrice,int menuCode); 이렇게 하면 가독성이 나쁘다 단점 차라리 native query를 사용해 직접 쿼리문작성
    // 하는게 나을 수도 있음 근데 너무 길죠
}
