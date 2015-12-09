<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/stylesheets/common.css" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/stylesheets/table.css" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/ext/uploadify/uploadify.css" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/json2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/ext/laydate/laydate.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/selectbox.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/zw/check.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext/uploadify/jquery.uploadify.min.js"></script>
    <title>导入测试</title>
</head>
<body>
	<div class="place">
        <span class="label-span">位置：</span>
        <ul id="place-list" class="place-ul">
            <li>导入测试</li>
        </ul>
    </div>
    <div class="body-warp">
        <div class="panel">
            <div class="panel-title">
                <i class="form-icon"></i>
                <span class="title-text">基本信息</span>
            </div>
            <div class="panel-body">
                <form>
                    <table class="form-table">
                        <tr>
                        	<td><label class="form-label">导入Excel：</label></td>
	                    	<td>
	                    		<div class="form-group">
			                    	<input id="upload" name="filename" type="file"/>
	                    		</div>
	                    	</td>
	                    	<td>&nbsp;</td>
	                    	<td>
	                    		<span class="form-tip alert alert-info">查看后台控制台输出</span>
	                    	</td>
                        </tr>

                        <tr>
                            <!-- 空内容的td 保持间距 -->
                            <td><label class="form-label">&nbsp;</label></td>
                            <td>
                                <input class="btn btn-success btn-large" type="submit"  value="提交">
                                <input class="btn btn-danger btn-large return-btn" onclick="_cancle();" type="button" value="返回">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>

	<script type="text/javascript">
	 	var uploadfile;
	 	$(function(){
			$("#upload").uploadify({
				'swf' : '<%=request.getContextPath()%>/ext/uploadify/uploadify.swf',
				'uploader' : '<%=request.getContextPath() %>/test/importExcel.do',
				'buttonText' : 'Excel文件',
				'fileTypeExts': '*.xls;*.xlsx', //多文件而是之间用 ; 隔开
				'width' : 100,
				'height' : 30,
				'buttonClass': 'btn btn-primary no-padding',
				'fileTypeDesc': 'Excel文件',
				'multi': false,
				'auto':false,
				'queueSizeLimit' :1,
				'fileObjName':'filename',
				'onSelect' : function(file) {
					uploadfile = file;
		         },
				'onUploadSuccess' : function(file, data, response) {
					var msg = $.parseJSON(data);
					if(1==msg.s){
						alert("成功");
					}else{
						if(msg.d){
							alert(msg.d);
						}else{
							alert("导入失败！");
						}
					}
				}
            });
		});	
	 	
	 	$("form").submit(function(event){
    		event.preventDefault();
    		if($(this).check()){
    			var post = $(this).serialize();
   				var postData = toParamObject(post);
   				$("#upload").uploadify('settings','formData',postData);
       			$("#upload").uploadify('upload','*');
    		}
    	});
	 	
	 	function toParamObject(search){
        	var params = {};
        	var tmps = search.split("&");
    		for(var i=0;i<tmps.length;i++){
    			var pair = tmps[i];
    			var ttmps = pair.split("=");
    			for(var j=0;j<ttmps.length;j=j+2){
    				if(ttmps[j+1]){
    					if(ttmps[j+1].indexOf('%')>=0){
        					params[ttmps[j]]=decodeURIComponent(ttmps[j+1]);
        				}else{
        					params[ttmps[j]]=ttmps[j+1];
        				}
    				}
    				if(params[ttmps[j]] === 'on'){
    					params[ttmps[j]] = 'true';
    				}
    			}
    		}
        	return params;
        }
	
		function _cancle(){
			window.location.href="<%=request.getContextPath()%>/group/whContacts.do?id=${group.id}";
		}
	</script>
</body>
</html>