<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
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
	               		<input type='hidden' id='id' name="id"/>
	                    <input class='span8' id='code_name' name="code_name" required placeholder='计费代码名称' type='text' />
	                    <span style=' color:red;'>*</span>
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>请求URL</label>
	            <div class='controls'>
	                <input class='span8' id='url' required name="url" placeholder='请求URL' type='text' />
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
				　　  <select class='span8' id='send_type' name="send_type" >
				　　        <option>GET</option>
				　　        <option>POST</option>
				　　    </select>
	                <!-- <input class='span8' id='send_type' name="send_type" placeholder='POST/GET' type='text' digits="true" /> -->
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>接口类型</label>
	            <div class='controls'>
	                <input class='span8' id='inf_type' required name='inf_type' digits="true" placeholder='1：不需要验证码，2需要接口反馈验证码，3.需要短信反馈验证码' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>反馈报文的格式</label>
	            <div class='controls'>
	                <input class='span8' id='back_msg_type' required name='back_msg_type' digits="true" placeholder='JSON/XML' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>反馈给客户端报文格式</label>
	            <div class='controls'>
	                <input class='span8' id='back_form' required name='back_form' digits="true" placeholder='{"msg":"'${msg}","serviceno":"${serviceno}","sms":"${sms}","charge_code":"${code_id}"}' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>运营商反馈报文格式</label>
	            <div class='controls'>
	                <input class='span8' id='return_form' required name='return_form' digits="true" placeholder='"":msg->msg,serviceno->serviceno,sms->sms' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>反馈验证码的请求URL</label>
	            <div class='controls'>
	                <input class='span8' id='ver_code_url' required name='ver_code_url' digits="true" placeholder='反馈验证码的请求URL' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>日限量</label>
	            <div class='controls'>
	                <input class='span8' id='date_limit' required name='date_limit' digits="true" placeholder='日限量' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>月限量</label>
	            <div class='controls'>
	                <input class='span8' id='month_limit' required name='month_limit' digits="true" placeholder='月限量' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>通道类型</label>
	            <div class='controls'>
	                <input class='span8' id='channel_type' required name='channel_type' digits="true" placeholder='1视频、2动漫、3阅读、4音乐' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>联系人</label>
	            <div class='controls'>
	                <input class='span8' id='linke_name' required name='linke_name' digits="true" placeholder='联系人' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>联系电话</label>
	            <div class='controls'>
	                <input class='span8' id='phone_no' required name='phone_no' digits="true" placeholder='联系电话' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>备注</label>
	            <div class='controls'>
	                <input class='span8' id='detail' required name='detail' digits="true" placeholder='备注' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>运营商反馈成功标示</label>
	            <div class='controls'>
	                <input class='span8' id='success_flag' required name='success_flag' digits="true" placeholder='反馈报文的成功标示字段  字段名:成功值' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>验证码的order_id字段</label>
	            <div class='controls'>
	                <input class='span8' id='order_id_code' required name='order_id_code' digits="true" placeholder='验证码的order_id字段' type='text' />
	            </div>
	        </div>
	    </div>
	    <div class='modal-footer'>
	        <button type="button" id="btnClose" class='btn'>关闭</button>
	        <button type="submit" id="btnSubmit" class='btn btn-primary'>保存</button>
	    </div>
    </form>
</div>

<div class='modal hide fade' id='modal-DistributePermission' role='dialog' tabindex='-1'>
    <div class='modal-header'>
        <button class='close' data-dismiss='modal' type='button'>&times;</button>
        <h3></h3>
    </div>
    <form class='form validate-form' id='submit-distributePermissionTreeForm' method="post" role="form"  style='margin-bottom: 0;'>
    <div class='modal-body'>
       <input type="hidden" id="menuId"/>
       <input type="hidden" id="distributePermissionTreeHidden"/>
       <ul id="distributePermissionTree" class="ztree" style="background: #f0f6e4;width:100%;height:300px;overflow-y:scroll;overflow-x:auto;"></ul>
    </div>
    <div class='modal-footer'>
        <button type="button" id="btnDistributePermissionClose" class='btn'>关闭</button>
        <button type="button" id="btnDistributePermissionSubmit" class='btn btn-primary'>保存</button>
    </div>
    </form>
</div>

<div class='modal hide fade' id='modal-CheckPermission' role='dialog' tabindex='-1'>
    <div class='modal-header'>
        <button class='close' data-dismiss='modal' type='button'>&times;</button>
        <h3></h3>
    </div>
    <div class='modal-body'>
       <ul id="checkPermissionTree" class="ztree" style="background: #f0f6e4;width:100%;height:300px;overflow-y:scroll;overflow-x:auto;"></ul>
    </div>
    <div class='modal-footer'>
        <button type="button" id="btnCheckPermissionClose" class='btn'>关闭</button>
    </div>
</div>
<div class='modal hide fade' id='modal-introduction' role='dialog' tabindex='-1'>
    <div class='modal-header'>
        <button class='close' data-dismiss='modal' type='button'>&times;</button>
        <h3></h3>
    </div>
    <div class='modal-body'>
         <textarea class='span12 form-control' id='appIntroduction' name='appIntroduction' rows='10' readonly></textarea>
    </div>
</div>
<script>
  seajs.use(['base','main/charge_code/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
