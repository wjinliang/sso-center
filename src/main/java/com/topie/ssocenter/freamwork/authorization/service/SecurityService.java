package com.topie.ssocenter.freamwork.authorization.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
public interface SecurityService {
    OrangeSideSecurityUser loadSecurityUserAccountByLoginName(String loginName);

    Map<String, Collection<ConfigAttribute>> getResourceMap();

    String getDefaultAction(String roleId);
}
