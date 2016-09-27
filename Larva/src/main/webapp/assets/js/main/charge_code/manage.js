// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        table:new core.Table('chargeCodeTable'),
        init:function(_basepath) {
            F.basepath = _basepath;
            /**
             * 是否具有添加菜单权限
             */
            if(base.perList.menu.create){
            	$("#charge-code-header .actions").append("<a href='#' id='addChargeCode' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'><i class='icon-plus'></i>添加</a>");
            }
            /**
             * 是否具有删除菜单权限
             */
            if(base.perList.menu.del){
            	$("#charge-code-header .actions").append("<a href='#' id='delChargeCode' class='btn btn-danger btn-small' style='margin-left:5px'><i class='icon-remove'></i>删除</a>");
            }
	        operateEvents = {
	        		
					/**
					 *修改计费代码
					 */
			        'click .editChargeCode': function (e, value, row, index) {
			        	core.openModel('modal-charge-code','修改计费代码',function(){
			        		if(row!=null){
			        			$('#submit-form')[0].reset();  
			        			$('#id').val(row.id); 
			        			$('#code_name').val(row.code_name); 
			        			$('#url').val(row.url);  
			        			$('#send_type').val(row.send_type);  
			        			$('#inf_type').val(row.inf_type);  
			        			$('#back_msg_type').val(row.back_msg_type);  
			        			$('#back_form').val(row.back_form);  
			        			$('#return_form').val(row.return_form);  
			        			$('#charge_code').val(row.charge_code);  
			        			$('#ver_code_url').val(row.ver_code_url);  
			        			$('#date_limit').val(row.date_limit);  
			        			$('#month_limit').val(row.month_limit);  
			        			$('#channel_type').val(row.channel_type);  
			        			$('#linke_name').val(row.linke_name);  
			        			$('#phone_no').val(row.phone_no);  
			        			$('#detail').val(row.detail);
			        			$('#success_flag').val(row.success_flag);
			        			$('#order_id_code').val(row.order_id_code);
			        			$('#ver_code_success_flag').val(row.ver_code_success_flag);
			        			$('#callbackurl').val(row.callbackurl);
			        			$('#callbacksuccess').val(row.callbacksuccess);
			        			$('#callbackcolumn').val(row.callbackcolumn);
			        			F.setDelAttr('input','readonly');
								F.setDelAttr('select','readonly');
								F.setDelAttr('input','disabled');
								F.setDelAttr('select','disabled');
								$('#callbackurl').attr('readonly','readonly')
			        			$('#btnSubmit').show();
			            	}
			        	});
						return false;
			        },
			        /**
					 * 删除计费代码
					 */
			        'click .delChargeCode': function (e, value, row, index) {
			        	base.bootConfirm("是否确定删除？",function(){
			        		var ids = new Array();  
			        		ids.push(row.id);   
			    			F.delChargeCode(ids);
			    		});
			        },
    		        /**
    		         * 查看计费代码
    		         */
    		        'click .checkChargeCode': function (e, value, row, index) {
    		        	core.openModel('modal-charge-code','查看计费代码',function(){
						});
	        			$('#submit-form')[0].reset();  
	        			$('#code_name').val(row.code_name); 
	        			$('#url').val(row.url);  
	        			$('#send_type').val(row.send_type);  
	        			$('#inf_type').val(row.inf_type);  
	        			$('#back_msg_type').val(row.back_msg_type);  
	        			$('#back_form').val(row.back_form);  
	        			$('#return_form').val(row.return_form);  
	        			$('#charge_code').val(row.charge_code);  
	        			$('#ver_code_url').val(row.ver_code_url);  
	        			$('#date_limit').val(row.date_limit);  
	        			$('#month_limit').val(row.month_limit);  
	        			$('#channel_type').val(row.channel_type);  
	        			$('#linke_name').val(row.linke_name);  
	        			$('#phone_no').val(row.phone_no);  
	        			$('#detail').val(row.detail);
	        			$('#success_flag').val(row.success_flag);
	        			$('#order_id_code').val(row.order_id_code);
	        			$('#ver_code_success_flag').val(row.ver_code_success_flag);
	        			$('#callbackurl').val(row.callbackurl);
	        			$('#callbacksuccess').val(row.callbacksuccess);
	        			$('#callbackcolumn').val(row.callbackcolumn);
	        			F.setAddAttr('input','readonly','readonly');
	        			F.setAddAttr('select','readonly','readonly');
	        			F.setAddAttr('input','disabled','disabled');
	        			F.setAddAttr('select','disabled','disabled');
	        			$('#btnSubmit').hide();
    		        },
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
		        		    },{
		        		        field: 'url',
		        		        title: '请求url',
		    			        formatter:F.urlFormate
		        		    },{
		    			        field: 'charge_code',
		    			        title: '计费代码报文',
		    			        visible:false
		    		        }, {
		    			        field: 'send_type',
		    			        title: '发送方式'
		    		        },{
		    			        field: 'inf_type',
		    			        title: '接口模式',
		    			        visible:false
		    		        }, {
		        		        field: 'linke_name',
		        		        title: '联系人'
		        		    },{
		    			        field: 'phone_no',
		    			        title: '联系电话'
		    		        },{
		    			        field: 'back_msg_type',
		    			        title: '反馈报文的格式JSON\XML',
		    			        visible:false
		    		        },{
		    			        field: 'order_back',
		    			        title: '订购回调',
		    			        visible:false
		    		        },{
		    			        field: 'back_form',
		    			        title: '反馈给客户端的报文格式',
		    			        visible:false
		    		        },{
		    			        field: 'return_form',
		    			        title: '反馈报文格式',
		    			        visible:false
		    		        },{
		    			        field: 'ver_code_url',
		    			        title: '反馈验证码的请求URL',
		    			        visible:false
		    		        },{
		    			        field: 'date_limit',
		    			        title: '日限量',
		    			        visible:false
		    		        },{
		    			        field: 'month_limit',
		    			        title: '月限量',
		    			        visible:false
		    		        },{
		    			        field: 'channel_type',
		    			        title: '通道类型(1视频、2动漫、3阅读、4音乐)',
		    			        visible:false
		    		        },{
		    			        field: 'detail',
		    			        title: '备注',
		    			        visible:false
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
		    			        title: '创建人名称',
		    			        visible:false
		    		        },{
		    			        field: 'update_time',
		    			        title: '更新时间',
		    			        visible:false
		    		        },{
		    			        field: 'update_people_name',
		    			        title: '更新人',
		    			        visible:false
		    		        },{
		    			        field: 'date_count',
		    			        title: '每天的订购数量',
		    			        visible:false
		    		        },{
		    			        field: 'month_count',
		    			        title: '每一个月的记录数',
		    			        visible:false
		    		        },{
		    			        field: 'success_flag',
		    			        title: '反馈报文的成功标示字段',
		    			        visible:false
		    		        },{
		    			        field: 'order_id_code',
		    			        title: '验证码的order_id字段',
		    			        visible:false
		    		        },{
		    			        field: 'ver_code_success_flag',
		    			        title: '验证码请求成功标示',
		    			        visible:false
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
	    		
	    		/**
				 * 新增计费代码
				 */
				$('#addChargeCode').click(function(){
					core.openModel('modal-charge-code','新增计费代码',function(){
						$('#submit-form')[0].reset(); 
						$('#id').val('');
						F.setDelAttr('input','readonly');
						F.setDelAttr('select','readonly');
						F.setDelAttr('input','disabled');
						F.setDelAttr('select','disabled');
						$('#callbackurl').attr('readonly','readonly')
						$('#btnSubmit').show();
					});
				});
				/**
				 * 关闭模态框
				 */
				$('#btnClose').click(function(){
					core.closeModel('modal-charge-code');
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
				$('#delChargeCode').click(function(){
					var ids = F.table.getIdSelections();
					if(ids!=null&&ids.length>0){
						base.bootConfirm("是否确定删除选定的"+ids.length+"个菜单？",function(){
							F.delChargeCode(ids);
						});
					}else{
						base.bootAlert({"ok":false,"msg":"请选择你要删除的菜单！"});
					}
				});
        },submit:function(){
        	var url = F.basepath+'/main/charge_code/create';
        	if($('#id').val()!=null&&$('#id').val()!='')
        		url =F.basepath+'/main/charge_code/edit';
        	var options = {
                    success: F.showResponse,      //提交后的回调函数
                    url: url,       //默认是form的action， 如果申明，则会覆盖
                    type: 'post',               //默认是form的method（get or post），如果申明，则会覆盖
                    dataType: 'json',           //html(默认), xml, script, json...接受服务端返回的类型
                    clearForm: false,          //成功提交后，清除所有表单元素的值
                    timeout: 30000               //限制请求的时间，当请求大于3秒后，跳出请求
                }
        	$('#submit-form').ajaxForm(options);
        },showResponse:function(data, status){
            base.bootAlert(data);
            if (data.ok) {
            	core.closeModel('modal-charge-code');
            	F.reload();
            }
        },delChargeCode:function(ids){
        	base.ajaxRequest(F.basepath+'/main/charge_code/del',{'chargeCodeIds':ids},function(data){
        		base.ajaxSuccess(data);
        		F.reload();
        	},function(data){
        		base.bootAlert(data);
        	});
        },reload:function(){
        	F.table.reload();
        },onClick:function(event, treeId, treeNode, clickFlag) {
			F.table.query({query: {'id':treeNode.id}});
		},
        operateFormatter:function (value, row, index) {
        	var _btnAction = '';
        	if (base.perList.menu.checkPermission) {
        		_btnAction += "<a class='checkChargeCode btn btn-info btn-small' href='#' title='查看计费代码' style='margin-left:5px'>查看</a>";
        	}
        	if (base.perList.menu.edit) {
        		_btnAction += "<a data-toggle='modal' class='editChargeCode btn btn-success btn-small' href='#' title='编辑计费代码' style='margin-left:5px'>编辑</a>";
        	}
        	if (base.perList.menu.del) {
        		_btnAction += "<a class='delChargeCode btn btn-danger btn-small' href='#' title='删除计费代码' style='margin-left:5px'>删除</a>";
        	}
        	return _btnAction;
        },urlFormate:function (value, row, index) {
        	if(value){
        		if(value.length>20){
        			return value.substr(0,20) + '...';
        		}else{
        			return value;
        		}
        	}
        },setAddAttr:function (colom,attr,value) {
			$(colom).attr(attr,value);
        },setDelAttr:function (colom,attr) {
    		$(colom).removeAttr(attr);
        }
    };

});
function delDisabledTime(o){
	console.info(o);
	o.parentElement.parentElement.remove();
	
}
