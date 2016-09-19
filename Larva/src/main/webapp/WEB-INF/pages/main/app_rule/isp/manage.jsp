<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<div class='span12 box bordered-box blue-border' style='margin-bottom:0;margin-left:0px'>
  <div class='box-header'>
    <div class='title'>APP列表</div>
  </div>
  <div class='box-content box-no-padding'>
   <div class='responsive-table'>
     <div class='scrollable-area-y'>
       <table id="appISPTable"></table>
     </div>
   </div>
 </div>
</div>
<script>
  seajs.use(['base','main/app_rule/isp/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
