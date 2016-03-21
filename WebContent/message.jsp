<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/ext/jquery/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/javascripts/socket.io.js"></script>
    <style>
        body { 
            padding:20px;
        }
        #console { 
            height: 400px; 
            overflow: auto; 
        }
        .username-msg {color:orange;}
        .connect-msg {color:green;}
        .disconnect-msg {color:red;}
        .send-msg {color:#888}
    </style>
</head>
<body>
    <h1>Netty-socketio实例</h1>
    <br/>
    <div id="console" class="well">
    </div>
        <form class="well form-inline" onsubmit="return false;">
           <input id="name" class="input-xlarge" type="text" placeholder="用户名"/>
           <input id="msg" class="input-xlarge" type="text" placeholder="发送内容"/>
           <button type="button" onClick="sendMessage()" class="btn">Send</button>
           <button type="button" onClick="sendDisconnect()" class="btn">Disconnect</button>
        </form>
</body>
<script type="text/javascript">

        var socket =  io.connect('http://192.168.1.158:9092');

        socket.on('connect', function() {
            output('<span class="connect-msg">XXX 登录了聊天室！</span>');
        });
        
        socket.on('chatevent', function(data) {
        	output('<span class="username-msg">' + data.userName + ':</span> ' + data.message);
        });
        
        socket.on('disconnect', function() {
        	output('<span class="disconnect-msg">客户端已断开连接！</span>');
        });
        function sendDisconnect() {
            socket.disconnect();
        }
        
        function sendMessage() {
            var userName = $("#name").val();
            var message = $('#msg').val();
            $('#msg').val('');
            socket.emit('chatevent', {userName: userName, message: message});
        }
        
        function output(message) {
            var currentTime = "<span class='time'>" +  new Date() + "</span>";
            var element = $("<div>" + currentTime + " " + message + "</div>");
            $('#console').prepend(element);
        }
    </script>
</html>