package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aloong
 * @description
 * @since 2025/11/25 上午12:53
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String WEIXINLOGIN = "https://api.weixin.qq.com/sns/jscode2session" ;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatProperties weChatProperties;

    @Override
    public User login(String code) {

        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WEIXINLOGIN, map);

        //解析openid
        JSONObject jsonObject = JSON.parseObject(json);
        User user = userMapper.getByOpenid(jsonObject.getString("openid"));

        //user不存在就进行创建
        if (user == null) {
            user = User.builder().openid(jsonObject.getString("openid"))
                    .createTime(LocalDateTime.now()).build();
            userMapper.insert(user);
        }
        return user;
    }
}
