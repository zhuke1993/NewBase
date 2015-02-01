<%@page import="sei.system.Base,sei.core.*"%>
<%@page contentType="text/html;charset=utf-8" session="true" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="css/page.css">
	<script type="text/javascript" src="js/page.js"></script>
	<style>.form form{margin-top: 20px;margin-left: 60px;}.form{width:600 !important}</style>
</head>
<body bgcolor="#FFFFFF" style="width:45%;margin:auto;">
<%
	String UserID= (String)session.getAttribute("UserID"); 
	if(UserID==null){
		out.print("<SCRIPT>top.location.href='/';</SCRIPT>");
		return;
	}else{
		String ID=request.getParameter("ID");
		String OPType=request.getParameter("OPType");if(OPType==null)OPType="";
		Base sys=new Base();
		sys.setMODEL("dept");
		
		String BT="";
		Pv pv=null;
		if(ID==null){//新增
			OPType="Insert";
			pv=sys.LoadData(UserID, ID, Pv.Add);
			if(!pv.PR_ADD){
				out.print("<div><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
				sys.CloseDataBase();
				return;				
			}
			BT="<a href='javascript:void(0)' class='xpc-button xpc-save' onclick='submitForm()'>提交</a>";
		}else{//编辑,查看
			if(OPType.equals("Update")){//编辑
				pv=sys.LoadData(UserID, ID, Pv.Edit);
				if (!pv.PR_EDIT){
					out.print("<div>对不起，权限不够，你无权进行本操作！</p></div>");
					sys.CloseDataBase();
					return;
				}
				BT="<a href='javascript:void(0)' class='xpc-button xpc-save' onclick='submitForm()'>提交</a>";
			}else{//查看
				pv=sys.LoadData(UserID, ID, Pv.Browser);
				if (!pv.PR_BROWSE){
					out.print("<div><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
					sys.CloseDataBase();
					return;
				}
				if (pv.PR_EDIT){BT="<a href='javascript:void(0)' class='xpc-button xpc-edit' onclick='edit()'>修改</a>";}
				if (pv.PR_DEL){BT=BT+"&nbsp;&nbsp;<a href='javascript:void(0)' class='xpc-button xpc-dell' onclick='dell()'>删除</a>";}
			}
		}
%>
<div class="form">
	<div class="header"><h1>信息</h1></div>
	<div class="body">
    <form id="ff" action="/SaveForm.do?Cls=<%=sys.getClass().getName()%>&OPType=<%=OPType%>" method="post" >
<table cellpadding="2">
<%if(ID==null || OPType.equals("Update")){%>
	<tr>
		<td align="right">上级部门：</td>
		<td><span class="combo"><input type="text" id="PARENT_NAME" name="PARENT_NAME" readonly class="main" data-options="{required:true,msg:'请选择上级部门！'}" value="<%=sys.getPARENT_NAME()%>"/><a href="#" class="search" onclick="top.SelectWindow('dept','SELECTWIN','请选择',document.getElementById('PARENT_ID'),document.getElementById('PARENT_NAME'),250,400);"></a></span></td>
	</tr>
	<tr>
		<td align="right">编号：</td>
		<td><%=((OPType.equals("Update"))?"<INPUT type='hidden' NAME='BASE_ID' readonly value=\""+sys.getBASE_ID()+"\">"+sys.getBASE_ID():"<input type='text' name='BASE_ID' class='textbox' data-options=\"{required:true,type:'char',msg:'请输入编号！'}\" value=\""+sys.getBASE_ID()+"\"></input>")%></td>
	</tr>
	<tr>
		<td align="right">排序：</td>
		<td><input type="text" name="BASE_ORDER" class="textbox" value="<%=sys.getBASE_ORDER()%>"/></td>
	</tr>
	<tr>
		<td align="right">名称：</td>
		<td><input type="text" name="BASE_NAME" class="textbox" data-options="{required:true,msg:'请输入名称！'}" value="<%=sys.getBASE_NAME()%>"/></td>
	</tr>
	<tr>
		<td align="right">类型：</td>
		<td><select name="BASE_TYPE1" value="<%=sys.getBASE_TYPE1()%>"><option value="xy"<%=((sys.getBASE_TYPE1().equals("xy"))?" selected":"")%>>学院</option><option value="bm"<%=((sys.getBASE_TYPE1().equals("bm"))?" selected":"")%>>职能部门</option><option value="qt"<%=((sys.getBASE_TYPE1().equals("qt"))?" selected":"")%>>其它</option></select></td>
	</tr>
	<INPUT type="hidden" NAME="BASE_TYPE" readonly value="<%=sys.getMODEL()%>">
	<INPUT type="hidden" ID="PARENT_ID" NAME="PARENT_ID" readonly value="<%=sys.getPARENT_ID()%>">
	<%}else{%>
	<tr>
		<td align="right">上级编号：</td>
		<td><%=sys.getPARENT_NAME()%></td>
	</tr>
	<tr>
		<td align="right">编号：</td>
		<td><%=sys.getBASE_ID()%></td>
	</tr>
	<tr>
		<td align="right">排序：</td>
		<td><%=sys.getBASE_ORDER()%></td>
	</tr>
	<tr>
		<td align="right">名称：</td>
		<td><%=sys.getBASE_NAME()%></td>
	</tr>
	<tr>
		<td align="right">类型：</td>
		<td><%=((sys.getBASE_TYPE1().equals("xy"))?"学院":((sys.getBASE_TYPE1().equals("bm"))?"职能部门":"其它"))%></td>
	</tr>
<%}%>
</table>
<%@ include file="buttom.jsp"%>
<%}%>
</body>
</html>