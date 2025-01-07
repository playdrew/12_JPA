package com.ohgiraffers.section02.crud;

// 테스트코드는 main 영역이 잘 동작을 하는가 안하는가 검증하는 곳
// main 해당하는 인스턴스 가져와서 메소드를 실행시켜보는 것

import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class EntityManagerCRUDTest {

    /* comments
    *   Test 클래스는 기존 Main 영역의 메소드를 테스트 하기 위함이다.
    *   따라서 검증할 클래스의 인스턴스를 생성해서 테스트 하는 용도로
    *   사용이 된다.
    * */

    private EntityManagerCRUD crud;

    @BeforeEach
    void initManager(){
        // 테스트 메소드 실행 전 crud 클래스 인스턴스 생성
        this.crud = new EntityManagerCRUD();
    }

    @AfterEach
    void rollback(){
        // 테스트가 실제 DB 에 반영되지 않게 rollback 설정
        EntityTransaction transaction
                = crud.getManagerInstance().getTransaction();

        // 모든 권한은 매니저에게 entity 를 관리하게 넘겨준다.
   //     transaction.rollback();
    }

    /* comment.
        테스트 시 여러 값들을 이용해서 검증이 필요한 경우 경우의 수
        별로 테스트 메소드를 작성해야한다.
        ParameterizedTest 는 경우의 수 만큼 반복해야 할 작업을
        줄여줄 수 있다
        파라미터로 전달한 값을 목록만큼 반복적으로 테스트 메소드를
        실행해준다.
     */

    @ParameterizedTest
    // 여러 개의 테스트 전용 파라미터를 전달. 쉼표로 값을 구분할 수 있다.
    // menuCode 1 expected 1 후 다음 2 , 2 다음 3, 3
    @CsvSource({"1,1","2,2","3,3"})
    void testFindByCode(int menuCode , int expected){
    // <class>com.ohgiraffers.section02.crud.Menu</class> 추가
        Menu foundMenu = crud.findMenuByMenuCode(menuCode);
        System.out.println("foundMenu = " + foundMenu);
        Assertions.assertEquals(expected, foundMenu.getMenuCode());
    }

    // 전달인자를 한꺼번에 묶어서 전달
    private static Stream<Arguments> newMenu(){
        return Stream.of(
                Arguments.of(
                        "불고기 백반",
                        11000,
                        4,
                        "Y"
                )
        );
    }

    @ParameterizedTest
    @DisplayName("새로운 메뉴 insert Test")
    @MethodSource("newMenu")
    void testInsertNewMenu(String name, int price , int code , String orderable){

        Menu newMenu = new Menu(name,price,code,orderable);
        System.out.println("newMenu = " + newMenu);

        // 새롭게 만든 Menu 객체 전달
        Long count = crud.saveAndReturnCount(newMenu);

        Assertions.assertEquals(22,count);
    }

    @ParameterizedTest
    @DisplayName("메뉴 이름 수정 테스트")
    // 24는 메뉴 코드인듯
    @CsvSource("24,우삼겹백반")
    void modifyTestMenu(int code,String name){

        Menu modifyMenu = crud.modifyMenuName(code,name);

        Assertions.assertEquals(name,modifyMenu.getMenuName());
    }

    @ParameterizedTest
    @DisplayName("메뉴 코드로 메뉴 삭제")
    // ints 인 이유 여러개 할 수 있다.
    @ValueSource(ints = {24})
    void testRemoveMenu(int code){
        Long count = crud.removeAndReturnCount(code);

        Assertions.assertEquals(22,count);
    }

}
