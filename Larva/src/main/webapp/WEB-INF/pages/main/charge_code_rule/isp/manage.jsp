<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
    	var oldHtml ="<div class='control-group'> \
            <label class='control-label'></label>\
            <div class='controls'>\
                 <select class='col-lg-2' id='disable_start_time' name='disable_start_time'  >\
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
                 <select class='col-lg-2' id='disable_end_time' name='disable_end_time'  >\
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
</head>
<body>
<div class='span12 box bordered-box blue-border' style='margin-bottom:0;margin-left:0px'>
  <div class='box-header'>
    <div class='title'>计费代码列表</div>
  </div>
  <div class='box-content box-no-padding'>
   <div class='responsive-table'>
     <div class='scrollable-area-y'>
       <table id="chargeCodeISPTable"></table>
     </div>
   </div>
 </div>
</div>

<div class='modal hide fade'  style='width:800px;'  id='modal-charge-code-disable-time' role='dialog' tabindex='-1'  >
    <div class='modal-header'>
        <button class='close' data-dismiss='modal' type='button'>&times;</button>
        <h3></h3>
    </div>
    <form class='form-horizontal' id='submit-form' method="post" role="form"  style='margin-bottom: 0;'>
	    <div class='modal-body'>
	        <div class='control-group'>
	            <label class='control-label'>代码名称</label>
	            <div class='controls'>
	               		<input type='hidden' id='charge_code_id' name="charge_code_id"/>
	                    <input class='span8' id='code_name' name="code_name" required placeholder='计费代码名称' type='text' disabled/>
	                    <span style=' color:red;'>*</span>
	            </div>
	        </div>
      		<div id='addDisabledTimeDiv'>
		    	<div class='control-group'>
		            <label class='control-label'>失效时间设置</label>
		            <div class='controls'>
		                 <select class='col-lg-2' id='disable_start_time' name='disable_start_time'  >
					　　        <option value='00:00'>00:00</option>
					　　        <option value='01:00'>01:00</option>
					　　        <option value='02:00'>02:00</option>
					　　        <option value='03:00'>03:00</option>
					　　        <option value='04:00'>04:00</option>
					　　        <option value='05:00'>05:00</option>
					　　        <option value='06:00'>06:00</option>
					　　        <option value='07:00'>07:00</option>
					　　        <option value='08:00'>08:00</option>
					　　        <option value='09:00'>09:00</option>
					　　        <option value='10:00'>10:00</option>
					　　        <option value='11:00'>11:00</option>
					　　        <option value='12:00'>12:00</option>
					　　        <option value='13:00'>13:00</option>
					　　        <option value='14:00'>14:00</option>
					　　        <option value='15:00'>15:00</option>
					　　        <option value='16:00'>16:00</option>
					　　        <option value='17:00'>17:00</option>
					　　        <option value='18:00'>18:00</option>
					　　        <option value='19:00'>19:00</option>
					　　        <option value='20:00'>20:00</option>
					　　        <option value='21:00'>21:00</option>
					　　        <option value='22:00'>22:00</option>
					　　        <option value='23:00'>23:00</option>
					　　    </select> 
					        <span style='color:red;'>--</span>
		                 <select class='col-lg-2' id='disable_end_time' name='disable_end_time'  >
					　　        <option value='00:00'>00:00</option>
					　　        <option value='01:00' selected="selected">01:00</option>
					　　        <option value='02:00'>02:00</option>
					　　        <option value='03:00'>03:00</option>
					　　        <option value='04:00'>04:00</option>
					　　        <option value='05:00'>05:00</option>
					　　        <option value='06:00'>06:00</option>
					　　        <option value='07:00'>07:00</option>
					　　        <option value='08:00'>08:00</option>
					　　        <option value='09:00'>09:00</option>
					　　        <option value='10:00'>10:00</option>
					　　        <option value='11:00'>11:00</option>
					　　        <option value='12:00'>12:00</option>
					　　        <option value='13:00'>13:00</option>
					　　        <option value='14:00'>14:00</option>
					　　        <option value='15:00'>15:00</option>
					　　        <option value='16:00'>16:00</option>
					　　        <option value='17:00'>17:00</option>
					　　        <option value='18:00'>18:00</option>
					　　        <option value='19:00'>19:00</option>
					　　        <option value='20:00'>20:00</option>
					　　        <option value='21:00'>21:00</option>
					　　        <option value='22:00'>22:00</option>
					　　        <option value='23:00'>23:00</option>
					　　    </select>
						<a id="addDisabledTime" href='javascript:void(0)' style='margin-left:5px;text-decoration:none;'><i class='icon-plus'></i></a>
		            </div>
		        </div>
	        </div>
		    <div class='modal-footer'>
		        <button type="button" id="btnClose" class='btn'>关闭</button>
		        <button type="submit" id="btnSubmit" class='btn btn-primary'>保存</button>
		    </div>
	    </div>
    </form>
</div>

<script>
  seajs.use(['base','main/charge_code_rule/isp/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
