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
	
</script>
<title>项目名称</title>
</head>
<body>
    <iframe class="top" src="<%=request.getContextPath() %>/index/top.do" height="88" frameborder="0" scrolling="no"></iframe>
    <!-- 有top页加class top-iframe-margin -->
    <div id="container" class="container top-iframe-margin">
        <iframe class="container-iframe" src="<%=request.getContextPath() %>/index/container.do" scrolling="yes" noresize="noresize" frameborder="0"></iframe>
    </div>
    <div class="top-collapse" id="top-collapse" title="折叠"></div>
    <script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/jquery-1.8.1.min.js"></script>
    <script>
        $(function(){
            var height = 0;
            $('#top-collapse').toggle(function() {
                height = $('#container').height();
                $('#container').height('100%').removeClass('top-iframe-margin');
                $(this).attr('title', '展开').css({
                    top: '0',
                    backgroundPosition: '0 0'
                });
            }, function() {
                $('#container').height(height).addClass('top-iframe-margin');
                $(this).attr('title', '折叠').css({
                    top: '88px',
                    backgroundPosition: '-56px 0'
                });
            });
        });
    </script>
</body>
</html>