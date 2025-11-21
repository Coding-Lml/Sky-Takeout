package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 下午1:30
 */
@Mapper
public interface SetmealDishMapper {
     List<Long> getSetmealIdsByDishIds(List<Long> ids);
}
