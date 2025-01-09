package com.ohgiraffers.mapping.section03.idclass;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class IdClassTest {

    // 임베디드 아이디는 하나의 클래스 내부에 두가지 필드를 정의
    // 2번 방식은 반대로 하나의 클래스에 복합키 2개 설정한다음에
    // 실제 엔티티에는 얘네들을 하나씩 끄집어내서 하나하나 필드로서 가지고 있는거에요
    // 어찌보면 직관적인 데이터베이스와 친화적 방식은 2번
    // 클래스적은 방식은 임베디드아이디 훨씬 더 클래스 즉 객체 지향적인 방식입니다.

    /* comment.
     *   복합키가 존재하는 테이블의 매핑 전략
     *   1. EmbeddedId
     *   - @Embeddable 클래스에 복합키를 정의하고
     *   - 사용할 엔티티에서 @Embedded 로 복합키 클래스를 매핑한다.
     *   2. @IdClass
     *   - 복합키를 필드로 정의한 클래스를 이용해서 엔티티 클래스에
     *   - @IdClass 라는 어노테이션으로 매핑을 한다.
     *   이 둘의 차이
     *   - 1번 방식은 복합키로 묶인 클래스를 ID 로 사용하여
     *   - 객체 지향적인 방식이다.
     *   - 2번 방식은 관계형 데이터베이스 즉 DB 친화적인 방식이다.
     * */

    @Autowired
    private CartService service;

    private static Stream<Arguments> getInfo(){
        return Stream.of(
                Arguments.of(1,1,10),
                Arguments.of(1,2,5),
                Arguments.of(2,1,13),
                Arguments.of(2,2,20)
        );
    }
    // 0번째 argument 가 {0} 1번째 argument 가 {1} 2번째 argument 가 {2}
    @ParameterizedTest(name = "{0}번 회원이 {1}번 책을 카트에 {2}권 담기")
    @MethodSource("getInfo") // 메소드로 넘어오는 값을 저장할 준비마침
    void testIdClass(int cartOwner, int addedBookNo , int quantity){
        // 인제 우리가 아까 만들오둔 dto 를 이용해서 값을 집어넣어요

        // 매개변수로 받고 있는 녀석들을 여기 생성자에 넣어요
        CartDTO cart = new CartDTO(
                cartOwner,addedBookNo,quantity
        );

        Assertions.assertDoesNotThrow(
                () -> service.addItemToCart(cart)
        );
    }


}
