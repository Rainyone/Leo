// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        codeName:'',
        charge_code_id:'',
        table:new core.Table('chargeCodeISPTable'),
        init:function(_basepath) {
            F.basepath = _basepath;
	        
	        operateEvents = {
	        		
			        /**
					 * 全部清除
					 */
			        'click .delIsp': function (e, value, row, index) {
			        	var code_id = row.code_id;
			        	if($('#yd-'+code_id).attr("checked")||$('#lt-'+code_id).attr("checked")||
		        				$('#dx-'+code_id).attr("checked")||$('#qt-'+code_id).attr("checked")){
			        		base.bootConfirm("是否确定全部删除？",function(){
			        			var data = {"code_id":code_id};
			        			var url = F.basepath+'/main/charge_code_rule/isp/dels';
			        			base.ajaxRequest(url,data,function(data, status){
			        				base.bootAlert(data);
			        				if (data.ok) {
			        					F.reload();
			        				}else{
			        					$(this).attr("checked",errorResult);   
			        				}
			        			},function(){
			        				//alert("异常");
			        			});
			        		});
		        		}
			        },
			        /**
		    		 * 选择事件
		    		 */
		    		'change .check':function(e, value, row, index) {
		    			var checkbox_value = $(this).attr('value'); 
		    			var isp_id = checkbox_value.split('-')[0];
		    			var code_id = checkbox_value.split('-')[1];
		    			var isChecked = 0;
		    			var errorResult = true;
		    			if($(this).attr("checked")){//选中
		    				isChecked = 1;
		    				errorResult = false;
		    			}
		    			var data = {"isp_id":isp_id,"code_id":code_id,"isChecked":isChecked};
		            	var url = F.basepath+'/main/charge_code_rule/isp/create';
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
		    			        field: 'code_id',
		    			        title: '主键',
		    			        visible:false
		    		        },{
		        		        field: 'code_name',
		        		        title: '计费代码名称'
		        		    },{
		        		        field: 'dx',
		        		        title: '电信',
		    			        visible:false
		        		    }];
		        cols.push({
			    	align: 'center',
			        title: '运营商限制设置',
			        events: operateEvents,
			        formatter:F.ispFormatter
			    });
		        cols.push({
			    	align: 'center',
			        title: '操作',
			        events: operateEvents,
			        formatter:F.operateFormatter
			    });
	    		F.table.init(F.basepath+'/main/charge_code_rule/isp/get-list',cols);
	    		
	    		
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
        },reload:function(){
        	F.table.reload();
        },
        ispFormatter:function (value, row, index) {
        	var code_id = row.code_id;
        	var yd = row.yd;
        	var dx = row.dx;
        	var lt = row.lt;
        	var qt = row.qt;
        	var _isp = "";
        	if(yd==1){
        		_isp+="移动：<input style='width:20px;height:20px;' class='check span1' id='yd-"+code_id+"' name='check'  type='checkbox' checked='checked' value='1002-"+code_id+"'/>";
        	}else{
        		_isp+="移动：<input style='width:20px;height:20px;' class='check span1' id='yd-"+code_id+"' name='check'  type='checkbox' value='1002-"+code_id+"'/>";
        	}
        	if(dx==1){
        		_isp+="电信：<input style='width:20px;height:20px;' class='check span1' id='dx-"+code_id+"' name='check'  type='checkbox' checked='checked'  value='1001-"+code_id+"'/>";
        	}else{
        		_isp+="电信：<input style='width:20px;height:20px;' class='check span1' id='dx-"+code_id+"' name='check'  type='checkbox'  value='1001-"+code_id+"'/>";
        	}
        	if(lt==1){
        		_isp+="联通：<input style='width:20px;height:20px;' class='check span1' id='lt-"+code_id+"' name='check'  type='checkbox' checked='checked'  value='1003-"+code_id+"'/>";
        	}else{
        		_isp+="联通：<input style='width:20px;height:20px;' class='check span1' id='lt-"+code_id+"' name='check'  type='checkbox'  value='1003-"+code_id+"'/>";
        	}
        	if(qt==1){
        		_isp+="其他：<input style='width:20px;height:20px;' class='check span1' id='qt-"+code_id+"' name='check'  type='checkbox' checked='checked'  value='1004-"+code_id+"'/>";;
        	}else{
        		_isp+="其他：<input style='width:20px;height:20px;' class='check span1' id='qt-"+code_id+"' name='check'  type='checkbox'  value='1004-"+code_id+"'/>";
        	}
        	return _isp;
        },
        operateFormatter:function (value, row, index) {
        	var _btnAction = "";
        	if (base.perList.menu.grant) {
        		_btnAction += "<a class='delIsp btn btn-primary btn-small' href='#' title='清除全部设置' style='margin-left:5px'>清除全部</a>";
        	}
        	return _btnAction;
        }
    };

});
