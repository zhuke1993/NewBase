<%@ page contentType="text/html;charset=utf-8" session="true" %>
<%@ page import="sei.core.*"%>
<jsp:useBean id="sys" class="sei.system.Base" scope="page"/>
<html><Head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../commonV1/popwin.css">
</Head>
<body>
<%
	String MODE=request.getParameter("MODE");if(MODE==null)MODE="0";
	String UserID= (String)session.getAttribute("UserID"); 
	if(UserID==null){out.print("<SCRIPT>top.location.href='../login.jsp';</SCRIPT>");}else{
	String ID=request.getParameter("ID");if(ID==null)ID="";
	String Type=request.getParameter("Type");if(Type==null)Type="";
	PvPageEdit pv=sys.getPvPageEdit(UserID, MODE, "T_BASE", "BASE_ID='"+ID+"'",Type,"","PUSER_ID");
	if (pv.PR_BROWSE==0){
		out.print("<SCRIPT>location.href='../common/NoPurview.jsp';</SCRIPT>");
	}else{	
		String BT="";
		int i=0;
		if (Type.equals("")){
			if(pv.PR_ADD==0){//无权限
				out.print("<SCRIPT>alert('权限不够,不能新增!');history.go(-1);</SCRIPT>");
				sys.CloseDataBase();
				return;
			}else{
				sys.setPARENT_ID("0");
				sys.setPARENT_NAME("系统 ");
				Type="Insert";
				BT="&nbsp;&nbsp;<a href='javascript:save();'><IMG align=absMiddle src='../images/save.gif' border=0>保存</a>";
				// out.print("<SCRIPT>toolframe.document.frm.txt.value=\"<TD class=coolButton noWrap title=保存 vAlign=center onclick='javascript:parent.messageframe.save();'>&nbsp;<IMG align=absMiddle src=../images/save.gif>保存</TD>\";parent.toolframe.document.frm.submit();</SCRIPT>");
			}
		}else {
			if (Type.equals("See")|Type.equals("Edit")){
				sys.LoadData("select * from T_BASE where BASE_ID='"+ID+"' AND BASE_TYPE='"+MODE+"'");
				if(Type.equals("Edit")){
					Type="Update";
					BT="&nbsp;&nbsp;<a href='javascript:save();'><IMG align=absMiddle src='../images/save.gif' border=0>保存</a>";
					// out.print("<SCRIPT>parent.toolframe.document.frm.txt.value=\"<TD class=coolButton noWrap title=保存 vAlign=center onclick=javascript:parent.messageframe.save();>&nbsp;<IMG align=absMiddle src=../images/save.gif>保存</TD>\";parent.toolframe.document.frm.submit();</SCRIPT>");
				}else{
					if (pv.PR_EDIT==1&&sys.getBASE_STATUS()==0) {
						BT="&nbsp;&nbsp;<a href='javascript:Edit();'><IMG align=absMiddle src='../images/edit.gif' border=0>编辑</a>";
						// out.print("<SCRIPT>parent.toolframe.document.frm.txt.value=parent.toolframe.document.frm.txt.value+'<TD class=coolButton noWrap title=编辑 vAlign=center onclick=javascript:parent.messageframe.Edit();>&nbsp;<IMG align=absMiddle src=../images/edit.gif>编辑</TD>';parent.toolframe.document.frm.submit();</SCRIPT>");else out.print("<SCRIPT>parent.toolframe.document.frm.submit();</SCRIPT>");
					}
				}
			}else {
				if (Type.equals("Update")){//判断是否修改状态		
					if(pv.PR_EDIT==1){
						%> <jsp:setProperty name="sys" property="*"/> <%
					    if(sys.getBASE_EXT3().equals("区间值")) sys.setBASE_EXT4(request.getParameter("SCORERANGE_MIN")+"-"+request.getParameter("SCORERANGE_MAX"));
						i=sys.Update(UserID,MODE);
					}else i=-3;
				}else if (Type.equals("Insert")){//判断是否修改状态
					if(pv.PR_ADD==1){
						%> <jsp:setProperty name="sys" property="*"/> <%
					    if(sys.getBASE_EXT3().equals("区间值")) sys.setBASE_EXT4(request.getParameter("SCORERANGE_MIN")+"-"+request.getParameter("SCORERANGE_MAX"));
						i=sys.Insert(UserID,MODE);
					}else i=-3;
				}else if(Type.equals("Delete")) {
					i=sys.Delete(UserID,ID,MODE);
				}
				if (i==-1){out.print("<SCRIPT>alert('保存错误,请检查你的输入值!');history.go(-1);</SCRIPT>");
				}else if (i==-2){out.print("<SCRIPT>alert('输入的关键字重复!');history.go(-1);</SCRIPT>");
				}else if(i==-3){out.print("<SCRIPT>alert('你的权限不够,不能保存数据!');history.go(-1);</SCRIPT>");
				}else{out.print("<SCRIPT>alert('保存成功!');parent.location.href=parent.location.href;</SCRIPT>");}
				sys.CloseDataBase();
				return;
			}
		}
%>	
<SCRIPT Language = javascript>
  function Edit(){window.location="ZbEdit.jsp?Type=Edit&ID=<%=ID%>&MODE=<%=MODE%>";}
  function Delete(){<%if(sys.getBASE_STATUS()==1){out.print("alert('固定指标不能删除!');");} else{%>if(confirm('删除本信息时将一并删除其下属信息，确认真的要删除吗？'))window.location="ZbEdit.jsp?Type=Delete&ID=<%=ID%>&MODE=<%=MODE%>";<%}%>}  
  function save(){
	if(frm.PARENT_ID.value==""){window.alert("您必须选择一个上级编号!"); return  false;}
  	if(frm.BASE_NAME.value == ""){window.alert("您必须输入名称!"); return  false;}
  	if(frm.PARENT_ID.value==frm.BASE_ID.value){	alert('不能选择自己作为上级部门,请重新选择一个上级部门.');return;}  	
  	document.frm.submit();
 }

<%--  function SelctParent(){
 	var m=window.showModalDialog("../common/SelectTreeOneDept.jsp?Page=Base.jsp&MODE=<%=MODE%>&RootChoice=1",'',"dialogWidth:410px;dialogHeight:310px;center:1;scroll:0;help:0;status:0");
	if (m!=null){
		document.frm.PARENT_ID.value=m[0][0];
		document.frm.PARENT_NAME.value=m[0][1];
	}	
 } --%>
 
 function SelctTree(field1,field2)
 {
 	var url="../common/SelectTreeOneDept.jsp?Page=Base.jsp&MODE=<%=MODE%>&RootChoice=1";
 	popwin(url,'选择指标',410,310,field1,field2);
 }
 function popwin(url,title,width,height,field1,field2){top.Add(url,'auto',window,'SelectValue',title,width,height,false,field1,field2);}

 
 function DataSource(SourceTable){
	sendRequest("ZbListSource.jsp","TABLE_NAME="+SourceTable+"&VALUE=<%=sys.getBASE_EXT8()%>",true);
}

function processRequest(){
	if (XMLRequest.readyState == 4 && XMLRequest.status == 200) {
		document.getElementById("SOURCE_COLUMN_CONTENT").innerHTML = XMLRequest.responseText;
    }
}
 function ScoreType(ScoreType){
    if(ScoreType=="固定值"){
	    document.getElementById("SCOREVALUE_FIXED").style.display="";
	    document.getElementById("SCOREVALUE_RANGE").style.display="none";
	}
	else{
	    document.getElementById("SCOREVALUE_FIXED").style.display="none";
	    document.getElementById("SCOREVALUE_RANGE").style.display="";
	}  
 }
 function TargetType(TargetType){
    if(TargetType=="指标值"){
	    document.getElementById("SCORE_TYPE").style.display="";
	    document.getElementById("SCORE_CHANGE").style.display="";
	    document.getElementById("SCORE_VALUE").style.display="";
	    document.getElementById("SCORE_LIMIT").style.display="";
	    document.getElementById("SOURCE_TABLE").style.display="none";
	    document.getElementById("SOURCE_COLUMN").style.display="none";
	}else if(TargetType=="公式指标"){
		document.getElementById("SCORE_TYPE").style.display="none";
	    document.getElementById("SCORE_CHANGE").style.display="none";
	    document.getElementById("SCORE_VALUE").style.display="none";
	    document.getElementById("SCORE_LIMIT").style.display="none";
	    document.getElementById("SOURCE_TABLE").style.display="";
	    document.getElementById("SOURCE_COLUMN").style.display="";
	}
	else{
	    document.getElementById("SCORE_TYPE").style.display="none";
	    document.getElementById("SCORE_CHANGE").style.display="none";
	    document.getElementById("SCORE_VALUE").style.display="none";
	    document.getElementById("SCORE_LIMIT").style.display="none";
	    document.getElementById("SOURCE_TABLE").style.display="none";
	    document.getElementById("SOURCE_COLUMN").style.display="none";
	}  
 }
</SCRIPT>
<body>
<form name="frm" action="ZbEdit.jsp?Type=<%=Type%>&ID=<%=ID%>&MODE=<%=MODE%>" method="POST">
<p>&nbsp;</p>
<table align=center class="table_list" style="width:60%">
    <tr>
    	<td width=20% align=right class="cx_Title">上级名称：</td>
        <td width=80%><% if(!Type.equals("See")){%><INPUT NAME="PARENT_NAME" readonly STYLE="width=80%" maxlength=10 value="<%=sys.getPARENT_NAME()%>"><input type="button" value="选择" name="sel" onClick="SelctTree(document.frm.PARENT_ID,document.frm.PARENT_NAME)"><%;}else %><%=sys.getPARENT_NAME()%></TD>
	</tr>
	<tr>
	    <td align=right class="cx_Title">指标类型：</td>
		<td><% if(!Type.equals("See")){%><select name="BASE_EXT1" onchange="TargetType(this.value)"><option <%if(sys.getBASE_EXT1().equals(""))out.print("selected"); %> value=""></option><option <%if(sys.getBASE_EXT1().equals("公式指标"))out.print("selected"); %> value="公式指标">公式指标</option><option <%if(sys.getBASE_EXT1().equals("指标分类"))out.print("selected"); %> value="指标分类">指标分类</option><option <%if(sys.getBASE_EXT1().equals("指标值"))out.print("selected"); %> value="指标值">指标值</option></select><%;}else %><%=sys.getBASE_EXT1()%></TD>
	</tr>		
	<tr>
        <td align=right class="cx_Title">编号：</td>
        <td><% if(Type.equals("Insert")){%><INPUT NAME="BASE_ID" STYLE="width=100%" maxlength=10 value="<%=sys.getBASE_ID()%>"><%;}else %><%=sys.getBASE_ID()%></td> 
    </tr>
	<tr>
        <td align=right class="cx_Title">排序：</td>
        <td><% if(!Type.equals("See")){%><INPUT NAME="BASE_ORDER" STYLE="width=100%" maxlength=10 value="<%=sys.getBASE_ORDER()%>"><%;}else %><%=sys.getBASE_ORDER()%></td>
    </tr>
	<tr>
	    <td align=right class="cx_Title">名称：</td>
		<td><% if(!Type.equals("See")){%><INPUT NAME="BASE_NAME" STYLE="width=100%" maxlength=30 value="<%=sys.getBASE_NAME()%>"><%;}else %><%=sys.getBASE_NAME()%></TD>
	</tr>
	<tr id="SOURCE_TABLE" <%if(!sys.getBASE_EXT1().equals("公式指标"))out.print("style='display:none'"); %>>
	    <td align=right class="cx_Title">来源模块：</td>
		<td><% if(!Type.equals("See")){out.print(Tools.buildListSource(sys.stmt, sys.rs, "BASE_EXT7", "Select TABLE_NAME, TABLE_COMMENT from INFORMATION_SCHEMA.tables Where table_schema = 'students' and table_name not in('t_attfile','t_audit_log','t_base','t_baseext','t_calculation','t_company','t_data','t_log','t_notic','t_obj','t_occuplevel','t_privilege','t_privmodule','t_privtype','t_seq','t_system','t_user')", sys.getBASE_EXT7(), "DataSource"));}else out.print(Tools.getName(sys.stmt, sys.rs, "Select TABLE_COMMENT from INFORMATION_SCHEMA.tables Where table_schema = 'students' and table_name = '"+sys.getBASE_EXT7()+"'"));%></TD>
	</tr>
	<tr id="SOURCE_COLUMN" <%if(!sys.getBASE_EXT1().equals("公式指标"))out.print("style='display:none'"); %>>
	    <td align=right class="cx_Title">来源字段：</td>
		<td id="SOURCE_COLUMN_CONTENT"><% if(!Type.equals("See")){out.println(Tools.buildListSource(sys.stmt, sys.rs, "BASE_EXT8", "Select COLUMN_NAME, COLUMN_COMMENT from INFORMATION_SCHEMA.COLUMNS Where table_name='"+sys.getBASE_EXT7()+"'", sys.getBASE_EXT8(), ""));}else out.print(Tools.getName(sys.stmt, sys.rs, "Select COLUMN_COMMENT from INFORMATION_SCHEMA.COLUMNS Where table_name='"+sys.getBASE_EXT7()+"' and column_name='"+sys.getBASE_EXT8()+"'"));%></TD>
	</tr>
	<tr id="SCORE_TYPE" <%if(!sys.getBASE_EXT1().equals("指标值"))out.print("style='display:none'"); %>>
	    <td align=right class="cx_Title">分值类型：</td>
		<td><% if(!Type.equals("See")){%><select name="BASE_EXT3" onchange="ScoreType(this.value)"><option <%if(sys.getBASE_EXT3().equals("固定值"))out.print("selected"); %> value="固定值">固定值</option><option <%if(sys.getBASE_EXT3().equals("区间值"))out.print("selected"); %> value="区间值">区间值</option></select><%;}else %><%=sys.getBASE_EXT3()%></TD>
	</tr>
	<tr id="SCORE_CHANGE" <%if(!sys.getBASE_EXT1().equals("指标值"))out.print("style='display:none'"); %>>
	    <td align=right class="cx_Title">分值操作：</td>
		<td><% if(!Type.equals("See")){%><select name="BASE_EXT2"><option selected value="加分">加分</option><option value="减分">减分</option></select><%;}else %><%=sys.getBASE_EXT2()%></TD>
	</tr>
	<tr id="SCORE_VALUE" <%if(!sys.getBASE_EXT1().equals("指标值"))out.print("style='display:none'"); %>>
	    <td align=right class="cx_Title">分值值：</td>
		<td id="SCOREVALUE_FIXED" <%if(sys.getBASE_EXT3().equals("区间值")){out.print("style='display:none'");}%>><% if(!Type.equals("See")){%><INPUT NAME="BASE_EXT4" STYLE="width=43%" maxlength=50 value="<%=sys.getBASE_EXT4()%>"><%;}else %><%=sys.getBASE_EXT4()%>&nbsp;(单位:分)</td>
		<td id="SCOREVALUE_RANGE" <%if(!sys.getBASE_EXT3().equals("区间值")){out.print("style='display:none'");}%>><% if(!Type.equals("See")){String[] SCORE_RANGE={"",""}; if(!sys.getBASE_EXT4().equals("")&&sys.getBASE_EXT4().contains("-")){SCORE_RANGE=sys.getBASE_EXT4().split("-");}%><INPUT NAME="SCORERANGE_MIN" STYLE="width=20%" maxlength=50 value="<%=SCORE_RANGE[0]%>">&nbsp;至&nbsp;<INPUT NAME="SCORERANGE_MAX" STYLE="width=20%" maxlength=50 value="<%=SCORE_RANGE[1]%>"><%;}else %><%=sys.getBASE_EXT4()%>&nbsp;(单位:分)</td>
	</tr>
	<tr id="SCORE_LIMIT" <%if(!sys.getBASE_EXT1().equals("指标值"))out.print("style='display:none'"); %>>
	    <td align=right class="cx_Title">分值上限：</td>
		<td><% if(!Type.equals("See")){%><INPUT NAME="BASE_EXT5" STYLE="width=20%" maxlength=50 value="<%=sys.getBASE_EXT5()%>">&nbsp;/&nbsp;<select name="BASE_EXT6"><option <%if(sys.getBASE_EXT6().equals(""))out.print("selected"); %> value=""></option><option <%if(sys.getBASE_EXT6().equals("学期"))out.print("selected"); %> value="学期">学期</option><option <%if(sys.getBASE_EXT6().equals("学年"))out.print("selected"); %> value="学年">学年</option></select><%;}else {if(!sys.getBASE_EXT5().equals(""))out.print(sys.getBASE_EXT5()+"分/"+sys.getBASE_EXT6());else out.print("无");}%></td>
	</tr>
	<tr>
        <td align=right class="cx_Title">备注：</td>
        <td><% if(!Type.equals("See")){%><INPUT NAME="BASE_MEMO" STYLE="width=100%" maxlength=100 value="<%=sys.getBASE_MEMO()%>"><%;}else %><%=sys.getBASE_MEMO()%></td> 
	</tr>
</table>
<hr class='line' style="width:65%"><%=BT%>
<INPUT type="hidden" NAME="BASE_ID" readonly value="<%=sys.getBASE_ID()%>">
<INPUT type="hidden" NAME="BASE_TYPE" readonly value="<%=MODE%>">
<INPUT type="hidden" NAME="PARENT_ID" readonly value="<%=sys.getPARENT_ID()%>">
</form></body>
<%}}sys.CloseDataBase();%>
</html>