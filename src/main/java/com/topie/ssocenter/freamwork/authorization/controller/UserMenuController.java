package com.topie.ssocenter.freamwork.authorization.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSONArray;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.Result;
import com.topie.ssocenter.freamwork.authorization.exception.AuBzConstant;
import com.topie.ssocenter.freamwork.authorization.exception.AuthBusinessException;
import com.topie.ssocenter.freamwork.authorization.model.UserMenu;
import com.topie.ssocenter.freamwork.authorization.service.UserMenuService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

@Controller
@RequestMapping("/usermenu")
public class UserMenuController {
    @Autowired
    private UserMenuService menuService;

    	@RequestMapping("/list")
    	public ModelAndView list(ModelAndView model, HttpServletRequest request) {
			List ml = new ArrayList();
			List<UserMenu> menuList = new ArrayList<UserMenu>();
			Example ex = new Example(UserMenu.class);
			ex.setOrderByClause("seq asc");
				menuList = this.menuService.selectByExample(ex);
			for (UserMenu menu : menuList) {
				Map m = new HashMap();
				m.put("id", menu.getId());
				m.put("name", menu.getName());
				m.put("seq", menu.getSeq());
				m.put("pId",menu.getPid()==null?"":menu.getPid());
				ml.add(m);
			}
			JSONArray arr = new JSONArray(ml);
			model.addObject("menuStr", arr.toJSONString());
			model.setViewName("/menu/menuList");
			return model;
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    public ModelAndView insertUserMenu(@Valid UserMenu menu,ModelAndView model) {
        if(menu.getId()!=null){
        	menuService.updateAll(menu);
        	model.setViewName("redirect:list");
            return model;
        }
        menu.setId(System.currentTimeMillis());
        menuService.save(menu);
        model.setViewName("redirect:list");
        return model;
    }

    @RequestMapping(value = "/menu_load", method = RequestMethod.GET)
    @ResponseBody
    public Result loadUserMenu(@RequestParam(value = "id", required = true) Long menuId) {
        UserMenu menu = menuService.selectByKey(menuId);
        return ResponseUtil.success(menu);
    }

    @RequestMapping(value = "/menu_del", method = RequestMethod.GET)
    @ResponseBody
    public Result delUserMenu(@RequestParam(value = "id", required = true) Long menuId) {
        if (SecurityUtils.getCurrentSecurityUser().getId().equals( menuId)) {
            throw new AuthBusinessException(AuBzConstant.CANNOT_DEL_CURRENT_USER);
        }
        menuService.deleteRecord(menuId);
        return ResponseUtil.success();
    }

    @RequestMapping("/setseq")
	@ResponseBody
	public Object setseq(
			HttpServletResponse response,
			@RequestParam(value = "currentid", required = false) Long currentid,
			@RequestParam(value = "targetid", required = false) Long targetid,
			@RequestParam(value = "moveType", required = false) String moveType,
			@RequestParam(value = "moveMode", required = false) String moveMode)
			{
		
		this.menuService.seqList( currentid, targetid,  moveType, moveMode);
		return ResponseUtil.success();
			}
			
}
