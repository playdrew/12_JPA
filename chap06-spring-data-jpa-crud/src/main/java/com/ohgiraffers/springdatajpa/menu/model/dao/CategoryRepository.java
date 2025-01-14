package com.ohgiraffers.springdatajpa.menu.model.dao;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    /* 여기서는 직접 쿼리문을 우리가 작성해보자 */
    // 네이티브 쿼리문법 FROM 절이 엔티티명이 아니라 테이블명이라서
    // nativeQuery = true 가
    @Query(value = "SELECT * FROM TBL_CATEGORY ORDER BY CATEGORY_CODE",nativeQuery = true)
    // 홀수면 안되고 나누었을때 소수면 안되고 등등 조건절이 복잡해지면 네이티브 쿼리를 사용하면 좋다
    List<Category> findAllCategory();
}
