package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "syn_division")
public class SynDivision {
    @Id
    private String id;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "division_id")
    private String divisionId;

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
     * @return division_id
     */
    public String getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId
     */
    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
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