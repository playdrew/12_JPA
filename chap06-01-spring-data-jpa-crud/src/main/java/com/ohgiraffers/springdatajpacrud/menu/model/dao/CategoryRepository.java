package com.ohgiraffers.springdatajpacrud.menu.model.dao;

import com.ohgiraffers.springdatajpacrud.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
