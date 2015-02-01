<%@ page contentType="text/html;charset=utf-8" session="true" buffer="none"%>
<%@ page import="sei.core.*,sei.util.*,students.*" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="../system/css/page.css">
	<script type="text/javascript" src="../system/js/page.js"></script>
	
	<link rel="stylesheet" type="text/css" href="../system/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../system/easyui/themes/icon.css">
	<script type="text/javascript" src="../system/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../system/easyui/jquery.easyui.min.js"></script>
	<script src="../system/easyui/locale/easyui-lang-zh_CN.js"></script>
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
		Arward sys=new Arward();
String BT="";
Pv pv=null;
if(ID==null){//新增
	OPType="Insert";
	pv=sys.LoadData(UserID, ID, Pv.Add);
	sys.setSTATUS("待审核");
	sys.setPUSER_NAME(Tools.getName(sys,"select user_name from t_sys_user where user_id='"+UserID+"'"));
	sys.setPUSER_TIME(Tools.getDateTime("yyyy-MM-dd"));

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
          <td align=right width="10%" class="cx_Title">状态:</td>
          <td width='40%'><%=sys.getSTATUS()%><input type=hidden name="STATUS" value="<%=sys.getSTATUS()%>"/></TD>
          <td align=right width="10%" class="cx_Title">指标:</td>
          <td width='40%'><input class="easyui-combotree" ID='ZB_ID' value="<%=sys.getZB_NAME()%>" style="width:100%" data-options="url:'/TreeServlet.do?model=zb',method:'get',onSelect:function(node){var tree = $(this).tree;var isLeaf = tree('isLeaf', node.target);if (!isLeaf){$('#ZB_ID').combotree('clear');}}"></TD>
       </tr>
	   <tr>
         <td align=right class="cx_Title">学号:</td>
          <td><INPUT NAME='XH' class="textbox" style="WIDTH:100%" readonly maxlength=10 value="<%=sys.getXH()%>"/></TD>
          <td align=right class="cx_Title">姓名:</td>
          <td><select id="XM" class="easyui-combogrid" value="<%=sys.getXM()%>" style="width:100%" data-options="
			panelWidth: 600,
			idField: 'c1',
			textField: 'c2',
			url: '/getPage.do?model=xsxx&rows=10',
			method: 'get',
			mode: 'remote',
			pagination : true,
			striped: true,
			rownumbers: true,
			columns: [[
				{field:'c1',title:'学号',width:80},
				{field:'c2',title:'姓名',width:80},
				{field:'c3',title:'性别',width:40,align:'right'},
				{field:'c5',title:'辅导员',width:60},
				{field:'c6',title:'班级',width:60},
				{field:'c7',title:'年级',width:60,align:'right'},
				{field:'c8',title:'专业',width:150},
				{field:'c9',title:'学院',width:100,align:'center'}
			]],
			keyHandler: {
				up: function() {},
                down: function() {},
                enter: function() {},
                query: function(q) {
                    //动态搜索
                    $('#XM').combogrid('grid').datagrid('reload', {'XM':'Like$%'+q+'%'});
                    $('#XM').combogrid('setValue', q);
                }
			},
			fitColumns: true
		"/></TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">学期:</td>
          <td><select style="width:99%"  name="XQ"><%=students.StuTools.getXQ("")%></select></TD>
          <td align=right class="cx_Title">时间:</td>
          <td><INPUT NAME='TIME1' class="textbox" style="WIDTH:100%" readonly maxlength=10 value="<%=sys.getTIME1()%>" onClick='javascript:ShowCalendar1(this)' onKeyPress='return event.keyCode>=48&&event.keyCode<=57||event.keyCode==45'/></TD>          
       </tr>
       <tr>
         <td align=right class="cx_Title">事由:</td>
         <td colspan=3><input class="textbox" style="WIDTH:100%" type="text" name="SY" data-options="required:true" value="<%=sys.getSY()%>"></TD>
        </tr>
		<tr valign=middle>
          <td align=right class="cx_Title">具体内容:</td>
          <td colspan=3><input class="easyui-textbox" name="CONTENT" data-options="multiline:true" style="height:60px;width:100%" value="<%=sys.getCONTENT()%>"/></TD>
        </tr>
        <tr valign=middle height=20>
          <td align=right class="cx_Title">备注:</td>
          <td colspan=3><input class="textbox" style="WIDTH:100%" type="text" name="MEMO" data-options="required:true" value="<%=sys.getMEMO()%>"></TD>
       </tr>
       <tr>
	    <td align=right class="cx_Title">上传证明文件：</td>
           <td colspan=3><input type="file" name="FileUp" style="width:90%"></td>
	 	</tr>	 
	<%}else{%>
	   <tr>
          <td align=right width="10%" class="cx_Title">状态:</td>
          <td width='40%'><%=sys.getSTATUS()%>&nbsp;</TD>
          <td align=right width="10%" class="cx_Title">指标:</td>
          <td width='40%'><%=sys.getZB_NAME()%>&nbsp;</TD>
       </tr>
	   <tr>
          <td align=right class="cx_Title">学号:</td>
          <td><%=sys.getXH()%>&nbsp;</TD>
          <td align=right class="cx_Title">姓名:</td>
          <td><%=sys.getXM()%>&nbsp;</TD>
       </tr>
		<tr>
          <td align=right class="cx_Title">学期:</td>
          <td><%=sys.getXQ()%>&nbsp;</TD>
          <td align=right class="cx_Title">时间:</td>
          <td><%=sys.getTIME1()%>&nbsp;</TD>
       </tr>
       <tr>
         <td align=right class="cx_Title">事由:</td>
         <td colspan=3><%=sys.getSY()%>&nbsp;</TD>
        </tr>
		<tr valign=middle height=120>
          <td align=right class="cx_Title">具体内容:</td>
          <td colspan=3 style="word-wrap:break-word; word-break:break-all;"><%=sys.getCONTENT()%>&nbsp;</TD>
        </tr>
        <tr valign=middle>
          <td align=right class="cx_Title">备注:</td>
          <td colspan=3><%=sys.getMEMO()%>&nbsp;</TD>
       </tr>
       <tr>
	    <td align=right class="cx_Title">证明文件：</td>
           <td colspan=7><%if(sys.getATTFILE()==0){out.print("无证明文件");}else{out.print("<a href='attfile.jsp?Type=Down&ATTSHOWFILENAME="+sys.getATTSHOWFILENAME()+"&ATTSAVEFILENAME="+sys.getATTSAVEFILENAME()+"'>"+sys.getATTSHOWFILENAME()+"</a>");}%></td>
	 	</tr>
<%}%>
	   <tr>
         <td align=right class="cx_Title">登记人:</td>
          <td><%=sys.getPUSER_NAME()%></TD>
          <td align=right class="cx_Title">登记时间:</td>
          <td><%=sys.getPUSER_TIME()%></TD>
       </tr>  
</table>
    </form>
    <div style="text-align:center;padding:5px;height:27px;margin:5px" id="cc">
    	<%=BT%>	
    </div>
<script>
	$('#XM').combogrid({selectOnNavigation:true})
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