package com.topie.ssocenter.freamwork.authorization.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.topie.ssocenter.common.utils.DmDateUtil;
import com.topie.ssocenter.freamwork.authorization.model.FileEntity;
import com.topie.ssocenter.freamwork.authorization.service.FileEntityService;
import com.topie.ssocenter.freamwork.authorization.utils.SecurityUtils;

@Controller
@RequestMapping({ "/KE" })
public class KEController {
	public PrintWriter writer = null;

	@Resource
	FileEntityService fileService;

	@Value("${file.htmlDir}")
	String htmlFolder;

	@Value("${file.uploadPath}")
	String uploadPath;

	@Value("${file.resource.basePath}")
	String resource;

	@RequestMapping(value="download/{fileId}")
	public void download(HttpServletRequest req,
			HttpServletResponse resp,@PathVariable String fileId){
		FileEntity file = this.fileService.selectByKey(fileId);
		String path = file.getRealpath();
		String name = file.getName();
		OutputStream out = null;
		InputStream in = null;
		try{
			req.setCharacterEncoding("UTF-8");
		    //第一步：设置响应类型
		    resp.setContentType("application/force-download");//应用程序强制下载
		    //第二读取文件
		    in = new FileInputStream(path);
		    //设置响应头，对文件进行url编码
		    name = URLEncoder.encode(name, "UTF-8");
		    resp.setHeader("Content-Disposition", "attachment;filename="+name);   
		    resp.setContentLength(in.available());
		    
		    //第三步：老套路，开始copy
		    out = resp.getOutputStream();
		    byte[] b = new byte[1024];
		    int len = 0;
		    while((len = in.read(b))!=-1){
		      out.write(b, 0, len);
		    }
		    out.flush();
		    out.close();
		    in.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
	}
	@RequestMapping(value = { "/file_upload" }, method = { RequestMethod.POST })
	public void file_upload(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "imgFile", required = false) MultipartFile file)
			throws Exception {
		try {
			String webappDir = request.getSession().getServletContext()
					.getRealPath("/");
			String projectName = request.getContextPath();
			webappDir = webappDir.substring(0,
					webappDir.indexOf(projectName.substring(1)));
			String savePath = webappDir + this.htmlFolder + this.resource
					+ this.uploadPath + "/"
					+ SecurityUtils.getCurrentSecurityUser().getId() + "/";

			String saveUrl = this.htmlFolder + this.resource + this.uploadPath
					+ "/" + SecurityUtils.getCurrentSecurityUser().getId()
					+ "/";

			HashMap extMap = new HashMap();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			extMap.put("flash", "swf,flv");
			extMap.put("media",
					"swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
			extMap.put("file",
					"flv,mp4,doc,docx,xls,xlsx,ppt,txt,zip,rar,gz,bz2");

			long maxSize = 40000000000000L;

			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			this.writer = response.getWriter();
			if (file == null) {
				this.writer.println(getError("请选择文件。"));
				return;
			}
			if (!ServletFileUpload.isMultipartContent(request)) {
				this.writer.println(getError("请选择文件。"));
				return;
			}

			File uploadDir = new File(savePath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			if (!uploadDir.isDirectory()) {
				this.writer.println(getError("上传目录不存在。"));
				return;
			}

			if (!uploadDir.canWrite()) {
				this.writer.println(getError("上传目录没有写权限。"));
				return;
			}

			String dirName = request.getParameter("dir");
			if (dirName == null) {
				dirName = "image";
			}
			if (!extMap.containsKey(dirName)) {
				this.writer.println(getError("目录名不正确。"));
				return;
			}

			savePath = savePath + dirName + "/";
			saveUrl = saveUrl + dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath = savePath + ymd + "/";
			saveUrl = saveUrl + ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			String fileName = file.getOriginalFilename();

			if (file.getSize() > maxSize) {
				this.writer.println(getError("上传文件大小超过限制。"));
				return;
			}

			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
					.toLowerCase();
			if (!Arrays.asList(((String) extMap.get(dirName)).split(","))
					.contains(fileExt)) {
				this.writer.println(getError("上传文件扩展名是不允许的扩展名。\n只允许"
						+ (String) extMap.get(dirName) + "格式。"));
				return;
			}

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_"
					+ new Random().nextInt(1000) + "_" + fileName;
			String id=String.valueOf(System.currentTimeMillis());
			try {
				SaveFileFromInputStream(file.getInputStream(), savePath,
						newFileName);
				FileEntity entity = new FileEntity();
				entity.setId(id);
				entity.setCdate(DmDateUtil.Current());
				entity.setFilesize(String.valueOf(file.getSize()));
				entity.setName(fileName);
				entity.setRealpath(savePath + newFileName);
				entity.setSaveflag("1");
				entity.setType(file.getContentType());
				entity.setUrl(saveUrl + newFileName);
				entity.setCreateuser(SecurityUtils.getCurrentSecurityUser()
						.getId());
				this.fileService.save(entity);
			} catch (Exception e) {
				this.writer.println(getError("上传文件失败。"));
			}
			JSONObject msg = new JSONObject();
			msg.put("error", Integer.valueOf(0));
			msg.put("url", saveUrl + newFileName);
			msg.put("id", id);
			msg.put("name", fileName);
			this.writer.println(msg.toString());
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SaveFileFromInputStream(InputStream stream, String path,
			String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + "/" + filename);
		byte[] buffer = new byte[1048576];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

	@RequestMapping({ "/file_manager" })
	public void file_manager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String webappDir = request.getSession().getServletContext()
				.getRealPath("/");
		String projectName = request.getContextPath();
		webappDir = webappDir.substring(0,
				webappDir.indexOf(projectName.substring(1)));
		String rootPath = webappDir + this.htmlFolder + this.resource
				+ this.uploadPath + "/"
				+ SecurityUtils.getCurrentSecurityUser().getId() + "/";

		String rootUrl = this.htmlFolder + this.resource + this.uploadPath
				+ "/" + SecurityUtils.getCurrentSecurityUser().getId() + "/";
		System.out.println(rootUrl);

		String[] fileTypes = { "gif", "jpg", "jpeg", "png", "bmp" };

		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if (!Arrays.asList(
					new String[] { "image", "flash", "media", "file" })
					.contains(dirName)) {
				out.println("Invalid Directory name.");
				return;
			}
			rootPath = rootPath + dirName + "/";
			rootUrl = rootUrl + dirName + "/";

			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}

		String path = request.getParameter("path") != null ? request
				.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0,
					currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0,
					str.lastIndexOf("/") + 1) : "";
		}

		String order = request.getParameter("order") != null ? request
				.getParameter("order").toLowerCase() : "name";

		if (path.indexOf("..") >= 0) {
			out.println("Access is not allowed.");
			return;
		}

		if ((!"".equals(path)) && (!path.endsWith("/"))) {
			out.println("Parameter is not valid.");
			return;
		}

		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			out.println("Directory does not exist.");
			return;
		}

		List fileList = new ArrayList();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable hash = new Hashtable();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", Boolean.valueOf(true));
					hash.put("has_file",
							Boolean.valueOf(file.listFiles() != null));
					hash.put("filesize", Long.valueOf(0L));
					hash.put("is_photo", Boolean.valueOf(false));
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", Boolean.valueOf(false));
					hash.put("has_file", Boolean.valueOf(false));
					hash.put("filesize", Long.valueOf(file.length()));
					hash.put(
							"is_photo",
							Boolean.valueOf(Arrays.asList(fileTypes).contains(
									fileExt)));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long
								.valueOf(file.lastModified())));
				fileList.add(hash);
			}
		}

		if ("size".equals(order))
			Collections.sort(fileList, new SizeComparator());
		else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else
			Collections.sort(fileList, new NameComparator());

		JSONObject msg = new JSONObject();
		msg.put("moveup_dir_path", moveupDirPath);
		msg.put("current_dir_path", currentDirPath);
		msg.put("current_url", currentUrl);
		msg.put("total_count", Integer.valueOf(fileList.size()));
		msg.put("file_list", fileList);

		String msgStr = msg.toString();
		out.println(msgStr);
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", Integer.valueOf(1));
		obj.put("message", message);
		return obj.toString();
	}

	class NameComparator implements Comparator {
		NameComparator() {
		}

		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if ((((Boolean) hashA.get("is_dir")).booleanValue())
					&& (!((Boolean) hashB.get("is_dir")).booleanValue()))
				return -1;
			if ((!((Boolean) hashA.get("is_dir")).booleanValue())
					&& (((Boolean) hashB.get("is_dir")).booleanValue())) {
				return 1;
			}
			return ((String) hashA.get("filename")).compareTo((String) hashB
					.get("filename"));
		}
	}

	class SizeComparator implements Comparator {
		SizeComparator() {
		}

		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if ((((Boolean) hashA.get("is_dir")).booleanValue())
					&& (!((Boolean) hashB.get("is_dir")).booleanValue()))
				return -1;
			if ((!((Boolean) hashA.get("is_dir")).booleanValue())
					&& (((Boolean) hashB.get("is_dir")).booleanValue())) {
				return 1;
			}
			if (((Long) hashA.get("filesize")).longValue() > ((Long) hashB
					.get("filesize")).longValue())
				return 1;
			if (((Long) hashA.get("filesize")).longValue() < ((Long) hashB
					.get("filesize")).longValue()) {
				return -1;
			}
			return 0;
		}
	}

	class TypeComparator implements Comparator {
		TypeComparator() {
		}

		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if ((((Boolean) hashA.get("is_dir")).booleanValue())
					&& (!((Boolean) hashB.get("is_dir")).booleanValue()))
				return -1;
			if ((!((Boolean) hashA.get("is_dir")).booleanValue())
					&& (((Boolean) hashB.get("is_dir")).booleanValue())) {
				return 1;
			}
			return ((String) hashA.get("filetype")).compareTo((String) hashB
					.get("filetype"));
		}
	}
}
