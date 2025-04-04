package com.gong.lease.web.admin.service.impl;

import com.gong.lease.common.constant.RedisConstant;
import com.gong.lease.common.exception.LeaseException;
import com.gong.lease.common.result.ResultCodeEnum;
import com.gong.lease.common.utils.JwtUtil;
import com.gong.lease.model.entity.SystemUser;
import com.gong.lease.model.enums.BaseStatus;
import com.gong.lease.web.admin.mapper.SystemUserMapper;
import com.gong.lease.web.admin.service.LoginService;
import com.gong.lease.web.admin.vo.login.CaptchaVo;
import com.gong.lease.web.admin.vo.login.LoginVo;
import com.gong.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public CaptchaVo getCaptcha() {

        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String key = RedisConstant.ADMIN_LOGIN_PREFIX + UUID.randomUUID();
        String value = specCaptcha.text().toLowerCase();
        stringRedisTemplate.opsForValue().set(key, value, RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        return new CaptchaVo(specCaptcha.toBase64(), key);
    }

    @Override
    public String login(LoginVo loginVo) {

        if (!StringUtils.hasLength(loginVo.getCaptchaCode())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }

        String code = stringRedisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if (!StringUtils.hasLength(code)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }

        if (!code.equals(loginVo.getCaptchaCode().toLowerCase())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }

        SystemUser systemUser = systemUserMapper.selectOneByUsername(loginVo.getUsername());

        if (systemUser == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }

        if (systemUser.getStatus() == BaseStatus.DISABLE) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        if (!systemUser.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }

        //创建JWT
        return JwtUtil.createToken(systemUser.getId(), systemUser.getUsername());
    }

    @Override
    public SystemUserInfoVo getUserInfoById(Long userId) {

        SystemUser systemUser = systemUserMapper.selectById(userId);
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;
    }
}
