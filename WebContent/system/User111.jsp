<%@ page contentType="text/html;charset=utf-8" session="true" buffer="none"%>
<%@ page import="sei.core.*" %>
<jsp:useBean id="sys" class="sei.system.User" scope="page"/>
<html><Head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../commonV1/popwin.css">
<script language=javascript src="../commonV1/com.js"></script>
</Head>
<body>
<%
	String UserID= (String)session.getAttribute("UserID"); 
	if(UserID==null){
		out.print("<SCRIPT>alert('由于你长时间没有操作,已经超过系统等待时限，请重新登陆后再操作！');top.location.href='/';</SCRIPT>");
	}else{

	String Type=request.getParameter("Type");if(Type==null)Type="";
	String ID=request.getParameter("ID");if(ID==null)ID="";
	PvPageEdit pv=sys.getPvPageEdit(UserID, "user", "T_SYS_USER", "USER_ID='"+ID+"'",Type,"DEPT_ID","USER_ID");
	if (pv.PR_BROWSE==0){
		out.print("<SCRIPT>location.href='../common/NoPurview.jsp';</SCRIPT>");
	}else{
		String BT = "";
		int i = -3;
		if (Type.equals("")){
			if(pv.PR_ADD==0){//无权限
				out.print("<SCRIPT>alert('权限不够,不能新增!');history.go(-1);</SCRIPT>");
				sys.CloseDataBase();
				return;
			}else{
				Type="Insert";
				BT = "<INPUT onclick='save();' type='button' value=' 提交 '>";
			}
		}else {
			if (Type.equals("See")|Type.equals("Edit")){
				if(Type.equals("Edit")){
					Type="Update";
					BT = "<INPUT onclick='save();' type='button' value=' 提交 '>";
				}else{
					if (pv.PR_EDIT==1)BT = "&nbsp;&nbsp;&nbsp;&nbsp;<input type='button' value=' 编辑 ' onclick='edit()' />";
				}
				sys.LoadData("select * from T_SYS_USER where USER_ID='"+ID+"'");
			}else {
				if (Type.equals("Update")){//判断是否修改状态	
					if(pv.PR_EDIT==1){
						%> <jsp:setProperty name="sys" property="*"/> <%
						i=sys.Update(UserID);
					}else i=-3;
				}else if (Type.equals("Insert")){//判断是否修改状态
					if(pv.PR_ADD==1){
						%> <jsp:setProperty name="sys" property="*"/> <%
						i=sys.Insert(UserID);
					}else i=-3;
				}
				if (i==-1){out.print("<SCRIPT>alert('保存错误,请检查你的输入值!');history.go(-1);</SCRIPT>");
				}else if (i==-2){out.print("<SCRIPT>alert('输入的关键字重复!');history.go(-1);</SCRIPT>");
				}else if(i==-3){out.print("<SCRIPT>alert('你的权限不够,不能保存数据!');history.go(-1);</SCRIPT>");
				}else{out.print("<SCRIPT>alert('保存成功!');window.parent.parent.closeAdd('DivAddEdit');</SCRIPT>");}
				sys.CloseDataBase();
				return;
			}
		}
%>	
<SCRIPT Language = javascript>
var IsSubmit=0;
function edit(){window.location="UserEdit.jsp?Type=Edit&ID=<%=ID%>";}
function save(){	
	if (IsSubmit==1){alert('命令已经提交，系统正在运行，请稍等！');return;}
	if(frm.DEPT_ID.value==""){window.alert("请选择该人员所属组织结构!"); return  false;}
  	if(frm.USER_ID.value==""){window.alert("您必须输入登录名!"); return  false;}
  	if(frm.USER_NAME.value==""){window.alert("您必须输入用户姓名!"); return  false;}
	IsSubmit=1;
  	document.frm.submit();
}
function SelctOneDept(field1,field2) {
	popwin("../common/SelectTreeOneDept.jsp?Page=Base.jsp&MODE=dept&LastLevel=0",'请选择',410,310,field1,field2);
}
function popwin(url,title,width,height,field1,field2){top.Add(url,'auto',window,'SelectValue',title,width,height,false,field1,field2);}
</SCRIPT>
<form name="frm" action="UserEdit.jsp?Type=<%=Type%>&ID=<%=ID%>" method="POST">		
<table align=center class="table_list">
        <% if(!Type.equals("See")){%>
             <tr>
              <td align="right" width="10%" class="cx_Title">子系统：</td>
              <td width="40%"><%=Tools.buildList(sys.stmt,sys.rs,"SYS_ID","Select SYS_ID,SYS_NAME from T_SYS_SYSTEM",sys.getSYS_ID(),"")%></td>
              <td align=right nowrap width="10%" class="cx_Title">用户类型：</td>
              <td nowrap width="40%"><select name="USER_TYPE">
              <option value="职能部门" <%if(sys.getUSER_TYPE().equals("职能部门"))out.print("selected");%>>职能部门</option>
              <option value="辅导员" <%if(sys.getUSER_TYPE().equals("辅导员"))out.print("selected");%>>辅导员</option>
              <option value="学生" <%if(sys.getUSER_TYPE().equals("学生"))out.print("selected");%>>学生</option>
              </select></td>
            </tr>          
            <tr valign=middle>
              <td align="right" class="cx_Title"><font color=red>*登录名</font>：</td>
              <td ><INPUT NAME="USER_ID" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_ID()%>"></td>
              <td align =right class="cx_Title">移动电话：</td>
              <td><INPUT NAME="USER_MOB_PHONE" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_MOB_PHONE()%>"></td>
            </tr>
            <tr valign=middle>
              <td align=right class="cx_Title">用户姓名：</td>
              <td><INPUT NAME="USER_NAME" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_NAME()%>"></TD>
              <td align=right class="cx_Title">通讯地址：</td>
              <td><INPUT NAME="USER_ADDR" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_ADDR()%>"></TD>
            </tr>
            <tr valign= middle>
              <td align=right class="cx_Title">密码：</td>
              <td><INPUT NAME="USER_PWD" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_PWD()%>"></TD> 
              <td align=right class="cx_Title">职务级别：</td>
              <td><INPUT NAME="USER_JOB" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_JOB()%>"></TD>
            </tr>
            <tr valign=middle>
              <td align=right class="cx_Title">登录IP：</td>
              <td><INPUT NAME="USER_IP" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_IP()%>"></TD>
              <td align=right class="cx_Title">电子邮件：</td>
              <td><INPUT NAME="USER_EMAIL" STYLE="width:100%" maxlength=50 value="<%=sys.getUSER_EMAIL()%>"></td>
            </tr>
            <tr valign=middle>
              <td align=right class="cx_Title">用户角色：</td>
              <td><%=Tools.buildList(sys.stmt,sys.rs,"ROLE_ID","Select BASE_ID,BASE_NAME from T_SYS_BASE WHERE BASE_TYPE='role'",sys.getROLE_ID(),"")%></td>
              <td align=right class="cx_Title">用户岗位：</td>
              <td><INPUT NAME="USER_DUTY" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_DUTY()%>"></TD>
            </tr>
            <tr valign=middle>
              <td align=right class="cx_Title">组织机构：</td>
              <td nowrap><INPUT NAME="DEPT_NAME" STYLE="width=216; height:19" maxlength=20 value="<%=sys.getDEPT_NAME()%>" readonly> <input type="button" value="选择" name="sel" onClick="SelctOneDept(document.frm.DEPT_ID,document.frm.DEPT_NAME)"></TD>
              <td align=right nowrap class="cx_Title">是否领导：</td>
              <td nowrap><select name="USER_LEAD">
              <option value="是" <%if(sys.getUSER_LEAD().equals("是"))out.print(" selected");%>>是</option>
              <option value="否" <%if(sys.getUSER_LEAD().equals("否"))out.print(" selected");%>>否</option>
              </select></TD>
            </tr>            
            <tr valign=middle>
              <td align=right nowrap class="cx_Title">用户状态：</td>
              <td><select name="USER_STATUS">
              <option value="0" <%if(sys.getUSER_STATUS().equals("0"))out.print(" selected");%>>停用</option>
              <option value="1" <%if(sys.getUSER_STATUS().equals("1"))out.print(" selected");%>>启用</option>
              </select></td>
              <td align=right nowrap class="cx_Title">邮政编码：</td>
              <td><INPUT NAME="USER_POST_CODE" STYLE="width:100%" maxlength=10 value="<%=sys.getUSER_POST_CODE()%>"></TD>
            </tr>
            <tr valign=middle>
              <td align=right nowrap class="cx_Title">序号：</td>
              <td nowrap><INPUT NAME="USER_ORDER" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_ORDER()%>"></TD>
              <td align=right nowrap class="cx_Title">家庭电话：</td>
              <td><INPUT NAME="USER_HOME_PHONE" STYLE="width:100%" maxlength=20 value="<%=sys.getUSER_HOME_PHONE()%>"></TD>
            </tr>
            <tr valign=middle>
              <td align=right nowrap class="cx_Title">密码有效期：</td>
              <td nowrap><INPUT NAME="USER_PSWD_DAYS" STYLE="width:100%" maxlength=5 value="<%=sys.getUSER_PSWD_DAYS()%>"></TD>
              <td align=right nowrap class="cx_Title">上次更改密码日期：</td>
              <td nowrap><%=sys.getUSER_PSWD_DATE()%></TD>
            </tr>
            <tr valign=top>
              <td align=right class="cx_Title">备注：<br></td>
              <td colspan="3"><textarea id="USER_MEMO" name="USER_MEMO" rows="4" style="width:100%" ><%=sys.getUSER_MEMO()%></textarea></td>
            </tr>
          <%}else{ %>
             <tr valign=middle>
              <td align=right align="right" width="10%" class="cx_Title">子系统：</td>
              <td width="40%"><%=Tools.getName(sys.stmt,sys.rs,"select SYS_NAME from T_SYS_SYSTEM where SYS_ID='"+sys.getSYS_ID()+"'")%></td>
              <td align=right nowrap width="10%" class="cx_Title">用户类型：</td>
              <td nowrap width="40%"><%=sys.getUSER_TYPE()%></TD>
            </tr>          
            <tr valign=middle>
              <td align="right" class="cx_Title"><font color=red>*登录名</font>：</td>
              <td><%=sys.getUSER_ID()%></td>
              <td align =right class="cx_Title">移动电话：</td>
              <td><%=sys.getUSER_MOB_PHONE()%></td>
            </tr>
            <tr valign=middle>
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
            <tr valign=middle>
              <td align=right class="cx_Title">登录IP：</td>
              <td><%=sys.getUSER_IP()%></TD>
              <td align=right class="cx_Title">电子邮件：</td>
              <td><%=sys.getUSER_EMAIL()%></td>
            </tr>
            <tr valign=middle>
              <td align=right class="cx_Title">用户角色：</td>
              <td><%=sys.getROLE_NAME()%></td>
              <td align=right class="cx_Title">用户岗位：</td>
              <td><%=sys.getUSER_DUTY()%></TD>
            </tr>
            <tr valign=middle>
              <td align=right class="cx_Title">组织机构：</td>
              <td nowrap><%=sys.getDEPT_NAME()%></TD>
              <td align=right nowrap class="cx_Title">是否领导：</td>
              <td nowrap><%=((sys.getUSER_LEAD().equals("1"))?"是":"否")%></TD>
            </tr>            
            <tr valign=middle>
              <td align=right nowrap class="cx_Title">用户状态：</td>
              <td><%=((sys.getUSER_STATUS().equals("0"))?"停用":"启用")%></td>
              <td align=right nowrap class="cx_Title">邮政编码：</td>
              <td><%=sys.getUSER_POST_CODE()%></TD>
            </tr>
            <tr valign=middle>
              <td align=right nowrap class="cx_Title">序号：</td>
              <td nowrap><%=sys.getUSER_ORDER()%></TD>
              <td align=right nowrap class="cx_Title">家庭电话：</td>
              <td><%=sys.getUSER_HOME_PHONE()%></TD>
            </tr>
            <tr valign=middle>
              <td align=right nowrap class="cx_Title">密码有效期：</td>
              <td nowrap><%=sys.getUSER_PSWD_DAYS()%></TD>
              <td align=right nowrap class="cx_Title">上次更改密码日期：</td>
              <td nowrap><%=sys.getUSER_PSWD_DATE()%></TD>
            </tr>
            <tr valign=top>
              <td align=right class="cx_Title">备注：<br></td>
              <td colspan="3"><pre><%=sys.getUSER_MEMO()%></pre></td>
            </tr>            
          <%}%>
		</table>
</table>
<%=BT%>&nbsp;&nbsp;&nbsp;&nbsp;<INPUT onclick="javascript:window.parent.parent.closeAdd('DivAddEdit');" type='button' value=' 关闭 '>
<INPUT type="hidden" NAME="DEPT_ID" readonly value="<%=sys.getDEPT_ID()%>">
</form></body>
<%}}sys.CloseDataBase();%>
</html>