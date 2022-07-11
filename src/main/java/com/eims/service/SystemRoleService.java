package com.eims.service;

import com.eims.entity.system.SystemRole;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 角色接口
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:47
 * @Version V 1.0.0
 */
@Service
public interface SystemRoleService {
    List<SystemRole> getRoleByRoleCode(String roleCode);

    Page<SystemRole> getPageRole(Map<String, Object> params, Integer pageSize, Integer currentPage);

    SystemRole addSystemRole(SystemRole role);

    SystemRole updateRole(SystemRole role);

    void deleteRole(SystemRole role);
}
