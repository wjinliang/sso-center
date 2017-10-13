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
import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.authorization.service.UserMenuService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

/**
 * Created by wjl
 */
@Controller
@RequestMapping("/menu")
public class UserMenuController {
    @Autowired
    private UserMenuService menuService;

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    @ResponseBody
    public Result menus(UserMenu menu,
                        @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<UserMenu> pageInfo = menuService.findUserMenuList(pageNum, pageSize, menu);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/menu_insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insertUserMenu(@Valid UserMenu menu, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.error(result);
        }
        menuService.insertUserMenu(menu);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/menu_update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserMenu(@Valid UserMenu menu, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.error(result);
        }
        menuService.updateNotNull(menu);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/menu_load", method = RequestMethod.GET)
    @ResponseBody
    public Result loadUserMenu(@RequestParam(value = "id", required = true) Long menuId) {
        UserMenu menu = menuService.findUserMenuById(menuId);
        return ResponseUtil.success(menu);
    }

    @RequestMapping(value = "/menu_del", method = RequestMethod.GET)
    @ResponseBody
    public Result delUserMenu(@RequestParam(value = "id", required = true) Long menuId) {
        if (SecurityUtils.getCurrentSecurityUser().getId().equals( menuId)) {
            throw new AuthBusinessException(AuBzConstant.CANNOT_DEL_CURRENT_USER);
        }
        menuService.delete(menuId);
        return ResponseUtil.success();
    }

}
