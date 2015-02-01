<%@ page contentType="text/html;charset=utf-8" session="true" buffer="none"%>
<%@ page import="sei.core.*,sei.system.*,sei.util.*" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="css/page.css">
	<script type="text/javascript" src="js/page.js"></script>
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
		User sys=new User();
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
      <td align="right" width="10%" class="cx_Title">子系统：</td>
      <td width="40%"><select name="SYS_ID" style="width:100%"><%=Tools.buildListItem(sys,"Select SYS_ID,SYS_NAME from T_SYS_SYSTEM",sys.getSYS_ID(),true)%></select></td>
      <td align=right nowrap width="10%" class="cx_Title">用户类型：</td>
      <td nowrap width="40%"><select name="USER_TYPE" style="width:100%">
      <option value="职能部门" <%if(sys.getUSER_TYPE().equals("职能部门"))out.print("selected");%>>职能部门</option>
      <option value="辅导员" <%if(sys.getUSER_TYPE().equals("辅导员"))out.print("selected");%>>辅导员</option>
      <option value="学生" <%if(sys.getUSER_TYPE().equals("学生"))out.print("selected");%>>学生</option>
      </select></td>
    </tr>          
    <tr>
      <td align="right" class="cx_Title"><font color=red>*登录名</font>：</td>
      <td ><INPUT NAME="USER_ID" class="textbox" style="width:99%" maxlength=20 value="<%=sys.getUSER_ID()%>"></td>
      <td align =right class="cx_Title">移动电话：</td>
      <td><INPUT NAME="USER_MOB_PHONE" class="textbox" style="width:99%" class="textbox" maxlength=20 value="<%=sys.getUSER_MOB_PHONE()%>"></td>
    </tr>
    <tr>
      <td align=right class="cx_Title">用户姓名：</td>
      <td><INPUT NAME="USER_NAME" class="textbox" style="width:99%" maxlength=20 value="<%=sys.getUSER_NAME()%>"></TD>
      <td align=right class="cx_Title">通讯地址：</td>
      <td><INPUT NAME="USER_ADDR" class="textbox" style="width:99%" maxlength=20 value="<%=sys.getUSER_ADDR()%>"></TD>
    </tr>
    <tr valign= middle>
      <td align=right class="cx_Title">密码：</td>
      <td><INPUT NAME="USER_PWD" class="textbox" style="width:99%" maxlength=20 value="<%=sys.getUSER_PWD()%>"></TD> 
      <td align=right class="cx_Title">职务级别：</td>
      <td><INPUT NAME="USER_JOB" class="textbox" style="width:99%" maxlength=20 value="<%=sys.getUSER_JOB()%>"></TD>
    </tr>
    <tr>
      <td align=right class="cx_Title">登录IP：</td>
      <td><INPUT NAME="USER_IP" class="textbox" style="width:99%" maxlength=20 value="<%=sys.getUSER_IP()%>"></TD>
      <td align=right class="cx_Title">电子邮件：</td>
      <td><INPUT NAME="USER_EMAIL" class="textbox" style="width:99%" maxlength=50 value="<%=sys.getUSER_EMAIL()%>"></td>
    </tr>
    <tr>
      <td align=right class="cx_Title">用户角色：</td>
      <td><select name="ROLE_ID" style="width:100%"><%=Tools.buildListItem(sys,"Select BASE_ID,BASE_NAME from T_SYS_BASE WHERE BASE_TYPE='role'",sys.getROLE_ID(),true)%></select></td>
      <td align=right class="cx_Title">用户岗位：</td>
      <td><INPUT NAME="USER_DUTY" class="textbox" style="width:99%" maxlength=20 value="<%=sys.getUSER_DUTY()%>"></TD>
    </tr>
    <tr>
      <td align=right class="cx_Title">组织机构：</td>
      <td><span class="combo" style="width:99%"><input type="text" id="DEPT_NAME" name="DEPT_NAME" readonly class="main" style="width:99%" data-options="{required:true,msg:'请选择组织机构！'}" value="<%=sys.getDEPT_NAME()%>"/><a href="#" class="search" onclick="top.SelectWindow('dept','SELECTWIN','请选择',document.getElementById('DEPT_ID'),document.getElementById('DEPT_NAME'),250,400);"></a></span></TD>
      <td align=right nowrap class="cx_Title">是否领导：</td>
      <td nowrap><select name="USER_LEAD" style="width:100%">
      <option value="是" <%if(sys.getUSER_LEAD().equals("是"))out.print(" selected");%>>是</option>
      <option value="否" <%if(sys.getUSER_LEAD().equals("否"))out.print(" selected");%>>否</option>
      </select></TD>
    </tr>            
    <tr>
      <td align=right nowrap class="cx_Title">用户状态：</td>
      <td><select name="USER_STATUS" style="width:100%">
      <option value="0" <%=((sys.getUSER_STATUS()==0)?" selected":"")%>>停用</option>
      <option value="1" <%=((sys.getUSER_STATUS()==1)?" selected":"")%>>启用</option>
      </select></td>
      <td align=right nowrap class="cx_Title">邮政编码：</td>
      <td><INPUT NAME="USER_POST_CODE" class="textbox" style="width:99%" maxlength=10 value="<%=sys.getUSER_POST_CODE()%>"></TD>
    </tr>
    <tr>
      <td align=right nowrap class="cx_Title">序号：</td>
      <td nowrap><INPUT NAME="USER_ORDER" class="textbox" style="width:99%" maxlength=20 value="<%=sys.getUSER_ORDER()%>"></TD>
      <td align=right nowrap class="cx_Title">家庭电话：</td>
      <td><INPUT NAME="USER_HOME_PHONE" class="textbox" style="width:99%" maxlength=20 value="<%=sys.getUSER_HOME_PHONE()%>"></TD>
    </tr>
    <tr>
      <td align=right nowrap class="cx_Title">密码有效期：</td>
      <td nowrap><INPUT NAME="USER_PSWD_DAYS" class="textbox" style="width:99%" maxlength=5 value="<%=sys.getUSER_PSWD_DAYS()%>"></TD>
      <td align=right nowrap class="cx_Title">上次更改密码日期：</td>
      <td nowrap><%=sys.getUSER_PSWD_DATE()%></TD>
    </tr>
    <tr>
      <td align=right class="cx_Title">备注：<br></td>
      <td colspan="3"><INPUT NAME="USER_MEMO" class="textbox" style="width:99%" maxlength=5 value="<%=sys.getUSER_MEMO()%>"></td>
    </tr>
  <%}else{ %>
     <tr>
      <td align=right align="right" width="10%" class="cx_Title">子系统：</td>
      <td width="40%"><%=Tools.getName(sys,"select SYS_NAME from T_SYS_SYSTEM where SYS_ID='"+sys.getSYS_ID()+"'")%></td>
      <td align=right nowrap width="10%" class="cx_Title">用户类型：</td>
      <td nowrap width="40%"><%=sys.getUSER_TYPE()%></TD>
    </tr>          
    <tr>
      <td align="right" class="cx_Title"><font color=red>*登录名</font>：</td>
      <td><%=sys.getUSER_ID()%></td>
      <td align =right class="cx_Title">移动电话：</td>
      <td><%=sys.getUSER_MOB_PHONE()%></td>
    </tr>
    <tr>
      <td align=right class="cx_Title">用户姓名：</td>
      <td><%=sys.getUSER_NAME()%></TD>
      <td align=right class="cx_Title">通讯地址：</td>
      <td><%=sys.getUSER_ADDR()%></TD>
    </tr>
    <tr valign= middle>
      <td align=right class="cx_Title">密码：</td>
      <td>********</TD> 
      <td align=right class="cx_Title">职务级别：</td>
      <td><%=sys.getUSER_JOB()%></TD>
    </tr>
    <tr>
      <td align=right class="cx_Title">登录IP：</td>
      <td><%=sys.getUSER_IP()%></TD>
      <td align=right class="cx_Title">电子邮件：</td>
      <td><%=sys.getUSER_EMAIL()%></td>
    </tr>
    <tr>
      <td align=right class="cx_Title">用户角色：</td>
      <td><%=sys.getROLE_NAME()%></td>
      <td align=right class="cx_Title">用户岗位：</td>
      <td><%=sys.getUSER_DUTY()%></TD>
    </tr>
    <tr>
      <td align=right class="cx_Title">组织机构：</td>
      <td nowrap><%=sys.getDEPT_NAME()%></TD>
      <td align=right nowrap class="cx_Title">是否领导：</td>
      <td nowrap><%=sys.getUSER_LEAD()%></TD>
    </tr>            
    <tr>
      <td align=right nowrap class="cx_Title">用户状态：</td>
      <td><%=((sys.getUSER_STATUS()==0)?"停用":"启用")%></td>
      <td align=right nowrap class="cx_Title">邮政编码：</td>
      <td><%=sys.getUSER_POST_CODE()%></TD>
    </tr>
    <tr>
      <td align=right nowrap class="cx_Title">序号：</td>
      <td nowrap><%=sys.getUSER_ORDER()%></TD>
      <td align=right nowrap class="cx_Title">家庭电话：</td>
      <td><%=sys.getUSER_HOME_PHONE()%></TD>
    </tr>
    <tr>
      <td align=right nowrap class="cx_Title">密码有效期：</td>
      <td nowrap><%=sys.getUSER_PSWD_DAYS()%></TD>
      <td align=right nowrap class="cx_Title">上次更改密码日期：</td>
      <td nowrap><%=sys.getUSER_PSWD_DATE()%></TD>
    </tr>
    <tr>
      <td align=right class="cx_Title">备注：<br></td>
      <td colspan="3"><pre><%=sys.getUSER_MEMO()%></pre></td>
    </tr>            
<%}%></table>
<INPUT type="hidden" id="DEPT_ID" name="DEPT_ID" readonly value="<%=sys.getDEPT_ID()%>">
    </form>
    <div style="text-align:center;padding:5px;height:27px;margin:5px" id="cc">
    	<%=BT%>	
    </div>
<script>
	function submitForm(){
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
//					window.document.body.innerHTML=msg;
				}
			});
	    }
	}
</script>
<%sys.CloseDataBase();}%>
</body></html>