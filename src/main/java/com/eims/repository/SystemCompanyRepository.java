package com.eims.repository;

import com.eims.entity.system.SystemCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description 单位交互接口
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:55
 * @Version V 1.0.0
 */
public interface SystemCompanyRepository extends JpaSpecificationExecutor<SystemCompany>, JpaRepository<SystemCompany, Long> {
}
