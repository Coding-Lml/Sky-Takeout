package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 * @author Aloong
 * @description 地址簿Service
 */
public interface AddressBookService {

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @return
     */
    List<AddressBook> list();

    /**
     * 新增地址
     *
     * @param addressBook
     */
    void save(AddressBook addressBook);

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    AddressBook getById(Long id);

    /**
     * 根据id修改地址
     *
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 设置默认地址
     *
     * @param addressBook
     */
    void setDefault(AddressBook addressBook);

    /**
     * 根据id删除地址
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 查询默认地址
     *
     * @return
     */
    AddressBook getDefault();
}
