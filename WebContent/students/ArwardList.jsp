<%@page contentType="text/html;charset=utf-8" session="true" import="sei.core.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="../system/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../system/easyui/themes/icon.css">
	<script type="text/javascript" src="../system/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../system/easyui/jquery.easyui.min.js"></script>
	<script src="../system/easyui/locale/easyui-lang-zh_CN.js"></script>
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
		Pv pv=sys.getPvPageList("arward", UserID, "XY_ID", "XH", Pv.Browser,Pv.Add);
		if(!pv.PR_BROWSE){
			out.print("<div><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
			sys.CloseDataBase();
			return;
		}
		String Type = request.getParameter("Type");if (Type == null)Type= "";
%>
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<%if(pv.PR_ADD){%><a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">新增</a><%}%>
			<a href="#" class="easyui-linkbutton" iconCls="icon-sum" onclick="see()">查看</a>
			类型:<input class="easyui-combotree" ID='ZB_ID' data-options="url:'/TreeServlet.do?model=zb&id=<%=((Type.equals("jl"))?"jfzb":"jjfzb")%>',method:'get'" style="width:120px;">
			姓名:<input class="easyui-textbox" style="width:60px" ID="XM">
			年级:<select id="NJ" class="easyui-combobox" style="width:50px;"><option value="" selected></option><%=students.StuTools.getNJ("")%></select>
			专业:<input class="easyui-combotree" ID='ZY_ID' data-options="url:'/TreeServlet.do?model=zy',method:'get',onSelect:function(node){var tree = $(this).tree;var isLeaf = tree('isLeaf', node.target);if (!isLeaf){$('#ZY_ID').combotree('clear');}}" style="width:120px;">
			学院:<input class="easyui-combotree" ID='XY_ID' data-options="url:'/TreeServlet.do?model=dept&model1=xy&id=cqupt',method:'get'" style="width:100px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="CSearch">查询</a>
		</div>
	</div>
	<table id="dg" class="easyui-datagrid" style="width:100%;height: 100%" data-options="autoRowHeight:false,pageSize:20,fitColumns:true,striped:true,rownumbers:true,singleSelect:true,pagination:true,url:'/getPage.do?model=arward&Type=<%=Type%>',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'c1',hidden:true">状态</th>
				<th data-options="field:'c2',align:'center',width:40">状态</th>
				<th data-options="field:'c3',align:'center',width:30">年级</th>
				<th data-options="field:'c4',align:'center',width:30">学期</th>
				<th data-options="field:'c5',align:'center',width:50">学号</th>
				<th data-options="field:'c6',align:'center',width:40">姓名</th>
				<th data-options="field:'c7',align:'center',width:90">学院</th>
				<th data-options="field:'c8',align:'center',width:60">时间</th>
				<th data-options="field:'c9',align:'center',width:100">奖惩类型</th>
				<th data-options="field:'c10',align:'center',width:40">奖惩分数</th>
				<th data-options="field:'c11',align:'center',width:150">事由</th>
			</tr>
		</thead>
	</table>

	<script type="text/javascript">
	$('#CSearch').click(function(){
		$('#dg').datagrid({
			queryParams: {
				ZB_ID:'=$'+$('#ZB_ID').combotree('getValue'),
				XM:'Like$%'+$("#XM").val()+'%',
				NJ:'=$'+$('#NJ').combobox('getValue'),
				XY_ID:'=$'+$('#XY_ID').combotree('getValue'),
				ZY_ID:'=$'+$('#ZY_ID').combotree('getValue')
			}
		});
	});
	function see(){top.OpenWindow('/students/ArwardEdit.jsp?ID='+$("#dg").datagrid("getSelected").c1,'EditWindow','奖惩信息',700,400);}
	<%if(pv.PR_ADD){%>function add(){top.OpenWindow('/students/ArwardEdit.jsp','EditWindow','奖惩信息',700,400);}<%}%>
	</script>
<%sys.CloseDataBase();}%>
</body>
</html>