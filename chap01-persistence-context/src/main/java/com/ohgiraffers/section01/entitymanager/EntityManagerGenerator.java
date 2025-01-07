package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EntityManagerGenerator {

    // 아깐 공장을 만들었으니 공장의 일꾼들을 만들어요
    public static EntityManager getInstance(){
        // 아까 만든 공장 인스턴스 생성
        EntityManagerFactory factory =
                EntityManagerFactoryGenerator.getInstance();

        return factory.createEntityManager();
    }
}
