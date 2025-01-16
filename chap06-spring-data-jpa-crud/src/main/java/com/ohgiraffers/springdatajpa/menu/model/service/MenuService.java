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
import org.springframework.transaction.annotation.Transactional;

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

        // 카테고리 리스트를 쫙 펼친다음에 category 라는 지역변수에 매핑을 해주는데 그게 dto 형태이다.
        // 그리고 펼친 것을 다시 뭉태기로 묶어서 List 화 시켜준다.
        return categoryList.stream().map(
                    category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    // DML 구문이기 때문에 Transactional 필요
    @Transactional
    public void registNewMenu(MenuDTO newMenu) {

        // save 는 데이터베이스에 엔티티 전달할테니 저장해줘라는 메소드
        // newMenu 가 아니라 엔터티를 넘겨주어야함
        // 지금까진 엔터티를 dto 로 변환 이젠 dto 를 엔터티로 변환
        // dml 구문에서는 dto 타입을 Entity 로 변환을 해야
        // PersistenceContext == jpa 가 관리를 해준다.
        repository.save(modelMapper.map(newMenu,Menu.class));
        // 동시에 넣는 경우 주인과 주인과 아닌 경우를 잘 구분해서 넣기
        // fk 를 가지고 있는게 주인 mappedBy 는 속성은 연관관계의 주인이 아닌 쪽(외래키가 없는)에
        //   사용된다.
    }

    @Transactional
    public void modifyMenu(MenuDTO modifyMenu) {
        // save 인설트 find 셀렉트 delete 삭제
        // update 는 없다.

        /* update 는 엔티티를 특정해서 필드의 값을 변경해주면 된다. */
        /* JPA 는 변경감지 기능이 있다.
        *  따라서 하나의 엔티티를 특정해서 필드 값을 변경하면
        *  변경된 값으로 flush(반영)을 해준다.*/

        // 메뉴(Menu) 엔티티 특정하기
        // 엔티티 찾기 (특정)
        // optional 은 예외처리를 해야함

        Menu foundMenu = repository.findById(modifyMenu.getMenuCode()).orElseThrow(IllegalArgumentException::new);

        System.out.println("찾은 Entity 값 foundMenu = " + foundMenu);

        /* 1. setter 를 통해 update 기능 - 지양한다. */
        // foundMenu.setMenuName(modifyMenu.getMenuName());

        System.out.println("setter 사용후 foundMenu : " + foundMenu);

        /* 2. @Builder 를 통해 update 기능 */
        // 새로운 값이 추가하면 다시한번 인스턴스를 추가해준다 의존성추가하듯이
//        foundMenu = foundMenu.toBuilder()
//                    .menuName(modifyMenu.getMenuName()).build();

        // build 를 통해서 foundMenu 새롭게 탄생 시켰으니
        // save 메소드를 통해 JPA 전달
//        repository.save(foundMenu);

        /* 3. Entity 내부에 Builder 패턴을 구현 */
        foundMenu = foundMenu.menuName(modifyMenu.getMenuName())
                             .builder();
        // save 라고 하지만 인설트 쿼리문 안돌아가고 변경감지한것만 퍼시스트한것이에요
        repository.save(foundMenu);
    }

    @Transactional
    public void deleteMenu(int menuCode) {
        // menuCode 를 특정후(select 후 삭제)
        repository.deleteById(menuCode);
    }
}

// 네이티브 쿼리를 사용할땐 조건절이 복잡할때 정확하게 특정 못할때가 있음..
// join 이 많으면 마이바티스 사용하는게 좋아요.
