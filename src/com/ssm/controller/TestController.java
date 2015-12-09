package com.ssm.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.common.BasicController;
import com.ssm.utils.ReadExcel;
import com.zcj.web.dto.ServiceResult;

/**
 * 测试使用例子
 * @author pks
 *
 */
@Controller
@RequestMapping("/test")
@Scope("prototype")
public class TestController extends BasicController{

	@RequestMapping("/uploadFile")
	public ModelAndView uploadFile(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("now", new Date());
		mav.setViewName("/WEB-INF/test/uploadFile.jsp");
		return mav;
	}
	
	@RequestMapping("/excelImportTest")
	public ModelAndView excelImportTest(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/WEB-INF/test/importExcel.jsp");
		return mav;
	}
	
	@RequestMapping(value = "/importExcel")
	public void importGroupContact(HttpServletRequest request,HttpServletResponse response,@RequestParam("filename") MultipartFile filename,PrintWriter out) throws Exception{
		if (filename.isEmpty()) {
			out.write(ServiceResult.initErrorJson("文件未上传！"));
			return ;
        } else {
        	if (!filename.getOriginalFilename().toLowerCase().endsWith(".xls")&&!filename.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
        		out.write(ServiceResult.initErrorJson("文件选择错误，请下载模板填写数据后进行上传！"));
    			return ;
			}
        	String str=filename.getOriginalFilename().substring(filename.getOriginalFilename().lastIndexOf("."));
			int random = (int) (Math.random() * 10000);
			String newFileName= System.currentTimeMillis() + random +str;
            // 文件临时保存目录
            String realPath = request.getSession().getServletContext().getRealPath("/uploadTemp");
            // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
            File file = new File(realPath, newFileName);
            try {
            	FileUtils.copyInputStreamToFile(filename.getInputStream(), file);
                if(filename.getOriginalFilename().toLowerCase().endsWith(".xls")){
                	ReadExcel.readXls(filename.getInputStream());
                }else{
                	ReadExcel.readXlsx(file.getPath());
                }
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				//删除临时文件
	            file.delete();
			}
        }
	}
}
