package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author Aloong
 * @description
 * @since 2025/11/21 下午6:11
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        if (setmealDishes != null && setmealDishes.size() > 0) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmeal.getId());  // 设置套餐菜品的套餐id 使用数据库自动生成主键的功能
            }
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.query(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> ids) {
        // 起售的套餐不能删除
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        // 根据id删除对应套餐
        for (Long id : ids) {
            setmealMapper.deleteById(id);
        }
        // 根据id查询套餐菜品
        for (Long id : ids) {
            List<SetmealDish> list = setmealDishMapper.queryBySetmealId(id);
            // 删除对应的套餐菜品
            if (list != null && list.size() > 0) {
                for (SetmealDish setmealDish : list) {
                    setmealDishMapper.deleteById(setmealDish.getId());
                }
            }
        }
    }

    @Override
    public void status(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder().id(id).status(status).build();
        setmealMapper.update(setmeal);
    }

    @Override
    public SetmealVO getById(Long id) {
        SetmealVO setmealVO = setmealMapper.get(id);

        // 根据ID查询套餐菜品
        List<SetmealDish> list = setmealDishMapper.queryBySetmealId(id);
        setmealVO.setSetmealDishes(list);

        return setmealVO;
    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 先把套餐内的内容进行修改
        setmealMapper.update(setmeal);
        // 套餐菜品就是先删除再添加
        setmealDishMapper.deleteById(setmealDTO.getId());
        List<SetmealDish> list = setmealDTO.getSetmealDishes();
        if (list != null && list.size() > 0) {
            setmealDishMapper.insertBatch(list);
        }
    }

    @Override
    public List<Setmeal> getByCategoryId(Long category) {
        return setmealMapper.getByCategoryId(category);
    }

    @Override
    public List<DishItemVO> getBySetmealId(Long id) {
        List<DishItemVO> list = setmealDishMapper.query(id);
        return list;
    }

}
