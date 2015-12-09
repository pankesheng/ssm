package com.ssm.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssm.common.Configuration;
import com.zcj.util.UtilString;
import com.zcj.util.filenameutils.FilenameUtils;
import com.zcj.web.dto.ServiceResult;
import com.zcj.web.dto.UploadErrorResult;
import com.zcj.web.dto.UploadResult;
import com.zcj.web.dto.UploadSuccessResult;
import com.zcj.web.springmvc.action.BasicAction;

@Controller
@RequestMapping("/file")
@Scope("prototype")
public class FileAction extends BasicAction {

	private static final String DEFAULT_FOLDER = "temp";

	// 文件上传
	@RequestMapping("/upload.ajax")
	public void upload(String type, HttpServletRequest request, PrintWriter out) throws Exception {

		if (!(request instanceof MultipartHttpServletRequest)) {
			out.write(ServiceResult.initErrorJson("请设置正确的上传方式"));
			return;
		}

		MultipartHttpServletRequest rm = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> mfs = rm.getFileMap();
		if (mfs == null || mfs.size() == 0) {
			out.write(ServiceResult.initErrorJson("请确定上传的内容中包含文件域"));
			return;
		}
		
		int fileCount = 0;
		for (Map.Entry<String, MultipartFile> entry : mfs.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				fileCount++;
			}
		}
		
		if (fileCount == 0) {
			out.write(ServiceResult.initErrorJson("请选择文件后上传"));
			return;
		}
		
		if (StringUtils.isBlank(type)) {
			type = DEFAULT_FOLDER;
		}
		String basePath = Configuration.getRealPath();
		if (!basePath.endsWith(File.separator)) {
			basePath += File.separator;
		}
		String absolutePath = basePath + type.replace("-", File.separator) + File.separator;
		File dir = new File(absolutePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		if (fileCount == 1) {
			for (Map.Entry<String, MultipartFile> entry : mfs.entrySet()) {
				MultipartFile file = entry.getValue();
				if (!file.isEmpty()) {
					String suffix = FilenameUtils.getExtension(file.getOriginalFilename());// 文件后缀
					String newFilename = UtilString.getSoleCode() + "." + suffix;// 保存后的文件名
					try {
						FileUtils.copyInputStreamToFile(file.getInputStream(), new File(absolutePath + newFilename));
						out.write(ServiceResult.initSuccessJson(new UploadSuccessResult(file.getOriginalFilename(), file.getSize(), file.getContentType(), entry.getKey(), suffix, newFilename, "/" + type.replace("-", "/") + "/" + newFilename)));
					} catch (Exception e) {
						e.printStackTrace();
						out.write(ServiceResult.initErrorJson("文件写入出错"));
					}
					return;
				}
			}
		} else {
			UploadResult uploadResult = new UploadResult();
			for (Map.Entry<String, MultipartFile> entry : mfs.entrySet()) {
				MultipartFile file = entry.getValue();
				if (!file.isEmpty()) {
					String suffix = FilenameUtils.getExtension(file.getOriginalFilename());// 文件后缀
					String newFilename = UtilString.getSoleCode() + "." + suffix;// 保存后的文件名
					try {
						FileUtils.copyInputStreamToFile(file.getInputStream(), new File(absolutePath + newFilename));
						uploadResult.getSuccess().add((new UploadSuccessResult(file.getOriginalFilename(), file.getSize(), file.getContentType(), entry.getKey(), suffix, newFilename, "/" + type.replace("-", "/") + "/" + newFilename)));
					} catch (Exception e) {
						e.printStackTrace();
						uploadResult.getError().add(new UploadErrorResult(file.getOriginalFilename(), file.getSize(), entry.getKey(), file.getContentType(), FilenameUtils.getExtension(file.getOriginalFilename()), "文件写入出错"));
					}
				}
			}
			out.write(ServiceResult.GSON_DT.toJson(uploadResult));
			return;
		}
	}

}
