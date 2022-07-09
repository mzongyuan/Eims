package com.eims.service.impl;

import com.eims.entity.system.SystemRole;
import com.eims.repository.SystemRoleRepository;
import com.eims.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;

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
}
