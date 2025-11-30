package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 下午5:25
 */
@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setmealService.save(setmealDTO);
        return Result.success();
    }

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除套餐
     * - 可以一次删除一个套餐 也可以批量删除套餐
     * - 起售的套餐不能删除
     * - 删除套餐时关联的套餐菜品表也要删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result delete (@RequestParam List<Long> ids){
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 启用禁用套餐
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result status(@PathVariable Integer status, @RequestParam Long id){
        setmealService.status(status, id);
        return Result.success();
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO setmeal = setmealService.getById(id);
        return Result.success(setmeal);
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }

}
