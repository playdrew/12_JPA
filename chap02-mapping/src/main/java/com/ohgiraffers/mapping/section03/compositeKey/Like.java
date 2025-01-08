package com.ohgiraffers.mapping.section03.compositeKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
//name = "like" like 가 예약어로 존재하므로 이건 안됨 예약어를 테이블 명칭으로 사용하면 안됨
@Table(name = "tbl_like")
public class Like {

    @EmbeddedId // pk
    private LikedCompositKey likeInfo;

    public Like() {
    }

    public Like(LikedCompositKey likeInfo) {
        this.likeInfo = likeInfo;
    }
}
