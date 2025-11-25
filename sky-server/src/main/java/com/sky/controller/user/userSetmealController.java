package com.sky.controller.user;

import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/25 下午8:27
 */
@RestController
@RequestMapping("user/setmeal")
@Slf4j
public class userSetmealController {
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/list")
    public Result<List<Setmeal>> userSetmealList(@RequestParam Long categoryId){
        List<Setmeal> list = setmealService.getByCategoryId(categoryId);
        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> getBySetmealId(@PathVariable Long id){
        List<DishItemVO> list = setmealService.getBySetmealId(id);
        return Result.success(list);
    }
}
