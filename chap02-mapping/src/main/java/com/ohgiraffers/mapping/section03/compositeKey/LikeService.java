package com.ohgiraffers.mapping.section03.compositeKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    public LikeRepository repository;

    @Transactional
    public void registLikeInfo(LikeDTO likeDTO) {

        Like like = new Like(
          new LikedCompositKey(
                  new LikedMemberNo(likeDTO.getLikedMemberNo()),
                  new LikeBookNo(likeDTO.getLikedBookNo())
          )
        );

        // 레파지토리한테 영속화 시켜달라고 하기
        repository.save(like);
    }
}
