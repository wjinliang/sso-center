package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "app_file")
public class AppFile {
    @Id
    private Integer id;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "app_id")
    private String appId;

    /**
     * 1启用 0停用
     */
    private String filestatus;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return file_id
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
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
     * 获取1启用 2停用
     *
     * @return filestatus - 1启用 2停用
     */
    public String getFilestatus() {
        return filestatus;
    }

    /**
     * 设置1启用 2停用
     *
     * @param filestatus 1启用 2停用
     */
    public void setFilestatus(String filestatus) {
        this.filestatus = filestatus;
    }
}