package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aloong
 * @description 分类接口
 * @since 2025/11/25 下午8:27
 */
@RestController
@RequestMapping("/user/category")
@Slf4j
public class userCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> findAll(@RequestParam(required = false) Integer type) {
        return Result.success(categoryService.getList(type));
    }
}
