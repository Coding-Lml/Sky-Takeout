package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @author Aloong
 * @description
 * @since 2025/11/25 上午12:53
 */
public interface UserService {
    User login(String code);
}
