package com.topie.ssocenter.freamwork.authorization.security;

import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;
import com.topie.ssocenter.common.cache.BasicCache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import sun.security.util.SecurityConstants;

/**
 * Created by cgj on 2016/4/13.
 */
public class OrangeSideUserCache implements UserCache, InitializingBean {
    private BasicCache<String, UserDetails> cache = new OrangeSideNullCache<String, UserDetails>();

    @Override public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache);
    }

    @Override public UserDetails getUserFromCache(String username) {
        UserDetails userDetails = null;
        try {
            userDetails =
                (UserDetails) cache.get(OrangeSideSecurityConstant.USER_CACHE_PREFIX + username);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return userDetails == null ? null : userDetails;
        }

    }

    @Override public void putUserInCache(UserDetails user) {
        cache.set(OrangeSideSecurityConstant.USER_CACHE_PREFIX + user.getUsername(), user);
    }

    @Override public void removeUserFromCache(String username) {
        cache.del(OrangeSideSecurityConstant.USER_CACHE_PREFIX + username);
    }

    public void setCache(BasicCache cache) {
        this.cache = cache;
    }


}
