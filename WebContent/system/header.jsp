<%@page contentType="text/html;charset=utf-8"%>
<% 
String BT="";
Pv pv=null;
if(ID==null){//新增
	OPType="Insert";
	pv=sys.LoadData(UserID, ID, Pv.Add);
	if(!pv.PR_ADD){
		out.print("<div id='msgb' class='easyui-panel' title='提示信息' style='width:500px;height:200px;padding:10px;' data-options=\"iconCls:'icon-tip'\"><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
		sys.CloseDataBase();
		return;				
	}
	BT="<a href='javascript:void(0)' class='xpc-button xpc-save' onclick='submitForm()'>提交</a>";
}else{//编辑,查看
	if(OPType.equals("Update")){//编辑
		pv=sys.LoadData(UserID, ID, Pv.Edit);
		//pv=sys.getPvPageEdit(sys.getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_BASE",ID,Pv.Edit);
		if (!pv.PR_EDIT){
			//out.print("<script type=\"text/javascript\">$.messager.show({title:'无权限',msg:'对不起，你无权查看本信息！',timeout:0,showType:'fade'});</script>");
			out.print("<div id='msgb' class='easyui-panel' title='提示信息' style='width:500px;height:200px;padding:10px;' data-options=\"iconCls:'icon-tip'\"><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
			sys.CloseDataBase();
			return;
		}
		BT="<a href='javascript:void(0)' class='xpc-button xpc-save' onclick='submitForm()'>提交</a>";
	}else{//查看
		pv=sys.LoadData(UserID, ID, Pv.Browser);
		//pv=sys.getPvPageEdit(sys.getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_BASE",ID,Pv.Browser,Pv.Edit,Pv.Del);
		if (!pv.PR_BROWSE){
			//out.print("<script type=\"text/javascript\">$.messager.show({title:'无权限',msg:'对不起，你无权查看本信息！',timeout:0,showType:'fade'});</script>");
			out.print("<div id='msgb' class='easyui-panel' title='提示信息' style='width:500px;height:200px;padding:10px;' data-options=\"iconCls:'icon-tip'\"><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
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
