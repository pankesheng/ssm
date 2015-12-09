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
<script type="text/javascript" src="<%=request.getContextPath() %>/ext/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/javascripts/tool.js"></script>
<script type="text/javascript">
	<c:if test="#session.login_info == null">
		document.location.href = "<%=request.getContextPath()%>/login.jsp";
	</c:if>
</script>
</head>
<body>
	main jsp
    <script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/jquery-1.8.1.min.js"></script>
    <script>
        //单行个数
        var iconWidth = 0;
        var $dom = $('#app-list');
        var liNums = (function(){
            var listWidth = $dom.width();
            iconWidth = $dom.find('li').outerWidth() | 139;
            var liNums = parseInt(listWidth / iconWidth);

            return liNums;
        })();
        //每行宽度
        var width = liNums * iconWidth;

        $(function(){
            init();
            addListeners();
        });

        //初始化
        function init () {
            $dom.each(function(index, el) {
                var length = $(this).find('li').length;

                $(this).data({
                    'curPage': 1,
                    'totalPage': length % liNums === 0 ? length / liNums : parseInt(length / liNums) + 1
                });
            });
        }
        function addListeners () {
            $('.left-arrow').bind('click', function(event) {
                if($(this).hasClass('disabled')){
                    return false;
                }

                loadPage($dom.data('curPage') - 1, $(this));
            });
            $('.right-arrow').bind('click', function(event) {
                if($(this).hasClass('disabled')){
                    return false;
                }

                loadPage($dom.data('curPage') + 1, $(this));
            });
        }

        function loadPage (pageNum, $arrow){
            if(pageNum <= $dom.data('totalPage') && pageNum > 0){
                $dom.animate({
                    marginLeft: '-' + width * (pageNum - 1) + 'px'
                },'fast');
                $dom.data('curPage', pageNum);
            }

            var iconBlock = $dom.parents('.icon-block');

            if(pageNum === 1){
                iconBlock.find('.left-arrow').addClass('disabled');
            }else{
                iconBlock.find('.left-arrow').removeClass('disabled');
            }

            if(pageNum === $dom.data('totalPage')){
                iconBlock.find('.right-arrow').addClass('disabled');
            }else{
                iconBlock.find('.right-arrow').removeClass('disabled');
            }
        };
    </script>
</body>
</html>