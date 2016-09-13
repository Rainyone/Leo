<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<div class='span5 box bordered-box blue-border' style='margin-bottom:0;margin-left:0px'>
  <div class='box-header'>
    <div class='title'>计费代码列表</div>
  </div>
  <div class='box-content box-no-padding'>
   <div class='responsive-table'>
     <div class='scrollable-area-y'>
       <table id="areaChargeCodeTable"></table>
     </div>
   </div>
 </div>
</div>
<div class='span7 box bordered-box blue-border' style='margin-bottom:0;'>
  <div class='box-header' id='charge-code-area-header'>
    <div class='title'>适用区域设置</div>
    <div class='actions'></div>
  </div>
  <div class='box-content box-no-padding'>
    <div class='responsive-table'>
      <div  class='scrollable-area-x' >
   		 <form class='form-horizontal' id='submit-form' method="post" role="form"  style='margin-bottom: 0;height: 335px'>
   		 <div class='scrollable-area-y'>
   		 	<table class='table'>
   		 		<tr>
   		 			  <td colspan="2">代码名称</td>
   		 			  <td colspan="8">代码名称</td>
   		 		</tr>
   		 		<tr>
   		 			<td>北京	</td><td><input style='width:20px;height:20px;' id='110000' name='check'  type='checkbox'  value='110000'/></td>
   		 			<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>天津	</td>	<td><input style='width:20px;height:20px;' id='120000' name='check'  type='checkbox'  value='120000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>	
					<td>河北	</td>	<td><input style='width:20px;height:20px;' id='130000' name='check'  type='checkbox'  value='130000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				    <td>山西	</td>	<td><input style='width:20px;height:20px;' id='140000' name='check'  type='checkbox'  value='140000'/></td>
				    <td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>内蒙古	</td>	<td><input style='width:20px;height:20px;' id='150000' name='check'  type='checkbox'  value='150000'/></td> 
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>辽宁	</td>	<td><input style='width:20px;height:20px;' id='210000' name='check'  type='checkbox'  value='210000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>吉林	</td>	<td><input style='width:20px;height:20px;' id='220000' name='check'  type='checkbox'  value='220000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>黑龙江	</td>	<td><input style='width:20px;height:20px;' id='230000' name='check'  type='checkbox'  value='230000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>上海	</td>	<td><input style='width:20px;height:20px;' id='310000' name='check'  type='checkbox'  value='310000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>江苏	</td>	<td><input style='width:20px;height:20px;' id='320000' name='check'  type='checkbox'  value='320000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>浙江	</td>	<td><input style='width:20px;height:20px;' id='330000' name='check'  type='checkbox'  value='330000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>安徽	</td>	<td><input style='width:20px;height:20px;' id='340000' name='check'  type='checkbox'  value='340000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>福建	</td>	<td><input style='width:20px;height:20px;' id='350000' name='check'  type='checkbox'  value='350000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>江西	</td>	<td><input style='width:20px;height:20px;' id='360000' name='check'  type='checkbox'  value='360000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>山东	</td>	<td><input style='width:20px;height:20px;' id='370000' name='check'  type='checkbox'  value='370000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>河南	</td>	<td><input style='width:20px;height:20px;' id='410000' name='check'  type='checkbox'  value='410000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>湖北	</td>	<td><input style='width:20px;height:20px;' id='420000' name='check'  type='checkbox'  value='420000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>湖南	</td>	<td><input style='width:20px;height:20px;' id='430000' name='check'  type='checkbox'  value='430000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>广东	</td>	<td><input style='width:20px;height:20px;' id='440000' name='check'  type='checkbox'  value='440000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>广西	</td>	<td><input style='width:20px;height:20px;' id='450000' name='check'  type='checkbox'  value='450000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>海南	</td>	<td><input style='width:20px;height:20px;' id='460000' name='check'  type='checkbox'  value='460000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>重庆	</td>	<td><input style='width:20px;height:20px;' id='500000' name='check'  type='checkbox'  value='500000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>四川	</td>	<td><input style='width:20px;height:20px;' id='510000' name='check'  type='checkbox'  value='510000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>贵州	</td>	<td><input style='width:20px;height:20px;' id='520000' name='check'  type='checkbox'  value='520000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>云南	</td>	<td><input style='width:20px;height:20px;' id='530000' name='check'  type='checkbox'  value='530000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>西藏	</td>	<td><input style='width:20px;height:20px;' id='540000' name='check'  type='checkbox'  value='540000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>陕西	</td>	<td><input style='width:20px;height:20px;' id='610000' name='check'  type='checkbox'  value='610000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>甘肃	</td>	<td><input style='width:20px;height:20px;' id='620000' name='check'  type='checkbox'  value='620000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>青海	</td>	<td><input style='width:20px;height:20px;' id='630000' name='check'  type='checkbox'  value='630000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>宁夏	</td>	<td><input style='width:20px;height:20px;' id='640000' name='check'  type='checkbox'  value='640000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>新疆	</td>	<td><input style='width:20px;height:20px;' id='650000' name='check'  type='checkbox'  value='650000'/></td>
					<td><input style="width:90%"  id='rxl' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input  style="width:90%" id='yxl' name="rxl" required placeholder='月限量' type='text'/></td>
   		 			<td><a href='#' id='' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
   		 	</table>
   		 	</div>
   		 </form>
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
  seajs.use(['base','main/charge_code_rule/area/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
