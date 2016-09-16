// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        tree:{},
        checkPermissionTree:{},
        distributePermissionTree:{},
        radioTree:{},
        table:new core.Table('menuTable'),
        init:function(_basepath) {
            F.basepath = _basepath;
           // var picSelect =new core.PicSelect('icon')
            /**
             * 是否具有添加App的权限
             */
            if(base.perList.app.create){
            	$("#menu-header .actions").append("<a href='#' id='addApp' data-toggle='modal' class='btn btn-success btn-small' style='margin-left:5px'><i class='icon-plus'></i>添加</a>");
            }
            /**
             * 是否具有删除APP权限
             */
            if(base.perList.app.del){
            	$("#menu-header .actions").append("<a href='#' id='delApps' class='btn btn-danger btn-small' style='margin-left:5px'><i class='icon-remove'></i>删除</a>");
            }
            
            /**
			 * 请求菜单数据
			 */
	        F.treeLoad();
	        
	        operateEvents = {
					/**
					 *修改App
					 */
			        'click .editApp': function (e, value, row, index) {
			        	core.openModel('modal-Menu','修改APP',function(){
			        		F.radioTree.load();
			        		if(row!=null){
			            		$('#id').val(row.id);
			            		$('#app_name').val(row.app_name);
			            		$('#app_package_name').val(row.app_package_name);
			            		$('#channel').val(row.channel);
			            		$('#date_limit').val(row.date_limit);
			            		$('#month_limit').val(row.month_limit);
			            		$('#link_name').val(row.link_name);
			            		$('#phone_no').val(row.phone_no);
			            		$('#description').val(row.description);
			            	}
			        	});
						return false;
			        },
			        /**
					 * 删除App
					 */
			        'click .delApp': function (e, value, row, index) {
			        	base.bootConfirm("是否确定删除？",function(){
			        		var ids = new Array();  
			        		ids.push(row.id);   
			    			F.delApp(ids);
			    		});
			        },
			        
			        /**
    				 * 菜单授权
    				 */
    		        'click .distributePermission': function (e, value, row, index) {
    		        	core.openModel('modal-DistributePermission','菜单授权',function(){
    		            	if(row!=null){
    		            		$("#menuId").val(row.id)
    		            		F.distributePermissionTree.load({"menuId":row.id},"distributePermissionTreeHidden");
    		            	}
    		        	});
    		        },
    		        /**
    		         * 查看权限
    		         */
    		        'click .checkPermission': function (e, value, row, index) {
    		        	core.openModel('modal-CheckPermission','查看权限',function(){
    		        		if(row!=null){
    		        			F.checkPermissionTree.load({"menuId":row.id});
    		        		}
    		        	});
    		        },
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
		        		        field: 'app_name',
		        		        title: 'APP名称'
		        		    }, {
		        		        field: 'app_package_name',
		        		        title: 'APP包名'
		        		    }, {
		        		        field: 'channel',
		        		        title: '渠道'
		        		    },{
		        		        field: 'date_limit',
		        		        title: '日限量'
		        		    },{
		    			        field: 'month_limit',
		    			        title: '月限量'
		    		        },{
		        		        field: 'link_name',
		        		        title: '联系人'
		        		    },{
		        		        field: 'phone_no',
		        		        title: '联系电话'
		        		    },{
		    			        field: 'description',
		    			        title: '简介',
		    			        events: operateEvents,
		    			        formatter:F.introductionFormatter
		    		        },{
		    			        field: 'create_time',
		    			        title: '创建时间'
		    		        }];
		        //是否需要操作列
		        if(base.perList.menu.edit||base.perList.menu.del||base.perList.menu.grant||base.perList.menu.checkPermission)
			        cols.push({
				    	align: 'center',
				        title: '操作',
				        events: operateEvents,
				        formatter:F.operateFormatter
				    });
	        		
	    		F.table.init(F.basepath+'/main/app/get-list-apps',cols);
	    		
	    		/**
				 * 打开模态框
				 */
				$('#addApp').click(function(){
					core.openModel('modal-Menu','新增APP',function(){
						//F.radioTree.load();
					});
				});
				
				/**
				 * 关闭模态框
				 */
				$('#btnClose').click(function(){
					core.closeModel('modal-Menu');
				});
				
				$('#btnDistributePermissionClose').click(function(){
					core.closeModel('modal-DistributePermission');
				});
				
				$('#btnCheckPermissionClose').click(function(){
					core.closeModel('modal-CheckPermission');
				});
				
				/**
				 * 提交表单
				 */
				$('#btnSubmit').click(function(){
					F.submit();
	            });
				
				$('#btnDistributePermissionSubmit').click(function(){
					F.permissionSubmit();
				});
				
				/**
				 * 批量删除
				 */
				$('#delApps').click(function(){
					var ids = F.table.getIdSelections();
					if(ids!=null&&ids.length>0){
						base.bootConfirm("是否确定删除选定的"+ids.length+"个APP？",function(){
							F.delApp(ids);
						});
					}else{
						base.bootAlert({"ok":false,"msg":"请选择你要删除的APP！"});
					}
				});
        },permissionSubmit:function(){
        	var perids = $("#distributePermissionTreeHidden").val();
        	var peridArray = new Array();
        	if(perids!=null && perids.length>0)
        		peridArray = perids.split(',');
        	var menuId = $("#menuId").val();
        	var data = {"id":menuId,"peridArray":peridArray};
        	var url = F.basepath+'/main/menu/grant';
        	base.ajaxRequest(url,data,function(data, status){
        		 base.bootAlert(data);
                 if (data.ok) {
                 	core.closeModel('modal-DistributePermission');
                 	F.reload();
                 }
        	},function(){
        		alert("异常");
        	});
        },submit:function(){
        	var url = F.basepath+'/main/app/create';
        	if($("#id").val()!=null&&$("#id").val()!="")
        		url =F.basepath+'/main/app/edit';
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
            	core.closeModel('modal-Menu');
            	F.reload();
            }
        },delApp:function(ids){
        	base.ajaxRequest(F.basepath+'/main/app/del',{"appIds":ids},function(data){
        		//base.ajaxSuccess(data);
        		base.bootAlert(data);
        		if (data.ok) {
                 	F.reload();
                }
        	},function(){
        		base.bootAlert({"ok":false,"msg":"网络异常"});
        	});
        },reload:function(){
        	//F.tree.load();
        	F.table.reload();
        },onClick:function(event, treeId, treeNode, clickFlag) {
			F.table.query({query: {'id':treeNode.id}});
		},treeLoad:function(){
//			F.tree = core.initTree("menuTree",F.basepath+'/main/menu/get-all-menus',F.onClick);
//        	F.tree.load();
//        	if (base.perList.app.edit||base.perList.app.create) {
//        		F.radioTree = core.initDropDownRadioTree("parentId",F.basepath+'/main/menu/get-all-menus');
//        	}
//        	if (base.perList.menu.grant) {
//        		F.distributePermissionTree = core.initRadioTree('distributePermissionTree',F.basepath+'/main/get-menu-check-permissions','distributePermissionTreeHidden')
//        	}
//        	if (base.perList.menu.checkPermission) {
//        		F.checkPermissionTree = core.initRadioTree('checkPermissionTree',F.basepath+'/main/get-menu-show-permissions');
//        	}
        },
        operateFormatter:function (value, row, index) {
        	var _btnAction = "";
//        	if (base.perList.menu.grant) {
//        		_btnAction += "<a class='distributePermission btn btn-primary btn-small' href='#' title='菜单授权' style='margin-left:5px'>授权</a>";
//        	}
//        	if (base.perList.menu.checkPermission) {
//        		_btnAction += "<a class='checkPermission btn btn-info btn-small' href='#' title='查看授权' style='margin-left:5px'>查看</a>";
//        	}
        	if (base.perList.app.edit) {
        		_btnAction += "<a data-toggle='modal' class='editApp btn btn-success btn-small' href='#' title='编辑APP' style='margin-left:5px'>编辑</a>";
        	}
        	if (base.perList.app.del) {
        		_btnAction += "<a class='delApp btn btn-danger btn-small' href='#' title='删除APP' style='margin-left:5px'>删除</a>";
        	}
        	return _btnAction;
        },picFormatter:function (value, row, index) {
        	if(row.icon!=null&&row.icon!="")
        		return "<i class='"+row.icon+"'/>";
        	else
        		return "-";
        },introductionFormatter:function (value, row, index) {
        	var _btnAction = "";
        		_btnAction += "<a class='introduction btn btn-info btn-small' href='#' title='查看APP简介' style='margin-left:5px'>查看</a>";
        	return _btnAction;
        }
    };

});
on;
        },picFormatter:function (value, row, index) {
        	if(row.icon!=null&&row.icon!="")
        		return "<i class='"+row.icon+"'/>";
        	else
        		return "-";
        },introductionFormatter:function (value, row, index) {
        	var _btnAction = "";
        		_btnAction += "<a class='introduction btn btn-info btn-small' href='#' title='查看APP简介' style='margin-left:5px'>查看</a>";
        	return _btnAction;
        }
    };

});
