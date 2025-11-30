package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 上午12:37
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        // 清除菜品缓存
        String pattern = "dish_" + dishDTO.getCategoryId();
        clearCache(pattern);

        return Result.success();
    }

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page (DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除指定的菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除指定id的菜品:{}", ids);
        dishService.deleteBatch(ids);

        // 清除所有菜品缓存最方便
        clearCache("dish_*");

        return Result.success();
    }

    /**
     * 启用禁用菜品
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        dishService.updateStatus(status, id);
        clearCache("dish_*");
        return Result.success();
    }

    /**
     * 根据id查询菜品 回显菜品数据
     * @param
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishVO> findById(@PathVariable Long id) {
        DishVO dishVO = dishService.findById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        clearCache("dish_*");
        return Result.success();
    }

    /**
     * 根据分类id查询菜品列表
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> queryByCategoryId(@RequestParam Long categoryId) {
        List<Dish> list = dishService.queryByCategoryId(categoryId);
        return Result.success(list);
    }

    /**
     * 管理端修改菜品时 对应清除redis缓存 保持数据一致性
     * @param pattern
     */
    private void clearCache(String pattern) {
        Set<String> keys = stringRedisTemplate.keys(pattern);
        stringRedisTemplate.delete(keys);
    }
}
