<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link rel="stylesheet" href="<%=request.getContextPath() %>/stylesheets/common.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/stylesheets/index.css" />
<script type="text/javascript">
	<c:if test="#session.login_info == null">
		document.location.href = "<%=request.getContextPath()%>/login.jsp";
	</c:if>
</script>
</head>
<body>
    <div class="left-menu">
        <div class="left-menu-title">项目名称</div>
        <div class="left-menu-list">
        </div>
    </div>
    <script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/jquery.nicescroll.min.js"></script>
    <!--[if lt IE 8]>
    <script type="text/javascript" src="<%=request.getContextPath() %>/ext/DD_belatedPNG/DD_belatedPNG.js"></script>
    <script>
        DD_belatedPNG.fix('.first-icon, .right-arrow, background, background');
    </script>
    <![endif]-->
    <script type="text/javascript" src="<%=request.getContextPath() %>/javascripts/jquery-zwbam.js"></script>
    <script>
        $(function(){
            //滚动条初始化
            $('.left-menu').niceScroll({
                cursorcolor: '#7db7fb',
                cursorwidth: '6px',
                cursorborderradius: 2,
                autohidemode: true,
                background: '#d0d0d0',
                cursoropacitymin: 1,
                cursorborder: 'none',
                horizrailenabled: false
            });
            /*菜单生成
            * $('.left-menu-list').zwbam('initMenu', 第二个参数)
            * 第二个参数可以为 json对象 或 菜单json请求地址
            */
            var datas = [];
            datas.push({
		    	name:'菜单一',
		    	url:'#',
		    	open:true,
		    	childs:
		    		[
		    	       	{
		    	       		name:"上传测试",
		    	       		url:'<%=request.getContextPath()%>/test/uploadFile.do'
		    	       	},
		    	       	{
		    	       		name:"导入excel",
		    	       		url:'<%=request.getContextPath()%>/test/excelImportTest.do'
		    	       	}
		    	    ]
		    });
            $('.left-menu-list').zwbam('initMenu', {
            	 data:{
	                "d": datas
            	 }
            });
        });
    </script>
</body>
</html>