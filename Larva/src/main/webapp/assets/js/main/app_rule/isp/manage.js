// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        codeName:'',
        charge_code_id:'',
        table:new core.Table('appISPTable'),
        init:function(_basepath) {
            F.basepath = _basepath;
	        
	        operateEvents = {
	        		
			        /**
					 * 全部清除
					 */
			        'click .delIsp': function (e, value, row, index) {
			        	var app_id = row.app_id;
			        	if($('#yd-'+app_id).attr("checked")||$('#lt-'+app_id).attr("checked")||
		        				$('#dx-'+app_id).attr("checked")||$('#qt-'+app_id).attr("checked")){
			        		base.bootConfirm("是否确定全部删除？",function(){
			        			var data = {"app_id":app_id};
			        			var url = F.basepath+'/main/app/app_rule/isp/dels';
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
		    			var app_id = checkbox_value.split('-')[1];
		    			var isChecked = 0;
		    			var errorResult = true;
		    			if($(this).attr("checked")){//选中
		    				isChecked = 1;
		    				errorResult = false;
		    			}
		    			var data = {"isp_id":isp_id,"app_id":app_id,"isChecked":isChecked};
		            	var url = F.basepath+'/main/app/app_rule/isp/create';
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
		    			        field: 'app_id',
		    			        title: '主键',
		    			        visible:false
		    		        },{
		        		        field: 'app_name',
		        		        title: 'APP名称'
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
	    		F.table.init(F.basepath+'/main/app/app_rule/isp/get-list',cols);
	    		
	    		
        },reload:function(){
        	F.table.reload();
        },
        ispFormatter:function (value, row, index) {
        	var app_id = row.app_id;
        	var yd = row.yd;
        	var dx = row.dx;
        	var lt = row.lt;
        	var qt = row.qt;
        	var _isp = "";
        	if(yd==1){
        		_isp+="移动：<input style='width:20px;height:20px;' class='check span1' id='yd-"+app_id+"' name='check'  type='checkbox' checked='checked' value='1002-"+app_id+"'/>";
        	}else{
        		_isp+="移动：<input style='width:20px;height:20px;' class='check span1' id='yd-"+app_id+"' name='check'  type='checkbox' value='1002-"+app_id+"'/>";
        	}
        	if(dx==1){
        		_isp+="电信：<input style='width:20px;height:20px;' class='check span1' id='dx-"+app_id+"' name='check'  type='checkbox' checked='checked'  value='1001-"+app_id+"'/>";
        	}else{
        		_isp+="电信：<input style='width:20px;height:20px;' class='check span1' id='dx-"+app_id+"' name='check'  type='checkbox'  value='1001-"+app_id+"'/>";
        	}
        	if(lt==1){
        		_isp+="联通：<input style='width:20px;height:20px;' class='check span1' id='lt-"+app_id+"' name='check'  type='checkbox' checked='checked'  value='1003-"+app_id+"'/>";
        	}else{
        		_isp+="联通：<input style='width:20px;height:20px;' class='check span1' id='lt-"+app_id+"' name='check'  type='checkbox'  value='1003-"+app_id+"'/>";
        	}
        	if(qt==1){
        		_isp+="其他：<input style='width:20px;height:20px;' class='check span1' id='qt-"+app_id+"' name='check'  type='checkbox' checked='checked'  value='1004-"+app_id+"'/>";;
        	}else{
        		_isp+="其他：<input style='width:20px;height:20px;' class='check span1' id='qt-"+app_id+"' name='check'  type='checkbox'  value='1004-"+app_id+"'/>";
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
