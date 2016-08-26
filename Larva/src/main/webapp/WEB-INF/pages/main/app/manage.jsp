<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<div class='span12 box bordered-box blue-border' style='margin-bottom:0;'>
  <div class='box-header' id="menu-header">
    <div class='title'>APP管理</div>
    <div class='actions'></div>
  </div>
  <div class='box-content box-no-padding'>
    <div class='responsive-table'>
      <div class='scrollable-area-x'>
        <table id="menuTable"></table>
      </div>
    </div>
  </div>
</div>
<div class='modal hide fade' id='modal-Menu' role='dialog' tabindex='-1' style='margin-bottom: 0;width:800px;height:500px;'>
    <div class='modal-header'>
        <button class='close' data-dismiss='modal' type='button'>&times;</button>
        <h3></h3>
    </div>
    <form class='form validate-form' id='submit-form' method="post" role="form"  >
	    <div class='modal-body'>
	        <div class='control-group'>
	        	<label class='control-label'>APP名称</label>
	        	<div class='controls' style="margin-top:10px;margin:left:10px;">
               		<input type='hidden' id='id' name="id"/>
                    <input class='span8' id='app_name' name="app_name" required placeholder='APP名称' type='text' />
           		 </div>
	        </div>
	        <div class='control-group'>
	        	<label class='control-label'>APP包名</label>
	        	<div class='controls' style="margin-top:10px;margin:left:10px;">
               		<input class='span8' id='app_package_name' required name="app_package_name" placeholder='App包名' type='text' />
           		</div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>日限量</label>
	            <div class='controls'>
	                <input class='span8' id='app_package_name' required name="app_package_name" placeholder='菜单名称' type='text' />
	            </div>
	             <label class='control-label'>月限量</label>
	            <div class='controls'>
	                <input class='span8' id='app_package_name' required name="app_package_name" placeholder='菜单名称' type='text' />
	            </div>
	        </div>
	         <div class='control-group'>
	            <label class='control-label'>联系人</label>
	            <div class='controls'>
	                <input class='span8' id='link_name' name="link_name" placeholder='菜单图片' type='text' />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>联系电话</label>
	            <div class='controls'>
	                <input class='span8' id='phone_no' name="phone_no" placeholder='菜单地址' type='text' digits="true" />
	            </div>
	        </div>
	        <div class='control-group'>
	            <label class='control-label'>简介</label>
	            <div class='controls'>
	                <input class='span8' id='description' required name='description' digits="true" placeholder='排序' type='text' />
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
  seajs.use(['base','main/app/manage'],function(b,m){
	b.init();
    m.init('${ctx}');
  });
</script>
</body>
</html>
