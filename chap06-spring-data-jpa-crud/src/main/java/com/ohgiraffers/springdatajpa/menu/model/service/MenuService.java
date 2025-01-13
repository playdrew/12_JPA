package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.dao.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.model.dao.MenuRepository;
import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;
    // Bean 으로 등록한 modelmapper 의존성 주입
    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    /* 메뉴 코드로 특정 메뉴 조회하기 */
    // db 가 아니라 entity 에서 찾기를 해야합니다.
    public MenuDTO findMenuByMenuCode(int menuCode) {

        Menu foundMenu = repository.findById(menuCode)
                                   .orElseThrow(IllegalArgumentException::new); // 잘못된 파라미터가 넘겨왔을 때 예외 처리 필수

        // modelmapper 의존성 추가
        // entity -> dto 로 변환 엔티티타입이 foundMenu 라는 녀석인데 그걸 MenuDTO 로 변환하겠다
        // map(변환 대상, 변환할 타입)
        return modelMapper.map(foundMenu , MenuDTO.class);

    }

    /* 페이징 처리하지 않은 메뉴 리스트 조회하기*/
    public List<MenuDTO> findMenuList() {

        // menuList 는 엔티티타입의 리스트
        // List<Menu> menuList = repository.findAll(); // 정렬없는 findAll
        List<Menu> menuList = repository.findAll(Sort.by("menuPrice").descending()); // 메뉴가격순 내림차순 정렬

        // stream : 컬렉션(List 등등) 데이터를 처리하기 위해 나열
        // 데이터를 연속처리하기 위해 나열 나열된 데이터를 각각 map 이란 메소드를 통해서
        // map : 스트림화 된 데이터를 꺼내 매핑 및 반환
        // collect : 스트림화 된 데이터를 다시 List 로 반환
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());

    }

    /* 페이징 처리를 한 메뉴 전체 조회 */
    // 스프링데이터도메인 임폴트
    public Page<MenuDTO> findMenuListByPaging(Pageable pageable) {

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending()
        );

        Page<Menu> menuList = repository.findAll(pageable);

        return menuList.map(
                menu -> modelMapper.map(menu, MenuDTO.class)
        );
    }

    public List<MenuDTO> findMenuByMenuPrice(int menuPrice) {

        // findBy 한다음 ctrl + enter
        // 쿼리문이 단순하다면 이렇게 쿼리문 작성없이 작성도 가능하다.
        List<Menu> menuList = repository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);

        // 엔티티를 dto 로 변환
        return menuList.stream().map(
                menu -> modelMapper.map(menu,MenuDTO.class)).collect(Collectors.toList());
    }

    public List<CategoryDTO> findAllCategory() {
        // 카테고리 엔티티에서 조회
        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream().map(
                category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }
}

