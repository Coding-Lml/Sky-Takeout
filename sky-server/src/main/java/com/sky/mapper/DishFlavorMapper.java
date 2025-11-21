package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 上午12:56
 */
@Mapper
public interface DishFlavorMapper {
    /**
     * 批量添加口味数据
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品id删除口味数据
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getDishFlavorByDishId(Long id);
}
