package com.eims.service.impl;

import com.eims.entity.system.SystemCompany;
import com.eims.repository.SystemCompanyRepository;
import com.eims.service.SystemCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 单位接口实现类
 * @Auth zongyuan.ma
 * @Date 2022/7/5 21:48
 * @Version V 1.0.0
 */
@Service
public class SystemCompanyServiceImpl implements SystemCompanyService {

    @Autowired
    private SystemCompanyRepository systemCompanyRepository;

    /**
     * @Description 获取单位信息
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/12 16:24
     * @Version V 1.0.0
     */
    @Override
    public List<SystemCompany> getCompany() {
        return systemCompanyRepository.findAll();
    }

    /**
     * @Description 修改单位信息
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/12 16:47
     * @Version V 1.0.0
     */
    @Override
    public SystemCompany updateCompany(SystemCompany company) {
        return systemCompanyRepository.saveAndFlush(company);
    }
}
