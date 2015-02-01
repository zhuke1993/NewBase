<%@ page contentType="text/html;charset=utf-8" session="true" buffer="none"%>
<%@ page import="sei.core.*,sei.util.*,students.*" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="../system/css/page.css">
	<script type="text/javascript" src="../system/js/page.js"></script>
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
		StudentsBase sys=new StudentsBase();
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
<tr><td width="90%" height="22px" colspan="8" bgcolor="#FAFDD9"><b>&nbsp;&nbsp;学生信息</b></td></tr>
<%if(ID==null || OPType.equals("Update")){%>
		<tr>
		  <td width='10%' align=right class="cx_Title">帐号状态:</td>
          <td width='40%'><%out.print((sys.getSTATUS()==0)?"启用&nbsp;":"禁用&nbsp;");%>&nbsp;</TD>
          <td width='10%' align=right class="cx_Title">姓名:</td>
          <td width='40%'><INPUT NAME='XM' class="textbox" style="width:99%" maxlength=30 value="<%=sys.getXM()%>"/></TD>
       </tr>
	   <tr>
          <td align=right width="6%" class="cx_Title">校园网号:</td>
          <td><INPUT NAME='XSID' class="textbox" style="width:99%" maxlength=7 value="<%=sys.getXSID()%>"/></TD>
          <td align=right width="6%" class="cx_Title">学号:</td>
          <td><INPUT NAME='XH' class="textbox" style="width:99%" maxlength=10 <%=((ID==null)?"":"readonly")%> value="<%=sys.getXH()%>"/></TD>
       </tr>
	   <tr>
	      <td align=right class="cx_Title">所属学院:</td>
          <td><input type="hidden" name="XY_ID" value="<%=sys.getXY_ID()%>"/><span class="combo"><input type="text" id="XY_NAME" name="XY_NAME" readonly class="main" data-options="{required:true,msg:'请选择学院！'}" value="<%=sys.getXY_NAME()%>"/><a href="#" class="search" onclick="top.SelectWindow('dept','SELECTWIN','请选择',document.getElementById('XY_ID'),document.getElementById('XY_NAME'),250,400);"></a></span></td>
          <td align=right class="cx_Title">专业:</td>
          <td><input type="hidden" name="ZY_ID" value="<%=sys.getZY_ID()%>"/><span class="combo"><input type="text" id="ZY_NAME" name="ZY_NAME" readonly class="main" data-options="{required:true,msg:'请选择专业！'}" value="<%=sys.getZY_NAME()%>"/><a href="#" class="search" onclick="top.SelectWindow('zy','SELECTWIN','请选择',document.getElementById('ZY_ID'),document.getElementById('ZY_NAME'),250,400);"></a></span></td>
       </tr>
	   <tr>
          <td align=right class="cx_Title">年级:</td>
          <td><%=sys.getNJ()%>&nbsp;</TD>
          <td align=right class="cx_Title">班级:</td>
          <td><INPUT NAME='BJ' class="textbox" style="width:99%" maxlength=20 value="<%=sys.getBJ()%>"/></TD>
       </tr>       
	   <tr>
          <td align=right class="cx_Title">身份证号:</td>
          <td><INPUT NAME='SFZH' class="textbox" style="width:99%" maxlength=18 value="<%=sys.getSFZH()%>"/></TD>
          <td align=right class="cx_Title">性别:</td>
          <td><select name="XB" style="width: 99%;"><option <%if(sys.getXB().equals("男"))out.print("selected"); %> value="男">男</option><option <%if(sys.getXB().equals("女"))out.print("selected"); %> value="女">女</option></select></td>
       </tr>
	   <tr>
          <td align=right class="cx_Title">政治面貌:</td>
          <td><select name="ZZMM" style="width: 99%;"><option value="党员" <%if(sys.getZZMM().equals("党员"))out.print("selected");%>>党员</option>
            <option value="团员" <%if(sys.getZZMM().equals("团员"))out.print("selected");%>>团员</option>
            <option value="民主党派" <%if(sys.getZZMM().equals("民主党派"))out.print("selected");%>>民主党派</option>
            <option value="群众" <%if(sys.getZZMM().equals("群众"))out.print("selected");%>>群众</option>
          </select></td>
          <td align=right class="cx_Title">辅导员:</td>
          <td><%=sys.getFDY_NAME()%>&nbsp;</TD>
       </tr>
       <tr height=20><td width="90%" colspan="8" bgcolor="#FAFDD9"><b>&nbsp;&nbsp;个人信息</b></td></tr>
		 <tr>
		  <td align=right width="6%" class="cx_Title">姓名拼音:</td>
          <td><INPUT NAME='XMPY' class="textbox" style="width:99%"  maxlength=60 value="<%=sys.getXMPY()%>"/></TD>
          <td align=right class="cx_Title">曾用名:</td>
          <td><INPUT NAME='XMCYM' class="textbox" style="width:99%"  maxlength=30 value="<%=sys.getXMCYM()%>"/></TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">出生日期:</td>
          <td><INPUT NAME="CSRQ" STYLE="width:99%" maxlength=10 value="<%=sys.getCSRQ()%>"  onfocus="WdatePicker()" onKeyPress='return event.keyCode>=48&&event.keyCode<=57||event.keyCode==45'></TD>
          <td align=right class="cx_Title">婚姻状况:</td>
          <td><select name="HYZK" style="width: 99%;">
            <option <%if(sys.getHYZK().equals("未婚"))out.print("selected"); %> value="未婚">未婚</option>
            <option <%if(sys.getHYZK().equals("已婚"))out.print("selected"); %> value="已婚">已婚</option>
            <option <%if(sys.getHYZK().equals("离异"))out.print("selected");%> value="离异">离异</option>
            <option <%if(sys.getHYZK().equals("丧偶"))out.print("selected");%> value="丧偶">丧偶</option>
            <option <%if(sys.getHYZK().equals("其他"))out.print("selected");%> value="其他">其他</option>
          </select></td>
       </tr>
		 <tr>
          <td align=right class="cx_Title">国籍:</td>
          <td><INPUT NAME='GJ' class="textbox" style="width:99%"  maxlength=20 value="<%=sys.getGJ()%>"/></TD>
          <td align=right class="cx_Title">籍贯:</td>
          <td><INPUT NAME='JG' class="textbox" style="width:99%"  maxlength=20 value="<%=sys.getJG()%>"/></TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">民族:</td>
          <td><select name="MZ" style="width:100%"><%=Tools.buildListItem(sys,"Select BASE_ID,BASE_NAME from T_SYS_BASE WHERE BASE_TYPE='nat'",sys.getMZ(),true)%></select></td>
          <td align=right class="cx_Title">健康状况:</td>
          <td><select STYLE="width:99%" name="JKZK">
            <option  value="健康"<%if(sys.getJKZK().equals("健康"))out.print("selected");%>>健康</option>
            <option  value="疾病" <%if(sys.getJKZK().equals("疾病"))out.print("selected");%>>疾病</option>
            <option  value="丧失劳动力" <%if(sys.getJKZK().equals("丧失劳动力"))out.print("selected");%>>丧失劳动力</option>
          </select></td>
       </tr>
	   <tr>
          <td align=right class="cx_Title">血型:</td>
          <td><select STYLE="width:99%" name="XX">
            <option  value="A"<%if(sys.getXX().equals("A"))out.print("selected");%>>A</option>
            <option  value="B" <%if(sys.getXX().equals("B"))out.print("selected");%>>B</option>
            <option  value="AB" <%if(sys.getXX().equals("AB"))out.print("selected");%>>AB</option>
            <option  value="O" <%if(sys.getXX().equals("O"))out.print("selected");%>>O</option>
          </select></td>
          <td align=right class="cx_Title">宗教信仰:</td>
          <td><INPUT NAME='ZJXY' class="textbox" style="width:99%"  maxlength=20 value="<%=sys.getZJXY()%>"/></TD>
       </tr>
	   <tr>
           <td align=right class="cx_Title">联系方式:</td>
          <td><INPUT NAME='LXDH' class="textbox" style="width:99%"  maxlength=20 value="<%=sys.getLXDH()%>"/></TD>
          <td align=right class="cx_Title">电子邮件:</td>
          <td><INPUT NAME='DZYJ' class="textbox" style="width:99%"  maxlength=10 value="<%=sys.getDZYJ()%>"/></TD>
        </tr>
        <tr height=20><td width="90%" colspan="8" bgcolor="#FAFDD9"><b>&nbsp;&nbsp;家庭信息</b></td></tr>
        <tr>
          <td align=right class="cx_Title">家庭地址:</td>
          <td><INPUT NAME='JTDZ' class="textbox" style="width:99%"  maxlength=10 value="<%=sys.getJTDZ()%>"/></TD>
 		  <td align=right class="cx_Title">联系方式:</td>
          <td><INPUT NAME='JTLXFS' class="textbox" style="width:99%" style="width:99%"  maxlength=20 value="<%=sys.getJTLXFS()%>"/></TD>
       </tr>
	<%}else{%>
		<tr>
		  <td width='10%' align=right class="cx_Title">帐号状态:</td>
          <td width='40%'><%out.print((sys.getSTATUS()==0)?"启用&nbsp;":"禁用&nbsp;");%>&nbsp;</TD>
          <td width='10%' align=right class="cx_Title">姓名:</td>
          <td width='40%'><%=sys.getXM()%></TD>
       </tr>
	   <tr> 
          <td align=right class="cx_Title">校园网号:</td>
          <td><%=sys.getXSID()%>&nbsp;</TD>
          <td align=right width="6%" class="cx_Title">学号:</td>
          <td><%=sys.getXH()%>&nbsp;</TD>
       </tr>
	   <tr>
	      <td align=right class="cx_Title">所属学院:</td>
          <td><%=sys.getXY_NAME()%>&nbsp;</TD>
          <td align=right class="cx_Title">专业:</td>
          <td><%=sys.getZY_NAME()%>&nbsp;</TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">年级:</td>
          <td><%=sys.getNJ()%>&nbsp;</TD>
          <td align=right class="cx_Title">班级:</td>
          <td><%=sys.getBJ()%>&nbsp;</TD>
       </tr>
       
	   <tr>
	      <td align=right class="cx_Title">身份证号:</td>
          <td><%=sys.getSFZH()%>&nbsp;</TD>
          <td align=right class="cx_Title">性别:</td>
          <td><%=sys.getXB()%>&nbsp;</TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">政治面貌:</td>
          <td><%=sys.getZZMM()%>&nbsp;</TD>
         <td align=right class="cx_Title">辅导员:</td>
          <td><%=sys.getFDY_NAME()%>&nbsp;</TD>
       </tr>
        <tr height=20><td width="90%" colspan="8" bgcolor="#FAFDD9"><b>&nbsp;&nbsp;个人信息</b></td></tr>
		 <tr>
		  <td align=right class="cx_Title">姓名拼音:</td>
          <td><%=sys.getXMPY()%>&nbsp;</TD>
          <td align=right class="cx_Title">曾用名:</td>
          <td><%=sys.getXMCYM()%>&nbsp;</TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">出生日期:</td>
          <td><%=sys.getCSRQ()%>&nbsp;</TD>
          <td align=right class="cx_Title">婚姻状况:</td>
          <td><%=sys.getHYZK()%>&nbsp;</TD> 
       </tr>
		 <tr>
          <td align=right class="cx_Title">国籍:</td>
          <td><%=sys.getGJ()%>&nbsp;</TD>
          <td align=right class="cx_Title">籍贯:</td>
          <td><%=sys.getJG()%>&nbsp;</TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">民族:</td>
          <td><%=sys.getMZ()%>&nbsp;</TD>
          <td align=right class="cx_Title">健康状况:</td>
          <td><%=sys.getJKZK()%>&nbsp;</TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">血型:</td>
          <td><%=sys.getXX()%>&nbsp;</TD>
          <td align=right class="cx_Title">宗教信仰:</td>
          <td><%=sys.getZJXY()%>&nbsp;</TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">联系方式:</td>
          <td><%=sys.getLXDH()%>&nbsp;</TD>
          <td align=right class="cx_Title">电子邮件:</td>
          <td><%=sys.getDZYJ()%>&nbsp;</TD>
       </tr>
        <tr height=20><td width="90%" colspan="8" bgcolor="#FAFDD9"><b>&nbsp;&nbsp;家庭信息</b></td></tr>
       <tr>
          <td align=right class="cx_Title">家庭地址:</td>
          <td><%=sys.getJTDZ()%>&nbsp;</TD>
          <td align=right class="cx_Title">联系方式:</td>
          <td><%=sys.getJTLXFS()%>&nbsp;</TD>
       </tr>
<%}%></table>
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