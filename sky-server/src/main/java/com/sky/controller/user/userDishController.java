package com.sky.controller.user;

import com.alibaba.fastjson.JSON;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user/dish")
@Slf4j
public class userDishController {  // 类名首字母大写

    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;  // 使用 StringRedisTemplate

    @GetMapping("/list")
    public Result<List<DishVO>> getById(@RequestParam Long categoryId) {

        String key = "dish_" + categoryId;

        // 1. 先从 Redis 查询
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json != null && !json.isEmpty()) {
            List<DishVO> list = JSON.parseArray(json, DishVO.class);
            return Result.success(list);
        }

        // 2. Redis 没有，查数据库
        List<DishVO> dishVOList = new ArrayList<>();
        List<Dish> dishList = dishService.queryByCategoryId(categoryId);

        for (Dish dish : dishList) {
            Long id = dish.getId();
            List<DishFlavor> dishFlavors = dishFlavorMapper.getDishFlavorByDishId(id);

            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish, dishVO);
            dishVO.setFlavors(dishFlavors);
            dishVOList.add(dishVO);
        }

        // 3. 存入 Redis，设置过期时间（如 30 分钟）
        log.info("准备存入 Redis: " + JSON.toJSONString(dishVOList));
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(dishVOList), 30, TimeUnit.MINUTES);
        log.info("成功存入 Redis: " + key);
        return Result.success(dishVOList);
    }
}