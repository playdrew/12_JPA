package com.ohgiraffers.springdatajpacrud.menu.controller;

import com.ohgiraffers.springdatajpacrud.common.Pagenation;
import com.ohgiraffers.springdatajpacrud.common.PagingButton;
import com.ohgiraffers.springdatajpacrud.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpacrud.menu.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/list")
    public String findAllMenu(Model model, @PageableDefault Pageable pageable){
        Page<MenuDTO> menuList = menuService.findMenuListByPaging(pageable);
        PagingButton pagingButton = Pagenation.getPagingInfo(menuList);
        model.addAttribute("menus", menuList);
        model.addAttribute("paging", pagingButton);
        return "menu/list";
    }
}
