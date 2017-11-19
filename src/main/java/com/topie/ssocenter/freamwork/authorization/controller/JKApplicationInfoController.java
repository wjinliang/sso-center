package com.topie.ssocenter.freamwork.authorization.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageInfo;
import com.topie.ssocenter.common.utils.ResponseUtil;
import com.topie.ssocenter.common.utils.UUIDUtil;
import com.topie.ssocenter.freamwork.authorization.model.JKApplicationInfo;
import com.topie.ssocenter.freamwork.authorization.service.JKApplicationInfoService;
import com.topie.ssocenter.freamwork.authorization.utils.R;

@Controller
@RequestMapping({ "/xtjk" })
public class JKApplicationInfoController {

	 @Autowired
	 private JKApplicationInfoService appService;
	 /**
	  * 系统列表
	  * @param model
	  * @param thispage
	  * @param pagesize
	  * @return
	  */
		@RequestMapping({ "/listApp" })
		public ModelAndView listApp(
				ModelAndView model,
				@RequestParam(value = "thispage", required = false) Integer thispage,
				@RequestParam(value = "pagesize", required = false) Integer pagesize,
				JKApplicationInfo app) {

				if (pagesize == null) {
					pagesize = Integer.valueOf(10);
				}
				if (thispage == null) {
					thispage = Integer.valueOf(0);
				}
				PageInfo<JKApplicationInfo> page = appService.findApplicationInfoList(thispage, pagesize, app);
				model.addObject(R.PAGE, page);
				model.addObject(R.SEARCHMODEL, app);
				model.setViewName("/jkapp/appList");
				return model;
		}


		@RequestMapping({ "/form/{mode}" })
		public ModelAndView gotoCreateAppPage(ModelAndView model,
				@PathVariable String mode,
				@RequestParam(value = "appid", required = false) String appid) {
			if ((mode != null) && (!mode.equals("new"))) {
				JKApplicationInfo appInfo = appService.selectByKey(appid);
				model.addObject("appInfo", appInfo);
			} 
			model.setViewName("/jkapp/viewAppPage");
			return model;
		}

		@RequestMapping({ "/saveApp" })
		public ModelAndView saveApp(ModelAndView model, JKApplicationInfo app) {
			if(StringUtils.isNotEmpty(app.getId())){
				this.appService.updateNotNull(app);
				model.setViewName("redirect:listApp");
				return model;
			}
			String uuid = UUIDUtil.getUUID();
			app.setId(uuid);
			this.appService.save(app);
			model.setViewName("redirect:listApp");
			return model;
		}

		@RequestMapping({ "/checkunique" })
		@ResponseBody
		public Object checkunique(
				@RequestParam(value = "param", required = true) String param,
				@RequestParam(value = "name", required = true) String name,
				@RequestParam(value = "id", required = false) String id) {
			
				Example example =new Example(JKApplicationInfo.class);
				example.createCriteria().andEqualTo(name,param);
				List<JKApplicationInfo> list = this.appService.selectByExample(example);
				if (id != null && !id.equals("")) {
					if (list.size() > 1) {
						return false;
					} else if (list.size() == 1) {
						JKApplicationInfo applicationInfo = list.get(0);
						if (applicationInfo.getId().equals(id)) {
							return true;
						} else {
							return false;
						}
					} else {
						return true;
					}
				} else {
					if (list.size() > 0) {
						return false;
					} else {
						return true;
					}
				}
		}

		@RequestMapping({ "/delete" })
		@ResponseBody
		public Object delete(
				@RequestParam(value = "appid", required = false) String appid)
				{
				if (appid != null) {
					String[] rid = appid.split(",");
					for (String str : rid) {
						this.appService.delete(str);
					}
				}
				return ResponseUtil.success();
		}

		@RequestMapping({ "/jianKong" })
		@ResponseBody
		public Object jianKong(
				@RequestParam(value = "appid", required = true) String appid)
				{
				JKApplicationInfo applicationInfo = this.appService.selectByKey(appid);
				HttpClient httpclient = new HttpClient();
				httpclient.getHttpConnectionManager().getParams()
						.setConnectionTimeout(2500);
				httpclient.getHttpConnectionManager().getParams().setSoTimeout(2500);
				String url = applicationInfo.getAppPath();
				GetMethod getMethod = new GetMethod(url);
				getMethod.getParams().setParameter("http.method.retry-handler",
						new DefaultHttpMethodRetryHandler());
				int statusCode = 0;
				try {
					statusCode = httpclient.executeMethod(getMethod);
					return ResponseUtil.success(statusCode);
				} catch (IOException e) {
					e.printStackTrace();
					return ResponseUtil.success(statusCode);
				}
		}
		@RequestMapping({ "/ping" })
		@ResponseBody
		public Object ping(ModelAndView model,
				@RequestParam(value = "appId", required = true) String appId) {
				Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
			try {
				JKApplicationInfo applicationInfo = this.appService.selectByKey(appId);
				String appPath = applicationInfo.getAppPath();
				String ip = getAppIp(appPath);
				System.out.println("应用IP为："+ip);
				Process process = null; // 声明处理类对象
				String line = null; // 返回行信息
				InputStream is = null; // 输入流
				InputStreamReader isr = null; // 字节流
				BufferedReader br = null;
				boolean res = false;// 结果
				process = runtime.exec("ping " + ip); // PING
				is = process.getInputStream(); // 实例化输入流
				isr = new InputStreamReader(is, "GBK");// 把输入流转换成字节流
				br = new BufferedReader(isr);// 从字节中读取文本
				int i =0;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if (line.contains("TTL") || line.contains("ttl")) {
						res = true;
						break;
					}
					i++;
					if(i>=4){
						break;
					}
					System.out.println(i);
				}
				is.close();
				isr.close();
				br.close();
				if (res) {
					//System.out.println("ping 通  ...");
					return ResponseUtil.success("ping 通...");
				} else {
					//System.out.println("ping 不通...");
					return ResponseUtil.error("ping 不通...");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseUtil.error("ping 不通...");
			}
		}
		
		public String getAppIp(String url) {
			String result = url.substring(7);
			String[] urlsz = result.split(":");
			String ip = urlsz[0];
			return ip;
		}
	
}
