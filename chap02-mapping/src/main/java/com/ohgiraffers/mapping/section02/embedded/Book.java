package com.ohgiraffers.mapping.section02.embedded;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_book")
public class Book {

    @Id
    @Column(name = "book_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookNo;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "anthor")
    private String author; // 저자

    @Column(name = "phlisher")
    private String publisher; // 출판사

    @Column(name = "created_date")
    private LocalDate createdDate; // 출판일

    // 이렇게 컬럼 추가할 필요 없이 하나로 뭉탱거릴 수 있다.
    @Embedded
    private Price price;

    public Book() {
    }

    public Book(String bookTitle, String author, String publisher, LocalDate createdDate, Price price) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.createdDate = createdDate;
        this.price = price;
    }
}
