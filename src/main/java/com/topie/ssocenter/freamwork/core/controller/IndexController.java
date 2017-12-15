package com.topie.ssocenter.freamwork.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.freamwork.authorization.exception.AuBzConstant;
import com.topie.ssocenter.freamwork.authorization.exception.AuthBusinessException;
import com.topie.ssocenter.freamwork.authorization.exception.RuntimeBusinessException;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.service.UserMenuService;
import com.topie.ssocenter.freamwork.authorization.service.UserRoleService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.freamwork.authorization.utils.SimpleCrypto;

@Controller
public class IndexController {

    @Autowired
    private UserAccountService userService;

    @Autowired
    private UserRoleService roleService;

    @Autowired
    private UserMenuService userMenuService;
    @Value("${encrypt.seed}")
	private String seed;

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
    @RequestMapping("/infoCenter")
    public ModelAndView infoCenter(ModelAndView model){
    	String userId = SecurityUtils.getCurrentSecurityUser().getId();
    	UserAccount userAccount = userService.selectByKey(userId);
    	model.addObject("userAccount", userAccount);
    	model.setViewName("user/info");
    	return model;
    }
    @RequestMapping("saveInfo")
    public ModelAndView saveInfo(ModelAndView model, UserAccount user){
    	String id = SecurityUtils.getCurrentSecurityUser().getId();
    	user.setCode(id);
    	userService.updateNotNull(user);
    	UserAccount userAccount = userService.selectByKey(id);
    	model.addObject("userAccount", userAccount);
    	model.setViewName("user/info");
    	return model;
    }
    @RequestMapping("updatePassword")
    public ModelAndView updatePassword(ModelAndView model, String oldPassword,String newPassword) throws Exception{
    	String id = SecurityUtils.getCurrentSecurityUser().getId();
    	ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		oldPassword = sha.encodePassword(oldPassword, null);
		String encryptPassword = SimpleCrypto.encrypt(seed,
				newPassword);
		model.addObject("infomsg", "修改成功下次登录生效");
		try{
			this.userService.updatePassword(id,oldPassword,encryptPassword);
		}catch(RuntimeBusinessException e){
			model.addObject("infomsg", e.getMessage());
		}
    	UserAccount userAccount = userService.selectByKey(id);
    	model.addObject("userAccount", userAccount);
    	
    	model.setViewName("user/info");
    	return model;
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
				//map.put("icon", new String(m.getIcon()));
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
