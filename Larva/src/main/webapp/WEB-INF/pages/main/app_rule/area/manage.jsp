<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<div class='span5 box bordered-box blue-border' style='margin-bottom:0;margin-left:0px'>
  <div class='box-header'>
    <div class='title'>APP区域限制列表</div>
  </div>
  <div class='box-content box-no-padding'>
   <div class='responsive-table'>
     <div class='scrollable-area-y'>
       <table id="areaAPPTable"></table>
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
   		 			  <td colspan="2"><label>App名称：</label></td>
   		 			  <td colspan="4"><label id='app_name'></label></td>
   		 			  <!-- <td colspan="2"><label>批量填写</label></td>
   		 			  <td><input style="width:90%"  id='all_rxl_110000' name="all_rxl" required placeholder='日限量' type='text' /></td>	
   		 			  <td><input style="width:90%" id='all_yxl_110000' name="all_yxl" required placeholder='月限量' type='text'/> -->
   		 		</tr>
   		 		<tr>
   		 			<td>北京	</td><td><input style='width:20px;height:20px;' id='110000' name='check'  type='checkbox'  value='110000'/></td>
   		 			<td><input style="width:90%"  id='rxl_110000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_110000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_110000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_110000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_110000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>天津	</td>	<td><input style='width:20px;height:20px;' id='120000' name='check'  type='checkbox'  value='120000'/></td>
					<td><input style="width:90%"  id='rxl_120000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_120000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_120000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_120000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_120000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>	
					<td>河北	</td>	<td><input style='width:20px;height:20px;' id='130000' name='check'  type='checkbox'  value='130000'/></td>
					<td><input style="width:90%"  id='rxl_130000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_130000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_130000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_130000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_130000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				    <td>山西	</td>	<td><input style='width:20px;height:20px;' id='140000' name='check'  type='checkbox'  value='140000'/></td>
					<td><input style="width:90%"  id='rxl_140000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_140000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_140000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_140000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_140000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>内蒙古	</td>	<td><input style='width:20px;height:20px;' id='150000' name='check'  type='checkbox'  value='150000'/></td> 
					<td><input style="width:90%"  id='rxl_150000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_150000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_150000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_150000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_150000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>辽宁	</td>	<td><input style='width:20px;height:20px;' id='210000' name='check'  type='checkbox'  value='210000'/></td>
					<td><input style="width:90%"  id='rxl_210000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_210000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_210000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_210000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_210000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>吉林	</td>	<td><input style='width:20px;height:20px;' id='220000' name='check'  type='checkbox'  value='220000'/></td>
					<td><input style="width:90%"  id='rxl_220000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_220000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_220000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_220000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_220000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>黑龙江	</td>	<td><input style='width:20px;height:20px;' id='230000' name='check'  type='checkbox'  value='230000'/></td>
					<td><input style="width:90%"  id='rxl_230000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_230000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_230000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_230000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_230000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>上海	</td>	<td><input style='width:20px;height:20px;' id='310000' name='check'  type='checkbox'  value='310000'/></td>
					<td><input style="width:90%"  id='rxl_310000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_310000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_310000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_310000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_310000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>江苏	</td>	<td><input style='width:20px;height:20px;' id='320000' name='check'  type='checkbox'  value='320000'/></td>
					<td><input style="width:90%"  id='rxl_320000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_320000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_320000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_320000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_320000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>浙江	</td>	<td><input style='width:20px;height:20px;' id='330000' name='check'  type='checkbox'  value='330000'/></td>
					<td><input style="width:90%"  id='rxl_330000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_330000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_330000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_330000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_330000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>安徽	</td>	<td><input style='width:20px;height:20px;' id='340000' name='check'  type='checkbox'  value='340000'/></td>
					<td><input style="width:90%"  id='rxl_340000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_340000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_340000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_340000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_340000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>福建	</td>	<td><input style='width:20px;height:20px;' id='350000' name='check'  type='checkbox'  value='350000'/></td>
					<td><input style="width:90%"  id='rxl_350000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_350000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_350000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_350000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_350000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>江西	</td>	<td><input style='width:20px;height:20px;' id='360000' name='check'  type='checkbox'  value='360000'/></td>
					<td><input style="width:90%"  id='rxl_360000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_360000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_360000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_360000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_360000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>山东	</td>	<td><input style='width:20px;height:20px;' id='370000' name='check'  type='checkbox'  value='370000'/></td>
					<td><input style="width:90%"  id='rxl_370000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_370000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_370000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_370000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_370000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>河南	</td>	<td><input style='width:20px;height:20px;' id='410000' name='check'  type='checkbox'  value='410000'/></td>
					<td><input style="width:90%"  id='rxl_410000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_410000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_410000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_410000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_410000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>湖北	</td>	<td><input style='width:20px;height:20px;' id='420000' name='check'  type='checkbox'  value='420000'/></td>
					<td><input style="width:90%"  id='rxl_420000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_420000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_420000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_420000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_420000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>湖南	</td>	<td><input style='width:20px;height:20px;' id='430000' name='check'  type='checkbox'  value='430000'/></td>
					<td><input style="width:90%"  id='rxl_430000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_430000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_430000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_430000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_430000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>广东	</td>	<td><input style='width:20px;height:20px;' id='440000' name='check'  type='checkbox'  value='440000'/></td>
					<td><input style="width:90%"  id='rxl_440000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_440000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_440000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_440000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_440000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>广西	</td>	<td><input style='width:20px;height:20px;' id='450000' name='check'  type='checkbox'  value='450000'/></td>
					<td><input style="width:90%"  id='rxl_450000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_450000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_450000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_450000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_450000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>海南	</td>	<td><input style='width:20px;height:20px;' id='460000' name='check'  type='checkbox'  value='460000'/></td>
					<td><input style="width:90%"  id='rxl_460000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_460000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_460000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_460000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_460000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>重庆	</td>	<td><input style='width:20px;height:20px;' id='500000' name='check'  type='checkbox'  value='500000'/></td>
					<td><input style="width:90%"  id='rxl_500000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_500000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_500000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_500000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_500000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>四川	</td>	<td><input style='width:20px;height:20px;' id='510000' name='check'  type='checkbox'  value='510000'/></td>
					<td><input style="width:90%"  id='rxl_510000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_510000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_510000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_510000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_510000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>贵州	</td>	<td><input style='width:20px;height:20px;' id='520000' name='check'  type='checkbox'  value='520000'/></td>
					<td><input style="width:90%"  id='rxl_520000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_520000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_520000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_520000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_520000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>云南	</td>	<td><input style='width:20px;height:20px;' id='530000' name='check'  type='checkbox'  value='530000'/></td>
					<td><input style="width:90%"  id='rxl_530000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_530000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_530000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_530000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_530000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>西藏	</td>	<td><input style='width:20px;height:20px;' id='540000' name='check'  type='checkbox'  value='540000'/></td>
					<td><input style="width:90%"  id='rxl_540000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_540000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_540000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_540000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_540000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>陕西	</td>	<td><input style='width:20px;height:20px;' id='610000' name='check'  type='checkbox'  value='610000'/></td>
					<td><input style="width:90%"  id='rxl_610000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_610000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_610000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_610000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_610000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>甘肃	</td>	<td><input style='width:20px;height:20px;' id='620000' name='check'  type='checkbox'  value='620000'/></td>
					<td><input style="width:90%"  id='rxl_620000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_620000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_620000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_620000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_620000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>青海	</td>	<td><input style='width:20px;height:20px;' id='630000' name='check'  type='checkbox'  value='630000'/></td>
					<td><input style="width:90%"  id='rxl_630000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_630000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_630000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_630000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_630000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
					<td>宁夏	</td>	<td><input style='width:20px;height:20px;' id='640000' name='check'  type='checkbox'  value='640000'/></td>
					<td><input style="width:90%"  id='rxl_640000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_640000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_640000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_640000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_640000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
				<tr>
					<td>新疆	</td>	<td><input style='width:20px;height:20px;' id='650000' name='check'  type='checkbox'  value='650000'/></td>
					<td><input style="width:90%"  id='rxl_650000' name="rxl" required placeholder='日限量' type='text' /></td>	
   		 			<td><input style="width:90%" id='yxl_650000' name="yxl" required placeholder='月限量' type='text'/>
   		 				<input id='hidden_650000' class='codeAreaId' type="hidden"/>
   		 				<input id='app_id_650000' class='appId' type="hidden"/>
   		 			</td>
   		 			<td><a href='#' id='tj_650000' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'>提交</a></td>
				</tr>
   		 	</table>
   		 	</div>
   		 </form>
      </div>
    </div>
  </div>
</div>

<script>
  seajs.use(['base','main/app_rule/area/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
