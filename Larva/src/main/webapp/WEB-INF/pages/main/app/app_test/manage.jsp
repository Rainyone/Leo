<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<div class='span12 box bordered-box blue-border' style='margin-bottom:0;'>
  <form class='form-horizontal' id='submit-form' method="post" role="form"  >
	    <div class='modal-body' style="height:550px;">
	    	 <div class='control-group'>
	        	<label class='control-label'>APP_ID</label>
	        	<div class='controls'>
                    <input class='span8' id='app_id' name="app_id" required placeholder='APP_ID' type='text' />
           		 </div>
	        </div>
	         <div class='control-group'>
	        	<label class='control-label'>APP_KEY</label>
	        	<div class='controls'>
                    <input class='span8' id='app_key' name="app_key" required placeholder='APP_KEY' type='text' />
           		 </div>
	        </div>
	        <div class='control-group'>
	        	<label class='control-label'>REQUEST_TYPE</label>
	        	 <div class='controls'>
	             	 <select class='span8' id='request_type' name="request_type"  required>
				　　        <option value='1'>计费</option>
				　　        <option value='2'>验证码</option>
				　　    </select>
	            </div>
	        </div>
	        <div class='control-group'>
	        	<label class='control-label'>CHANNEL</label>
	        	<div class='controls'>
               		<input class='span8' id='channel' required name="channel" placeholder='渠道号（是否为客户端提供）' type='text' />
           		</div>
	        </div>
	        <div class='control-group'>
	        	<label class='control-label'>PRICE</label>
	        	<div class='controls'>
               		<input class='span8' id='price' required name="price" placeholder='金额 分为单位 ' type='text' />
           		</div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>IMEI</label>
	            <div class='controls'>
	                <input class='span8' id='imei' required name="imei" placeholder='手机IMEI信息' type='number' />
	            </div>
	            
	        </div>
	         <div class='control-group'>
	         	 <label class='control-label'>IMSI</label>
	             <div class='controls'>
	                <input class='span8' id='imsi' required name="imsi" placeholder='手机IMSI信息' type='number' />
	             </div>
	         </div>
	         <div class='control-group'>
	            <label class='control-label'>BSC_LAC</label>
	            <div class='controls'>
	                <input class='span8' id='bsc_lac' name="bsc_lac" placeholder='移动基站信息' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>BSC_CID</label>
	            <div class='controls'>
	                <input class='span8' id='bsc_cid' name="bsc_cid" required placeholder='移动基站信息' type='number' digits="true" />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>MOBILE</label>
	            <div class='controls'>
	                 <input class="span8" id="mobile" name="mobile" placeholder='手机号码' type='text'/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>ICCID</label>
	            <div class='controls'>
	                 <input class="span8" id="iccid" name="iccid" placeholder='sim卡iccid号' type='text'/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>MAC</label>
	            <div class='controls'>
	                 <input class="span8" id="mac" name="mac" placeholder='mac地址' type='text'/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>CPPARM</label>
	            <div class='controls'>
	                 <textarea class="span8" id="cpparm" name="cpparm" placeholder='透传参数' type='text'></textarea>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>FMT</label>
	            <div class='controls'>
	                 <input class="span8" id="fmt" name="fmt" placeholder='反馈报文格式 json' type='text'/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>ISP</label>
	             <div class='controls'>
	             	 <select class='span8' id='isp' name="isp"  required>
				　　        <option value='1001'>中国电信</option>
				　　        <option value='1002'>中国移动</option>
				　　        <option value='1003'>中国联通</option>
				　　        <option value='1004'>其他</option>
				　　    </select>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>CODE_ID</label>
	            <div class='controls'>
	                 <input class="span8" id="code_id" name="code_id" placeholder='计费代码编码，request_type=2时必填' type='text'/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>ORDER_ID</label>
	            <div class='controls'>
	                 <input class="span8" id="order_id" name="order_id" placeholder='验证码回传的订单号，当request_type=2时必填' type='text'/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>VER_CODE</label>
	            <div class='controls'>
	                 <input class="span8" id="ver_code" name="ver_code" placeholder='验证码，当request_type=2时必填' type='text'/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>结果</label>
	            <div class='controls'>
	                 <textarea class="span8" rows="5" id="result" name="result" placeholder='结果' type='text'></textarea>
	                 <a href='#' id='getResult' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'> 获取URL</a>
	            </div>
	        </div>
	    </div>
    </form>
</div>
<script>
  seajs.use(['base','main/app/app_test/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
