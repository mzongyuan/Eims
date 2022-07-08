package com.eims.controller.system;

import com.eims.common.PwdCheckUtil;
import com.eims.common.Result;
import com.eims.common.ResultCode;
import com.eims.common.TokenUtils;
import com.eims.entity.system.SystemUser;
import com.eims.service.SystemUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 用户Controller
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:59
 * @Version V 1.0.0
 */
@RestController
@RequestMapping(value = "/v1/user")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    /**
     * @Description 获取用户列表
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/5 22:14
     * @Version V 1.0.0
     */
    @GetMapping
    public Result<List<SystemUser>> getAllUser() {
        List<SystemUser> list = systemUserService.getAllUser();
        return Result.success(list);
    }

    /**
     * @Description 新增用户
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/8 13:13
     * @Version V 1.0.0
     */
    @PostMapping
    public Result<Object> addUser(@RequestBody Map<String, Object> body, HttpServletRequest httpRequest) {
        SystemUser user = new SystemUser();
        // 提交信息校验
        if (StringUtils.isEmpty((String) body.get("userCode"))||StringUtils.isEmpty((String) body.get("userName"))||StringUtils.isEmpty((String) body.get("userPwd"))) {
            return Result.failure(ResultCode.USERUSER_INFO_DEFECT);
        }
        // 获取新增用户信息
        user.setUserCode((String) body.get("userCode"));
        user.setUserName((String) body.get("userName"));
        user.setUserPwd((String) body.get("userPwd"));
        user.setUserFlag(1);
        user.setUserDesc((String) body.get("userDesc"));
        // 根据请求token获取当前登陆人
        String currentToken = httpRequest.getHeader("token");
        String cruuentUser = TokenUtils.tokenToOut(currentToken);
        user.setCreateUser(cruuentUser);
        user.setCreateDate(new Date());
        // 判断userCode是否已存在
        List<SystemUser> userList = systemUserService.getUserByUserCode(user.getUserCode());
        if (!CollectionUtils.isEmpty(userList)) {
            return Result.failure(ResultCode.USER_CODE_EXIST);
        }
        // 密码复杂度校验
        if (!userPwdChecking(user.getUserPwd())) {
            return Result.failure(ResultCode.PASSRORD_DISCONTENT_COMPLEXITY);
        }
        // 保存用户信息
        systemUserService.addSystemUser(user);

        return Result.success(user);
    }

    /**
     * @Description 密码复杂度校验
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/8 13:33
     * @Version V 1.0.0
     */
    private boolean userPwdChecking(String userPwd) {
        if (PwdCheckUtil.checkPasswordLength(userPwd, "6", "12") || //密码场地6-12位
            PwdCheckUtil.checkContainDigit(userPwd) || // 包含数字
            PwdCheckUtil.checkContainCase(userPwd) ||   // 包含字母（不区分大小写）
            PwdCheckUtil.checkContainSpecialChar(userPwd)) {    // 包含特殊字符
            return true;
        }
        return false;
    }

    ;
}
