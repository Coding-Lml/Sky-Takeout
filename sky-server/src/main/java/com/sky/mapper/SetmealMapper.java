package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 下午6:17
 */
@Mapper
public interface SetmealMapper {

    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);


    Page<SetmealVO> query(SetmealPageQueryDTO setmealPageQueryDTO);

    @Delete("delete from setmeal where id = #{id}")
    void deleteById(Long id);

    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    @Select("SELECT s.*, c.name AS categoryName " +
            "FROM setmeal s " +
            "LEFT JOIN category c ON s.category_id = c.id " +
            "WHERE s.id = #{id}")
    SetmealVO get(Long id);

    @Select("select * from setmeal where category_id = #{category}")
    List<Setmeal> getByCategoryId(Long category);
}
