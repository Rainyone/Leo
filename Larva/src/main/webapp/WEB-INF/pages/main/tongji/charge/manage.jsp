<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <script src='${ctxAssets}/echarts/echarts.min.js' type='text/javascript'></script>
</head>
<body>
<div class='span12'>
  <div class="span2"><h2>当月请求数：</h2></div>
  <div class="span2"><h2 id='month_request_count'>0</h2></div>
  <div class="span2"><h2>当月成功数：</h2></div>
  <div class="span2"><h2 id='month_request_success_count'>0</h2></div>
  <div class="span2"><h2>当月金额：</h2></div>
  <div class="span2"><h2 id='month_request_account'>0</h2></div>
</div>
<div class='span12 '></div>
<div class='span12'>
	<form class='form-horizontal' id='submit-form' method='post' role='form' >
		<div class='control-group'>
      		<div class='controls span6'>
      			<button id='sevenDate' type='button' class='btn btn-danger'>最近7天</button>
      			<button id='fifteenDate' type='button' class='btn btn-success'>最近15天</button>
        		<button id='thirtyDate' type='button' class='btn btn-warning'>最近30天</button>
        	</div>
        </div>
        <div class='control-group'>
        	<div class='controls span2'>
                 <select id="charge_id" class="form-control">
                 </select>
      		</div>
        	<label class='control-label span2'>开始时间：</label>
        	<div class='controls span2'>
 				<input type='text' id='datetimeStart' readonly class='form_datetime span12' placeholder='开始时间'>
      		</div>
      		<label class='control-label span2'>结束时间：</label>
        	<div class='controls span2'>
               <input type='text' id='datetimeEnd' readonly class='form_datetime span12' placeholder='结束时间'>
      		</div>
      		<div class='controls span2'>
      			<button id='reset' type='reset' class='btn btn-info'>重置</button>
      			<button id='query' type='button' class='btn btn-primary'>查询</button>
      		</div>
        </div>
	</form>
</div>
<ul id='myTab' class='nav nav-tabs' style='margin-bottom: 0px;'>
	<li class='active'>
		<a href='#order' data-toggle='tab'>
			订购记录
		</a>
	</li>
</ul>
<div id='myTabContent' class='tab-content'>
	<div id='order' class='tab-pane active ' style='margin-left: 0'>
		<div>
	        <table id='orderTable'></table>
	    </div>
	</div>
	<div id='chart' class='tab-pane span12' style='margin-left: 0;'>
		 <div id='echarts' style='width: 100%;height: 400px'>
		 </div>
	</div>
</div >
<script>
  seajs.use(['base','main/tongji/charge/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
