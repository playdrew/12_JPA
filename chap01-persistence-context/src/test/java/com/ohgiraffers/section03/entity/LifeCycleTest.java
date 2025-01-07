package com.ohgiraffers.section03.entity;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class LifeCycleTest {

    private EntityLifeCycle lifeCycle;

    // 테스트 메소드가 실행될때마다 인스턴스 생성
    @BeforeEach
    void setLifeCycle(){
        this.lifeCycle = new EntityLifeCycle();
    }

    // 하나의 데이터베이스에 관리 안됨 어떤 짓을 해도 db에 반영이 안된다는 것
    @ParameterizedTest
    @DisplayName("비영속 테스트(관리하지 않음)")
    @ValueSource(ints = {1,2})
    void testNotManaged(int menuCode){

        Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);

        // 컴파일에러이유는 모든 필드를 초기화하는 생성자가 없음
        Menu newMenu = new Menu(
          foundMenu.getMenuCode(),
          foundMenu.getMenuName(),
          foundMenu.getMenuPrice(),
          foundMenu.getCategoryCode(),
          foundMenu.getOrderableStatus()
        );

        Assertions.assertNotEquals(foundMenu,newMenu);

    }

    @ParameterizedTest
    @DisplayName("다른 엔티티 메니저가 관리하는 영속성 테스트")
    @ValueSource(ints={1,2})
    void testOtherManager(int menuCode){
        Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
        Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);

        Assertions.assertEquals(menu1,menu2);
    }

    // 위와는 다르게 assertEquals 테스트를 통과하는 모습을 보이고 있습니다.
    @ParameterizedTest
    @DisplayName("같은 엔티티 매니저가 관리하는 영속성 테스트")
    @ValueSource(ints={1,2})
    void testSameManager(int menuCode){
        EntityManager manager = EntityManagerGenerator.getInstance();

        Menu menu1 = manager.find(Menu.class,menuCode);
        Menu menu2 = manager.find(Menu.class,menuCode);

        Assertions.assertEquals(menu1,menu2);
    }

    /* comment.
    *   엔티티 매니저가 영속성 컨텍스트에 엔티티 객체를 저장(persist)
    *   하게 된다면 영속성 컨텍스트가 관리할 수 있게 되며,
    *   이를 영속 상태라고 한다.
    *   find() , createQuery() 을 사용한 조회도 자동으로 영속 상태가 된다.
    *   영속 상태인 엔티티 객체는 PK 로 조회를 하면 이미 관리가 되고 있기
    *   때문에 같은 객체(인스턴스)를 반한하게 된다.
    * */

    // 영속상태인 것을 DETACH 제외를 시켜봅시다.
    @ParameterizedTest
    @DisplayName("준영속화 detach 테스트")
    @CsvSource({"11,1000","12,1000"})
    void testDetachEntity(int menuCode , int menuPrice){
        // jpa 가 굴려가려면 영속성 컨텍스트가 필요한데 팩토리가 필요한데
        // 팩토리를 굴려가게하려면 매니저가 있어야 하는데 이게 매니저를 생성하는 코드
        EntityManager manager
                = EntityManagerGenerator.getInstance();

        // 명령을 내리기전에 트랜잭션을 가지게 끔 하고 만든 코드
        // 한 사람에게 트랜젝션을 넣어줬고
        EntityTransaction transaction
                = manager.getTransaction();

        // find가 select 문 그 안에 있는 데이터중에 메뉴코드를 넣어서 가져와달란것
        // 11번 있는데 11번 해당하는에 12번 있는데 12번 해당하는애 달려있음
        // 하나의 row 를 가져와서 2개의 row 를 가져오는 foundMenu
        Menu foundMenu = manager.find(Menu.class,menuCode);

        // 트랙젝션으로 인하여 데이터를 추가하기 위한 검증 데이터베이스에 맞게 들어가는지 검증
        transaction.begin();

        // detach : 특정 엔티티만 준영속 상태(관리하지 않는 상태)로 변경
        // manager.detach(foundMenu); 주석하고 실행도 해보세요 주석해제하고 하면 update문 안돌아감
        // detach 관리하고 있는 부분을 때어났으므로 더이상 관리를 하지 못해요
        manager.detach(foundMenu);
        // 하나의 메뉴중에 가격만 바꾸겠다.가격 2개다 값을 update
        foundMenu.setMenuPrice(menuPrice);
        // flush 까지 하면 데이터베이스에 넣는 것인데 잘 들어가는지만 테스트
        manager.flush();

        //manager.find(Menu.class,menuCode)는 87과 같아서 foundMenu 로 바꿔도 상관없어요
        Assertions.assertEquals(menuPrice,manager.find(Menu.class,menuCode).getMenuPrice());
        // db에 적용을 하려면 commit 해야함 rollback 이 아니라
        transaction.rollback();

    }

    @ParameterizedTest
    @DisplayName("준영속화 detach 후 재영속화(merge) 테스트")
    @CsvSource({"11,1000","12,1000"})
    void testDetachAndPersist(int menuCode , int menuPrice){

        EntityManager manager
                = EntityManagerGenerator.getInstance();

        EntityTransaction transaction
                = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class,menuCode);

        transaction.begin();

        manager.detach(foundMenu); // 관리포기
        foundMenu.setMenuPrice(menuPrice); // 관리포기상태 값 변경

        /* comment.
        *   파라미터로 넘어온 foundMenu 준영속 엔티티 객체의 식발자 값으로
        *   1차 캐시에서 조회, 만약 1차 캐시에 엔티티가 없으면 DB 에서 엔티티
        *   조회 후 1차 캐시에 저장한다.
        *   조회한 영속 엔티티 객체에 준영속 상태의 엔티티 객체의 값을 병합
        *   한 뒤 엔티티 객체를 반환하게 된다.
        * */
        manager.merge(foundMenu); // flush 이전에 병합해달라는 요청 제대로 update 된다.
        manager.flush();

        Assertions.assertEquals(menuPrice,manager.find(Menu.class,menuCode).getMenuPrice());
        transaction.rollback();
    }

    @ParameterizedTest
    @DisplayName("영속성 엔티티 삭제 remove")
    @ValueSource(ints={1})
    void testRemoveEntity(int menuCode){

        EntityManager manager =
                EntityManagerGenerator.getInstance();

        EntityTransaction transaction =
                manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class,menuCode);

        transaction.begin();

        /* comment.
        *   remove() 엔티티를 영속성 컨텍스트 및 데이터베이스에서
        *   제거한다.
        *   단 트렌젝션을 제어하지 않으면 데이터베이스에 영구 반영되지는
        *   않는다.
        *   트렌젝션을 커밋, 플러쉬 하는 순간 영속성 컨텍스트에서 관리하는
        *   엔티티 객체를 DB 에 반영을 하게 된다.
        * */

        // 찾았으니까 find 우리가 찾아온 메뉴를 remove 할거라고 요청해요
        manager.remove(foundMenu);

        // remove 반영 flush 는 영속성 컨텍스트에서 db에 쏴주는 건 맞는데 영향을 미치진 않아요
        // 영향을 미치려면 commit 을 해야합니다. flush 가 없더라도 commit 이 flush 를 가지고 있음
        manager.flush();

        // 같은 메뉴 코드로 동일한 데이터 찾아지는 지 확인 ( null 값 )
        Menu refoundMenu = manager.find(Menu.class,menuCode);

        Assertions.assertNull(refoundMenu);

    }
}