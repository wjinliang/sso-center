package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_division")
public class Division {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "FULLNAME")
    private String fullname;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "CODE")
    private Long code;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "REGIONSOURCETYPE")
    private Integer regionsourcetype;

    @Column(name = "BIGDIVISION")
    private String bigdivision;

    @Column(name = "BUILDERCODE")
    private Integer buildercode;

    @Column(name = "CREATETIME")
    private String createtime;

    @Column(name = "ISDELETE")
    private Integer isdelete;

    private Integer level;

    @Column(name = "ISNONSTANDARD")
    private Integer isnonstandard;

    @Column(name = "ISPROVINCEMANAGECOUNTY")
    private Integer isprovincemanagecounty;

    @Column(name = "EXREGIONGUID")
    private String exregionguid;

    private Integer seq;

    /**
     * @return ID
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
     * @return FULLNAME
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
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
     * @return PARENT_ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return CODE
     */
    public Long getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(Long code) {
        this.code = code;
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
     * @return REGIONSOURCETYPE
     */
    public Integer getRegionsourcetype() {
        return regionsourcetype;
    }

    /**
     * @param regionsourcetype
     */
    public void setRegionsourcetype(Integer regionsourcetype) {
        this.regionsourcetype = regionsourcetype;
    }

    /**
     * @return BIGDIVISION
     */
    public String getBigdivision() {
        return bigdivision;
    }

    /**
     * @param bigdivision
     */
    public void setBigdivision(String bigdivision) {
        this.bigdivision = bigdivision;
    }

    /**
     * @return BUILDERCODE
     */
    public Integer getBuildercode() {
        return buildercode;
    }

    /**
     * @param buildercode
     */
    public void setBuildercode(Integer buildercode) {
        this.buildercode = buildercode;
    }

    /**
     * @return CREATETIME
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    /**
     * @return ISDELETE
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * @param isdelete
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * @return level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return ISNONSTANDARD
     */
    public Integer getIsnonstandard() {
        return isnonstandard;
    }

    /**
     * @param isnonstandard
     */
    public void setIsnonstandard(Integer isnonstandard) {
        this.isnonstandard = isnonstandard;
    }

    /**
     * @return ISPROVINCEMANAGECOUNTY
     */
    public Integer getIsprovincemanagecounty() {
        return isprovincemanagecounty;
    }

    /**
     * @param isprovincemanagecounty
     */
    public void setIsprovincemanagecounty(Integer isprovincemanagecounty) {
        this.isprovincemanagecounty = isprovincemanagecounty;
    }

    /**
     * @return EXREGIONGUID
     */
    public String getExregionguid() {
        return exregionguid;
    }

    /**
     * @param exregionguid
     */
    public void setExregionguid(String exregionguid) {
        this.exregionguid = exregionguid;
    }

    /**
     * @return seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

	@Override
	public String toString() {
		return "Division [id=" + id + ", fullname=" + fullname + ", name="
				+ name + ", parentId=" + parentId + ", code=" + code
				+ ", type=" + type + ", regionsourcetype=" + regionsourcetype
				+ ", bigdivision=" + bigdivision + ", buildercode="
				+ buildercode + ", createtime=" + createtime + ", isdelete="
				+ isdelete + ", level=" + level + ", isnonstandard="
				+ isnonstandard + ", isprovincemanagecounty="
				+ isprovincemanagecounty + ", exregionguid=" + exregionguid
				+ ", seq=" + seq + "]";
	}
    
}