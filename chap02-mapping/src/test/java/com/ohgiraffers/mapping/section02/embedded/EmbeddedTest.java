package com.ohgiraffers.mapping.section02.embedded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

@SpringBootTest
public class EmbeddedTest {

    @Autowired
    private BookService service;

    private static Stream<Arguments> getBook(){
        return Stream.of(
                Arguments.of(
                        "JPA 의 정석",
                        "너구리",
                        "하이미디어출판" ,
                        LocalDate.now(),
                        35000,
                        0.1
                )
        );
    }

    // 중복되는 컬럼이 있으면 임베드가 가능하게 만든다음에 필요한 엔티티에서 가져다 쓰는 것입니다.

    @ParameterizedTest
    @DisplayName("임베디드 시 테스트")
    @MethodSource("getBook")
    void testEmbeddedPrice(String bookTitle, String author, String publisher , LocalDate createdDate , int regularPrice , double discountRate){

        BookRegistDTO newBook = new BookRegistDTO(
                bookTitle,author,publisher,createdDate,regularPrice,discountRate
        );

        Assertions.assertDoesNotThrow(
                () -> service.registBook(newBook)
        );
    }
}
