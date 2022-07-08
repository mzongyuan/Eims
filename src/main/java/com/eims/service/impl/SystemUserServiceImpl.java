package com.eims.service.impl;

import com.eims.entity.system.SystemUser;
import com.eims.repository.SystemUserRepository;
import com.eims.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @Description 用户接口实现类
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:48
 * @Version V 1.0.0
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserRepository systemUserRepository;

    /**
     * @Description 查询用户列表
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/5 22:27
     * @Version V 1.0.0
     */
    @Override
    public List<SystemUser> getAllUser() {
        return systemUserRepository.findAll();
    }

    /**
     * @Description 根据UserCode获取用户信息
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/6 22:22
     * @Version V 1.0.0
     */
    @Override
    public List<SystemUser> getUserByUserCode(String userCode) {
        Specification<SystemUser> spec = new Specification<SystemUser>() {
            @Override
            public Predicate toPredicate(Root<SystemUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> userCodeT = root.get("userCode");
                Path<Integer> userFlagT = root.get("userFlag");
                Predicate p = criteriaBuilder.equal(userFlagT, 1);
                Predicate p1 = criteriaBuilder.equal(userCodeT, userCode);
                criteriaQuery.where(criteriaBuilder.and(p, p1));
                return null;
            }
        };
        return systemUserRepository.findAll(spec);
    }

    /**
     * @Description 新增用户
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/8 13:47
     * @Version V 1.0.0
     */
    @Override
    public SystemUser addSystemUser(SystemUser user) {
        return systemUserRepository.save(user);
    }
}
