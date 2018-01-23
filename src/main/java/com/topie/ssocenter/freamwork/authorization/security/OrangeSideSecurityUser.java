package com.topie.ssocenter.freamwork.authorization.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
public class OrangeSideSecurityUser extends User {
    private String id;
    private String loginName;
    private String displayName;
    private String email;
    private String contactPhone;
    private String mergeUuid;
    private Long orgId;
    //private Date lastPasswordReset;
    public OrangeSideSecurityUser(com.topie.ssocenter.freamwork.authorization.model.UserAccount user,
                                  Collection<GrantedAuthority> userGrantedAuthorities) {
        super(user.getLoginname(), user.getPassword(), user.getEnabled(),
                user.getAccountExpired(), user.getPasswordExpired(),
                !user.getLocked(), userGrantedAuthorities);
        if (user != null) {
            setId(user.getCode());
            setLoginName(user.getLoginname());
            setDisplayName(user.getName());
            setEmail(user.getEmail());
            setContactPhone(user.getMobile());
            setOrgId(user.getOrgId());
            setMergeUuid(user.getMergeUuid());
            //setLastPasswordReset(user.getLastPasswordReset());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

	public String getMergeUuid() {
		return mergeUuid;
	}

	public void setMergeUuid(String mergeUuid) {
		this.mergeUuid = mergeUuid;
	}

}
