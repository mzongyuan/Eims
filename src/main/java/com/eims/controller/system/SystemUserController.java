package com.eims.controller.system;

import com.eims.common.*;
import com.eims.entity.system.SystemRole;
import com.eims.entity.system.SystemUser;
import com.eims.service.SystemRoleService;
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

    @Autowired
    private SystemRoleService systemRoleService;

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
        SystemRole role = new SystemRole();
        // 提交信息校验
        if (StringUtils.isEmpty((String) body.get("userCode"))||StringUtils.isEmpty((String) body.get("userName"))||StringUtils.isEmpty((String) body.get("userPwd"))) {
            return Result.failure(ResultCode.USERUSER_INFO_DEFECT);
        }
        // 获取新增用户信息
        user.setUserCode((String) body.get("userCode"));
        user.setUserName((String) body.get("userName"));
        user.setUserPwd((String) body.get("userPwd"));
        user.setRoleCode((String) body.get("roleCode"));
        user.setOrgCode((String) body.get("orgCode"));
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
        // 密码MD5加密
        String userPwd = Md5Utils.md5(user.getUserPwd());
        user.setUserPwd(userPwd);
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

    /**
     * @Description 编辑用户
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/8 14:52
     * @Version V 1.0.0
     */
    @PutMapping
    public Result<Object> updateUser(@RequestBody Map<String, Object> body) {
        // 获取修改内容
        String userCode = (String) body.get("userCode");
        // 根据UserCode查询用户信息
        List<SystemUser> userList = systemUserService.getUserByUserCode(userCode);
        if (CollectionUtils.isEmpty(userList)) {
            return Result.failure(ResultCode.USER_NOT_EXIST);
        }
        // 更新用户信息
        SystemUser user = userList.get(0);
        user.setUserName((String) body.get("userName"));
        user.setUserDesc((String) body.get("userDesc"));
        user.setRoleCode((String) body.get("orgCode"));
        user.setOrgCode((String) body.get("orgCode"));

        // 更新
        this.systemUserService.updateUser(user);

        return Result.success(user);
    }

    /**
     * @Description 删除用户
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/9 19:49
     * @Version V 1.0.0
     */
    @DeleteMapping
    public Result<Object> deleteUser(@RequestBody Map<String, Object> body) {
        //获取用户编码
        String userCode = (String) body.get("userCode");
        // 根据用户编码获取用户信息
        List<SystemUser> userList = systemUserService.getUserByUserCode(userCode);
        if (CollectionUtils.isEmpty(userList)) {
            return Result.failure(ResultCode.USER_NOT_EXIST);
        }
        // 修改用户状态
        SystemUser user = userList.get(0);
        user.setUserFlag(0);
        // 删除用户
        this.systemUserService.deleteUser(user);

        return Result.success(null);
    }

    /**
     * @Description 获取当前用户信息
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/9 20:27
     * @Version V 1.0.0
     */
    @GetMapping(value = "/currentUser")
    public Result<Object> getCurrentUser(HttpServletRequest httpRequest, @RequestParam(value = "token", required = false)String token) {
        // Token 信息
        String userToken = (StringUtils.isEmpty(token)) ? httpRequest.getHeader("token") : token;
        if (StringUtils.isEmpty(userToken)) {
            return Result.failure(ResultCode.TOKEN_NONE);
        }
        // 通过Token解析userCode
        String userCode = TokenUtils.tokenToOut(userToken);
        // 根据userCode查询用户信息
        List<SystemUser> user = systemUserService.getUserByUserCode(userCode);
        if (CollectionUtils.isEmpty(user)) {
            return Result.failure(ResultCode.USER_NOT_EXIST);
        }

        return Result.success(user.get(0));
    }
}
