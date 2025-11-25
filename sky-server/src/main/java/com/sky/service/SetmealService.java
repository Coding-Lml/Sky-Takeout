package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 下午6:13
 */
public interface SetmealService {
    void save(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteByIds(List<Long> ids);

    void status(Integer status, Long id);

    SetmealVO getById(Long id);

    void update(SetmealDTO setmealDTO);

    List<Setmeal> getByCategoryId(Long category);

    List<DishItemVO> getBySetmealId(Long id);
}
