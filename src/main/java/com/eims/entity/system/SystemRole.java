package com.eims.entity.system;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description 角色实体类
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:25
 * @Version V 1.0.0
 */
@Entity
@Table(name = "system_role")
@Data
public class SystemRole {

    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleId;

    // 角色编码
    @Column(name = "role_code")
    private String roleCode;

    // 角色名称
    @Column(name = "role_name")
    private String roleName;

    // 角色状态
    @Column(name = "role_flag")
    private Integer roleFlag;

    // 角色描述
    @Column(name = "role_desc")
    private String roleDesc;

    // 创建人员
    @Column(name = "create_user")
    private String createUser;

    // 创建时间
    @Column(name = "create_date")
    private Date createDate;
}
