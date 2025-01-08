package com.ohgiraffers.mapping.section03.compositeKey;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;

@Embeddable
public class LikedCompositKey {

    // import 같은 느낌
    @Embedded
    private LikedMemberNo likedMemberNo;

    // import 같은 느낌
    @Embedded
    private LikeBookNo likedBookNo;

    public LikedCompositKey() {
    }

    public LikedCompositKey(LikedMemberNo likedMemberNo, LikeBookNo likedBookNo) {
        this.likedMemberNo = likedMemberNo;
        this.likedBookNo = likedBookNo;
    }
}
