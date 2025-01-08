package com.ohgiraffers.mapping.section03.compositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// 엔티티는 setter는 없다.
@Embeddable
public class LikeBookNo {

    @Column(name = "liked_book_no")
    private int likedBookNo;

    public LikeBookNo() {
    }

    public LikeBookNo(int likedBookNo) {
        this.likedBookNo = likedBookNo;
    }
}
