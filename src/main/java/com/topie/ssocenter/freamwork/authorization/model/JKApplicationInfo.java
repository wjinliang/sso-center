package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "jk_applicationinfo")
public class JKApplicationInfo {
	@Id
    private String id;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "app_code")
    private String appCode;

    @Column(name = "app_path")
    private String appPath;

    @Column(name = "app_username")
    private String appUsername;

    @Column(name = "app_password")
    private String appPassword;

    private String description;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return app_name
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return app_code
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * @param appCode
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    /**
     * @return app_path
     */
    public String getAppPath() {
        return appPath;
    }

    /**
     * @param appPath
     */
    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    /**
     * @return app_username
     */
    public String getAppUsername() {
        return appUsername;
    }

    /**
     * @param appUsername
     */
    public void setAppUsername(String appUsername) {
        this.appUsername = appUsername;
    }

    /**
     * @return app_password
     */
    public String getAppPassword() {
        return appPassword;
    }

    /**
     * @param appPassword
     */
    public void setAppPassword(String appPassword) {
        this.appPassword = appPassword;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

	@Override
	public String toString() {
		return "JKApplicationInfo [id=" + id + ", appName=" + appName
				+ ", appCode=" + appCode + ", appPath=" + appPath
				+ ", appUsername=" + appUsername + ", appPassword="
				+ appPassword + ", description=" + description + "]";
	}
    
    
}