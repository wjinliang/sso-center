package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_user_role")
public class UserRole {
    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "DETAIL")
    private String detail;

    private Boolean enabled;

    @Column(name = "HOMEPAGE")
    private String homepage;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SEQ")
    private Long seq;

    /**
     * @return CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
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
     * @return enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return HOMEPAGE
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * @param homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
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

	@Override
	public String toString() {
		return "UserRole [code=" + code + ", detail=" + detail + ", enabled="
				+ enabled + ", homepage=" + homepage + ", name=" + name
				+ ", seq=" + seq + "]";
	}
    
}