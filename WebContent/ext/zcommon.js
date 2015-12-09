
// 后台管理系统各页面引入

// 参数
// 		basepath : 根路径，默认空串。
//		loginpath : 登陆页的绝对路径，如：/poh/login.jsp，默认"/login.jsp"。
//		loginpager : 是否为登陆页，默认否。
var _z_basepath=$("script:last").attr("basepath")||"";
var _z_loginpath=$("script:last").attr("loginpath")||"/login.jsp";
var _z_donothing=$("script:last").attr("donothing");
if (_z_donothing) {
	
} else {
	z_ajaxCheckLogin(_z_basepath+_z_loginpath);
}

// 验证当前AJAX请求时的登陆状态，如果未登陆，则转到登陆页面
// z_ajaxCheckLogin("/ohedu/login.jsp");
function z_ajaxCheckLogin(loginPage) {
	$(document).ajaxSuccess(function(evt, request, settings){
		   var d=jQuery.parseJSON(request.responseText);
		   if(d.s==2){
			   var ws=_getParents();
			   var w=ws.pop();
			   if(w) {
				   w.location.href=loginPage;
			   } else {
				   window.location.href=loginPage;
			   }
		   }
	});
}

// 当前页面的最顶层页面转到登录页面
// z_iframeCheckLogin("/ohedu/login.jsp");
function z_iframeCheckLogin(loginPage) {
	var ws=_getParents();
	var w=ws.pop();
	if(w) {
		w.location.href=loginPage;
	} else {
		window.location.href=loginPage;
	}
}

function _getParents(w){//获取父级win 
	w=w||window;
	var p=w.parent,ws=[];
	while(p!=w&&p){
	   ws.push(p);
	   w=p;p=p.parent;
	}
	return ws;
}

// 初始化图片上传功能
// z_initImgUpload("upload1", "addOrModify_imgs", "${contextPath}", "Downloads-zt", 1);
function z_initImgUpload(uploadButtonId, imgListId, basePath, saveCatalog, maxCount) {
	
	var multi = false;
	if (maxCount && maxCount > 1) {
		multi = true;
	}
	
	$('#'+uploadButtonId).uploadify({
		'uploader': basePath+'/file/upload.ajax',
		'formData': { type: saveCatalog },
		'swf': basePath+'/ext/uploadify/uploadify.swf',
		'width' : 100,
		'height' : 32,
		'buttonClass': 'btn btn-primary no-padding',
		'buttonText': '选择图片',
		'removeTimeout': 0.1,
		'multi': multi,
		'fileSizeLimit': '20MB',// 文件大小限制
		'fileTypeExts': '*.gif;*.jpg;*.png;*.bmp;*.jpeg',// 默认为所有类型
		
		'onUploadSuccess': function(file, data, response){
			var data2 = JSON.parse(data);
			if(data2.s==1){
				$("#"+imgListId).zImgslider_addImg(basePath,data2.d.savePath,maxCount);
			}else{
				alert(data2.d);
			}
		}
	});
}

//初始化文件上传功能
// z_initFlieUpload("upload2", "${contextPath}", "Downloads-zt", "linkUrl");
function z_initFlieUpload(uploadButtonId, basePath, saveCatalog, resultPathInputId) {
	
	$('#'+uploadButtonId).uploadify({
		'uploader': basePath+'/file/upload.ajax',
		'formData': { type: saveCatalog },
		'swf': basePath+'/ext/uploadify/uploadify.swf',
		'width' : 100,
		'height' : 32,
		'buttonClass': 'btn btn-primary no-padding',
		'buttonText': '选择文件',
		'removeTimeout': 0.1,
		'multi': false,
		'fileSizeLimit': '100MB',// 文件大小限制
		'onUploadSuccess': function(file, data, response){
			var data2 = JSON.parse(data);
			if(data2.s==1){
				$("#"+resultPathInputId).val(basePath+data2.d.savePath);
			}else{
				alert(data2.d);
			}
		}
		
	});
}

// 弹出窗口
function z_openIframe(title, width, height, src) {
	//如果没有附加参数
	if (src.indexOf('?') == -1) {
		src += '?n=' + Math.random();
	} else {
		src += '&n=' + Math.random();
	}
	
	$.layer({
		type: 2,// 0：信息框（默认），1：页面层，2：iframe层，3：加载层，4：tips层。
		maxmin: true,// 是否输出窗口最小化/全屏/还原按钮。 
		shadeClose: false,// 用来控制点击遮罩区域是否关闭层。
		title: title,
		area: [width+'px', height+'px'],
		iframe: {
			src: src
		}
	});
}

// 删除
function z_delete(dataString, url) {
	if (dataString.length == 0) {
		layer.alert('请选择至少一条记录！', 8);
		return false;
	}
	layer.confirm('确认删除选定记录？', function() {
		$.getJSON(url + '?' + dataString, function(json, textStatus) {
			if (json.s) {
				grid.refresh();
				layer.closeAll();
			} else {
				layer.alert(json.d, 8);
			}
		});
	});
}

// 图片居中显示
// z_DrawImage($("#img1"),80,60);
function z_DrawImage(ImgD,iwidth,iheight){
	var image=new Image();
	image.src=ImgD.src;
	if(image.width > 0 && image.height > 0){
		if(image.width/image.height >= iwidth/iheight){
			if(image.width>iwidth){
				ImgD.width=iwidth;
				ImgD.height=(image.height*iwidth)/image.width;
			}else{
				ImgD.width=image.width;
				ImgD.height=image.height;
			}
			$(ImgD).css({"margin-top":(iheight - ImgD.height) / 2,"margin-left":(iwidth - ImgD.width) / 2});
		}
		else{
			if(image.height>iheight){
				ImgD.height=iheight;
				ImgD.width=(image.width*iheight)/image.height;
			}else{
				ImgD.width=image.width;
				ImgD.height=image.height;
			}
			$(ImgD).css({"margin-top":(iheight - ImgD.height) / 2,"margin-left":(iwidth - ImgD.width) / 2});
		}
	}
}
