package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.*;

@Table(name = "syn_org")
public class synOrg {
    private String id;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "org_id")
    private String orgId;

    @Column(name = "syn_time")
    private String synTime;

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
     * @return app_id
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return org_id
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * @return syn_time
     */
    public String getSynTime() {
        return synTime;
    }

    /**
     * @param synTime
     */
    public void setSynTime(String synTime) {
        this.synTime = synTime;
    }
}