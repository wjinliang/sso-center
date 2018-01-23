package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.*;

@Table(name = "t_filetable")
public class FileEntity {
    @Id
    private String id;

    @Column(name = "CDATE")
    private String cdate;

    @Column(name = "FILESIZE")
    private String filesize;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "URL")
    private String url;

    @Column(name = "CREATEUSER")
    private String createuser;

    @Column(name = "REALPATH")
    private String realpath;

    @Column(name = "SAVEFLAG")
    private String saveflag;

    @Column(name = "userObject")
    private String userobject;

    @Column(name = "OBJFIELD")
    private String objfield;

    @Column(name = "URLFIELD")
    private String urlfield;

    @Column(name = "OBJ_ID")
    private String objId;

    @Column(name = "OBJ_TYPE")
    private String objType;

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
     * @return CDATE
     */
    public String getCdate() {
        return cdate;
    }

    /**
     * @param cdate
     */
    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    /**
     * @return FILESIZE
     */
    public String getFilesize() {
        return filesize;
    }

    /**
     * @param filesize
     */
    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return TYPE
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return CREATEUSER
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * @param createuser
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    /**
     * @return REALPATH
     */
    public String getRealpath() {
        return realpath;
    }

    /**
     * @param realpath
     */
    public void setRealpath(String realpath) {
        this.realpath = realpath;
    }

    /**
     * @return SAVEFLAG
     */
    public String getSaveflag() {
        return saveflag;
    }

    /**
     * @param saveflag
     */
    public void setSaveflag(String saveflag) {
        this.saveflag = saveflag;
    }

    /**
     * @return userObject
     */
    public String getUserobject() {
        return userobject;
    }

    /**
     * @param userobject
     */
    public void setUserobject(String userobject) {
        this.userobject = userobject;
    }

    /**
     * @return OBJFIELD
     */
    public String getObjfield() {
        return objfield;
    }

    /**
     * @param objfield
     */
    public void setObjfield(String objfield) {
        this.objfield = objfield;
    }

    /**
     * @return URLFIELD
     */
    public String getUrlfield() {
        return urlfield;
    }

    /**
     * @param urlfield
     */
    public void setUrlfield(String urlfield) {
        this.urlfield = urlfield;
    }

    /**
     * @return OBJ_ID
     */
    public String getObjId() {
        return objId;
    }

    /**
     * @param objId
     */
    public void setObjId(String objId) {
        this.objId = objId;
    }

    /**
     * @return OBJ_TYPE
     */
    public String getObjType() {
        return objType;
    }

    /**
     * @param objType
     */
    public void setObjType(String objType) {
        this.objType = objType;
    }
}