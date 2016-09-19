<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<div class='span5 box bordered-box blue-border' style='margin-bottom:0;margin-left:0px'>
  <div class='box-header'>
    <div class='title'>APP列表</div>
  </div>
  <div class='box-content box-no-padding'>
   <div class='responsive-table'>
     <div class='scrollable-area-y'>
       <table id="appCodeTable"></table>
     </div>
   </div>
 </div>
</div>

<div class='span7 box bordered-box blue-border' style='margin-bottom:0;'>
  <div class='box-header' id='app-code-header'>
    <div class='title'>适用计费代码列表</div>
    <div class='actions'></div>
  </div>
  <div class='box-content box-no-padding'>
    <div class='responsive-table'>
      <div class='scrollable-area-x'>
      		<table id="appCodeRuleTable"></table>
      </div>
    </div>
  </div>
</div>

<div class='modal hide fade'  style='width:800px;'  id='modal-app-code-rule' role='dialog' tabindex='-1'  >
    <div class='modal-header'>
        <button class='close' data-dismiss='modal' type='button'>&times;</button>
       <h3></h3>
    </div>
    <div class='modal-body'>
       	<div class='span12 box bordered-box blue-border' style='margin-bottom:0;'>
		  <div class='box-content box-no-padding'>
		    <div class='responsive-table'>
		      <div class='scrollable-area-x'>
		      		<table id="chargeCodeList"></table>
		      </div>
		    </div>
		  </div>
		</div>
    </div>
    <div class='modal-footer'>
        <button type="button" id="btnClose" class='btn'>关闭</button>
    </div>
</div>

<script>
  seajs.use(['base','main/app_rule/app_code/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
