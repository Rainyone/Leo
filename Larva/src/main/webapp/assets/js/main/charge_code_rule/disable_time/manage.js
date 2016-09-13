// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        codeName:'',
        charge_code_id:'',
        table:new core.Table('chargeCodeTable'),
        chargeCodeTimeRuleTable:new core.Table('chargeCodeTimeRuleTable'), 
        init:function(_basepath) {
            F.basepath = _basepath;
            //时间控件
            $("#disable-time-header .actions").append("<a href='#' id='addChargeCodeDisableTime' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'><i class='icon-plus'></i>添加</a>");
            $("#disable-time-header .actions").append("<a href='#' id='delChargeCodeDisableTime' class='btn btn-danger btn-small' style='margin-left:5px'><i class='icon-remove'></i>删除</a>");
	        
	        operateEvents = {
	        		
					/**
					 * 刷新失效时间设置table
					 */
			        'click .setDisableTime': function (e, value, row, index) {
			        	//查询失效时间
	            		F.codeName = row.code_name;
	            		F.charge_code_id = row.id;
			        	$('#chargeCodeTimeRuleTable').bootstrapTable('refresh',{url:F.basepath+'/main/charge_code_rule/disableTime/get-list-disabled-times?chargeCodeId='+row.id});
						return false;
			        },
			        /**
					 * 删除菜单
					 */
			        'click .delDisableTime': function (e, value, row, index) {
			        	base.bootConfirm("是否确定删除？",function(){
			        		var ids = new Array();  
			        		ids.push(row.id);   
			    			F.delDisableTime(ids);
			    		});
			        }
			    };
		        
		        var cols = [
		                    {
		        		        checkbox:true
		        		    },{
		    			        field: 'id',
		    			        title: '菜单主键',
		    			        visible:false
		    		        },{
		        		        field: 'code_name',
		        		        title: '计费代码名称'
		        		    }];
		        //是否需要操作列
		        if(base.perList.menu.edit||base.perList.menu.del||base.perList.menu.grant||base.perList.menu.checkPermission)
			        cols.push({
				    	align: 'center',
				        title: '操作',
				        events: operateEvents,
				        formatter:F.operateFormatter
				    });
	        		
	    		F.table.init(F.basepath+'/main/charge_code/get-list-charge-codes',cols);
	    		
	    		//chargeCodeTimeRuleTable 初始化
	    		var chargeCodeTimeRuleCols = [
		                    {
		        		        checkbox:true
		        		    },{
		    			        field: 'id',
		    			        title: '菜单主键',
		    			        visible:false
		    		        },{
		        		        field: 'code_name',
		        		        title: '计费代码名称'
		        		    },{
		        		        field: 'charge_code_id',
		        		        title: '计费代码id',
		    			        visible:false
		        		    },{
		        		        field: 'disable_start_time',
		        		        title: '开始时间'
		        		    },{
		        		        field: 'disable_end_time',
		        		        title: '结束时间'
		        		    },{
		        		        field: 'state',
		        		        title: '状态',
		    			        visible:false
		        		    },{
		        		        field: 'create_time',
		        		        title: '创建时间',
		    			        visible:false
		        		    },{
		        		        field: 'create_people_name',
		        		        title: '创建人',
		    			        visible:false
		        		    },{
		        		        field: 'update_time',
		        		        title: '更新时间',
		    			        visible:false
		        		    },{
		        		        field: 'update_people_name',
		        		        title: '更新人',
		    			        visible:false
		        		    }];
		        //是否需要操作列
		        if(base.perList.menu.edit||base.perList.menu.del||base.perList.menu.grant||base.perList.menu.checkPermission)
		        	chargeCodeTimeRuleCols.push({
				    	align: 'center',
				        title: '操作',
				        events: operateEvents,
				        formatter:F.chargeCodeTimeOperateFormatter
				    });
		        F.chargeCodeTimeRuleTable.init(F.basepath+'/main/charge_code_rule/disableTime/get-list-disabled-times',chargeCodeTimeRuleCols);
	    		
	    		/**
	    		 * 添加时间窗口
	    		 */
	    		$('#addDisabledTime').click(function(){
					$("#addDisabledTimeDiv").append(oldHtml);
				});
	    		/**
				 * 打开模态框
				 */
				$('#addChargeCodeDisableTime').click(function(){
					if(F.codeName==''){
						base.bootAlert({"ok":false,"msg":"请先选择计费代码并点击设置失效时间"});
						return;
					}else{
						core.openModel('modal-charge-code-disable-time','新增失效时间',function(){
							$('#submit-form')[0].reset();  
							$('#code_name').val(F.codeName); 
							$('#charge_code_id').val(F.charge_code_id);
						});
					}
				});
				/**
				 * 关闭模态框
				 */
				$('#btnClose').click(function(){
					core.closeModel('modal-charge-code-disable-time');
				});
				
				/**
				 * 提交表单
				 */
				$('#btnSubmit').click(function(){
					F.submit();
	            });
				
				/**
				 * 批量删除
				 */
				$('#delChargeCodeDisableTime').click(function(){
					var ids = F.chargeCodeTimeRuleTable.getIdSelections();
					if(ids!=null&&ids.length>0){
						base.bootConfirm("是否确定删除选定的"+ids.length+"个菜单？",function(){
							F.delDisableTime(ids);
						});
					}else{
						base.bootAlert({"ok":false,"msg":"请选择你要删除的菜单！"});
					}
				});
        },submit:function(){
        	var url = F.basepath+'/main/charge_code_rule/disableTime/create';
        	var options = {
                    success: F.showResponse,      //提交后的回调函数
                    url: url,       //默认是form的action， 如果申明，则会覆盖
                    type: 'post',               //默认是form的method（get or post），如果申明，则会覆盖
                    dataType: 'json',           //html(默认), xml, script, json...接受服务端返回的类型
                    clearForm: true,          //成功提交后，清除所有表单元素的值
                    timeout: 30000               //限制请求的时间，当请求大于3秒后，跳出请求
                }
        	$('#submit-form').ajaxForm(options);
        },showResponse:function(data, status){
            base.bootAlert(data);
            if (data.ok) {
            	core.closeModel('modal-charge-code-disable-time');
            	F.reload();
            }
        },delDisableTime:function(ids){
        	base.ajaxRequest(F.basepath+'/main/charge_code_rule/disableTime/del',{"chargeCodeDisableTimeIds":ids},function(data){
        		base.ajaxSuccess(data);
        		F.reload();
        	},function(){
        		base.bootAlert({"ok":false,"msg":"网络异常"});
        	});
        },reload:function(){
        	F.chargeCodeTimeRuleTable.reload();
        },
        operateFormatter:function (value, row, index) {
        	var _btnAction = "";
        	if (base.perList.menu.grant) {
        		_btnAction += "<a class='setDisableTime btn btn-primary btn-small' href='#' title='设置失效时间' style='margin-left:5px'>设置失效时间</a>";
        	}
        	return _btnAction;
        },
        chargeCodeTimeOperateFormatter:function (value, row, index) {
        	var _btnAction = "";
        	if (base.perList.menu.grant) {
        		_btnAction += "<a class='delDisableTime btn btn-primary btn-small' href='#' title='删除失效时间' style='margin-left:5px'>删除</a>";
        	}
        	return _btnAction;
        }
    };

});
function delDisabledTime(o){
	o.parentElement.parentElement.remove();
	
}