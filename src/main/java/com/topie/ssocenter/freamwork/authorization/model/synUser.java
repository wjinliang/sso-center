package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.*;

@Table(name = "syn_user")
public class synUser {
    private String id;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "user_id")
    private String userId;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
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