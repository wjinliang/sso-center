package com.topie.ssocenter.freamwork.authorization.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.PageConvertUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.Result;
import com.topie.ssocenter.freamwork.authorization.exception.AuBzConstant;
import com.topie.ssocenter.freamwork.authorization.exception.AuthBusinessException;
import com.topie.ssocenter.freamwork.authorization.model.UserRole;
import com.topie.ssocenter.freamwork.authorization.service.UserRoleService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

/**
 * Created by wjl
 */
@Controller
@RequestMapping("/role")
public class UserRoleController {
    @Autowired
    private UserRoleService roleService;

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public Result roles(UserRole role,
                        @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<UserRole> pageInfo = roleService.findUserRoleList(pageNum, pageSize, role);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/role_insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insertUserRole(@Valid UserRole role, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.error(result);
        }
        roleService.insertUserRole(role);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/role_update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserRole(@Valid UserRole role, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.error(result);
        }
        roleService.updateNotNull(role);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/role_load", method = RequestMethod.GET)
    @ResponseBody
    public Result loadUserRole(@RequestParam(value = "id", required = true) String roleId) {
        UserRole role = roleService.findUserRoleById(roleId);
        return ResponseUtil.success(role);
    }

    @RequestMapping(value = "/role_del", method = RequestMethod.GET)
    @ResponseBody
    public Result delUserRole(@RequestParam(value = "id", required = true) String roleId) {
        if (SecurityUtils.getCurrentSecurityUser().getId().equals( roleId)) {
            throw new AuthBusinessException(AuBzConstant.CANNOT_DEL_CURRENT_USER);
        }
        roleService.delete(roleId);
        return ResponseUtil.success();
    }

}
