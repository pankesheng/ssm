<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/stylesheets/common.css" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/stylesheets/table.css" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/ext/jquery_zcj/jquery.zimgslider.css" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/ext/uploadify/uploadify.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/laydate/laydate.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/uploadify/jquery.uploadify.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/zw/grid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/json2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/selectbox.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/layer/layer.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/javascripts/tool.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/zw/check.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery_zcj/jquery.zimgslider.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/zcommon.js" basepath="<%=request.getContextPath() %>"></script>
</head>
<body>
	<div class="place">
	    <span class="label-span">位置：</span>
	    <ul id="place-list" class="place-ul">
	        <li>
	          	测试上传
	        </li>
	    </ul>
	</div>
	<div class="body-warp">
	    <div class="panel">
	    	<div class="panel-title">
	            <i class="form-icon"></i>
	            <span class="title-text">测试上传</span>
	        </div>
	        <div class="panel-body">
	            <form id="myform">
	            	<table class="form-table">
						<tr>
							<td>
								<label class="form-label">图片：</label>
							</td>
							<td>
								<input type="hidden" name="imgs">
								<div id="addOrModify_imgs"></div>
								<input id="upload1" type="file"/>
							</td>
						</tr>
						<tr>
	            			<td><label class="form-label">作品视频：</label></td>
	            			<td>
	            				<input class="form-control" type="text" size="40" value="" id="filePath" name="filePath">
								<input id="upload2" type="file"/>
	            			</td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	    </div>
    </div>
    <script>
    $(function(){
		// 初始化上传图片控件
		z_initImgUpload("upload1", "addOrModify_imgs", "<%=request.getContextPath() %>", "Downloads-images", 3);
		// 初始化图片显示
		$("#addOrModify_imgs").zImgslider_init('<%=request.getContextPath() %>','',false); 
		
		// 初始化上传文件控件
		z_initFlieUpload("upload2", "<%=request.getContextPath()%>", "Downloads-files", "filePath");
		
    });
    </script>
</body>
</html>