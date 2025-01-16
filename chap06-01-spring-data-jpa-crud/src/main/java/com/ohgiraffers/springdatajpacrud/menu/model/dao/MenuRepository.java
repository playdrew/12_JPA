package com.ohgiraffers.springdatajpacrud.menu.model.dao;

import com.ohgiraffers.springdatajpacrud.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {


}
