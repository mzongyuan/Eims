package com.eims.service;

import com.eims.entity.system.SystemCompany;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Auth zongyuan.ma
 * @Date 2022/7/12 16:21
 * @Version V 1.0.0
 */
@Service
public interface SystemCompanyService {
    List<SystemCompany> getCompany();

    SystemCompany updateCompany(SystemCompany company);
}
