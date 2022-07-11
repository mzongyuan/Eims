package com.eims.service.impl;

import com.eims.entity.system.SystemRole;
import com.eims.repository.SystemRoleRepository;
import com.eims.service.SystemRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

/**
 * @Description 角色接口实现类
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:49
 * @Version V 1.0.0
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleRepository systemRoleRepository;

    /**
     * @Description 根据角色编码查询角色
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/8 15:23
     * @Version V 1.0.0
     */
    @Override
    public List<SystemRole> getRoleByRoleCode(String roleCode) {
        Specification<SystemRole> spec = new Specification<SystemRole>() {
            @Override
            public Predicate toPredicate(Root<SystemRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> roleCodeT = root.get("roleCode");
                Path<Integer> roleFlagT = root.get("roleFlag");
                Predicate p = criteriaBuilder.equal(roleFlagT, 1);
                Predicate p1 = criteriaBuilder.equal(roleCodeT, roleCode);
                criteriaQuery.where(criteriaBuilder.and(p, p1));
                return null;
            }
        };
        return systemRoleRepository.findAll(spec);
    }

    /**
     * @Description 角色分页查询
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/11 15:35
     * @Version V 1.0.0
     */
    @Override
    public Page<SystemRole> getPageRole(Map<String, Object> objectMap, Integer pageSize, Integer currentPage) {
        Object sorter = objectMap.get("sorter");
        Sort sort = null;
        if (sorter != null && !"".equals(sorter)){
            String[] sorters = sorter.toString().split("_");
            if (sorters.length == 2) {
                if ("ascend".equals(sorters[1])) {
                    sort = Sort.by(Sort.Direction.ASC, sorters[0]);
                } else if ("descend".equals(sorters[1])) {
                    sort = Sort.by(Sort.Direction.DESC, sorters[0]);
                }
            }
        }
        if (sort == null) {
            sort = Sort.by(Sort.Direction.ASC, "roleId");
        }
        Pageable pageable = PageRequest.of(currentPage - 1 < 0 ? 0 : currentPage - 1, pageSize, sort);
        Specification<SystemRole> spec = new Specification<SystemRole>() {
            @Override
            public Predicate toPredicate(Root<SystemRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Integer> roleFlag = root.get("roleFlag");
                Predicate p = cb.equal(roleFlag, 1);

                if (StringUtils.isEmpty((String)objectMap.get("roleName"))) {
                    String roleName = (String)objectMap.get("roleName");
                    Path<String> roleNameT = root.get("roleName");
                    Predicate up = cb.equal(roleNameT, roleName);
                }

                if (StringUtils.isEmpty((String)objectMap.get("roleCode"))) {
                    String roleCode = (String)objectMap.get("roleCode");
                    Path<String> roleCodeT = root.get("roleCode");
                    Predicate up = cb.equal(roleCodeT, roleCode);
                }
                return null;
            }
        };
        return systemRoleRepository.findAll(spec, pageable);
    }

    /**
     * @Description 新增角色
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/11 15:53
     * @Version V 1.0.0
     */
    @Override
    public SystemRole addSystemRole(SystemRole role) {
        return systemRoleRepository.save(role);
    }

    /**
     * @Description 编辑角色
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/11 15:59
     * @Version V 1.0.0
     */
    @Override
    public SystemRole updateRole(SystemRole role) {
        return systemRoleRepository.saveAndFlush(role);
    }

    /**
     * @Description 删除角色
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/11 16:05
     * @Version V 1.0.0
     */
    @Override
    public void deleteRole(SystemRole role) {
        systemRoleRepository.saveAndFlush(role);
    }
}
