package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryGenerator {

    // 실제 db에 존재하는 것을 바탕으로 공장을 만드는데 그와 관련된 설정을 해주어야 합니다.
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpatest");

    // 싱글톤이니 외부에서는 생성못하게 기본생성자는 private 로 접근 제한
    private EntityManagerFactoryGenerator(){}

    // static 인스턴스를 리턴해주는 메소드 생성
    public static EntityManagerFactory getInstance(){
        return factory;
    }
}
