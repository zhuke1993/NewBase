<%@ page contentType="text/html;charset=utf-8" session="true" buffer="none"%>
<%@ page import="sei.core.*,sei.system.*,sei.util.*" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="css/page.css">
	<script type="text/javascript" src="js/page.js"></script>
	<script language="JavaScript" type="text/JavaScript" src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<%
	String UserID= (String)session.getAttribute("UserID"); 
	if(UserID==null){
		out.print("<SCRIPT>top.location.href='/';</SCRIPT>");
		return;
	}else{
		String ID=request.getParameter("ID");
		String OPType=request.getParameter("OPType");if(OPType==null)OPType="";
		Diary sys=new Diary();
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
			out.print("<div><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
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
<form id="ff" class="form" action="/SaveForm.do?Cls=<%=sys.getClass().getName()%>&OPType=<%=OPType%>" method="post" >
<table align=center class="table_list">
<%if(ID==null || OPType.equals("Update")){%>
	<tr>
		<td style="width:50px" align=right class="cx_Title">学期：</td>
		<td width="224">&nbsp;<%=sys.getCurrXQ()%></td>
		<td width="50" align=right class="cx_Title">发生日期：</td>
		<td width="224"><INPUT style="WIDTH: 98%" name="TIME" class="textbox" value="<%=sys.getTIME()%>" data-options="{required:true,msg:'请选择日期！'}" onfocus="WdatePicker()" onKeyPress='return event.keyCode>=48&&event.keyCode<=57||event.keyCode==45'></td>
	</tr>
	<tr>
		<td align=right class="cx_Title">姓名：</td>
		<td>&nbsp;<%=Tools.getName(sys,"select USER_NAME FROM T_SYS_USER WHERE USER_ID='"+UserID+"'")%></td>
		<td align=right class="cx_Title">类型：</td>
        <td><span class="combo"><input type="text" id="TYPE_NAME" name="TYPE_NAME" readonly class="main" data-options="{required:true,msg:'请选择类型！'}" value="<%=sys.getTYPE_NAME()%>"/><a href="#" class="search" onclick="top.SelectWindow('rizhi','SELECTWIN','请选择',document.getElementById('TYPE_ID'),document.getElementById('TYPE_NAME'),250,400);"></a></span></TD>
	</tr>
	<tr>
		<td align=right class="cx_Title">部门：</td>
		<td>&nbsp;<%=Tools.getName(sys,"select DEPT_NAME FROM T_SYS_USER WHERE USER_ID='"+UserID+"'")%></td>
		<td align=right class="cx_Title">登记时间：</td>
		<td>&nbsp;<%=Tools.getDateTime("yyyy-MM-dd HH:mm:ss")%></td>
	</tr>
	</table>
	<script type="text/javascript" src="ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="ueditor/ueditor.all.min.js"></script>
	<script id="container" name="content" type="text/plain" style="width:99%;height:70%;">
	<%=sys.getCONTENT()%>
	</script>
	<script type="text/javascript">
		var ue = UE.getEditor('container');
	</script>
	<textarea name="CONTENT" ID="CONTENT" style="display:none"></textarea>
	<INPUT type="hidden" NAME="XQ" readonly value="<%=sys.getCurrXQ()%>">
	<INPUT type="hidden" NAME="TYPE_ID" readonly value="<%=sys.getTYPE_ID()%>">
	<%} else {%>
	<tr>
		<td width="50" class="cx_Title" align=right>学期：</td>
		<td width="224">&nbsp;<%=sys.getXQ()%></td>
		<td width="50" align=right class="cx_Title">发生日期：</td>
		<td width="224"><%=sys.getTIME()%></td>
	</tr>
	<tr>
		<td align=right class="cx_Title">姓名：</td>
		<td>&nbsp;<%=sys.getUSER_NAME()%></td>
		<td align=right class="cx_Title">类型：</td>
        <td><%=sys.getTYPE_NAME()%></TD>
	</tr>
	<tr>
		<td align=right class="cx_Title">学院：</td>
		<td>&nbsp;<%=sys.getDEPT_NAME()%></td>
		<td align=right class="cx_Title">登记时间：</td>
		<td>&nbsp;<%=sys.getPUSER_TIME()%></td>
	</tr>
	</table>
	<div style="margin:0 auto;text-align:left;">
	<%=sys.getCONTENT()%>
	</div>
<%}%>
    </form>
    <div style="text-align:center;padding:5px;height:27px;margin:5px" id="cc">
    	<%=BT%>	
    </div>
<script>
	function submitForm(){
		document.getElementById('CONTENT').value =ue.getContent();
		if (document.getElementById('CONTENT').value == "") { alert("日志内容不能为空,请输入日志内容！");return; }
		if(validate.val())ff.submit();
	}
	function edit(){window.location.href="<%=request.getRequestURI()%>?OPType=Update&ID=<%=ID%>";}
	function dell(){
		var myFlag = window.confirm("删除后将不能恢复，真的要删除吗？");
	    if(myFlag){
			$$.ajax({
				url : "/SaveForm.do?Cls=<%=sys.getClass().getName()%>&model=<%=sys.getMODEL()%>&OPType=Delete&ID=<%=ID%>",
				params: "",
				dataType:"text",
				success: function (msg){
					if(msg==1){
						alert("删除成功！");
						window.parent.location.reload();
					}else if(msg==-1){
						alert("由于系统或网络错误，无法删除该记录，请稍后再试！");
					}else if(msg==-2){
						alert("由于系统未登陆或长时间未操作，导致系统等待超时，系统或网络错误，无法删除该记录，请稍后再试！");
					}else if(msg==-3){
						alert("由于权限不够，你不能删除该记录！");
					}
				}
			});
	    }
	}
</script>
<%sys.CloseDataBase();}%>
</body></html>