package com.eims.entity.system;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description 单位
 * @Auth zongyuan.ma
 * @Date 2022/7/11 16:30
 * @Version V 1.0.0
 */
@Entity
@Table(name = "system_company")
@Data
public class SystemCompany {

    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    private Long companyId;

    // 组织编码
    @Column(name = "company_code")
    private String companyCode;

    // 组织名称
    @Column(name = "company_name")
    private String companyName;

    // 公司地质
    @Column(name = "company_address")
    private String orgAddress;

    // 邮编
    @Column(name = "postcode")
    private String postcode;

    // 邮箱
    @Column(name = "email")
    private String email;

    // 公司网站
    @Column(name = "company_web")
    private String companyWeb;

    // 联系电话
    @Column(name = "company_tel")
    private String companyTel;

    // 创建人员
    @Column(name = "create_user")
    private String createUser;

    // 创建时间
    @Column(name = "create_date")
    private Date createDate;
}
