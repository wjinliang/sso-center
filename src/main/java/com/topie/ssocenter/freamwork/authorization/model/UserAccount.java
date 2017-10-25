package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_user_account")
public class UserAccount {
    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "HEADPIC")
    private String headpic;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "headphoto_id")
    private String headphotoId;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "LOGINNAME")
    private String loginname;

    @Column(name = "MOBILEPHONE")
    private String mobilephone;

    @Column(name = "USERTYPE")
    private String usertype;

    @Column(name = "LASTLOGINTIME")
    private String lastlogintime;

    @Column(name = "LOGINCOUNT")
    private Integer logincount;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "REMOTEIPADDR")
    private String remoteipaddr;

    @Column(name = "ACCOUNT_EXPIRED")
    private Boolean accountExpired;

    @Column(name = "LOCKED")
    private Boolean locked;

    @Column(name = "PASSWORD_EXPIRED")
    private Boolean passwordExpired;

    @Column(name = "SPECIALACTION")
    private String specialaction;

    @Column(name = "ugroup_id")
    private String ugroupId;

    @Column(name = "SYNPASSWORD")
    private String synpassword;

    @Column(name = "DTYPE")
    private String dtype;

    @Column(name = "merge_uuid")
    private String mergeUuid;

    @Column(name = "origin_id")
    private String originId;

    @Column(name = "school_age")
    private String schoolAge;

    private String title;

    private String speciality;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "bizphone_no")
    private String bizphoneNo;

    @Column(name = "fax_no")
    private String faxNo;

    private String address;

    private String duty;

    private String birthday;

    private String gender;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "system_id")
    private String systemId;

    @Column(name = "old_loginname")
    private String oldLoginname;

    @Column(name = "is_delete")
    private Boolean isDelete;

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
     * @return EMAIL
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return ENABLED
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
     * @return HEADPIC
     */
    public String getHeadpic() {
        return headpic;
    }

    /**
     * @param headpic
     */
    public void setHeadpic(String headpic) {
        this.headpic = headpic;
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
     * @return PASSWORD
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return headphoto_id
     */
    public String getHeadphotoId() {
        return headphotoId;
    }

    /**
     * @param headphotoId
     */
    public void setHeadphotoId(String headphotoId) {
        this.headphotoId = headphotoId;
    }

    /**
     * @return org_id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return LOGINNAME
     */
    public String getLoginname() {
        return loginname;
    }

    /**
     * @param loginname
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    /**
     * @return MOBILEPHONE
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * @param mobilephone
     */
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    /**
     * @return USERTYPE
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * @param usertype
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    /**
     * @return LASTLOGINTIME
     */
    public String getLastlogintime() {
        return lastlogintime;
    }

    /**
     * @param lastlogintime
     */
    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    /**
     * @return LOGINCOUNT
     */
    public Integer getLogincount() {
        return logincount;
    }

    /**
     * @param logincount
     */
    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    /**
     * @return MOBILE
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return REMOTEIPADDR
     */
    public String getRemoteipaddr() {
        return remoteipaddr;
    }

    /**
     * @param remoteipaddr
     */
    public void setRemoteipaddr(String remoteipaddr) {
        this.remoteipaddr = remoteipaddr;
    }

    /**
     * @return ACCOUNT_EXPIRED
     */
    public Boolean getAccountExpired() {
        return accountExpired;
    }

    /**
     * @param accountExpired
     */
    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    /**
     * @return LOCKED
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * @param locked
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * @return PASSWORD_EXPIRED
     */
    public Boolean getPasswordExpired() {
        return passwordExpired;
    }

    /**
     * @param passwordExpired
     */
    public void setPasswordExpired(Boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    /**
     * @return SPECIALACTION
     */
    public String getSpecialaction() {
        return specialaction;
    }

    /**
     * @param specialaction
     */
    public void setSpecialaction(String specialaction) {
        this.specialaction = specialaction;
    }

    /**
     * @return ugroup_id
     */
    public String getUgroupId() {
        return ugroupId;
    }

    /**
     * @param ugroupId
     */
    public void setUgroupId(String ugroupId) {
        this.ugroupId = ugroupId;
    }

    /**
     * @return SYNPASSWORD
     */
    public String getSynpassword() {
        return synpassword;
    }

    /**
     * @param synpassword
     */
    public void setSynpassword(String synpassword) {
        this.synpassword = synpassword;
    }

    /**
     * @return DTYPE
     */
    public String getDtype() {
        return dtype;
    }

    /**
     * @param dtype
     */
    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    /**
     * @return merge_uuid
     */
    public String getMergeUuid() {
        return mergeUuid;
    }

    /**
     * @param mergeUuid
     */
    public void setMergeUuid(String mergeUuid) {
        this.mergeUuid = mergeUuid;
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
     * @return school_age
     */
    public String getSchoolAge() {
        return schoolAge;
    }

    /**
     * @param schoolAge
     */
    public void setSchoolAge(String schoolAge) {
        this.schoolAge = schoolAge;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return speciality
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * @param speciality
     */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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
     * @return bizphone_no
     */
    public String getBizphoneNo() {
        return bizphoneNo;
    }

    /**
     * @param bizphoneNo
     */
    public void setBizphoneNo(String bizphoneNo) {
        this.bizphoneNo = bizphoneNo;
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
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return duty
     */
    public String getDuty() {
        return duty;
    }

    /**
     * @param duty
     */
    public void setDuty(String duty) {
        this.duty = duty;
    }

    /**
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return user_type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
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
     * @return old_loginname
     */
    public String getOldLoginname() {
        return oldLoginname;
    }

    /**
     * @param oldLoginname
     */
    public void setOldLoginname(String oldLoginname) {
        this.oldLoginname = oldLoginname;
    }

    /**
     * @return is_delete
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

	@Override
	public String toString() {
		return "UserAccount [code=" + code + ", email=" + email + ", enabled="
				+ enabled + ", headpic=" + headpic + ", name=" + name
				+ ", password=" + password + ", seq=" + seq + ", headphotoId="
				+ headphotoId + ", orgId=" + orgId + ", loginname=" + loginname
				+ ", mobilephone=" + mobilephone + ", usertype=" + usertype
				+ ", lastlogintime=" + lastlogintime + ", logincount="
				+ logincount + ", mobile=" + mobile + ", remoteipaddr="
				+ remoteipaddr + ", accountExpired=" + accountExpired
				+ ", locked=" + locked + ", passwordExpired=" + passwordExpired
				+ ", specialaction=" + specialaction + ", ugroupId=" + ugroupId
				+ ", synpassword=" + synpassword + ", dtype=" + dtype
				+ ", mergeUuid=" + mergeUuid + ", originId=" + originId
				+ ", schoolAge=" + schoolAge + ", title=" + title
				+ ", speciality=" + speciality + ", createDate=" + createDate
				+ ", createUser=" + createUser + ", bizphoneNo=" + bizphoneNo
				+ ", faxNo=" + faxNo + ", address=" + address + ", duty="
				+ duty + ", birthday=" + birthday + ", gender=" + gender
				+ ", userType=" + userType + ", systemId=" + systemId
				+ ", oldLoginname=" + oldLoginname + ", isDelete=" + isDelete
				+ "]";
	}
    
}