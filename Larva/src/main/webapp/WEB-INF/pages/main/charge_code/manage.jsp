<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
    	var oldHtml ="<div class='control-group'> \
            <label class='control-label'></label>\
            <div class='controls'>\
                 <select class='col-lg-2' id='start_time' name='start_time'  >\
			　　        <option value='00:00'>00:00</option>\
			　　        <option value='01:00'>01:00</option>\
			　　        <option value='02:00'>02:00</option>\
			　　        <option value='03:00'>03:00</option>\
			　　        <option value='04:00'>04:00</option>\
			　　        <option value='05:00'>05:00</option>\
			　　        <option value='06:00'>06:00</option>\
			　　        <option value='07:00'>07:00</option>\
			　　        <option value='08:00'>08:00</option>\
			　　        <option value='09:00'>09:00</option>\
			　　        <option value='10:00'>10:00</option>\
			　　        <option value='11:00'>11:00</option>\
			　　        <option value='12:00'>12:00</option>\
			　　        <option value='13:00'>13:00</option>\
			　　        <option value='14:00'>14:00</option>\
			　　        <option value='15:00'>15:00</option>\
			　　        <option value='16:00'>16:00</option>\
			　　        <option value='17:00'>17:00</option>\
			　　        <option value='18:00'>18:00</option>\
			　　        <option value='19:00'>19:00</option>\
			　　        <option value='20:00'>20:00</option>\
			　　        <option value='21:00'>21:00</option>\
			　　        <option value='22:00'>22:00</option>\
			　　        <option value='23:00'>23:00</option>\
			　　    </select> \
			        <span style='color:red;'>--</span>\
                 <select class='col-lg-2' id='end_time' name='end_time'  >\
			　　        <option value='00:00'>00:00</option>\
			　　        <option value='01:00' selected='selected'>01:00</option>\
			　　        <option value='02:00'>02:00</option>\
			　　        <option value='03:00'>03:00</option>\
			　　        <option value='04:00'>04:00</option>\
			　　        <option value='05:00'>05:00</option>\
			　　        <option value='06:00'>06:00</option>\
			　　        <option value='07:00'>07:00</option>\
			　　        <option value='08:00'>08:00</option>\
			　　        <option value='09:00'>09:00</option>\
			　　        <option value='10:00'>10:00</option>\
			　　        <option value='11:00'>11:00</option>\
			　　        <option value='12:00'>12:00</option>\
			　　        <option value='13:00'>13:00</option>\
			　　        <option value='14:00'>14:00</option>\
			　　        <option value='15:00'>15:00</option>\
			　　        <option value='16:00'>16:00</option>\
			　　        <option value='17:00'>17:00</option>\
			　　        <option value='18:00'>18:00</option>\
			　　        <option value='19:00'>19:00</option>\
			　　        <option value='20:00'>20:00</option>\
			　　        <option value='21:00'>21:00</option>\
			　　        <option value='22:00'>22:00</option>\
			　　        <option value='23:00'>23:00</option>\
			　　    </select>\
			　　    <a id='delDisabledTime' href='javascript:void(0)' onclick='delDisabledTime(this)' style='margin-left:5px;text-decoration:none;'><i class='icon-minus'></i></a>\
            </div>\
        </div>";
    </script>
    <%
    	String introduction = "{\"msg\":\"'${msg}\",\"serviceno\":\"${serviceno}\",\"sms\":\"${sms}\",\"charge_code\":\"${code_id}\"}";
    %>
</head>
<body>
<div class='span12 box bordered-box blue-border' style='margin-bottom:0;'>
  <div class='box-header' id="charge-code-header">
    <div class='title'>计费代码设置</div>
    <div class='actions'></div>
  </div>
  <div class='box-content box-no-padding'>
    <div class='responsive-table'>
      <div class='scrollable-area-x'>
        <table id="chargeCodeTable"></table>
      </div>
    </div>
  </div>
</div>

<div class='modal hide fade'  style='width:800px;'  id='modal-charge-code' role='dialog' tabindex='-1'  >
    <div class='modal-header'>
        <button class='close' data-dismiss='modal' type='button'>&times;</button>
        <h3></h3>
    </div>
    <form class='form-horizontal' id='submit-form' method="post" role="form"  style='margin-bottom: 0;'>
	    <div class='modal-body'>
	        <div class='control-group'>
	            <label class='control-label'>代码名称</label>
	            <div class='controls'>
	               		<input type='hidden' id='id' name='id'/>
	                    <input class='span8' id='code_name' name="code_name"  placeholder='计费代码名称' type='text' required/>
	                    <span style=' color:red;'>*</span>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>请求URL</label>
	            <div class='controls'>
	                <input class='span8' id='url' name="url" placeholder='请求URL' type='text' />
	            </div>
	        </div>
	         <div class='control-group'>
	            <label class='control-label'>请求内容</label>
	            <div class='controls'>
	                <input class='span8' id='charge_code' name="charge_code" placeholder='报文内容' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>报文发送方式</label>
	            <div class='controls'>
	             	 <select class='span8' id='send_type' name="send_type" required>
				　　     <option value='GET'>GET</option>
				　　        <option value='PSOT'>POST</option>
				　　    </select>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>接口类型</label>
	            <div class='controls'>
	             	 <select class='span8' id='inf_type' name="inf_type"  required>
				　　        <option value='1'>不需要验证码</option>
				　　        <option value='2'>需要接口反馈验证码</option>
				　　        <option value='3'>需要短信反馈验证码</option>
				　　    </select>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>反馈报文的格式</label>
	            <div class='controls'>
	            	 <select class='span8' id='back_msg_type' name="back_msg_type" required>
				　　        <option value='JSON'>JSON</option>
				　　        <option value='XML'>XML</option>
				　　    </select>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>反馈给客户端报文格式</label>
	            <div class='controls'>
	                <input class='span8' id='back_form' name='back_form' placeholder='' type='text' required/>
	                <span class="help-block">格式：<%=introduction%></span>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>运营商反馈报文格式</label>
	            <div class='controls'>
	                <input class='span8' id='return_form'  name='return_form' placeholder='"":msg->msg,serviceno->serviceno,sms->sms' type='text' required/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>反馈验证码的请求URL</label>
	            <div class='controls'>
	                <input class='span8' id='ver_code_url'  name='ver_code_url' placeholder='反馈验证码的请求URL' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>日限量</label>
	            <div class='controls'>
	                <input class='span8' id='date_limit'  name='date_limit' digits="true" placeholder='日限量' type="number" value='-1' required/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>月限量</label>
	            <div class='controls'>
	                <input class='span8' id='month_limit'  name='month_limit' placeholder='月限量' type='number' value='-1' required/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>通道类型</label>
	            <div class='controls'>
	            	 <select class='span8' id='channel_type' name="channel_type" >
				　　        <option value='1'>视频</option>
				　　        <option value='2'>动漫</option>
				　　        <option value='3'>阅读</option>
				　　        <option value='4'>音乐</option>
				　　    </select>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>联系人</label>
	            <div class='controls'>
	                <input class='span8' id='linke_name' name='linke_name' placeholder='联系人' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>联系电话</label>
	            <div class='controls'>
	                <input class='span8' id='phone_no' name='phone_no' placeholder='联系电话' type='number' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>备注</label>
	            <div class='controls'>
	                <input class='span8' id='detail' name='detail' placeholder='备注' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>运营商反馈成功标示</label>
	            <div class='controls'>
	                <input class='span8' id='success_flag' name='success_flag' digits="true" placeholder='反馈报文的成功标示字段  字段名:成功值当借口类型为“需要短信反馈验证码”是填写："msg":"success"' value='"msg":"success"' type='text' required/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>验证码的order_id字段</label>
	            <div class='controls'>
	                <input class='span8' id='order_id_code' name='order_id_code' digits="true" placeholder='验证码的order_id字段' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>验证码请求成功标示</label>
	            <div class='controls'>
	                <input class='span8' id='ver_code_success_flag' name='ver_code_success_flag' digits="true" placeholder='验证码请求时运营商反馈的成功标示 字段名:成功值' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>运营商回调接口地址</label>
	            <div class='controls'>
	                <input class='span8' id='callbackurl' name='callbackurl' digits="true" placeholder='运营商回调接口地址' type='text' readonly/>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>运营商回调计费成功标示</label>
	            <div class='controls'>
	                <input class='span8' id='callbacksuccess' name='callbacksuccess' digits="true" placeholder='运营商回调计费成功标示，如：result=1' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>运营商回调透传参数字段</label>
	            <div class='controls'>
	                <input class='span8' id='callbackcolumn' name='callbackcolumn' digits="true" placeholder='字段名称，正常情况记录的是计费记录id' type='text' />
	            </div>
	        </div>
	    </div>
	    <div class='modal-footer'>
	        <button type="button" id="btnClose" class='btn'>关闭</button>
	        <button type="submit" id="btnSubmit" class='btn btn-primary'>保存</button>
	    </div>
    </form>
</div>

<script>
  seajs.use(['base','main/charge_code/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
