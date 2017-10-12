package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.List;

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
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

/**
 * Created by cgj on 2016/4/9.
 */
@Controller
@RequestMapping("/urf")
public class UserAccountController {
    @Autowired
    private UserAccountService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public Result users(UserAccount user,
                        @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<UserAccount> pageInfo = userService.findUserAccountList(pageNum, pageSize, user);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/user_insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insertUserAccount(@Valid UserAccount user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.error(result);
        }
        if (userService.findExistUserAccount(user) > 0) {
            throw new AuthBusinessException(AuBzConstant.LOGIN_NAME_EXIST);
        }
        userService.insertUserAccount(user);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/user_update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserAccount(@Valid UserAccount user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.error(result);
        }
        if (userService.findExistUserAccount(user) > 0) {
            throw new AuthBusinessException(AuBzConstant.LOGIN_NAME_EXIST);
        }
        userService.updateNotNull(user);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/user_load", method = RequestMethod.GET)
    @ResponseBody
    public Result loadUserAccount(@RequestParam(value = "id", required = true) String userId) {
        UserAccount user = userService.findUserAccountById(userId);
        return ResponseUtil.success(user);
    }

    @RequestMapping(value = "/user_del", method = RequestMethod.GET)
    @ResponseBody
    public Result delUserAccount(@RequestParam(value = "id", required = true) String userId) {
        if (SecurityUtils.getCurrentSecurityUser().getId().equals( userId)) {
            throw new AuthBusinessException(AuBzConstant.CANNOT_DEL_CURRENT_USER);
        }
        userService.delete(userId);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/user_roles", method = RequestMethod.GET)
    @ResponseBody
    public Result userRoles(@RequestParam(value = "id", required = true) String userId) {
        List roles = userService.findUserAccountRoleByUserAccountId(userId);
        return ResponseUtil.success(roles);
    }
}
