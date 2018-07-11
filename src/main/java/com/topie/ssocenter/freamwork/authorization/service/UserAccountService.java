package com.topie.ssocenter.freamwork.authorization.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;
import com.topie.ssocenter.freamwork.database.baseservice.IService;

/**
 */
public interface UserAccountService extends IService<UserAccount,String> {


    UserAccount findUserAccountByLoginName(String loginName);

    int insertUserAccountRole(String userId, String roleId);

    List<String> findUserAccountRoleByUserAccountId(String userId);

    PageInfo<UserAccount> findUserAccountList(Integer pageNum, Integer pageSize, UserAccount user);

    int findExistUserAccount(UserAccount user);

	String selectMaxUserLoginNameByOrgCode(String code);

	void updatePassword(String userId,String oldPassword, String newPassword);
	
	/**
	 * 获取用户没有关联的列表
	 * @param thispage
	 * @param pagesize
	 * @param user   code  mergeid
	 * @return
	 */
	PageInfo<UserAccount> listNotMergeUsers(Integer thispage, Integer pagesize,
			UserAccount user);

	/**
	 * 获取用户关联的列表
	 * @param thispage
	 * @param pagesize
	 * @param user   code  mergeid
	 * @return
	 */
	PageInfo<UserAccount> listMergeUsers(UserAccount user, Integer thispage,
			Integer pagesize);
	/**
	 * appId 查找已同步的用户
	 * @param thispage
	 * @param pagesize
	 * @param user
	 * @param appId
	 * @return
	 */
	PageInfo<UserAccount> findSynUserByAppId(Integer thispage,
			Integer pagesize, UserAccount user, String appId);
	/**
	 * appId 查找未同步的用户
	 * @param thispage
	 * @param pagesize
	 * @param user
	 * @param appId
	 * @return
	 */
	PageInfo<UserAccount> findToSynUserByAppId(Integer thispage,
			Integer pagesize, UserAccount user, String appId);
	/**
	 * tongbu
	 * @param user
	 * @param appId
	 * @param typeCode 11 新增 12 更新 13 删除
	 * @param typeName
	 * @return u.put("opType", typeName);
	 *	u.put("appName", app.getAppName());
	 *	u.put("appId", app.getId());
	 *	u.put("appCode", app.getAppCode());
	 *	u.put("status", true);
	 *  u.put("result", 操作成功);
	 */
	Map synOneUser(UserAccount user, String appId, String typeCode, String typeName);
	/**
	 * 更新用户所属系统
	 * @param id
	 * @param startTime
	 */
	void updateUserSystem(String id, String startTime);

	UserAccount selectUserByXtbs(String mergeUuid,String appId);
}
