package com.topie.ssocenter.freamwork.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.service.UserMenuService;
import com.topie.ssocenter.freamwork.authorization.service.UserRoleService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

@Controller
public class IndexController {

    @Autowired
    private UserAccountService userService;

    @Autowired
    private UserRoleService roleService;

    @Autowired
    private UserMenuService functionService;

    @RequestMapping("/")
    public String root() {
        return "redirect:index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        String userId = SecurityUtils.getCurrentSecurityUser().getId();
        //List<String> roleIds = userService.findUserAccountRoleByUserAccountId(userId);
        return "index";
    }

}
