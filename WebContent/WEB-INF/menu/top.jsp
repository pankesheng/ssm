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
    <div class="header clearfix">
        <div class="function-block">
            <p class="function-list">
                <%-- <a href="javascript:void(0);">${sessionScope.login_info.realName }</a> --%>
                <a href="<%=request.getContextPath() %>/index/main.do" target="rightFrame">首页</a>
                <a class="split" href="javascript:void(0);" target="rightFrame">${sessionScope.login_info.name }</a>
                <a class="split" href="#" target="_parent" >退出</a>
            </p>
        </div>
        <h1 class="web-title">项目名称</h1>
        <div class="icon-bar clearfix" id="iconBar">
            <div class="icon-list">
                <ul class="icon-ul"></ul>
            </div>
            <div class="next-list">
                <a href="javascript:void(0);" class="next-btn" id="next-btn"></a>
                <ul class="dots-list clearfix"></ul>
                <div class="nums-list">
                    <span class="cur-page">1</span>
                    <span>/</span>
                    <span class="total-page">4</span>
                </div>
            </div>
        </div>
        <div class="mini-list" id="mini-list"></div>
    </div>
    <script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/jquery-1.8.1.min.js"></script>
    <!--[if lt IE 8]>
    <script type="text/javascript" src="./ext/jquery/jquery.pngFix.js"></script>
    <script>
        //修复IE6 PNG
        var fixpng = function(){
            $(document).pngFix();
        };
    </script>
    <![endif]-->
    <script type="text/javascript" src="<%=request.getContextPath() %>/javascripts/jquery-zwbam.js"></script>
</body>
</html>