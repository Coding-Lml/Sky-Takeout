package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 下午6:17
 */
@Mapper
public interface SetmealMapper {

    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);
}
