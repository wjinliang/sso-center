package com.topie.ssocenter.freamwork.authorization.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_division_system")
public class DivisionSystem {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "system_id")
    private String systemId;

    @Column(name = "division_id")
    private String divisionId;

    @Column(name = "division_name")
    private String divisionName;

    @Column(name = "division_code")
    private String divisionCode;

    @Column(name = "new_division_code")
    private String newDivisionCode;

    @Column(name = "create_time")
    private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getNewDivisionCode() {
		return newDivisionCode;
	}

	public void setNewDivisionCode(String newDivisionCode) {
		this.newDivisionCode = newDivisionCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

    
}