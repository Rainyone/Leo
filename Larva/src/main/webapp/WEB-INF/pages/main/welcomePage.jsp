<%--
  Created by IntelliJ IDEA.
  User: sxjun
  Date: 15-9-18
  Time: 下午3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <% String str = "${参数}"; 
    	String code_id = "${code_id}";
    	String port1 = "${port}";
    	String msg1 = "${msg}";
    	String type1 = "${type}";
    %>
</head>
<body>
<link  type="text/css" rel="stylesheet" href="${ctxAssets}/thinker-md/thinker-md.vendor.css" />
 <h4>该管理系统分为两部分，一部分为系统管理：系统设置、用户部门、日志记录；一部分为业务管理：基础设置、规则管理、统计分析</h4>
<pre><code class="hljs livecodeserver">
基础设置-APP管理：添加、查看、删除、编辑APP，能够重新获取APP_KEY
基础设置-计费代码管理：添加、查看、删除、编辑计费代码
	字段说明：
		代码名称:即计费代码名称；
		请求URL:即发送请求给运营商的地址，地址中的参数用<%=str %>代替，	程序在运行过程中会根据客户端请求的参数替换地址中需要替换的参数；
		请求内容：如果是post的方式发送请求，则需要提供请求报文，请求报文及此请求内容，内容中如果有参数则和URL中参数一样处理；
		报文发送方式：请求运营商地址所使用的请求方式；分为GET和POST
		接口类型：分为三种
			一、是不需要验证码。即此接口不用客户端再次发起验证码请求，直接反馈相关订购信息给客户端
			二、需要接口反馈验证码，即此接口运营商会反馈订单号，客户端需要再次发起验证码请求，将订单号和验证码一起发送给平台，才能完成一次订购。
			三、需要短信反馈验证码，即客户端第一次请求时，平台会将验证码地址等信息反馈给客户端，客户端自行发送短信完成订购，客户端不用发起验证码请求到平台；
		反馈报文格式：目前只支持JSON
		反馈给客户端报文格式：此报文是平台反馈给客户端的。取值符号里的名称来自于运营商反馈报文格式字段中箭头后面的字符串
			报文样例：{"code_id": "<%=code_id %>", "inf_type": "1", "orderId": "", "port": "<%=port1 %>", "msg": "<%=msg1 %>", "type": "<%=type1 %>"}。
			code_id为计费代码id，系统默认会替换参数。
			inf_type即接口类型，对应上面接口类型字段，依次为1(不需要验证码)、2(需要接口反馈验证码)、3(需要短信反馈验证码)
			orderId为订单id，在接口类型为'需要接口反馈验证码'时需要提供此参数
			port为短信端口。
			msg为短信内容。
			type为短信类型，0-普通短信，1-data短信 默认值1
		运营商反馈报文格式：节点名(s/m):子节点(s/m):末节点->反馈报文参数字段|节点名(s/m):子节点(s/m):末节点->反馈报文参数字段
						  datas(s):data(m):msg(m):port->port,content->msg|datas(s):data(m):isHaveVer->needVerCode
						  s即single为单节点，此节点的内容为对象，不是数组；m即more为多节点，此节点的内容为数组。
						    末节点即最后要取值的节点。反馈报文参数字段即末节点的值会赋值到此字段上。然后替换反馈给客户端报文格式
						    里面的对应参数
			解析运营商反馈的报文并赋值给定义的字段
			如运营商反馈的报文是：{
								    "ok": "true",
								    "msg": "请求成功",
								    "data_list": [
								        {
								            "port-no": "10086",
								            "message": "1",
								            "type": "0"
								        },
								        {
								            "port-no": "10086",
								            "message": "1",
								            "type": "0"
								        }
								    ]
								}
			我们需要解析port-no,message,type这三个字段,则可以写成data_list(m):port-no->port,message->msg,type->type
			
								
</code></pre>
<h4>未完待续...</h4>

</body>
</html>
