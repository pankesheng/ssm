<%@ page contentType="text/html; charset=GBK"%>   
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">  
<meta HTTP-EQUIV="expires" CONTENT="0">  
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">  
<title></title>  
<script type="text/javascript" src="<%=request.getContextPath() %>/ofc/swfobject.js"></script>  
<script type="text/javascript">  
  
 /*  
  *Open Chart Flashͼ��ʾ��1  
  *@author: modiliany  
  *@2011-01-24  
  */  
//demo1:��������ͼ������txt�ļ���*************  
//expressInstall.swf:flashplayer���������������û�������flash���������ʾ��װ  

swfobject.embedSWF("<%=request.getContextPath() %>/ofc/open-flash-chart.swf", "OpenFlashChartContainer1", "240", "160","9.0.0", "<%=request.getContextPath() %>/ofc/expressInstall.swf",{"data-file":"<%=request.getContextPath() %>/chartdata.txt"});  

  
//demo2:��������ͼ********************  
swfobject.embedSWF("<%=request.getContextPath() %>/ofc/open-flash-chart.swf", "OpenFlashChartContainer2", "240", "160",  
    "9.0.0", "<%=request.getContextPath() %>/ofc/expressInstall.swf",{"get-data":"getChartData2",  
          "id":"chart2",  
          "save_image_message":"��ͼ�α���ΪͼƬ",  
          "loading":"���ڼ�����..."}  
      );  
  
function getChartData2(){  
    var jsonData ='{"title":{"text":"��������ͼ(·��PCIͼ)","style":"font-size:12;font-weight:bold;"},' +  
                  ' "elements":[' +   
                  '     {"type":"line","alpha":0.7,"text":"PCI","colour":"#FF0000",' +   
                  '      "dot-style":{"type":"solid-dot","tip":"#x_label#��PCIΪ#val#"},' +  
                  '      "on-show":{"type":"drop","cascade":1,"delay":0.5},' +   
                  '      "values":[92.89,94.06,91.64,0,0]}],' +  
                  ' "x_axis":{"stroke":2,"tick_height":2,"colour":"#d000d0","grid_colour":"#00ff00","offset":1,"labels":{"rotate":315,"siz":11,"labels":["K001","K002","K003","K004","K005"],"size":12}},' +   
                  ' "y_axis":{"max":100,"steps":10,"offset":1}' +   
                  '}';  
    return jsonData;  
}   
  
  
//demo3:��������ͼ********************  
swfobject.embedSWF("<%=request.getContextPath() %>/ofc/open-flash-chart.swf", "OpenFlashChartContainer3", "240", "160",  
    "9.0.0", "<%=request.getContextPath() %>/ofc/expressInstall.swf",{"get-data":"getChartData3",  
          "id":"chart3",  
          "save_image_message":"��ͼ�α���ΪͼƬ",  
          "loading":"���ڼ�����..."}  
      );  
  
function getChartData3(){  
    var jsonData ='{"title":{"text":"��������ͼ(·��PCI��RQIͼ)","style":"font-size:12;font-weight:bold;"},' +   
                  ' "elements":[' +   
                  '     {"type":"line","alpha":0.7,"text":"PCI","colour":"#FF0000","dot-style":{"type":"solid-dot","tip":"#x_label#��PCIΪ#val#"},"values":[92.89,94.06,91.64,0,0],"on-show":{"type":"drop","cascade":1,"delay":0.5}},' +   
                  '     {"type":"line","alpha":0.7,"text":"RQI","colour":"#0000FF","dot-style":{"type":"solid-dot","tip":"#x_label#��RQIΪ#val#"},"values":[78.27,2.1,0.34,0,0],"on-show":{"type":"drop","cascade":1,"delay":0.5}}],' +    
                  ' "x_axis":{"stroke":1,"tick_height":10,"colour":"#d000d0","grid_colour":"#00ff00","offset":1,"labels":{"rotate":315,"siz":11,"labels":["K001","K002","K003","K004","K005"],"size":12}},' +   
                  ' "y_axis":{"max":100,"steps":10,"offset":1}' +  
                  '}';  
    return jsonData;  
}   
  
//demo4:��ͼ********************  
swfobject.embedSWF("<%=request.getContextPath() %>/ofc/open-flash-chart.swf", "OpenFlashChartContainer4", "240", "160",  
    "9.0.0", "<%=request.getContextPath() %>/ofc/expressInstall.swf",{"get-data":"getChartData4",  
          "id":"chart4",  
          "save_image_message":"��ͼ�α���ΪͼƬ",  
          "loading":"���ڼ�����..."}  
      );  
        
function getChartData4(){  
    var valueStr='';  
    var per='';  
    var i =0;  
    for(i=1; i<=5; i++){   
        valueStr += '{"value":' + i +',"label":"ռ:';  
        var percent = (i/15.0)*100 + '';  
        if(percent.indexOf('.')!=-1){  
            percent = percent.substring(0,percent.indexOf('.') + 3);  
        }  
          
        valueStr += percent + '%","tip":"����' + i + ':' + i + '�� "},';  
    }    
      
    valueStr = valueStr.substring(0 ,valueStr.length-1);   
    var jsonData =  '{"title":{"text":"��ͼ(�������ͼ)","style":"font-size:12;font-weight:bold;"},' +   
                    ' "elements":[{"font-size":11,"animate":"pie",' +   
                    '   "colours":["#1C9E05","#FF368D","#FF0000","#0000FF","#008000"],' +   
                    '   "type":"pie",' +   
                    '   "values":['+valueStr+'],' +   
                    '   "start-angle":180,' +   
                    ' "gradient-fill":true,' +   
                    ' "alpha":0.7}]' +   
                    '}';   
    return jsonData;  
}  
  
//demo5:�ѻ�ͼ********************  
swfobject.embedSWF("<%=request.getContextPath() %>/ofc/open-flash-chart.swf", "OpenFlashChartContainer5", "240", "160",  
    "9.0.0", "<%=request.getContextPath() %>/ofc/expressInstall.swf",{"get-data":"getChartData5",  
          "id":"chart5",  
          "save_image_message":"��ͼ�α���ΪͼƬ",  
          "loading":"���ڼ�����..."}  
      );  
        
function getChartData5(){  
    var jsonData ='{"title":{"text":"�ѻ�ͼ(������)","style":"{font-size:12;font-weight:bold;text-align:center;}"},' +  
                  ' "bg_colour":"#FFFFFF",' +   
                  ' "elements":[{"type":"bar_stack","colours":["#0000FF","#008000","#FF0000"],' +  
                  '             "keys": [' +  
                  '                 {"colour":"#0000FF", "text": "����", "font-size": 12},' +  
                  '                 {"colour":"#008000", "text": "����", "font-size": 12},' +  
                  '                 {"colour":"#FF0000", "text": "���", "font-size": 12}],' +  
                  '             "tip":"#x_label# --- #val#<br>(total:#total#)",' +  
                  '             "values": [' +  
                  '                 [{"val":1, "tip":"#x_label# #val# ������"},{"val":2, "tip":"#x_label# #val# ������"},{"val":3, "tip":"#x_label# #val# �����"}],' +   
                  '                 [{"val":3, "tip":"#x_label# #val# ������"},{"val":2, "tip":"#x_label# #val# ������"},{"val":1, "tip":"#x_label# #val# �����"}]]},' +  
                  '             {"type":"tags","font":"Cambria","font-size":12,"colour":"#000000","padding":0,' +   
                  '                 "rotate":0,"align-x":"center","align-y":"above","text":"#y#",' +   
                  '                 "values":[{"x":0,"y":6},{"x":1,"y":6}]}' +   
                  '         ],' + //end of "elements"  
                  ' "x_axis":{"labels": {"labels": ["����һ","������"]} ,"stroke": 2 ,"tick-height": 2},' +  
                  ' "y_axis":{"max": 10}}';  
    return jsonData;  
}     
  
//demo6:��״ͼ********************  
swfobject.embedSWF("<%=request.getContextPath() %>/ofc/open-flash-chart.swf", "OpenFlashChartContainer6", "240", "160",  
    "9.0.0", "<%=request.getContextPath() %>/ofc/expressInstall.swf",{"get-data":"getChartData6",  
          "id":"chart6",  
          "save_image_message":"��ͼ�α���ΪͼƬ",  
          "loading":"���ڼ�����..."}  
      );  
       
function getChartData6(){    
    var jsonData =' {"title":{"text":"��״ͼ(�ϸ�����ͼ)","style":"{font-size:12;font-weight:bold;text-align:center;}"},' +   
                  '  "bg_colour":"#FFFFFF",'  +  
                  '  "elements":[' +  
                  '     {"type":"bar" ,"alpha":0.7 ,"colour":"#0000FF" ,"text":"�ϸ�����" ,"font-size":12 ,' +   
                  '         "values":[8,4] ,"tip":"�ϸ�����#val#��"}],' +  
                  '  "x_axis":{"stroke":1 ,"tick_height":1 ,"colour":"#d000d0" ,"grid_colour":"#00ff00" ,' +   
                  '            "labels":{"size":12,"labels":["С��1","С��2"]}},' +  
                  '  "y_axis":{"stroke":2 ,"tick_length":1 ,"colour":"#d000d0" ,"grid_colour":"#00ff00" ,' +   
                  '  "offset":0 ,"max":10}' +   
                  ' }';  
    return jsonData;  
}  
    
//demo7:�����״ͼ********************  
swfobject.embedSWF("<%=request.getContextPath() %>/ofc/open-flash-chart.swf", "OpenFlashChartContainer7", "240", "160",  
    "9.0.0", "<%=request.getContextPath() %>/ofc/expressInstall.swf",{"get-data":"getChartData7",  
          "id":"chart7",  
          "save_image_message":"��ͼ�α���ΪͼƬ",  
          "loading":"���ڼ�����..."}  
      );  
       
function getChartData7(){   
    var jsonData=' {"title":{"text":"�����״ͼ(�������ͼ)","style":"{font-size:12;font-weight:bold;text-align:center;}"},' +   
                 '  "bg_colour":"#FFFFFF",' +  
                 '  "elements":[' +  
                 '      {"type":"bar" ,"alpha":0.5 ,"colour":"#0000FF" ,"text":"ͬ������" ,"font-size":12 ,' +   
                 '          "values":[8,4] ,"tip":"ͬ������#val#��"},' +  
                 '      {"type":"bar" ,"alpha":0.5 ,"colour":"#008000" ,"text":"��������" ,"font-size":12 ,' +   
                 '          "values":[2,5] ,"tip":"��������#val#��"}],' +  
                 '  "x_axis":{"stroke":2 ,"tick_height":2 ,"colour":"#d000d0" ,"grid_colour":"#00ff00" ,' +   
                 '          "labels":{"size":12,"labels":["����1","����2"]}},' +  
                 '  "y_axis":{"stroke":2 ,"tick_length":2 ,"colour":"#d000d0" ,"grid_colour":"#00ff00" ,' +   
                 '          "offset":0 ,"max":10}' +   
                 ' }';  
                   
    return jsonData;  
}       
  
//demo8:������״ͼ********************  
swfobject.embedSWF("<%=request.getContextPath() %>/ofc/open-flash-chart.swf", "OpenFlashChartContainer8", "240", "160",  
    "9.0.0", "<%=request.getContextPath() %>/ofc/expressInstall.swf",{"get-data":"getChartData8",  
          "id":"chart8",  
          "save_image_message":"��ͼ�α���ΪͼƬ",  
          "loading":"���ڼ�����..."}  
      );  
       
function getChartData8(){    
    var jsonData=' {"title":{"text":"������״ͼ(�ϸ�ͼ)","style":"{font-size:12;font-weight:bold;text-align:center;}"},' +   
                 '  "bg_colour":"#FFFFFF",' +  
                 '  "elements":[' +  
                 '      {"type":"hbar" ,"colour":"#0000FF" ,"text":"�ϸ�����" ,"font-size":12 ,' +   
                 '          "values":[{"left":0,"right":8},{"left":0,"right":3}], "tip":"�ϸ�����#right#��"},' +  
                 '      {"type":"tags","font":"Cambria","font-size":12,"colour":"#000000","padding":0,' +   
                 '          "rotate":90,"align-x":"above","align-y":"center","text":"#y#",' +   
                 '          "values":[{"x":3,"y":0},{"x":8,"y":1}]}' +    
                 '  ],' +  
                 '  "y_axis":{"stroke":2 ,"tick_length":2 ,"colour":"#d000d0" ,"grid_colour":"#00ff00" ,' +   
                 '            "offset":1,"labels":["С��1","С��2"]},' +  
                 '  "x_axis":{"stroke":2 ,"tick_height":2 ,"colour":"#d000d0" ,"grid_colour":"#00ff00" ,' +   
                 '            "offset":0 ,"max":10}' +   
                 '  }';  
                   
    return jsonData;  
}   
</script>  
  
</head>  
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">  
<table width="100%" height="100%" cellspacing=0 cellpading=0 border=0>  
    <tr valign="top">  
        <td align="center">   
             <table width="720px" height="480px" cellspacing=0 cellpading=0 border=1>  
                 <tr>  
                     <td colspan="3" style="background-color:#cccccc">  
                        Open Chart Flashͼ��ʾ��1:  
                     </td>  
                 </tr>  
                 <tr>  
                     <td width="33%">  
                         <div id="OpenFlashChartContainer1"></div>  
                     </td>  
                     <td width="33%">  
                         <div id="OpenFlashChartContainer2"></div>  
                     </td>  
                     <td width="33%">  
                         <div id="OpenFlashChartContainer3"></div>  
                     </td>  
                 </tr>  
                 <tr>  
                     <td width="33%">  
                         <div id="OpenFlashChartContainer4"></div>  
                     </td>  
                     <td width="33%">  
                         <div id="OpenFlashChartContainer5"></div>  
                     </td>  
                     <td width="33%">  
                         <div id="OpenFlashChartContainer6"></div>  
                     </td>  
                 </tr>  
                 <tr>  
                     <td width="33%">  
                         <div id="OpenFlashChartContainer7"></div>  
                     </td>  
                     <td width="33%">  
                         <div id="OpenFlashChartContainer8"></div>  
                     </td>  
                     <td width="33%">  
                         <div id="OpenFlashChartContainer9"></div>  
                     </td>  
                 </tr>  
             </table>  
         </td>  
    </tr>  
</table>   
</body>  
</html>  