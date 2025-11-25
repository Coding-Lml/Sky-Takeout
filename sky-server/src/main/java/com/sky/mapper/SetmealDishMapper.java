package com.sky.mapper;

import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 下午1:30
 */
@Mapper
public interface SetmealDishMapper {
     List<Long> getSetmealIdsByDishIds(List<Long> ids);

     void insertBatch(List<SetmealDish> setmealDishes);

     @Select("select * from setmeal_dish where setmeal_id = #{id}")
     List<SetmealDish> queryBySetmealId(Long id);

     @Delete("delete from setmeal_dish where id = #{id}")
     void deleteById(Long id);

     List<DishItemVO> query(Long id);
}
