package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.common.Pagenation;
import com.ohgiraffers.springdatajpa.common.PagingButton;
import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor // 필드에 final 키워드가 붙은 녀석들을 자동으로 생성자 주입을 해준다.
@Slf4j // 롬북 라이브러리에서 제공하는 로깅 관련 어노테이션
public class MenuController {

    private final MenuService menuSerivce;

    // 경로를 타고 넘어오는 변수로서 저장
    @GetMapping("/{menuCode}")
    public String findMenuByPathVariable(@PathVariable int menuCode, Model model){

        MenuDTO resultMenu = menuSerivce.findMenuByMenuCode(menuCode);

        model.addAttribute("result",resultMenu);

        return "menu/detail";
    }

    @GetMapping("/list")
    public String findAllMenu(Model model, @PageableDefault Pageable pageable){
//        페이징 처리하지 않은 findAll
//        List<MenuDTO> menuList = menuSerivce.findMenuList();
//        model.addAttribute("menus",menuList);

        log.info("pageble : {}" , pageable);

        Page<MenuDTO> menuList = menuSerivce.findMenuListByPaging(pageable);

        log.info("조회한 내용 목록 : {}" , menuList.getContent());
        log.info("총 페이지 수 : {}" , menuList.getTotalPages());
        log.info("총 메뉴의 수 : {}" , menuList.getTotalElements());
        log.info("해당 페이지에 표현 될 요소의 수 : {}" , menuList.getSize());
        log.info("첫 페이지 여부 : {}" , menuList.isFirst());
        log.info("마지막 페이지 여부 : {}" , menuList.isLast());
        log.info("정렬 방식 : {}" ,  menuList.getSort());
        log.info("여러 페이지 중 현재 인덱스 : {}" , menuList.getNumber());

        PagingButton pagingButton = Pagenation.getPagingInfo(menuList);
        model.addAttribute("menus",menuList);
        model.addAttribute("paging",pagingButton);

        return "menu/list";
    }

    @GetMapping("/querymethod")
    public void queryMethod(){}

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam int menuPrice , Model model){
        List<MenuDTO> menuList = menuSerivce.findMenuByMenuPrice(menuPrice);
        model.addAttribute("menuList",menuList);
        model.addAttribute("price",menuPrice);

        return "menu/searchResult";
    }

    // 핸들할 핸들러메소드
    @GetMapping("/regist")
    public void registPage(){}

    // produces = "application/json; charset=UTF-8" 스프링은 자바객체를 알아서 컨버터를 해줘서 ResponseBody 만 있으면 됨
    @GetMapping(value = "/category", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> findCategoryList(){
        // return 구문이 view 를 지정하는 것이 아닌 , Data 를 return 하고 있다.
        return menuSerivce.findAllCategory();
    }
}
