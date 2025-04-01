package com.gong.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gong.lease.common.constant.RedisConstant;
import com.gong.lease.common.exception.LeaseException;
import com.gong.lease.common.result.ResultCodeEnum;
import com.gong.lease.common.utils.CodeUtil;
import com.gong.lease.common.utils.JwtUtil;
import com.gong.lease.model.entity.UserInfo;
import com.gong.lease.model.enums.BaseStatus;
import com.gong.lease.web.app.mapper.UserInfoMapper;
import com.gong.lease.web.app.service.LoginService;
import com.gong.lease.web.app.service.SmsService;
import com.gong.lease.web.app.vo.user.LoginVo;
import com.gong.lease.web.app.vo.user.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SmsService smsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void sendCode(String phone) {
        String code = CodeUtil.getRandomCode(6);
        String key = RedisConstant.APP_LOGIN_PREFIX + phone;

        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if (hasKey) {
            Long ttl = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (ttl > RedisConstant.APP_LOGIN_CODE_TTL_SEC - RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC) {
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }

        stringRedisTemplate.opsForValue().set(key, code, RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
        smsService.sendSms(phone, code);
    }

    @Override
    public String login(LoginVo loginVo) {

        if (!StringUtils.hasLength(loginVo.getPhone())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }

        if (!StringUtils.hasLength(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }


        String key = RedisConstant.APP_LOGIN_PREFIX + loginVo.getPhone();
        String code = stringRedisTemplate.opsForValue().get(key);

        if (!StringUtils.hasLength(code)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }

        if (!loginVo.getCode().equals(code)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }

        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, loginVo.getPhone());
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);

        if (userInfo == null) {
            //注册
            userInfo = new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setNickname("尚庭公寓-" + loginVo.getPhone().substring(7));
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfoMapper.insert(userInfo);
        } else {
            //禁用？
            if (userInfo.getStatus() == BaseStatus.DISABLE) {
                throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
            }
        }

        return JwtUtil.createToken(userInfo.getId(), userInfo.getPhone());
    }

    @Override
    public UserInfoVo getLoginUserInfoById(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        return  new UserInfoVo(userInfo.getNickname(),userInfo.getAvatarUrl());
    }
}
