// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        table:new core.Table('orderTable'),
        init:function(_basepath) {
            F.basepath = _basepath;
            $('#datetimeStart').datetimepicker({
		        format: 'yyyy-mm-dd',
		        minView:'month',
		        language: 'zh-CN',
		        autoclose:true
		    })
	    	$('#datetimeEnd').datetimepicker({
			        format: 'yyyy-mm-dd',
			        minView:'month',
			        language: 'zh-CN',
			        autoclose:true
			    })
	        operateEvents = {
    		        /**
    		         * 查看简介
    		         */
    		        'click .introduction': function (e, value, row, index) {
    		        	core.openModel('modal-introduction','APP简介',function(){
    		        		if(row!=null){
    		        			$("#appIntroduction").val(row.description)
    		        		}
    		        	});
    		        }
			    };
		        
		        var cols = [
		                    {
		        		        checkbox:true
		        		    },{
		        		        field: 'id',
		        		        title: 'log_id',
		        		        visible:false
		        		    },{
		        		        field: 'app_id',
		        		        title: 'app_id'
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
		    			        field: 'create_time',
		    			        title: '创建时间'
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
		}
    };
});
