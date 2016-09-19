// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        appName:'',
        app_id:'',
        table:new core.Table('appCodeTable'),
        appCodeRuleTable:new core.Table('appCodeRuleTable'), 
        chargeCodeList:new core.Table('chargeCodeList'), 
        init:function(_basepath) {
            F.basepath = _basepath;
            $("#app-code-header .actions").append("<a href='#' id='addAppCodeRule' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'><i class='icon-plus'></i>添加</a>");
            $("#app-code-header .actions").append("<a href='#' id='delAppCodeRules' class='btn btn-danger btn-small' style='margin-left:5px'><i class='icon-remove'></i>删除</a>");
	        
	        operateEvents = {
	        		
					/**
					 * 设置适用的计费代码
					 */
			        'click .setAppCodeRule': function (e, value, row, index) {
	            		F.appName = row.app_name;
	            		F.app_id = row.id;
			        	$('#appCodeRuleTable').bootstrapTable('refresh',{url:F.basepath+'/main/app/app_rule/app_code/get-list?app_id='+row.id});
						return false;
			        },
			        /**
					 * 删除
					 */
			        'click .delAppCodeRule': function (e, value, row, index) {
			        	base.bootConfirm("是否确定删除？",function(){
			        		var ids = new Array();  
			        		ids.push(row.id);   
			    			F.delAppCodeRule(ids);
			    		});
			        },
			        /**
		    		 * 选择事件
		    		 */
		    		'change .check':function(e, value, row, index) {
		    			var charge_id = $(this).attr('value'); 
		    			var isChecked = 0;
		    			var errorResult = true;
		    			if($(this).attr("checked")){//选中
		    				isChecked = 1;
		    				errorResult = false;
		    			}
		    			var data = {"charge_id":charge_id,"app_id":F.app_id,"isChecked":isChecked};
		            	var url = F.basepath+'/main/app/app_rule/app_code/create';
		            	base.ajaxRequest(url,data,function(data, status){
		            		 //base.bootAlert(data);
		                     if (data.ok) {
		                     	F.reload();
		                     }else{
		                    	 $(this).attr("checked",errorResult);   
		                     }
		            	},function(){
		            		//alert("异常");
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
		        		        field: 'app_name',
		        		        title: 'APP名称'
		        		    }];
			        cols.push({
				    	align: 'center',
				        title: '操作',
				        events: operateEvents,
				        formatter:F.operateFormatter
				    });
	        		
	    		F.table.init(F.basepath+'/main/app/get-list-apps',cols);
	    		
	    		//appCodeRuleTableCols 初始化
	    		var appCodeRuleTableCols = [
		                    {
		        		        checkbox:true
		        		    },{
		    			        field: 'id',
		    			        title: '菜单主键',
		    			        visible:false
		    		        },{
		        		        field: 'app_name',
		        		        title: 'APP名称'
		        		    },{
		        		        field: 'charge_code_name',
		        		        title: '计费代码名称'
		        		    }];
	        	appCodeRuleTableCols.push({
			    	align: 'center',
			        title: '操作',
			        events: operateEvents,
			        formatter:F.delAppCodeOperateFormatter
			    });
		        F.appCodeRuleTable.init(F.basepath+'/main/app/app_rule/app_code/get-list',appCodeRuleTableCols);
	    		
		        var chargeCodeListCols = [
		                      {
		    			        field: 'id',
		    			        title: '计费代码主键',
		    			        visible:false
		    		        },{
		        		        field: 'code_name',
		        		        title: '计费代码名称'
		        		    }];
		        chargeCodeListCols.push({
			    	align: 'center',
			        title: '操作',
			        events: operateEvents,
			        formatter:F.appCodeOperateFormatter
			    });
		        F.chargeCodeList.init(F.basepath+'/main/app/app_rule/get-list-charge-codes',chargeCodeListCols);
	    		/**
				 * 打开模态框
				 */
				$('#addAppCodeRule').click(function(){
					if(F.appName==''){
						base.bootAlert({"ok":false,"msg":"请先选择APP并点击设置"});
						return;
					}else{
						core.openModel('modal-app-code-rule','新增计费代码',function(){
							$('#chargeCodeList').bootstrapTable('refresh',{url:F.basepath+'/main/app/app_rule/get-list-charge-codes?app_id='+F.app_id});
						});
					}
				});
				/**
				 * 关闭模态框
				 */
				$('#btnClose').click(function(){
					core.closeModel('modal-app-code-rule');
					
				});
				
				/**
				 * 批量删除
				 */
				$('#delAppCodeRules').click(function(){
					var ids = F.appCodeRuleTable.getIdSelections();
					if(ids!=null&&ids.length>0){
						base.bootConfirm("是否确定删除选定的"+ids.length+"个？",function(){
							F.delAppCodeRule(ids);
						});
					}else{
						base.bootAlert({"ok":false,"msg":"请选择你要删除计费代码！"});
					}
				});
        },delAppCodeRule:function(ids){
        	base.ajaxRequest(F.basepath+'/main/app/app_rule/app_code/del',{"appCodeRuleIds":ids},function(data){
        		base.ajaxSuccess(data);
        		F.reload();
        	},function(){
        		base.bootAlert({"ok":false,"msg":"网络异常"});
        	});
        },reload:function(){
//        	F.chargeCodeTimeRuleTable.reload();
        	$('#appCodeRuleTable').bootstrapTable('refresh',{url:F.basepath+'/main/app/app_rule/app_code/get-list?app_id='+F.app_id});
        },
        operateFormatter:function (value, row, index) {
        	var _btnAction = "";
        	if (base.perList.menu.grant) {
        		_btnAction += "<a class='setAppCodeRule btn btn-primary btn-small' href='#' title='设置适用的计费代码' style='margin-left:5px'>设置适用计费代码</a>";
        	}
        	return _btnAction;
        },
        delAppCodeOperateFormatter:function (value, row, index) {
        	var _btnAction = "";
        	if (base.perList.menu.grant) {
        		_btnAction += "<a class='delAppCodeRule btn btn-primary btn-small' href='#' title='删除适用的计费代码' style='margin-left:5px'>删除</a>";
        	}
        	return _btnAction;
        },
        appCodeOperateFormatter:function (value, row, index) {
        	var _btnAction = "";
        	var charge_id = row.code_id;
        	var is_have = row.is_have;
        	if(is_have==1){
        		_btnAction += "<input style='width:20px;height:20px;' class='check span1' name='check'  type='checkbox'  value='"+charge_id+"' checked='checked'/>";
        	}else{
        		_btnAction += "<input style='width:20px;height:20px;' class='check span1' name='check'  type='checkbox'  value='"+charge_id+"'/>";
        	}
        	return _btnAction;
        }
    };

});