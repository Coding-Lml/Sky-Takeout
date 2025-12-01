package com.sky.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 购物车前端传来的DTO对象
 * 菜品id、套餐id
 * 菜品口味
 */
@Data
public class ShoppingCartDTO implements Serializable {

    private Long dishId;
    private Long setmealId;
    private String dishFlavor;

}
