package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "syn_log")
public class SynLog {
	@Id
    private String id;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "syn_time")
    private String synTime;

    @Column(name = "syn_result")
    private String synResult;

    @Column(name = "syn_userid")
    private String synUserid;

    @Column(name = "syn_username")
    private String synUsername;

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

    /**
     * @return syn_result
     */
    public String getSynResult() {
        return synResult;
    }

    /**
     * @param synResult
     */
    public void setSynResult(String synResult) {
        this.synResult = synResult;
    }

    /**
     * @return syn_userid
     */
    public String getSynUserid() {
        return synUserid;
    }

    /**
     * @param synUserid
     */
    public void setSynUserid(String synUserid) {
        this.synUserid = synUserid;
    }

    /**
     * @return syn_username
     */
    public String getSynUsername() {
        return synUsername;
    }

    /**
     * @param synUsername
     */
    public void setSynUsername(String synUsername) {
        this.synUsername = synUsername;
    }
}