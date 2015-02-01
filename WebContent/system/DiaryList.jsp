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
		Pv pv=sys.getPvPageList("diary", UserID, "DEPT_ID", "USER_ID", Pv.Browser,Pv.Add);
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
			学期:<select id="XQ" class="easyui-combobox" style="width:80px;"><option value="" selected></option><%=students.StuTools.getXQ("")%></select>
			开始时间:<input class="easyui-datebox" style="width:80px" ID="TIME1">
			结束时间:<input class="easyui-datebox" style="width:80px" ID="TIME2">
			部门:<input class="easyui-combotree" ID='DEPT_ID' data-options="url:'/TreeServlet.do?model=dept&model1=xy&id=cqupt',method:'get'" style="width:150px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="CSearch">查询</a>
		</div>
	</div>
    <table id="dg" class="easyui-datagrid" style="width:100%;height: 100%" data-options="autoRowHeight:false,pageSize:20,fitColumns:true,striped:true,rownumbers:true,singleSelect:true,pagination:true,url:'/getPage.do?model=diary',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'c1',hidden:true">编号</th>
				<th data-options="field:'c2',align:'center',width:40">学期</th>
				<th data-options="field:'c3',align:'center',width:80">处理日期</th>
				<th data-options="field:'c4',align:'center',width:100">登记日期</th>
				<th data-options="field:'c5',align:'center',width:80">类型</th>
				<th data-options="field:'c6',align:'center',width:60">姓名</th>
				<th data-options="field:'c7',align:'center',width:100">学院</th>
				<th data-options="field:'c8',align:'center',width:250">内容</th>
			</tr>
		</thead>
	</table>

	<script type="text/javascript">
	$('#CSearch').click(function(){
		$('#dg').datagrid({
			queryParams: {
				XQ:'=$'+$('#XQ').combobox('getValue'),
				TIME:'>=$'+$("#TIME1").datebox('getValue'),
				TIME:'<=$'+$('#TIME2').datebox('getValue')
			}
		});
	});
	function see(){top.OpenWindow('/system/DiaryEdit.jsp?ID='+$("#dg").datagrid("getSelected").c1,'EditWindow','日志信息',600,425);}
	<%if(pv.PR_ADD){%>function add(){top.OpenWindow('/system/DiaryEdit.jsp','EditWindow','日志信息',1000,650);}<%}%>
	</script>
<%sys.CloseDataBase();}%>
</body>
</html>