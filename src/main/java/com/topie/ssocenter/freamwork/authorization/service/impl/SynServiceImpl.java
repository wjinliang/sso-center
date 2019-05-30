package com.topie.ssocenter.freamwork.authorization.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisProperties;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSONObject;
import com.topie.ssocenter.freamwork.authorization.dao.ApplicationInfoMapper;
import com.topie.ssocenter.freamwork.authorization.dao.SynDivisionMapper;
import com.topie.ssocenter.freamwork.authorization.dao.SynLogMapper;
import com.topie.ssocenter.freamwork.authorization.dao.SynOrgMapper;
import com.topie.ssocenter.freamwork.authorization.dao.SynUserMapper;
import com.topie.ssocenter.freamwork.authorization.model.ApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.model.SynDivision;
import com.topie.ssocenter.freamwork.authorization.model.SynLog;
import com.topie.ssocenter.freamwork.authorization.model.SynOrg;
import com.topie.ssocenter.freamwork.authorization.model.SynUser;
import com.topie.ssocenter.freamwork.authorization.service.SynService;
import com.topie.ssocenter.freamwork.authorization.utils.SSLIgnoreErrorProtocolSocketFactory;

@Service
public class SynServiceImpl implements SynService {

	@Autowired
	private ApplicationInfoMapper appMapper;
	@Autowired
	private SynOrgMapper synOrgMapper;
	@Autowired
	private SynUserMapper synUserMapper;
	@Autowired
	private SynDivisionMapper synDivisionMapper;
	@Autowired
	private SynLogMapper synLogMapper;

	@Value("${liantongIP}")
	String liantongIP;
	@Value("${yidongIP}")
	String yidongIP;

	public String synStart(String appId, String infoCode, String opType) {
		ApplicationInfo applicationInfo = appMapper.selectByPrimaryKey(appId);
		/*
		 * if(1==1){ return "000"; }
		 */
		ServletRequestAttributes srAttrs = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpServletRequest request = srAttrs.getRequest();
		if (applicationInfo != null) {
			String synType = applicationInfo.getSynType();
			if (synType.equals("axis1")) {
				return synAxis1(request, applicationInfo, infoCode, opType);
			} else if (synType.equals("axis2")) {
				return synAxis2(request, applicationInfo, infoCode, opType);
			} else if (synType.equals("http")) {
				return synHttp(request, applicationInfo, infoCode, opType);
			}
			return "此系统同步借口尚未实现！";
		} else {
			return "此系统尚未注册！";
		}
	}

	private String synHttp(HttpServletRequest request,
			ApplicationInfo applicationInfo, String infoCode, String opType) {
		String notReadNum;
		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("GBK");
		String url = getAppPath(request, applicationInfo);

		// 2.构造PostMethod的实例
		PostMethod postMethod = new PostMethod(url);

		// 3.把参数值放入到PostMethod对象中
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("opType", opType);
		jsonObject.put("infoCode", infoCode);
		String jsonStr = jsonObject.toString();
		postMethod.addParameter("jsonStr", jsonStr);

		try {
			// 4.执行postMethod,调用http接口
			httpClient.executeMethod(postMethod);// 200
			// 5.读取内容
			notReadNum = postMethod.getResponseBodyAsString().trim();
			notReadNum = notReadNum.replace("\"", "");
			if (!notReadNum.equalsIgnoreCase("000")) {
				notReadNum = swtichError(notReadNum);
			}
		} catch (HttpException e) {
			e.printStackTrace();
			return "远程服务调用异常";
		} catch (IOException e) {
			e.printStackTrace();
			return "远程服务调用异常";
		} finally {
			// 7.释放连接
			postMethod.releaseConnection();
		}
		return notReadNum;

	}

	private String synAxis2(HttpServletRequest request,
			ApplicationInfo applicationInfo, String infoCode, String opType) {

		String webServiceURL = getAppPath(request, applicationInfo);
		String qName = applicationInfo.getPackagename().trim();
		String notReadNum;
		try {
			RPCServiceClient serviceClient;
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			if (webServiceURL.startsWith("https")) {
				Protocol protocol = null;
				SSLIgnoreErrorProtocolSocketFactory socketfactory = null;
				socketfactory = new SSLIgnoreErrorProtocolSocketFactory();
				URL url = new URL(webServiceURL);
				protocol = new Protocol("https", socketfactory, url.getPort());
				options.setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER,
						protocol);
			}
			options.setProperty(
					org.apache.axis2.transport.http.HTTPConstants.CONNECTION_TIMEOUT,
					new Integer(480000000));
			EndpointReference targetEPR = new EndpointReference(webServiceURL);
			options.setTo(targetEPR);
			QName opGetAllLegalInfor = new QName(qName, "SynchronizedInfo");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("opType", opType);
			jsonObject.put("infoCode", infoCode);
			String jsonStr = jsonObject.toString();
			Object[] opGetAllLegalInforArgs = new Object[] { jsonStr };
			Class[] returnTypes = new Class[] { String.class };
			Object[] response = serviceClient.invokeBlocking(
					opGetAllLegalInfor, opGetAllLegalInforArgs, returnTypes);
			notReadNum = (String) response[0];
			serviceClient.cleanupTransport();
			if (!notReadNum.equalsIgnoreCase("000")) {
				notReadNum = swtichError(notReadNum);
			}
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
			notReadNum = "远程服务调用异常";
		}
		return notReadNum;
	}

	private String synAxis1(HttpServletRequest request,
			ApplicationInfo applicationInfo, String infoCode, String opType) {
		String webServiceURL = getAppPath(request, applicationInfo);
		String notReadNum;
		// 创建Service实例
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		AxisProperties.setProperty("axis.socketSecureFactory",
				"org.apache.axis.components.net.SunFakeTrustSocketFactory");
		// 通过Service实例创建Call实例
		Call call;
		try {
			call = (Call) service.createCall();
			// 将WebService的服务路径加入到Call实例中，并为Call设置服务的位置

			URL url = new URL(webServiceURL);
			call.setTargetEndpointAddress(url);
			// 调用WebService方法
			if (applicationInfo.getPackagename() != null
					&& !applicationInfo.getPackagename().equals("")) {
				call.setOperationName(new QName(applicationInfo
						.getPackagename(), "SynchronizedInfo"));
				if (applicationInfo.getParamName() != null
						&& !applicationInfo.getParamName().equals("")) {
					call.addParameter(
							new QName(applicationInfo.getPackagename(),
									applicationInfo.getParamName()),
							XMLType.XSD_STRING, ParameterMode.IN);
				}
			} else {
				call.setOperationName("SynchronizedInfo");
				if (applicationInfo.getParamName() != null
						&& !applicationInfo.getParamName().equals("")) {
					call.addParameter(applicationInfo.getParamName(),
							XMLType.XSD_STRING, ParameterMode.IN);
				}
			}
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(applicationInfo.getPackagename()+"SynchronizedInfo");
			call.setReturnType(XMLType.XSD_STRING); 
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("opType", opType);
			jsonObject.put("infoCode", infoCode);
			String jsonStr = jsonObject.toString();
			// 调用WebService传入参数
			notReadNum = (String) call.invoke(new Object[] { jsonStr });
			if (!notReadNum.equalsIgnoreCase("000")) {
				notReadNum = swtichError(notReadNum);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			notReadNum = "服务调用有问题";
		} catch (MalformedURLException e) {
			e.printStackTrace();
			notReadNum = "服务链接格式不对";
		} catch (RemoteException e) {
			e.printStackTrace();
			notReadNum = "远程服务调用异常";
		}
		return notReadNum;
	}

	private String swtichError(String notReadNum) {
		String errorString = notReadNum;
		if (notReadNum.equalsIgnoreCase("111")) {
			errorString = "同步失败";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("101")) {
			errorString = "增加组织唯一标识重复";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("102")) {
			errorString = "增加组织的上级节点不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("103")) {
			errorString = "增加组织时同级组织名称重复";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("201")) {
			errorString = "组织唯一标识对应的组织不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("202")) {
			errorString = "删除的组织下存在子组织";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("203")) {
			errorString = "删除的组织下存在用户";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("204")) {
			errorString = "删除的组织下存在角色";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("301")) {
			errorString = "组织唯一标识对应的组织不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("301------------")) {
			errorString = "组织组织不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("302")) {
			errorString = "修改组织时上级节点不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("303")) {
			errorString = "修改组织时同级组织名称重复";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("401")) {
			errorString = "增加用户唯一标识重复";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("402")) {
			errorString = "增加用户的角色标识编号不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("403")) {
			errorString = "增加用户的组织标识编号不存在";
			return errorString;
		}

		if (notReadNum.equalsIgnoreCase("501")) {
			errorString = "用户唯一标识对应的用户不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("502")) {
			errorString = "修改用户的角色标识编号不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("503")) {
			errorString = "修改用户的组织标识编号不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("601")) {
			errorString = "用户唯一标识对应的用户不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("701")) {
			errorString = "增加角色唯一标识重复";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("702")) {
			errorString = "增加角色名字重复";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("703")) {
			errorString = "增加角色的组织标识编号不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("801")) {
			errorString = "唯一标识对应的角色不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("802")) {
			errorString = "修改角色名字重复";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("803")) {
			errorString = "修改角色的组织标识编号不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("901")) {
			errorString = "唯一标识对应的角色不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("999")) {
			errorString = "机构类型未设置";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1001")) {
			errorString = "格式错误";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1002")) {
			errorString = "其他错误";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1003")) {
			errorString = "调用中心没有找到返回";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1101")) {
			errorString = "增加区划唯一标识存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1102")) {
			errorString = "增加区划没有对应的父节点";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1103")) {
			errorString = "增加区划名字重复";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1104")) {
			errorString = "增加区划区划编码存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1201")) {
			errorString = "更新区划不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1202")) {
			errorString = "更新区划父节点不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1203")) {
			errorString = "更新区划编码已存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1204")) {
			errorString = "更新区划名字已存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1301")) {
			errorString = "删除区划不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1302")) {
			errorString = "删除区划有子节点";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("1303")) {
			errorString = "删除的区存在组织";
			return errorString;
		}

		if (notReadNum.equalsIgnoreCase("104")) {
			errorString = "新增组织没有区划";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("304")) {
			errorString = "更新组织没有区划";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("104")) {
			errorString = "";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("10101")) {
			errorString = "请求参数有误";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("10500")) {
			errorString = "服务器内部错误";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("10404")) {
			errorString = "请求资源不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("20404")) {
			errorString = "操作码不存在";
			return errorString;
		}
		if (notReadNum.equalsIgnoreCase("30404")) {
			errorString = "用户不存在";
			return errorString;
		}

		return errorString;
	}

	/**
	 * 通过当前访问得IP 获取要访问的应用网络类型
	 * 
	 * @param request
	 * @param app
	 * @return
	 */
	private String getAppPath(HttpServletRequest request, ApplicationInfo app) {
		String path1 = app.getSynPath1();
		if (path1 == null || path1.equals("")) {
			return app.getSynPath();
		}
		String ip = request.getServerName();
		if (ip != null && ip.equalsIgnoreCase(liantongIP)) {
			return app.getSynPath();
		}
		if (ip != null && ip.equalsIgnoreCase(yidongIP)) {
			return path1;
		}
		return app.getSynPath();
	}

	@Override
	public void save(SynOrg synOrg) {
		this.synOrgMapper.insert(synOrg);

	}

	@Override
	public void save(SynUser synUser) {
		this.synUserMapper.insert(synUser);

	}

	@Override
	public void save(SynDivision synDivision) {
		this.synDivisionMapper.insert(synDivision);

	}

	@Override
	public void save(SynLog synLog) {
		this.synLogMapper.insert(synLog);

	}

	@Override
	public List<SynOrg> selectOrgSynInfo(Long id) {
		Example example = new Example(SynOrg.class);
		example.createCriteria().andEqualTo("orgId", id);
		return this.synOrgMapper.selectByExample(example);
	}

	@Override
	public List<SynUser> selectUserSynInfo(String code) {
		Example example = new Example(SynUser.class);
		example.createCriteria().andEqualTo("userId", code);
		return this.synUserMapper.selectByExample(example);
	}

	@Override
	public int deleteSynOrg(String orgId, String appId) {
		Example ex = new Example(SynOrg.class);
		ex.createCriteria().andEqualTo("orgId", orgId)
				.andEqualTo("appId", appId);
		return synOrgMapper.deleteByExample(ex);
	}

	@Override
	public int deleteSynUser(String userId, String appId) {
		Example ex = new Example(SynUser.class);
		ex.createCriteria().andEqualTo("userId", userId)
				.andEqualTo("appId", appId);
		return synUserMapper.deleteByExample(ex);
	}
}
