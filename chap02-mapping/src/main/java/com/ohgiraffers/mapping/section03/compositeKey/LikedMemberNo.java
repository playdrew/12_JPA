package com.ohgiraffers.mapping.section03.compositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LikedMemberNo {

    @Column(name = "like_member_no")
    private int likedMemberNo;

    public LikedMemberNo() {
    }

    public LikedMemberNo(int likedMemberNo) {
        this.likedMemberNo = likedMemberNo;
    }
}
