<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="sei.core.*"%>
<html><head>
<title></title><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../commonV1/table.css">
<script language=javascript src="../commonV1/com.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../commonV1/Calendar.js"></script>
<style>td {font-size: 12px}body {margin: 0;padding: 0;background: #EFF7FF;FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,StartColorStr=#ffffff, EndColorStr=#ddeeff );}a {TEXT-DECORATION: none}a:hover {TEXT-DECORATION: underline}</style>
</head>
<%  String UserID = (String) session.getAttribute("UserID");
	if(UserID==null){out.print("<SCRIPT>top.location.href='../login.jsp';</SCRIPT>");return;}else{
		Privilege sys=new Privilege();
		if (sys.getPvCanDo(UserID, "fdysz")==false){
			out.print("<SCRIPT>window.location.href='../common/NoPurview.jsp';</SCRIPT>");
		}else{
			String Type= request.getParameter("Type");
			if (Type== null)Type= "";
			String SQL ="";
			
			String NJ = request.getParameter("NJ");
			if (NJ == null)	NJ= "";
			if (!NJ.equals(""))SQL = Tools.getSQL(SQL, "AND", "NJ='" + NJ + "'");
	
			String XY_ID = request.getParameter("XY_ID");
			if (XY_ID == null)XY_ID = "";
			if (!XY_ID.equals("")){SQL =Tools.getSQL(SQL,"AND","XY_ID='" + XY_ID + "'");}

			String XY_NAME = request.getParameter("XY_NAME");
			if (XY_NAME == null)XY_NAME = "";
			
			String ZY_ID = request.getParameter("ZY_ID");
			if (ZY_ID == null)ZY_ID = "";			
			if(!ZY_ID.equals("")){SQL = Tools.getSQL(SQL,"AND","ZY_ID ='" + ZY_ID + "'");}

			String ZY_NAME = request.getParameter("ZY_NAME");
			if (ZY_NAME == null)ZY_NAME = "";

			String FDY_ID = request.getParameter("FDY_ID");
			if (FDY_ID == null)FDY_ID = "";	
			
			String FDY_NAME = request.getParameter("FDY_NAME");
			if (FDY_NAME == null)FDY_NAME = "";	
			
			String Act = request.getParameter("Act");
			if (Act == null)Act = "";
			if (Act.equals("save")) {
				try {
					int i=-1;
					try{
						sys.con.setAutoCommit(false);
						sys.stmt.executeUpdate("update t_bj set fdy_id='',FDY_NAME='' where fdy_ID='"+FDY_ID+"'");
						sys.stmt.executeUpdate("update t_students_base set fdy_id='',FDY_NAME='' where fdy_ID='"+FDY_ID+"'");
						String[] DocID = request.getParameterValues("myCheckBox");
						if (DocID != null) {
							int sum=(DocID.length-1);							
							for(int j=0;j<sum;j++){
								sys.stmt.executeUpdate("update t_bj set fdy_id='"+FDY_ID+"',fdy_xm='"+FDY_NAME+"' where bj_id='"+DocID[j]+"'");
								sys.stmt.executeUpdate("update t_bj set fdy_id='"+FDY_ID+"',fdy_xm='"+FDY_NAME+"' where bj='"+DocID[j]+"'");
							}
						}
						sys.con.commit();
						i=1;
						Type="";
					}catch(Exception e1){e1.printStackTrace();i=-1;try {sys.con.rollback();} catch (Exception x) {}}
					try{sys.con.setAutoCommit(true);}catch(Exception ee){}
					if (i==-1){
						out.print("<SCRIPT>alert('错误，辅导员设置不成功！');</SCRIPT>");
					}else{
						out.print("<SCRIPT>alert('辅导员设置成功！');</SCRIPT>");
						SQL = Tools.getSQL(SQL,"AND","FDY_ID ='" + FDY_ID + "'");
					}
				} catch (Exception e) {e.printStackTrace();}
			}
%>
<script language=javascript>
function save() {document.frm.submit();}
function fh(){location.href = "FdySz.jsp";}
</script>
<body topmargin="0" leftmargin="0">
	<form name="frm" action="FdySzEdit.jsp" method="POST">
		<table align=center width="98%" bordercolorlight="#98CCFE" cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" border="1">
			<tr>
				<td width="55" class="cx_Title" align=right>辅导员:</td>
				<td style="width:80px;"><%=FDY_NAME%></td>
				<td width="55" class="cx_Title" align=right>年级:</td>
				<td style="width:80px;"><%=NJ%></td>
				<td width="55" class="cx_Title" align=right>学院:</td>
				<td style="width:150px;"><%=XY_NAME%></td>
				<td width="55" class="cx_Title" align=right>专业:</td>
				<td style="width:200px;"><%=ZY_NAME%></td>	
				<%if (!Act.equals("save")){%><td width='*'><input type='button' value=' 确定提交 ' onClick='save()' /><%}else{%><td width='*'><input type='button' value=' 继续设置辅导员带班 ' onClick='fh()' /><%}%></td>
			</tr>
		</table>
		<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="table_list_td">
					<table class="table_list" align="center" border="1">
						<thead>
							<tr align=center height=20>
								<%if (!Act.equals("save")){%><td width='5%' class='Back_Td'>选中</td><%}%>
								<td width="10%" class='Back_Td'>年级</td>
								<td width="30%" class='Back_Td'>学院</td>
								<td width="30%" class='Back_Td'>专业</td>
								<td width="15%" class='Back_Td'>班级</td>
							</tr>
							<%
								try {
									sys.rs=sys.stmt.executeQuery("SELECT NJ,XY_NAME,ZY_NAME,BJ_ID,FDY_ID FROM T_BJ "+(((SQL.equals(""))?"":"WHERE "))+SQL+" ORDER BY XY_ID ASC,ZY_ID ASC,BJ_ID ASC");
									if (sys.rs != null) {
										while (sys.rs.next()) {
											out.print("<tr align=center height=20 onMouseOver='SetColor(this);' onmouseout='GetColor(this)'>"+((Act.equals("save"))?"":"<td><input type='checkbox' name='myCheckBox' value='"+ sys.rs.getString(4)+ "' "+((sys.rs.getString(5).equals(FDY_ID))?"checked":"")+"></td>")+"<td>"+ sys.rs.getString(1)+ "&nbsp;</TD><TD>"+ sys.rs.getString(2)+ "&nbsp;</TD><TD>"+ sys.rs.getString(3)+ "&nbsp;</TD><td><a target='_blank' href='../students/StudentsBaseList.jsp?BJ="+sys.rs.getString(4)+"'>" + sys.rs.getString(4)+ "</a></td></tr>");
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							%>
<input type='hidden' name='myCheckBox'/>
<INPUT type="hidden" NAME="Act" readonly value="save">
<INPUT type="hidden" NAME="NJ" readonly value="<%=NJ%>">
<INPUT type="hidden" NAME="FDY_ID" readonly value="<%=FDY_ID%>">
<INPUT type="hidden" NAME="FDY_NAME" readonly value="<%=FDY_NAME%>">
<INPUT type="hidden" NAME="XY_NAME" readonly value="<%=XY_NAME%>">
<INPUT type="hidden" NAME="ZY_NAME" readonly value="<%=ZY_NAME%>">
</form></body><%}sys.CloseDataBase();}%></html>