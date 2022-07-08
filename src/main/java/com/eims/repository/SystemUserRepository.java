package com.eims.repository;

import com.eims.entity.system.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

/**
 * @Description 用户交互接口
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:52
 * @Version V 1.0.0
 */
public interface SystemUserRepository extends JpaSpecificationExecutor<SystemUser>, JpaRepository<SystemUser, Long> {
}
