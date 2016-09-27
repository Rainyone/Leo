// 所有模块都通过 define 来定义
define(function (require, exports, module) {
    var base = require('base');
    var core = require('core');
    // 通过 require 引入依赖
    var F = module.exports = {
        basepath: '',
        init:function(_basepath) {
            F.basepath = _basepath;
           // var picSelect =new core.PicSelect('icon')
			/**
			 * 获取URL
			 */
    		$('#getResult').click(function(){
    			var url = F.basepath+'/main/app/app_test/get-url';
    			var app_id = $('#app_id').val();
    			if(app_id==''){F.baseAlert('app_id 不能为空');return;}
    			var app_key = $('#app_key').val();
    			if(app_key==''){F.baseAlert('app_key 不能为空');return;}
    			var request_type = $('#request_type').val();
    			if(request_type==''){F.baseAlert('request_type 不能为空');return;}
    			var channel = $('#channel').val();
    			var price = $('#price').val();
    			var imei = $('#imei').val();
    			if(imei==''){F.baseAlert('imei 不能为空');return;}
    			var imsi = $('#imsi').val();
    			if(imsi==''){F.baseAlert('imsi 不能为空');return;}
    			var bsc_cid = $('#bsc_cid').val();
    			var bsc_lac = $('#bsc_lac').val();
    			var mobile = $('#mobile').val();
    			var iccid = $('#iccid').val();
    			var mac = $('#mac').val();
    			var cpparm = $('#cpparm').val();
    			var fmt = $('#fmt').val();
    			if(fmt==''){F.baseAlert('fmt 不能为空');return;}
    			var isp = $('#isp').val();
    			var code_id = $('#code_id').val();
    			var order_id = $('#order_id').val();
    			var ver_code = $('#ver_code').val();
    			var data = {app_id:app_id,app_key:app_key,request_type:request_type,channel:channel,
    					price:price,imei:imei,imsi:imsi,bsc_lac:bsc_lac,bsc_lac:bsc_lac,mobile:mobile,
    					iccid:iccid,mac:mac,cpparm:cpparm,fmt:fmt,isp:isp,code_id:code_id,order_id:order_id
    					}
    			base.ajaxRequest(url,data,function(data, status){
                    if (data.ok) {
                    	F.showResponse(data);
	                 }
	           	},function(){
	           		alert("异常");
	           	});
    		});
        },showResponse:function(data, status){
            if (data.ok) {
            	$('#result').val(data.data);
            }
        },baseAlert:function(msg){
        	 bootbox.dialog(msg, [
	              {
	                  label: "确定",
	                  "class": "btn-success"
	              }
           ]);
        }
    };
});
