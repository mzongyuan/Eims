package com.eims.controller.system;

import com.eims.common.Result;
import com.eims.common.ResultCode;
import com.eims.common.TokenUtils;
import com.eims.entity.system.SystemRole;
import com.eims.pagination.PageObjectList;
import com.eims.pagination.Pagination;
import com.eims.service.SystemRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 角色Controller
 * @Auth zongyuan.ma
 * @Date 2022/7/8 15:17
 * @Version V 1.0.0
 */
@RestController
@RequestMapping(value = "/v1/role")
public class SystemRoleController {

    @Autowired
    private SystemRoleService systemRoleService;

    @GetMapping
    public Result<Object> getAllRole(@RequestParam Map<String, Object> params) {
        // 结果对象
        PageObjectList pageObjectList = new PageObjectList();
        // 获取分页参数
        Integer pageSize = StringUtils.isEmpty((String)params.get("pageSize"))?10:Integer.parseInt((String) params.get("pageSize"));
        Integer currentPage = StringUtils.isEmpty((String)params.get("currentPage"))?1:Integer.parseInt((String) params.get("currentPage"));
        Page<SystemRole> page = systemRoleService.getPageRole(params, pageSize, currentPage);
        // 封装结果
        pageObjectList.setList(page.getContent());
        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(currentPage);
        pagination.setTotal(page.getTotalElements());
        Double totalPage = Double.valueOf(Double.valueOf(String.valueOf(page.getTotalElements()))/pageSize);
        pagination.setTotalPages((int) Math.ceil(totalPage));
        pageObjectList.setPagination(pagination);

        return Result.success(pageObjectList);
    }

    /**
     * @Description 新增角色
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/11 15:43
     * @Version V 1.0.0
     */
    @PostMapping
    public Result<Object> addRole(@RequestBody Map<String, Object> body, HttpServletRequest httpRequest) {
        SystemRole role = new SystemRole();
        // 提交信息校验
        if (StringUtils.isEmpty((String) body.get("roleCode"))||StringUtils.isEmpty((String) body.get("roleName"))) {
            return Result.failure(ResultCode.ROLE_INFO_DEFECT);
        }
        // 获取新增角色信息
        role.setRoleCode((String) body.get("roleCode"));
        role.setRoleName((String) body.get("roleName"));
        role.setRoleDesc((String) body.get("roleDesc"));
        role.setRoleFlag(1);
        // 根据请求token获取当前登陆人
        String currentToken = httpRequest.getHeader("Authorization");
        String cruuentUser = TokenUtils.tokenToOut(currentToken);
        role.setCreateUser(cruuentUser);
        role.setCreateDate(new Date());
        // 判断userCode是否已存在
        List<SystemRole> roleList = systemRoleService.getRoleByRoleCode(role.getRoleCode());
        if (!CollectionUtils.isEmpty(roleList)) {
            return Result.failure(ResultCode.ROLE_CODE_EXIST);
        }

        // 保存用户信息
        systemRoleService.addSystemRole(role);

        return Result.success(role);
    }

    /**
     * @Description 编辑角色
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/11 15:55
     * @Version V 1.0.0
     */
    @PutMapping
    public Result<Object> updateRole(@RequestBody Map<String, Object> body) {
        // 获取修改内容
        String roleCode = (String) body.get("roleCode");
        // 根据UroerCode查询角色信息
        List<SystemRole> roleList = systemRoleService.getRoleByRoleCode(roleCode);
        if (CollectionUtils.isEmpty(roleList)) {
            return Result.failure(ResultCode.ROLE_NOT_EXIST);
        }
        // 更新角色信息
        SystemRole role = roleList.get(0);
        role.setRoleName(StringUtils.isEmpty((String) body.get("roleName"))?role.getRoleName():(String) body.get("roleName"));
        role.setRoleDesc(StringUtils.isEmpty((String) body.get("roleDesc"))?role.getRoleDesc():(String) body.get("roleDesc"));

        // 更新
        this.systemRoleService.updateRole(role);

        return Result.success(role);
    }

    /**
     * @Description 删除角色
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/11 16:03
     * @Version V 1.0.0
     */
    @DeleteMapping
    public Result<Object> deleteRole(@RequestBody Map<String, Object> body) {
        //获取角色编码
        String roleCode = (String) body.get("roleCode");
        // 根据角色编码获取角色信息
        List<SystemRole> roleList = systemRoleService.getRoleByRoleCode(roleCode);
        if (CollectionUtils.isEmpty(roleList)) {
            return Result.failure(ResultCode.ROLE_NOT_EXIST);
        }
        // 修改角色状态
        SystemRole role = roleList.get(0);
        role.setRoleFlag(0);
        // 删除用户
        this.systemRoleService.deleteRole(role);

        return Result.success(null);
    }

    /**
     * @Description 根据角色编码获取角色信息
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/11 16:08
     * @Version V 1.0.0
     */
    public Result<Object> getRoleInfo(@RequestParam String roleCode) {
        // 参数异常
        if (StringUtils.isEmpty(roleCode)) {
            return Result.failure(ResultCode.PARAMETER_ERROR);
        }
        // 获取角色信息
        List<SystemRole> roleList = systemRoleService.getRoleByRoleCode(roleCode);
        SystemRole role = roleList.get(0);

        return Result.success(role);
    }
}
