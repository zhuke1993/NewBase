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
		Pv pv=sys.getPvPageList("xsxx", UserID, "XY_ID", "XH", Pv.Browser,Pv.Add);
		if(!pv.PR_BROWSE){
			out.print("<div><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
			sys.CloseDataBase();
			return;
		}
%>
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<%if(pv.PR_ADD){%><a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">新增</a><%}%>
			<a href="#" class="easyui-linkbutton" iconCls="icon-sum" onclick="see()">查看</a>
			姓名:<input class="easyui-textbox" style="width:80px" ID="XM">
			政治面貌:<input class="easyui-combotree" ID='ZZMM' data-options="url:'/TreeServlet.do?model=zzmm',method:'get'" style="width:100px;">
			年级:<select id="NJ" class="easyui-combobox" style="width:60px;"><option value="" selected></option><%=students.StuTools.getNJ("")%></select>
			专业:<input class="easyui-combotree" ID='ZY_ID' data-options="url:'/TreeServlet.do?model=zy',method:'get',onSelect:function(node){var tree = $(this).tree;var isLeaf = tree('isLeaf', node.target);if (!isLeaf){$('#ZY_ID').combotree('clear');}}" style="width:200px;">
			学院:<input class="easyui-combotree" ID='XY_ID' data-options="url:'/TreeServlet.do?model=dept&model1=xy&id=cqupt',method:'get'" style="width:150px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="CSearch">查询</a>
		</div>
	</div>
	<table id="dg" class="easyui-datagrid" style="width:100%;height: 100%" data-options="autoRowHeight:false,pageSize:20,fitColumns:true,striped:true,rownumbers:true,singleSelect:true,pagination:true,url:'/getPage.do?model=xsxx',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'c1',align:'center',width:60">学号</th>
				<th data-options="field:'c2',align:'center',width:60">姓名</th>
				<th data-options="field:'c3',align:'center',width:40">性别</th>
				<th data-options="field:'c4',align:'center',width:80">政治面貌</th>
				<th data-options="field:'c5',align:'center',width:60">辅导员</th>
				<th data-options="field:'c6',align:'center',width:60">班级</th>
				<th data-options="field:'c7',align:'center',width:60">年级</th>
				<th data-options="field:'c8',align:'center',width:150">专业</th>
				<th data-options="field:'c9',align:'center',width:150">学院</th>
			</tr>
		</thead>
	</table>

	<script type="text/javascript">
	$('#CSearch').click(function(){
		$('#dg').datagrid({
			queryParams: {
				ZZMM:'=$'+$('#ZZMM').combotree('getValue'),
				XM:'Like$%'+$("#XM").val()+'%',
				NJ:'=$'+$('#NJ').combobox('getValue'),
				XY_ID:'=$'+$('#XY_ID').combotree('getValue'),
				ZY_ID:'=$'+$('#ZY_ID').combotree('getValue')
			}
		});
	});
	function see(){top.OpenWindow('/students/StudentsBaseEdit.jsp?ID='+$("#dg").datagrid("getSelected").c1,'EditWindow','学生信息',700,580);}
	<%if(pv.PR_ADD){%>function add(){top.OpenWindow('/students/StudentsBaseEdit.jsp','EditWindow','学生信息',700,580);}<%}%>
	</script>
<%sys.CloseDataBase();}%>
</body>
</html>