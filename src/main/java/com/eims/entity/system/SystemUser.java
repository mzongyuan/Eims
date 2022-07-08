package com.eims.entity.system;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
/**
 * @Description 用户实体类
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:25
 * @Version V 1.0.0
 */
@Entity
@Table(name = "system_user")
@Data
public class SystemUser {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    // 用户名称
    @Column(name = "user_name")
    private String userName;

    // 用户编码
    @Column(name = "user_code")
    private String userCode;

    // 账号状态
    @Column(name = "user_flag")
    private Integer userFlag;

    // 账号密码
    @Column(name = "user_pwd")
    private String userPwd;

    // 用户描述
    @Column(name = "user_desc")
    private String userDesc;

    // 创建人员
    @Column(name = "create_user")
    private String createUser;

    // 创建时间
    @Column(name = "create_date")
    private Date createDate;
}
