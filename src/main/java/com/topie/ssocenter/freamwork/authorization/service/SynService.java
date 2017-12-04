package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;

import com.topie.ssocenter.freamwork.authorization.model.SynDivision;
import com.topie.ssocenter.freamwork.authorization.model.SynLog;
import com.topie.ssocenter.freamwork.authorization.model.SynOrg;
import com.topie.ssocenter.freamwork.authorization.model.SynUser;

public interface SynService {

	public abstract String synStart(String appId, String infoCode, String opType);

	public abstract void save(SynOrg synOrg);

	public abstract void save(SynUser synUser);

	public abstract void save(SynDivision synDivision);

	public abstract void save(SynLog synLog);

	public abstract List<SynOrg> selectOrgSynInfo(Long id);

}
