package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.*;

@Table(name = "t_user_menu")
public class UserMenu {
    @Id
    private Long id;

    @Column(name = "CHECKED")
    private Boolean checked;

    @Column(name = "DETAIL")
    private String detail;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "ISSHOW")
    private Boolean isshow;

    @Column(name = "NAME")
    private String name;

    @Column(name = "OPEN")
    private Boolean open;

    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "MENUURL")
    private String menuurl;

    @Column(name = "PID")
    private Long pid;

    @Column(name = "ISCOMMON")
    private String iscommon;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return CHECKED
     */
    public Boolean getChecked() {
        return checked;
    }

    /**
     * @param checked
     */
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    /**
     * @return DETAIL
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return ICON
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return ISSHOW
     */
    public Boolean getIsshow() {
        return isshow;
    }

    /**
     * @param isshow
     */
    public void setIsshow(Boolean isshow) {
        this.isshow = isshow;
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
     * @return OPEN
     */
    public Boolean getOpen() {
        return open;
    }

    /**
     * @param open
     */
    public void setOpen(Boolean open) {
        this.open = open;
    }

    /**
     * @return SEQ
     */
    public Long getSeq() {
        return seq;
    }

    /**
     * @param seq
     */
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    /**
     * @return MENUURL
     */
    public String getMenuurl() {
        return menuurl;
    }

    /**
     * @param menuurl
     */
    public void setMenuurl(String menuurl) {
        this.menuurl = menuurl;
    }

    /**
     * @return PID
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return ISCOMMON
     */
    public String getIscommon() {
        return iscommon;
    }

    /**
     * @param iscommon
     */
    public void setIscommon(String iscommon) {
        this.iscommon = iscommon;
    }
}