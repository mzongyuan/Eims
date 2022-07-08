package com.eims.property.system;

import lombok.Data;

import java.util.Date;

/**
 * @Description TODO
 * @Auth zongyuan.ma
 * @Date 2022/7/7 22:26
 * @Version V 1.0.0
 */
@Data
public class SystemUserV0 {
    // 主键ID
    private Long userId;

    // 用户名称
    private String userName;

    // 用户编码
    private String userCode;

    // 账号状态
    private Integer userFlag;

    // 账号密码
    private String userPwd;

    // 用户描述
    private String userDesc;

    // 创建人员
    private String createUser;

    // 创建时间
    private Date createDate;

    // 登录Token
    private String token;
}
