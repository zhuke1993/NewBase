<%@page contentType="text/html;charset=utf-8" session="true" import="sei.core.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<style>html,body{height: 100%;}body {margin: 2px;overflow: hidden;}</style>
</head>
<body marginheight="10">
<%
	String UserID= (String)session.getAttribute("UserID"); 
	if(UserID==null){
		out.print("<SCRIPT>top.location.href='/';</SCRIPT>");
		return;
	}else{
		Privilege sys=new Privilege();
		Pv pv=sys.getPvPageList("log", UserID, "", "USER_ID", Pv.Browser);
		if(!pv.PR_BROWSE){
			out.print("<div><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
			sys.CloseDataBase();
			return;
		}
%>
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			类型:<select id="doid" class="easyui-combobox" style="width:80px;"><option value="" selected></option><option value="1">登陆</option><option value="3">修改密码</option><option value="5">新增</option><option value="6">修改</option><option value="7">删除</option></select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="CSearch">查询</a>
		</div>
	</div>
	<table id="dg" class="easyui-datagrid" style="width:100%;height: 100%" data-options="autoRowHeight:false,pageSize:20,fitColumns:true,striped:true,rownumbers:true,singleSelect:true,pagination:true,url:'/getPage.do?model=log',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'c1',align:'center',width:80">记录编号</th>
				<th data-options="field:'c2',align:'center',width:80">模块编号</th>
				<th data-options="field:'c3',align:'center',width:100">操作类型</th>
				<th data-options="field:'c4',align:'center',width:120">用户编号</th>
				<th data-options="field:'c5',align:'center',width:80">操作时间</th>
				<th data-options="field:'c6',align:'center',width:80">备注</th>
			</tr>
		</thead>
	</table>

	<script type="text/javascript">
	$('#CSearch').click(function(){
		$('#dg').datagrid({
			queryParams: {
				doid:'=$'+$('#doid').combobox('getValue'),
			}
		});
	});
	</script>
<%sys.CloseDataBase();}%>
</body>
</html>