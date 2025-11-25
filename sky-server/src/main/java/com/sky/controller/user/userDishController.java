package com.sky.controller.user;

import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/25 下午8:28
 */
@RestController
@RequestMapping("/user/dish")
@Slf4j
public class userDishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @GetMapping("/list")
    public Result<List<DishVO>> getById(@RequestParam Long categoryId) {

        List<DishVO> DishVOList = new ArrayList<>();

        // 根据分类id查询Dish菜品
        List<Dish> list = dishService.queryByCategoryId(categoryId);
        // 根据查到的菜品id来查询口味数据
        for (Dish dish : list) {
            Long id = dish.getId();
            List<DishFlavor> dishFlavors = dishFlavorMapper.getDishFlavorByDishId(id);
            DishVO dishVO = new DishVO();

            // Dish数据加口味数据封装到DishVO中
            BeanUtils.copyProperties(dish, dishVO);
            dishVO.setFlavors(dishFlavors);
            DishVOList.add(dishVO);
        }

        return Result.success(DishVOList);
    }
}
