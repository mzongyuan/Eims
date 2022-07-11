package com.eims.service;

import com.eims.entity.system.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 用户接口
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:46
 * @Version V 1.0.0
 */
@Service
public interface SystemUserService {
    List<SystemUser> getAllUser();

    List<SystemUser> getUserByUserCode(String userCode);

    SystemUser addSystemUser(SystemUser user);

    void deleteUser(SystemUser user);

    void updateUser(SystemUser user);

    Page<SystemUser> getPageUser(Map<String, Object> params, Integer pageSize, Integer currentPage);
}
