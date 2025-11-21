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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
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

}
