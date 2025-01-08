package com.ohgiraffers.mapping.section03.compositeKey;

import lombok.*;

// 좋아요 복합키로 많이 쓰임 같은 1번이지만 앞에게 무엇이냐에 따라서 다른 행이 되는 것
// 인조 식별자 만들어서 관리해도 되긴 함
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LikeDTO {

    private int likedMemberNo;
    private int likedBookNo;

}
