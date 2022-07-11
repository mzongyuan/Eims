package com.eims.service.impl;

import com.eims.entity.system.SystemUser;
import com.eims.repository.SystemUserRepository;
import com.eims.service.SystemUserService;
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
 * @Description 用户接口实现类
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:48
 * @Version V 1.0.0
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    public static final int SORT_LENGTH = 2;

    public static final int SORT_VALUE_INDEX = 1;

    public static final int SORT_KEY_INDEX = 0;

    public static final String ASCEND = "ascend";

    public static final String DESCEND = "descend";

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

    /**
     * @Description 删除用户
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/9 20:09
     * @Version V 1.0.0
     */
    @Override
    public void deleteUser(SystemUser user) {
        systemUserRepository.saveAndFlush(user);
    }

    /**
     * @Description 更新用户
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/9 20:19
     * @Version V 1.0.0
     */
    @Override
    public void updateUser(SystemUser user) {
        systemUserRepository.saveAndFlush(user);
    }

    /**
     * @Description 用户分页查询
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/11 15:03
     * @Version V 1.0.0
     */
    @Override
    public Page<SystemUser> getPageUser(Map<String, Object> objectMap, Integer pageSize, Integer currentPage) {
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
            sort = Sort.by(Sort.Direction.ASC, "userId");
        }
        Pageable pageable = PageRequest.of(currentPage - 1 < 0 ? 0 : currentPage - 1, pageSize, sort);
        Specification<SystemUser> spec = new Specification<SystemUser>() {
            @Override
            public Predicate toPredicate(Root<SystemUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Integer> userFlag = root.get("userFlag");
                Predicate p = cb.equal(userFlag, 1);

                if (StringUtils.isEmpty((String)objectMap.get("userName"))) {
                    String userName = (String)objectMap.get("userName");
                    Path<String> userNameT = root.get("userName");
                    Predicate up = cb.equal(userNameT, userName);
                }

                if (StringUtils.isEmpty((String)objectMap.get("userCode"))) {
                    String userCode = (String)objectMap.get("userCode");
                    Path<String> userCodeT = root.get("userCode");
                    Predicate up = cb.equal(userCodeT, userCode);
                }
                return null;
            }
        };
        return systemUserRepository.findAll(spec, pageable);
    }

}
