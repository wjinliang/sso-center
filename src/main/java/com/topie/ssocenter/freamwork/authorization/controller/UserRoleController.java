package com.topie.ssocenter.freamwork.authorization.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.Result;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.exception.AuBzConstant;
import com.topie.ssocenter.freamwork.authorization.exception.AuthBusinessException;
import com.topie.ssocenter.freamwork.authorization.model.UserRole;
import com.topie.ssocenter.freamwork.authorization.service.UserRoleService;
import com.topie.ssocenter.freamwork.authorization.utils.R;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

/**
 * Created by wjl
 */
@Controller
@RequestMapping("/userrole")
public class UserRoleController {
    @Autowired
    private UserRoleService roleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(UserRole role,
    					ModelAndView model,
                        @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
    	model.addObject(R.SEARCHMODEL, role);
        PageInfo<UserRole> pageInfo = roleService.findUserRoleList(pageNum, pageSize, role);
        model.addObject(R.PAGE, pageInfo);
        model.setViewName("/role/list");
        return model;
    }
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public ModelAndView list(UserRole role,
    					ModelAndView model){
    	if(role.getCode()!=null){
    		role = this.roleService.selectByKey(role.getCode());
    	}
    	String tree = roleService.selectRoleMenusTreeStr(role.getCode());
    	model.addObject("role", role);
    	model.addObject("menus",tree);
        model.setViewName("/role/form");
        return model;
    }

    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public ModelAndView insertUserRole(@Valid UserRole role,String menuids,
			ModelAndView model) {
    	if(role.getCode()==null){
    		role.setCode(UUIDUtil.getUUID8());
    		roleService.save(role,menuids);
	       model.setViewName("redirect:list");
	       return model;
    	}
    	roleService.updateNotNull(role,menuids);
        model.setViewName("redirect:list");
        return model;
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
    @RequestMapping("/stoprole")
    @ResponseBody
    public Object stopRole(UserRole role){
    	if(role.getCode()==null)
    		return ResponseUtil.error();
    	role.setEnabled(false);
    	this.roleService.updateNotNull(role);
        return ResponseUtil.success();
    }
    
    @RequestMapping("/startrole")
    @ResponseBody
    public Object startRole(UserRole role){
    	if(role.getCode()==null)
    		return ResponseUtil.error();
    	role.setEnabled(true);
    	this.roleService.updateNotNull(role);
        return ResponseUtil.success();
    }

}
