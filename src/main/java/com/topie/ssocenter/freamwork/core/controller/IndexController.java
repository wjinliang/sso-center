package com.topie.ssocenter.freamwork.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.exception.AuBzConstant;
import com.topie.ssocenter.freamwork.authorization.exception.AuthBusinessException;
import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
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
    private UserMenuService userMenuService;

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
    
    @RequestMapping("/menu")
    @ResponseBody
    public Object getCurrentUserMenu(){
    	OrangeSideSecurityUser currentUser = SecurityUtils.getCurrentSecurityUser();
        if (currentUser==null) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }
        List list = new ArrayList();
        try {
			List<UserMenu> menuList = userMenuService.findMenusByUserId(currentUser.getId());
			for (UserMenu m : menuList) {
				if(!m.getIsshow()) continue;
				Map map = new HashMap();
				map.put("id", m.getId());
				map.put("name", m.getName());
				if (m.getPid() != null) {
						map.put("pId", m.getPid());
				} else {
					map.put("pId", 0);
				}
				map.put("icon", new String(m.getIcon()));
				map.put("url", m.getMenuurl());
				/*if (m.getChildren().size() == 0) {
					map.put("isLeaf", true);
				} else {
					map.put("isLeaf", false);
				}*/
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthBusinessException("获取菜单异常");
		}
        return ResponseUtil.success(list);
    }

}
