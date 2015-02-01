<%@page contentType="text/html;charset=utf-8"%>
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
<%
System.out.print("ddd");
%>
<body marginheight="10">
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-sum" onclick="see()">查看</a>
			类型:<select id="USER_TYPE" class="easyui-combobox" style="width:80px;"><option value="" selected></option><option value="职能部门">职能部门</option><option value="辅导员">辅导员</option><option value="学生">学生</option></select>
			姓名:<input class="easyui-textbox" style="width:80px" ID="USER_NAME">
			部门:<input class="easyui-combotree" ID='DEPT_ID' data-options="url:'/TreeServlet.do?model=dept',method:'get'" style="width:150px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="CSearch">查询</a>
		</div>
	</div>
	<table id="dg" class="easyui-datagrid" style="width:100%;height: 100%" data-options="autoRowHeight:false,pageSize:20,fitColumns:true,striped:true,rownumbers:true,singleSelect:true,pagination:true,url:'/getPage.do?model=user',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'c1',align:'center',width:80">用户类型</th>
				<th data-options="field:'c2',align:'center',width:80">登录名</th>
				<th data-options="field:'c3',align:'center',width:100">姓名</th>
				<th data-options="field:'c4',align:'center',width:120">机构部门</th>
				<th data-options="field:'c5',align:'center',width:80">角色</th>
				<th data-options="field:'c6',align:'center',width:80">职务级别</th>
				<th data-options="field:'c7',align:'center',width:60">是否领导</th>
				<th data-options="field:'c8',align:'center',width:120">联系电话</th>
			</tr>
		</thead>
	</table>

	<script type="text/javascript">
	$('#CSearch').click(function(){
		$('#dg').datagrid({
			queryParams: {
				//USER_TYPE:'=$'+$('#USER_TYPE').combobox('getValue'),
				//USER_NAME:'Like$%'+$("#USER_NAME").val()+'%',
				USER_TYPE:'='+$('#USER_TYPE').combobox('getValue'),
				USER_NAME:'Like %'+$("#USER_NAME").val()+'%',
				DEPT_ID: 'getTreeChilds(\'dept\',1,?)$'+$('#DEPT_ID').combotree('getValue')
			}
		});
	});
	function see(){top.OpenWindow('system/userEdit.jsp?ID='+$("#dg").datagrid("getSelected").c2,'EditWindow','用户信息',600,425);}
	</script>
</body>
</html>