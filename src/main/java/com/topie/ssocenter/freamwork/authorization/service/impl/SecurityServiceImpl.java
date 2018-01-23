package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.authorization.model.UserRole;
import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;
import com.topie.ssocenter.freamwork.authorization.service.SecurityService;
import com.topie.ssocenter.freamwork.authorization.service.UserAccountService;
import com.topie.ssocenter.freamwork.authorization.service.UserRoleService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    UserAccountService userService;
    @Autowired
    UserRoleService roleService;

    @Override
    public OrangeSideSecurityUser loadSecurityUserAccountByLoginName(String loginName) {
        UserAccount user = userService.findUserAccountByLoginName(loginName);
        if (user == null) {
            return null;
        }
        Collection<GrantedAuthority> userGrantedAuthorities = new ArrayList<GrantedAuthority>();
        List<String> grantedAuthorities = userService.findUserAccountRoleByUserAccountId(user.getCode());
        if (grantedAuthorities != null && grantedAuthorities.size() > 0) {
            for (String grantedAuthority : grantedAuthorities) {
                GrantedAuthority ga = new SimpleGrantedAuthority(grantedAuthority);
                userGrantedAuthorities.add(ga);
            }
        }
        OrangeSideSecurityUser orangeSideSecurityUserAccount = new OrangeSideSecurityUser(user, userGrantedAuthorities);
        return orangeSideSecurityUserAccount;
    }

    @Override
    public Map<String, Collection<ConfigAttribute>> getResourceMap() {
        Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();
//        Map<String, Collection<ConfigAttribute>> resourceMapcache = MenuCache.getInstense().getSecurityMenuMapCache();
//        if(resourceMapcache!=null) return resourceMapcache;
        List<Map> roleFunctions = roleService.findUserRoleMatchUpFunctions();
        if (roleFunctions != null && roleFunctions.size() > 0) {
            for (Map roleFunction : roleFunctions) {
                String url = (String) roleFunction.get("function");
                String role = (String) roleFunction.get("role");
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                if (!resourceMap.containsKey(url)) {
                    configAttributes.add(new SecurityConfig(role));
                    resourceMap.put(url, configAttributes);
                } else {
                    ConfigAttribute configAttribute = new SecurityConfig(role);
                    configAttributes = resourceMap.get(url);
                    configAttributes.add(configAttribute);
                    resourceMap.put(url, configAttributes);
                }
            }
        }
        return resourceMap;
    }

    @Override
    public String getDefaultAction(String roleId) {
        UserRole role = roleService.selectByKey(roleId);
        if (StringUtils.isNotBlank(role.getHomepage()))
            return role.getHomepage();
        return "";
    }

}
