package com.eims.controller.system;

import com.eims.common.Result;
import com.eims.common.ResultCode;
import com.eims.common.TokenUtils;
import com.eims.entity.system.SystemCompany;
import com.eims.service.SystemCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 单位Controller
 * @Auth zongyuan.ma
 * @Date 2022/7/12 16:18
 * @Version V 1.0.0
 */
@RestController
@RequestMapping(value = "/v1/company")
public class SystemCompanyController {

    @Autowired
    private SystemCompanyService systemCompanyService;

    /**
     * @Description 获取单位信息
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/12 16:19
     * @Version V 1.0.0
     */
    @GetMapping
    public Result<Object> getCompany() {
        // 获取单位信息
        List<SystemCompany> companyList = systemCompanyService.getCompany();
        // 单位信息
        if (CollectionUtils.isEmpty(companyList)) {
            return Result.success(new SystemCompany());
        }
        SystemCompany company = companyList.get(0);

        return Result.success(company);
    }

    /**
     * @Description 更新单位信息
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/12 16:29
     * @Version V 1.0.0
     */
    @PutMapping
    public Result<Object> updateCompany(HttpServletRequest httpServletRequest, @RequestBody Map<String, Object> body) {
        SystemCompany company = new SystemCompany();
        // 校验信息
        if (StringUtils.isEmpty(body.get("companyCode"))||StringUtils.isEmpty(body.get("companyName"))) {
            return Result.failure(ResultCode.COMPANY_INFO_DEFECT);
        }
        // 获取前端信息
        company.setCompanyId(StringUtils.isEmpty((String)body.get("companyId"))?1: (Long) body.get("companyId"));
        company.setCompanyCode((String) body.get("companyCode"));
        company.setCompanyName((String) body.get("companyName"));
        company.setOrgAddress((String) body.get("address"));
        company.setCompanyTel((String) body.get("companyTel"));
        company.setEmail((String) body.get("email"));
        company.setPostcode((String) body.get("postcode"));
        company.setCompanyWeb((String) body.get("companyWeb"));
        // 根据请求token获取当前登录人
        String currentToken = httpServletRequest.getHeader("Authorization");
        String cruuentUser = TokenUtils.tokenToOut(currentToken);
        company.setCreateUser(cruuentUser);
        company.setCreateDate(new Date());

        systemCompanyService.updateCompany(company);

        return Result.success(company);
    }
}
