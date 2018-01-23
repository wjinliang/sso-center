package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;
import java.util.Map;

import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;

public interface TjfxService {

	List<Map> getUserOrgCountForApp(ApplicationInfo app);

	List<Map> getUserCountForDivision(ApplicationInfo app);

	List<Map> getUserLonginCountForDivision(ApplicationInfo app);

	List<Map> getUserDelCountForDivision(ApplicationInfo app);

}
