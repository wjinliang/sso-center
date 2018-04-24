package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_org")
public class Org {
    @Id
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "division_id")
    private String divisionId;

    @Column(name = "division_code")
    private String divisionCode;

    private String type;

    @Column(name = "origin_id")
    private String originId;

    @Column(name = "lead_name")
    private String leadName;

    @Column(name = "link_man")
    private String linkMan;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "system_id")
    private String systemId;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "postal_address")
    private String postalAddress;

    @Column(name = "fax_no")
    private String faxNo;

    @Column(name = "phone_no")
    private String phoneNo;
    
    @Column(name = "org_type")
    private String orgType;

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

    /**
     * @return parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
     * @return division_code
     */
    public String getDivisionCode() {
        return divisionCode;
    }

    /**
     * @param divisionCode
     */
    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    /**
     * @return type
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
     * @return origin_id
     */
    public String getOriginId() {
        return originId;
    }

    /**
     * @param originId
     */
    public void setOriginId(String originId) {
        this.originId = originId;
    }

    /**
     * @return lead_name
     */
    public String getLeadName() {
        return leadName;
    }

    /**
     * @param leadName
     */
    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    /**
     * @return link_man
     */
    public String getLinkMan() {
        return linkMan;
    }

    /**
     * @param linkMan
     */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    /**
     * @return create_date
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * @return system_id
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * @param systemId
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * @return postal_code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return postal_address
     */
    public String getPostalAddress() {
        return postalAddress;
    }

    /**
     * @param postalAddress
     */
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     * @return fax_no
     */
    public String getFaxNo() {
        return faxNo;
    }

    /**
     * @param faxNo
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    /**
     * @return phone_no
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Override
	public String toString() {
		return "Org [id=" + id + ", code=" + code + ", name=" + name + ", seq="
				+ seq + ", parentId=" + parentId + ", divisionId=" + divisionId
				+ ", divisionCode=" + divisionCode + ", type=" + type
				+ ", originId=" + originId + ", leadName=" + leadName
				+ ", linkMan=" + linkMan + ", createDate=" + createDate
				+ ", createUser=" + createUser + ", systemId=" + systemId
				+ ", postalCode=" + postalCode + ", postalAddress="
				+ postalAddress + ", faxNo=" + faxNo + ", phoneNo=" + phoneNo
				+ ", orgType=" + orgType
				+ "]";
	}
    
}