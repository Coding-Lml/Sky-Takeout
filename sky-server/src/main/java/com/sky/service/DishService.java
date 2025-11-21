package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 上午12:41
 */
public interface DishService {

    void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    void updateStatus(Integer status, Long id);

    DishVO findById(Long id);

    void update(DishDTO dishDTO);
}
