package com.eims.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 结果码
 * @Auth zongyuan.ma
 * @Date 2022/7/6 21:26
 * @Version V 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 成功
    SUCCESS(0, "success"),

    // 失败
    FAILURE(1000, "Failure"),

    // 账号密码输入异常
    LOGIN_EXCEPTION(5001, "账号、密码信息不能为空！"),

    // 用户账号不存在或用户账号被锁
    USER_INFO_EXCEPTION(5002, "用户信息不存在或账户异常"),

    // 登录密码错误
    PASSWORD_ERROR(5003, "账户密码错误，请重试！"),

    // Token为空或Token已过期
    TOKEN_NONE(5004, "Token为空或者Token已过期！"),

    // 用户账号已存在
    USER_CODE_EXIST(5005, "用户账号系统中已存在！"),

    // 用户信息录入不完整
    USERUSER_INFO_DEFECT(5006, "用户提交信息不完整，请检查！"),

    // 密码复杂度不满足密码策略
    PASSRORD_DISCONTENT_COMPLEXITY(5007, "密码复杂度不满足密码策略！"),

    // 用户不存在
    USER_NOT_EXIST(5008, "用户信息不存在！"),

    // 角色信息提交不完整
    ROLE_INFO_DEFECT(5009, "角色信息提交不完整，请检查！"),

    // 角色编码信息已存在
    ROLE_CODE_EXIST(5010, "角色编码信息已存在！"),

    // 用户不存在
    ROLE_NOT_EXIST(5011, "角色信息不存在！"),

    // 参数错误
    PARAMETER_ERROR(5012, "参数错误！"),

    // 单位信息不全
    COMPANY_INFO_DEFECT(5013, "单位信息不完整！");

    private final Integer code;

    private final String message;
}
