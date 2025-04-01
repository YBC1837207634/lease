package com.gong.lease.web.app.service;

import com.gong.lease.web.app.vo.user.LoginVo;
import com.gong.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {
    void sendCode(String phone);

    String login(LoginVo loginVo);

    UserInfoVo getLoginUserInfoById(Long userId);
}
