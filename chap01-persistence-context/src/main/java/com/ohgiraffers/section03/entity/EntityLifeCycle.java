// 영속성 컨텍스트에 엔티티 등록 해제 등등 생명 주기 확인

package com.ohgiraffers.section03.entity;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;

public class EntityLifeCycle {

    private EntityManager manager;

    public EntityManager getManagerInstance() {
        return manager;
    }

    public Menu findMenuByMenuCode(int menuCode) {

        manager = EntityManagerGenerator.getInstance();

        // 메뉴 엔티티(Menu.class)를 menuCode 를 기준으로 찾아달라
        return manager.find(Menu.class,menuCode);

    }
}
