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
            //初始化cols
            var cols = F.getJsonCols();
            /*= [
	                    {
	        		        field: 'datelist',
	        		        title: '日期'
	        		    },{
	        		        field: 'request_count',
	        		        title: '请求次数'
	        		    },{
	    			        field: 'request_account',
	    			        title: '计费金额'
	    		        },{
	        		        field: 'request_success_count',
	        		        title: '计费成功次数'
	        		    }];*/
	        
            //F.initAppList();
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
		       
				/**
				 * 查询
				 */
				$('#query').click(function(){
					F.table.destroy();
					var cols = [
			                    {
			        		        field: 'datelist',
			        		        title: '日期'
			        		    },{
			        		        field: 'request_success_count',
			        		        title: '计费成功次数'
			        		    }];
					F.table.init('',cols);
					/*F.submit();
					F.getPlatformQueryCount();
					F.initCharts();*/
	            });
				/**
				 * 最近30天
				 */
				$('#thirtyDate').click(function(){
					F.getDataByDate(-30);
					F.initCharts(-30);
	            });
				/**
				 * 最近七天
				 */
				$('#sevenDate').click(function(){
					F.getDataByDate(-7);
					F.initCharts(-7);
	            });
				/**
				 * 最近十五
				 */
				$('#fifteenDate').click(function(){
					F.getDataByDate(-15);
					F.initCharts(-15);
	            });
				$("#appId").change(function() {
					F.submit();
					F.getPlatformQueryCount();
					F.initCharts();
	    			return false;
	    		});
        },submit:function(){
        	var url = F.basepath+'/main/tongji/app/query';
        	var datetimeStart = $('#datetimeStart').val();
        	var datetimeEnd = $('#datetimeEnd').val();
        	var appId = $("#appId").val();
        	var data = "datetimeStart="+datetimeStart+"&datetimeEnd="+datetimeEnd+"&app_id="+appId;
        	$('#orderTable').bootstrapTable('refresh',{url:url+'?'+data});
        },GetDateStr:function(AddDayCount) {
		    var dd = new Date();
		    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
		    var y = dd.getFullYear();
		    var m = dd.getMonth()+1;//获取当前月份的日期
		    var d = dd.getDate();
		    return y+"-"+m+"-"+d;
		},getDataByDate:function(dateCount){
			var url = F.basepath+'/main/tongji/app/query';
        	var datetimeStart = F.GetDateStr(dateCount);
        	var datetimeEnd ='';
        	var appId = $("#appId").val();
        	var data = "datetimeStart="+datetimeStart+"&datetimeEnd="+datetimeEnd + "&app_id="+appId;
        	$('#orderTable').bootstrapTable('refresh',{url:url+'?'+data});
		},getPlatformQueryCount:function(){
			var url = F.basepath+'/main/tongji/app/queryCount';
			var appId = $("#appId").val();
			base.ajaxRequest(url,{app_id:appId},function(data){
	        	$('#month_request_count')[0].innerText = data.data.request_count;	
	        	$('#month_request_account')[0].innerText = data.data.request_account;	
	        	$('#month_request_success_count')[0].innerText = data.data.request_success_count;	
        	},function(data){
        	});
		},/*initAppList:function(){
			var url = F.basepath+'/main/tongji/app/queryAppAll';
			base.ajaxRequest(url,{},function(data){
				if (data) {
					var __items =data.data;
					$("#appId").html("");
	        		$.each(__items, function(index, value){
        				if (index==0) {
        					var item = $('<option selected="selected" value="'+value.app_id+'">'+value.app_name+'</option>');
        					
        				} else {
        					var item = $('<option value="'+value.app_id+'">'+value.app_name+'</option>');
        				}
	        			$("#appId").append(item);
	        		});	
	        		//做个初始化查询
					F.submit();
					F.getPlatformQueryCount();
					F.initCharts();
			} else {
				$.messager.alert('错误', '网络异常，请稍后再试！', 'error');
			}
        	},function(data){
        	});
		},*/initCharts:function(dateCount){
			var url = F.basepath+'/main/tongji/app/charts';
			var datetimeStart = '';
			var datetimeEnd = '';
			if(dateCount){
				datetimeStart = F.GetDateStr(dateCount);
	        	datetimeEnd ='';
			}else{
				datetimeStart = $('#datetimeStart').val();
				datetimeEnd = $('#datetimeEnd').val();
			}
        	var appId = $("#appId").val();
        	var data = {datetimeStart:datetimeStart,datetimeEnd:datetimeEnd,app_id:appId};
        	base.ajaxRequest(url,data,function(data){
        		var xdata = data.data.xdata;
        		var ydata_request_count = data.data.ydata_request_count;
        		var ydata_request_account = data.data.ydata_request_account;
        		var ydata_request_success_count = data.data.ydata_request_success_count;
				var myChart = echarts.init(document.getElementById('echarts'));   
				option = {
					    title : {
					        text: '统计数据',
					        subtext: '当前条件'
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['请求次数','计费金额','计费成功次数']
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            dataView : {show: true, readOnly: false},
					            magicType : {show: true, type: ['line', 'bar']},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    xAxis : [
					        {
					            type : 'category',
					            data :xdata
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'请求次数',
					            type:'bar',
					            data:ydata_request_count,
					            markPoint : {
					                data : [
					                    {type : 'max', name: '最大值'},
					                    {type : 'min', name: '最小值'}
					                ]
					            }
					        },
					        {
					            name:'计费金额',
					            type:'bar',
					            data:ydata_request_account,
					            markPoint : {
					                data : [
					                    {type : 'max', name: '最大值'},
					                    {type : 'min', name: '最小值'}
					                ]
					            }
					        },{
					            name:'计费成功次数',
					            type:'bar',
					            data:ydata_request_success_count,
					            markPoint : {
					                data : [
					                    {type : 'max', name: '最大值'},
					                    {type : 'min', name: '最小值'}
					                ]
					            }
					        }
					    ]
					};

				  // 为echarts对象加载数据   
		          myChart.setOption(option);
        	},function(data){
        	});
		},getJsonCols:function(beginDate,endDate){
			if(!beginDate){
				beginDate = $('#datetimeStart').val();
			}
			if(!endDate){
				endDate = $('#datetimeEnd').val();
			}
			var url = F.basepath+'/main/tongji/appnew/queryCols';
			base.ajaxRequest(url,{datetimeStart:beginDate,datetimeEnd:endDate},function(data){
				console.info(data);
				if (data.ok) {
					var cols =data.data;
				} else {
					$.messager.alert('错误', '网络异常，请稍后再试！', 'error');
				}
				F.table.destroy();
				var queryType =  $('#app_type').val();
				var tableUrl = F.basepath+'/main/tongji/appnew/queryColsResult?datetimeStart='
				 + beginDate+"&datetimeEnd=" + endDate + "&queryType=" + queryType ;
				F.table.init(tableUrl,cols);
        	},function(data){
        	});	
		}
    };
});
