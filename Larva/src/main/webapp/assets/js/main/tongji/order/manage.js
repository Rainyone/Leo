// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        table:new core.Table('orderTable'),
        appLogTable:new core.Table('appLogTable'),
        init:function(_basepath) {
            F.basepath = _basepath;
            $('#datetimeStart').datetimepicker({
		        format: 'yyyy-mm-dd hh:ii',
		        minView:1,
		        startView:2,
		        language: 'zh-CN',
		        autoclose:true
		    })
	    	$('#datetimeEnd').datetimepicker({
			        format: 'yyyy-mm-dd hh:ii',
			        minView:1,
			        startView:2,
			        language: 'zh-CN',
			        autoclose:true
			    })
	        operateEvents = {
    		        /**
    		         * 查看简介
    		         */
    		        'click .app_log': function (e, value, row, index) {
    		        	core.openModel('modal-app-log','APP日志',function(){
    		        		var charge_key = row.id;
    		        		//查询结果绑定到列表中
    		        		$('#appLogTable').bootstrapTable('refresh',{url:F.basepath+'/main/tongji/order/appLog?charge_key=' +charge_key});
    		        	});
    		        }
			    };
            var appLogCols = [
    		                  {
			        		        field: 'id',
			        		        title: 'app_log_id',
			        		        visible:false
			        		  }, {
			        		        field: 'charge_key',
			        		        title: '计费日志id'
			        		  }, {
			        		        field: 'imsi',
			        		        title: 'imsi'
			        		  }, {
			        		        field: 'channel',
			        		        title: 'channel'
			        		  }, {
			        		        field: 'logtime',
			        		        title: 'logtime'
			        		  }, {
			        		        field: 'stepname',
			        		        title: 'stepname'
			        		  }, {
			        		        field: 'context',
			        		        title: '内容'
			        		  }, {
			        		        field: 'create_time',
			        		        title: '创建时间'
			        		  }, {
			        		        field: 'ip',
			        		        title: 'ip'
			        		  }
    		     ];
    			F.appLogTable.init(null,appLogCols);
		        var cols = [
		                    {
		        		        checkbox:false
		        		    },{
		        		        field: 'id',
		        		        title: 'log_id'
		        		    },{
		        		        field: 'app_id',
		        		        title: 'app_id',
		        		        visible:false
		        		    },{
		        		        field: 'app_name',
		        		        title: 'APP名称'
		        		    },{
		    			        field: 'charge_code_name',
		    			        title: '计费代码名称'
		    		        },{
		        		        field: 'order_no',
		        		        title: 'order_no'
		        		    },{
		    			        field: 'order_state',
		    			        title: '订单状态'
		    		        }, {
		        		        field: 'area_name',
		        		        title: '省份'
		        		    },{
		        		        field: 'isp_name',
		        		        title: '运营商'
		        		    },{
		        		        field: 'imei',
		        		        title: 'IMEI'
		        		    },{
		    			        field: 'imsi',
		    			        title: 'IMSI'
		    		        },{
		    			        field: 'ip',
		    			        title: 'IP地址'
		    		        },{
		    			        field: 'bsc_lac',
		    			        title: 'bsc_lac'
		    		        },{
		    			        field: 'bsc_cid',
		    			        title: 'bsc_cid'
		    		        },{
		    			        field: 'mobile',
		    			        title: 'mobile'
		    		        },{
		    			        field: 'iccid',
		    			        title: 'iccid'
		    		        },{
		    			        field: 'mac',
		    			        title: 'mac'
		    		        },{
		    			        field: 'cpparm',
		    			        title: 'cpparm'
		    		        },{
		    			        field: 'price',
		    			        title: 'price'
		    		        },{
		    			        field: 'charge_price',
		    			        title: '计费代码单价'
		    		        },{
		    			        field: 'create_time',
		    			        title: '创建时间'
		    		        },{
		    		        	 field: 'app_log',
			    			     title: 'APP日志',
		    		        	 events: operateEvents,
			    			     formatter:F.appLogFormatter
		    		        }];
	    		F.table.init(F.basepath+'/main/tongji/order/query',cols);
	    		
				/**
				 * 查询
				 */
				$('#query').click(function(){
					F.submit();
	            });
				/**
				 * 最近一天
				 */
				$('#oneDate').click(function(){
					F.getDataByDate(-1);
	            });
				/**
				 * 最近七天
				 */
				$('#sevenDate').click(function(){
					F.getDataByDate(-7);
	            });
				/**
				 * 最近十五
				 */
				$('#fifteenDate').click(function(){
					F.getDataByDate(-15);
	            });
				/**
				 * 关闭模态框
				 */
				$('#btnAppLogClose').click(function(){
					core.closeModel('modal-app-log');
				});
        },submit:function(){
        	var url = F.basepath+'/main/tongji/order/query';
        	var order_id = $('#order_id').val();
        	var code_name = $('#code_name').val();
        	var app_name = $('#app_name').val();
        	var order_state = $('#order_state').val();
        	var datetimeStart = $('#datetimeStart').val();
        	var datetimeEnd = $('#datetimeEnd').val();
        	var data = "order_id=" + order_id +"&code_name=" + code_name +"&app_name="+app_name 
        	+ "&order_state=" + order_state +"&datetimeStart="+datetimeStart+"&datetimeEnd="+datetimeEnd;
        	$('#orderTable').bootstrapTable('refresh',{url:url+'?'+data});
        },GetDateStr:function(AddDayCount) {
		    var dd = new Date();
		    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
		    var y = dd.getFullYear();
		    var m = dd.getMonth()+1;//获取当前月份的日期
		    var d = dd.getDate();
		    return y+"-"+m+"-"+d;
		},getDataByDate:function(dateCount){
			var url = F.basepath+'/main/tongji/order/query';
        	var order_id = $('#order_id').val();
        	var code_name = $('#code_name').val();
        	var app_name = $('#app_name').val();
        	var order_state = $('#order_state').val();
        	var datetimeStart = F.GetDateStr(dateCount);
        	var datetimeEnd ='';
        	var data = "order_id=" + order_id +"&code_name=" + code_name +"&app_name="+app_name 
        	+ "&order_state=" + order_state +"&datetimeStart="+datetimeStart+"&datetimeEnd="+datetimeEnd;
        	$('#orderTable').bootstrapTable('refresh',{url:url+'?'+data});
		},appLogFormatter:function(){
			var _btnAction = "";
    		_btnAction += "<a class='app_log btn btn-info btn-small' href='#' title='查看APP日志' style='margin-left:5px'>查看</a>";
    		return _btnAction;
		}
    };
});
