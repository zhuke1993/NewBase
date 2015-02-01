<%@ page contentType="text/html;charset=utf-8" session="true" buffer="none"%>
<%@ page import="sei.core.*,sei.util.Tools" %>
<HTML><HEAD>
<TITLE></TITLE><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language=javascript src="js/com.js"></script>
<link rel="stylesheet" type="text/css" href="css/table.css">
</HEAD>
<script language="JavaScript"> 
function popwin(url){window.parent.parent.Add(url,'auto',window,'DivAddEdit','权限设置',600,240,false)}
function change(){if(document.frm.ROLE_ID.value==""){alert("请选择一个角色！");return;}location.href="PrivilegeList.jsp?ROLE_ID="+document.frm.ROLE_ID.value+"&SYS="+document.frm.SYS.value;}
</script>
<%
	String UserID= (String)session.getAttribute("UserID"); 
	if(UserID==null){
		out.print("<SCRIPT>top.location.href='../login.jsp';</SCRIPT>");
	}else{
		sei.system.Privg sys=new sei.system.Privg();
        Pv pv=sys.getPvPageList(sys.getMODEL(), UserID, "", "PUSER_ID", Pv.Browser,Pv.Add);
		if (!pv.PR_BROWSE){
            out.print("<SCRIPT>window.location.href='nopv.html';</SCRIPT>");
		}else{
            String SQL=request.getParameter("SQL");if (SQL==null)SQL="";
            String SelectValue=request.getParameter("ROLE_ID");if(SelectValue==null)SelectValue="";
            String SYS=request.getParameter("SYS");if(SYS==null)SYS="";
%>
<BODY>
<form name="frm" method="POST">
	<table align=center class="tableHead">
		<tr>
			<td width="40" class="cx_Title" align=right>系统:</td>
			<td width="60"><select name='SYS' onchange="javascript:change()"><option value='' selected>请选择</option><%=Tools.buildListItem(sys,"Select SYS_ID,SYS_NAME from T_sys_SYSTEM",SYS,false)%></select></td>
			<td width="40" class="cx_Title" align=right>角色:</td>
			<td width="60"><select name='ROLE_ID' onchange="javascript:change()"><option value='' selected>请选择</option><%=Tools.buildListItem(sys,"Select BASE_ID,BASE_NAME from T_sys_BASE WHERE BASE_TYPE='role'",SelectValue,false)%></select></td>
		</tr>
	</table>
	<table align=center class="table_list">
    <tr ><td width="30" class='Back_Td'>选中</td><td class='Back_Td'>所属系统</td><%if(SelectValue.equals("*")){%><td class='Back_Td'>角色名称</td><%}%><td class='Back_Td'>模块编号</td><td class='Back_Td'>模块名称</td><td class='Back_Td'>查看权限</td><td class='Back_Td'>新增权限</td><td class='Back_Td'>编辑权限</td><td class='Back_Td'>删除权限</td><td class='Back_Td'>定制权限</td></tr>
	<% String str="";
	if(SelectValue.equals("")&&sys.equals("")){
		str="";
	}else{
		if(!SelectValue.equals(""))SQL="(ROLE_ID='"+SelectValue+"')";
		//if (Base.Ver==0)
		//	str="SELECT (SELECT SYS_NAME FROM t_sys_system WHERE t_sys_system.SYS_ID=T_SYS_OBJ.SYS_ID) AS SYS_ID,T_SYS_PRIVILEGE.PR_ID,T_SYS_OBJ.OBJ_ID, T_SYS_OBJ.OBJ_NAME, T_SYS_PRIVILEGE.PR_BROWSE,T_SYS_PRIVILEGE.PR_ADD, T_SYS_PRIVILEGE.PR_EDIT, T_SYS_PRIVILEGE.PR_DEL,T_SYS_PRIVILEGE.PR_AUDIT FROM T_SYS_OBJ,T_SYS_PRIVILEGE WHERE T_SYS_OBJ.OBJ_ID = T_SYS_PRIVILEGE.OBJ_ID AND "+SQL+Tools.getAddSQL("AND",pv.PR_BROWSE_SCOPE)+" UNION SELECT (SELECT SYS_NAME FROM t_sys_system WHERE t_sys_system.SYS_ID=T_SYS_OBJ.SYS_ID) AS SYS_ID,'{00000000-0000-0000-0000-000000000000}',OBJ_ID, OBJ_NAME, '0', '0', '0', '0', '0' FROM T_SYS_OBJ WHERE OBJ_ID NOT IN (SELECT OBJ_ID FROM T_SYS_PRIVILEGE WHERE "+SQL+")";  //SQL server版本
		//else if (Base.Ver==1)
		//	str="SELECT (SELECT SYS_NAME FROM t_sys_system WHERE t_sys_system.SYS_ID=T_SYS_OBJ.SYS_ID) AS SYS_ID,T_SYS_PRIVILEGE.PR_ID,T_SYS_OBJ.OBJ_ID, T_SYS_OBJ.OBJ_NAME, T_SYS_PRIVILEGE.PR_BROWSE,T_SYS_PRIVILEGE.PR_ADD, T_SYS_PRIVILEGE.PR_EDIT, T_SYS_PRIVILEGE.PR_DEL,T_SYS_PRIVILEGE.PR_AUDIT FROM T_SYS_OBJ,T_SYS_PRIVILEGE WHERE T_SYS_OBJ.OBJ_ID = T_SYS_PRIVILEGE.OBJ_ID AND "+SQL+Tools.getAddSQL("AND",pv.PR_BROWSE_SCOPE)+" UNION SELECT (SELECT SYS_NAME FROM t_sys_system WHERE t_sys_system.SYS_ID=T_SYS_OBJ.SYS_ID) AS SYS_ID,'0-0',OBJ_ID, OBJ_NAME,0,0,0,0,0 FROM T_SYS_OBJ WHERE OBJ_ID NOT IN (SELECT OBJ_ID FROM T_SYS_PRIVILEGE WHERE "+SQL+")";  //Oracle版本
		// else
			str="SELECT (SELECT SYS_NAME FROM T_SYS_SYSTEM WHERE t_sys_system.SYS_ID=T_SYS_OBJ.SYS_ID) AS SYS_ID,T_SYS_PRIVILEGE.PR_ID,T_SYS_OBJ.OBJ_ID, T_SYS_OBJ.OBJ_NAME, T_SYS_PRIVILEGE.PR_BROWSE,T_SYS_PRIVILEGE.PR_ADD, T_SYS_PRIVILEGE.PR_EDIT, T_SYS_PRIVILEGE.PR_DEL,T_SYS_PRIVILEGE.PR_AUDIT FROM T_SYS_OBJ,T_SYS_PRIVILEGE WHERE T_SYS_OBJ.OBJ_ID = T_SYS_PRIVILEGE.OBJ_ID"+((!SYS.equals(""))?" and T_SYS_OBJ.sys_id='"+SYS+"'":"")+Tools.getAddSQL("AND",SQL)+Tools.getAddSQL("AND",pv.PR_BROWSE_SCOPE)+" UNION SELECT (SELECT SYS_NAME FROM t_sys_system WHERE t_sys_system.SYS_ID=T_SYS_OBJ.SYS_ID) AS SYS_ID,'0-0',OBJ_ID, OBJ_NAME,0,0,0,0,0 FROM T_SYS_OBJ WHERE OBJ_ID NOT IN (SELECT OBJ_ID FROM T_SYS_PRIVILEGE WHERE "+SQL+")"+((!SYS.equals(""))?" and T_SYS_OBJ.sys_id='"+SYS+"'":"");  //MYSQL版本
		try{
			//System.out.print(str);
            sys.rs=sys.DoSQL(str);
			if(sys.rs!=null){while (sys.rs.next()) {%>			
				<tr onMouseOver='SetColor(this);' onmouseout='GetColor(this)'>
					<td><input type="checkbox" name="myCheckBox" value="<%=sys.rs.getString("PR_ID")%>"></td>
					<td><%=sys.rs.getString("SYS_ID")%></td>
					<%if(SelectValue.equals("*")){%><td><%=sys.rs.getString("ROLE_ID")%></td><%}%>
					<td><%=sys.rs.getString("OBJ_ID")%></td>
					<td><a href="javascript:top.OpenWindow('/system/PrivilegeEdit.jsp?ID=<%=sys.rs.getString("PR_ID")%>','EditWindow','权限信息',600,425);"><%=sys.rs.getString("OBJ_NAME")%></a>&nbsp;</td>
					<td><%if(sys.rs.getInt("PR_BROWSE")==0){%><img border="0" src="icons/pri0.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_BROWSE")==1){%><img border="0" src="icons/pri1.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_BROWSE")==2){%><img border="0" src="icons/pri2.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_BROWSE")==3){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"> </font><%}else if(sys.rs.getInt("PR_BROWSE")==4){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri1.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_BROWSE")==5){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri2.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_BROWSE")==6){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri3.gif" width="20" height="20"></font><%}else{%>-<%}%></td>
					<td><%if(sys.rs.getInt("PR_ADD")==0){%><img border="0" src="icons/pri0.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_ADD")==1){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri3.gif" width="20" height="20"></font><%}else{%>-<%}%></td>
					<td><%if(sys.rs.getInt("PR_EDIT")==0){%><img border="0" src="icons/pri0.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_EDIT")==1){%><img border="0" src="icons/pri1.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_EDIT")==2){%><img border="0" src="icons/pri2.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_EDIT")==3){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_EDIT")==4){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"></font><img border="0" src="icons/pri1.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_EDIT")==5){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri2.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_EDIT")==6){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri3.gif" width="20" height="20"></font><%}else{%>-<%}%></td>
					<td><%if(sys.rs.getInt("PR_DEL")==0){%><img border="0" src="icons/pri0.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_DEL")==1){%><img border="0" src="icons/pri1.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_DEL")==2){%><img border="0" src="icons/pri2.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_DEL")==3){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_DEL")==4){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri1.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_DEL")==5){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri2.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_DEL")==6){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri3.gif" width="20" height="20"></font><%}else{%>-<%}%></td>
					<td><%if(sys.rs.getInt("PR_AUDIT")==0){%><img border="0" src="icons/pri0.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_AUDIT")==1){%><img border="0" src="icons/pri1.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_AUDIT")==2){%><img border="0" src="icons/pri2.gif" width="20" height="20"><%}else if(sys.rs.getInt("PR_AUDIT")==3){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_AUDIT")==4){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri1.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_AUDIT")==5){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri2.gif" width="20" height="20"></font><%}else if(sys.rs.getInt("PR_AUDIT")==6 || sys.rs.getInt("PR_AUDIT")==9){%><font size="2"><img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri3.gif" width="20" height="20"></font><%}else{%>-<%}%></td>
				</tr>
			<%}}}catch (Exception e) {e.printStackTrace();}}%>			  
</table><input type='hidden' name='myCheckBox'/>
<INPUT type="hidden" NAME="Act" readonly></form>
<hr class='line'>
<table width="95%" align=center border=0 cellPadding=0 cellSpacing=0 >
	<tr><td width="100%" colspan="6">图例说明：</td></tr>
	<tr><td width="10%">权限图标</td><td width="10%">权限级别</td><td width="30%">权限名称</td><td width="10%">权限图标</td><td width="10%">权限级别</td><td width="30%">权限名称</td></tr>
	<tr><td width="10%"><img border="0" src="icons/pri0.gif" width="20" height="20"> </td><td width="10%">０级</td><td width="30%" align="left">不能操作数据</td><td width="10%"> <img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri1.gif" width="20" height="20">　</td><td width="10%">４级</td><td width="30%" align="left">能操作指定人员数据</td></tr>
	<tr><td width="10%"> <img border="0" src="icons/pri1.gif" width="20" height="20"></td><td width="10%">１级</td><td width="30%" align="left">只能操作自己写的数据</td><td width="10%"> <img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri2.gif" width="20" height="20">　</td><td width="10%">５级</td><td width="30%" align="left">能操作指定组织结构数据</td></tr>
	<tr><td width="10%"><img border="0" src="icons/pri2.gif" width="20" height="20"> </td><td width="10%">２级</td><td wwidth="30%" align="left">能操作本组织结构数据</td><td width="10%"> <img border="0" src="icons/pri3.gif" width="20" height="20"><img border="0" src="icons/pri3.gif" width="20" height="20">　</td><td width="10%">６级</td><td width="30%" align="left">能操作所有数据</td></tr>
	<tr><td width="10%"> <img border="0" src="icons/pri3.gif" width="20" height="20"> </td><td width="10%">３级</td><td width="30%" align="left">能操作本组织结构及下属组织结构数据</td><td width="10%">　</td><td width="10%"></td><td width="30%" align="left"></td></tr>
</table></BODY>
<%}sys.CloseDataBase();}%>
</HTML>
