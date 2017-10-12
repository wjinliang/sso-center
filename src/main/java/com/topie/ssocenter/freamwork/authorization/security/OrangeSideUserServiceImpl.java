package com.topie.ssocenter.freamwork.authorization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.topie.ssocenter.freamwork.authorization.service.SecurityService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
public class OrangeSideUserServiceImpl implements UserDetailsService {
    @Autowired
    SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OrangeSideSecurityUser orangeSideSecurityUser = securityService.loadSecurityUserAccountByLoginName(username);
        if (orangeSideSecurityUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return orangeSideSecurityUser;
    }
}
