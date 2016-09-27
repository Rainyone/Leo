<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>

<div >
	<form class='form-horizontal' id='submit-form' method='post' role='form' >
	    <div class='control-group'>
        	<label class='control-label span1'>订单号：</label>
        	<div class='controls span2'>
               <input class='span12' id='order_id' name='order_id' placeholder='订单号' type='text' />
      		</div>
        	<label class='control-label span2'>计费代码名称：</label>
        	<div class='controls span2'>
               <input class='span12' id='code_name' name='code_name' placeholder='计费代码名称' type='text' />
      		</div>
      		<label class='control-label span2'>APP名称：</label>
        	<div class='controls span2'>
               <input class='span12' id='app_name' name='app_name' placeholder='APP名称' type='text' />
      		</div>
        </div>
         <div class='control-group'>
        	<label class='control-label span1'>状态：</label>
        	<div class='controls span2' >
	             	 <select class='span12' id='order_state' name="order_state" placeholder='订单状态'  required value='-1'>
					   <option value='-1'>全部</option>
					   <option value='0'>订单发起</option>
				　　        <option value='1'>运营商反馈成功</option>
				　　        <option value='2'>运营商反馈失败</option>
				　　        <option value='3'>运营商回调成功</option>
				　　        <option value='4'>运营商回调失败</option>
				　　        <option value='5'>客户端回调成功</option>
				　　        <option value='6'>客户端回调失败</option>
				　　        <option value='7'>验证码发送成功</option>
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
        <div class='control-group'>
        	<button id='oneDate' type='button' class='btn btn-warning'>最近1天</button>
      		<button id='sevenDate' type='button' class='btn btn-danger'>最近7天</button>
      		<button id='fifteenDate' type='button' class='btn btn-success'>最近15天</button>
        </div>
	</form>
</div>
<ul id='myTab' class='nav nav-tabs' style='margin-bottom: 0px;'>
	<li class='active'>
		<a href='#order' data-toggle='tab'>
			订购记录
		</a>
	</li>
	<!-- <li><a href='#chart' data-toggle='tab'>图表</a></li> -->
</ul>
<div id='myTabContent' class='tab-content'>
	<div id='order' class='tab-pane fade in active span12 ' style='margin-left: 0'>
	  <div class='box-content box-no-padding'>
	    <div class='responsive-table'>
	      <div class='scrollable-area-x'>
	        <table id='orderTable'></table>
	      </div>
	    </div>
	  </div>
	</div>
	<div id='chart' class='tab-pane fade span12' style='margin-left: 0'>
		 <div class='box-content box-no-padding'>
		    <div class='responsive-table'>
		      <div class='scrollable-area-x'>
		        <table id='menuTable'></table>
		      </div>
		    </div>
		  </div>
	</div>
</div >
<script>
  seajs.use(['base','main/tongji/order/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
