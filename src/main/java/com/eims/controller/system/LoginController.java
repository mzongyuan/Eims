package com.eims.controller.system;

import com.eims.common.*;
import com.eims.entity.system.SystemUser;
import com.eims.property.system.SystemUserV0;
import com.eims.service.SystemUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description 登录逻辑
 * @Auth zongyuan.ma
 * @Date 2022/7/6 21:18
 * @Version V 1.0.0
 */
@RestController
@RequestMapping(value = "/v1/login")
@Slf4j
public class LoginController {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @Description 登录
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/6 22:19
     * @Version V 1.0.0
     */
    @SneakyThrows
    @PostMapping(value = "/front")
    public Result<Object> login(@RequestBody Map<String, Object> body) {
        // 返回对象
        SystemUserV0 systemUserV0 = new SystemUserV0();
        // 获取用户名
        String userCode = (String) body.get("userCode");
        // 获取密码
        String userPwd = (String) body.get("userPwd");
        // 判断账号密码是输入合理
        if (StringUtils.isEmpty(userCode) || StringUtils.isEmpty(userPwd)) {
            return Result.failure(ResultCode.LOGIN_EXCEPTION);
        }
        // 根据userCode查询用户信息
        List<SystemUser> userList = systemUserService.getUserByUserCode(userCode);
        // 判断用户是否存在或者账号状态是否正常
        if (CollectionUtils.isEmpty(userList)) {
            return Result.failure(ResultCode.USER_INFO_EXCEPTION);
        }
        // 校验账号密码
        SystemUser user = userList.get(0);
        if (!Md5Utils.md5(userPwd).equals(user.getUserPwd())) {
            return Result.failure(ResultCode.PASSWORD_ERROR);
        }
        // 校验完成后登录系统，创建登录用户Token
        String token = TokenUtils.getoken(user);
        // 生成Token后缓存到Redis中
        redisUtil.set(user.getUserCode(), token, Constant.REDIS_TIMEOUT);
        // 处理返回信息
        BeanUtils.copyProperties(user, systemUserV0);
        systemUserV0.setToken(token);
        log.info(token);
        // 登录完成返回用户信息
        return Result.success(systemUserV0);
    }

}
