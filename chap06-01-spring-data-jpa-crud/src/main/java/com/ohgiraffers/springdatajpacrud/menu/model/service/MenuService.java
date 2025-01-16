package com.ohgiraffers.springdatajpacrud.menu.model.service;

import com.ohgiraffers.springdatajpacrud.menu.entity.Menu;
import com.ohgiraffers.springdatajpacrud.menu.model.dao.CategoryRepository;
import com.ohgiraffers.springdatajpacrud.menu.model.dao.MenuRepository;
import com.ohgiraffers.springdatajpacrud.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpacrud.menu.model.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;

    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;

    public Page<MenuDTO> findMenuListByPaging(Pageable pageable) {

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending()
        );

        Page<Menu> menuList = repository.findAll(pageable);

        List<MenuDTO> menuDTOList = menuList.stream()
                .map(menu -> {
                    MenuDTO menuDTO = modelMapper.map(menu, MenuDTO.class);
                    if (menu.getCategoryCode() != null) {
                        CategoryDTO categoryDTO = modelMapper.map(menu.getCategoryCode(), CategoryDTO.class);
                        menuDTO.setCategoryDTO(categoryDTO);
                    }
                    return menuDTO;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(menuDTOList, pageable, menuList.getTotalElements());
    }
}
