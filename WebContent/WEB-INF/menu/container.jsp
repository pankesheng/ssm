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
<script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
	<c:if test="#session.login_info == null">
		document.location.href = "<%=request.getContextPath()%>/login.jsp";
	</c:if>
</script>
</head>
<body>
    <iframe class="left" style="height:100%;" src="<%=request.getContextPath() %>/index/left.do" scrolling="yes" noresize="noresize" frameborder="0"></iframe>
    <div class="main">
        <div class="content-wrap">
            <iframe class="content" src="<%=request.getContextPath() %>/index/main.do" name="rightFrame" id="rightFrame" frameborder="0" title="rightFrame" ></iframe>
        </div>
    </div>
    <div class="left-collapse" id="left-collapse" title="折叠"></div>
    <!--[if lt IE 8]>
    <script type="text/javascript" src="<%=request.getContextPath() %>/ext/DD_belatedPNG/DD_belatedPNG.js"></script>
    <script>
    	DD_belatedPNG.fix('.left-collapse, background');
    </script>
    <![endif]-->
    <script>
        /*路径设置*/
        window.indexObj = {
            setPlaceHistory: function(html){
                $('#place-list').html(html);
            }
        };
        /*向左折叠*/
        $(function(){
            $('#left-collapse').toggle(function() {
                $('.left').width(0);
                $('.main').css({
                    left: '0',
                    width: '100%'
                });
                $(this).attr('title', '展开').css({
                    left: '0',
                    top: '50%',
                    backgroundPosition: '0 -56px'
                });
            }, function() {
                $('.left').width(187);
                $('.main').attr('style', '').css({
                    left: '187px'
                });
                $(this).attr('title', '折叠').css({
                    left: '187px',
                    top: '50%',
                    backgroundPosition: '0 0'
                });
            });
        });
    </script>
</body>
</html>