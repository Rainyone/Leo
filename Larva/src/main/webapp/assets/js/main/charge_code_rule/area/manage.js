// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        codeName:'',
        charge_code_id:'',
        table:new core.Table('areaChargeCodeTable'),
        init:function(_basepath) {
            F.basepath = _basepath;
            //时间控件
            $("#charge-code-area-header .actions").append("<a href='#' id='addChargeCodeDisableTime' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'><i class='icon-plus'></i>全部选中</a>");
            $("#charge-code-area-header .actions").append("<a href='#' id='delChargeCodeDisableTime' class='btn btn-danger btn-small' style='margin-left:5px'><i class='icon-remove'></i>全部取消</a>");
	        
	        operateEvents = {
	        		
			        /**
					 * 删除菜单
					 */
			        'click .setChargeArea': function (e, value, row, index) {
			        }
			    };
		        
		        var cols = [
		                   {
		    			        field: 'id',
		    			        title: '菜单主键',
		    			        visible:false
		    		        },{
		        		        field: 'code_name',
		        		        title: '计费代码名称'
		        		    }];
		        cols.push({
			    	align: 'center',
			        title: '操作',
			        events: operateEvents,
			        formatter:F.operateFormatter
			    });
	    		F.table.init(F.basepath+'/main/charge_code/get-list-charge-codes',cols);
	    		
				/**
				 * 提交表单
				 */
				$('#btnSubmit').click(function(){
					F.submit();
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
        		_btnAction += "<a class='setChargeArea btn btn-primary btn-small' href='#' title='设置适用区域' style='margin-left:5px'>设置适用区域</a>";
        		_btnAction += "<a class='viewAreaLimit btn btn-primary btn-small' href='#' title='查看适用区域' style='margin-left:5px'>查看</a>";
        	}
        	return _btnAction;
        }
    };

});
function delDisabledTime(o){
	o.parentElement.parentElement.remove();
	
}