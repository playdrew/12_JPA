package com.ohgiraffers.springdatajpa.config;


import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ohgiraffers.springdatajpa")
public class BeanConfig {

//    @Bean
//    public ModelMapper modelMapper(){
//
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                   .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
//                   .setFieldMatchingEnabled(true);
//
//        return modelMapper;
//
//    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        // Custom mapping 설정
        modelMapper.addMappings(new PropertyMap<Menu, MenuDTO>() {
            @Override
            protected void configure() {
                map().setCategoryCode(source.getCategoryCode().getCategoryCode()); // 명시적으로 매핑
            }
        });

        return modelMapper;
    }
}
