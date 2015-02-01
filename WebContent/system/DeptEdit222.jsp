<%@page import="sei.system.Base,sei.core.*"%>
<%@page contentType="text/html;charset=utf-8" session="true" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="css/page.css">
</head>
<body bgcolor="#FFFFFF">
<%
	String model=request.getParameter("model");if(model==null)model="0";
	String UserID= (String)session.getAttribute("UserID"); 
	if(UserID==null){
		response.sendRedirect("/");
		return;
	}else{
		String ID=request.getParameter("ID");
		String Tmp="";
		Pv pv=null;
		Base sys=new Base(ID);
		if(ID==null){//新增
			pv=sys.getPvPageEdit(model,UserID,"","PUSER_ID","T_SYS_BASE",ID,Pv.Add);
			if(!pv.PR_ADD){
				out.print("<div id='msgb' class='easyui-panel' title='提示信息' style='width:500px;height:200px;padding:10px;' data-options=\"iconCls:'icon-tip'\"><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
				sys.CloseDataBase();
				return;
			}
		}else{//查看
			pv=sys.getPvPageEdit(model,UserID,"DEPT_ID","PUSER_ID","T_SYS_BASE",ID,Pv.Browser,Pv.Edit,Pv.Del);
			if (!pv.PR_BROWSE){
				//out.print("<script type=\"text/javascript\">$.messager.show({title:'无权限',msg:'对不起，你无权查看本信息！',timeout:0,showType:'fade'});</script>");
				out.print("<div id='msgb' class='easyui-panel' title='提示信息' style='width:500px;height:200px;padding:10px;' data-options=\"iconCls:'icon-tip'\"><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
				sys.CloseDataBase();
				return;
			}
			Tmp="disabled:true,";
		}
%>	
	<div class="form">
		<div class="header"><h1>信息</h1></div>
		<div class="body">
	    <form id="ff" class="easyui-form" method="post" >
	    	<table cellpadding="2">
	    		<tr>
	    			<td align="right">上级编号：</td>
	    			<td id="PARENT_NAME"><span class="combo"><input type="text" name="PARENT_NAME" readonly class="main" value="<%=sys.getPARENT_NAME()%>"/><a href="#" class="search" disabled onclick="alert('ddd');"></a></span>
	    		</tr>
	    		<tr>
	    			<td align="right">编号：</td>
	    			<td><input type="text" name="BASE_ID" class="textbox" disabled value="<%=sys.getBASE_ID()%>"></input></td>
	    		</tr>
	    		<tr>
	    			<td align="right">排序：</td>
	    			<td><input type="text" name="BASE_ORDER" class="textbox" disabled value="<%=sys.getBASE_ORDER()%>"></input></td>
	    		</tr>
	    		<tr>
	    			<td align="right">名称：</td>
	    			<td><input type="text" name="BASE_NAME" class="textbox" disabled value="<%=sys.getBASE_NAME()%>"></input></td>
	    		</tr>
	    		<tr>
	    			<td align="right">类型：</td>
	    			<td><select name="BASE_TYPE1" value="<%=sys.getBASE_TYPE1()%>" disabled><option value="xy"<%=((sys.getBASE_TYPE1().equals("xy"))?" selected":"")%>>学院</option><option value="bm"<%=((sys.getBASE_TYPE1().equals("bm"))?" selected":"")%>>职能部门</option><option value="qt"<%=((sys.getBASE_TYPE1().equals("qt"))?" selected":"")%>>其它</option></select></td>
	    		</tr>
	    	</table>
			<INPUT type="hidden" NAME="BASE_TYPE" readonly value="<%=model%>">
			<INPUT type="hidden" NAME="PARENT_ID" readonly value="<%=sys.getPARENT_ID()%>">
	    </form>
	    <div style="text-align:center;padding:5px" id="cc">
	    	<%if(pv.PR_EDIT){%><a href="javascript:void(0)" class="xpc-button xpc-edit" style="width:80px" onclick="edit()">修改</a><%}%>
	    	<%if(pv.PR_DEL){%><a href="javascript:void(0)" class="xpc-button xpc-dell" style="width:80px" onclick="dell()">删除</a><%}%>
	    	<%if(ID==null){%><a href='javascript:void(0)' class='xpc-button xpc-save' style='width:80px' onclick='submitForm()'>提交</a><%}%>	    	
	    </div>
	    </div>
	</div>
	<div id="w" class="easyui-window" title="请选择" data-options="modal:true,closed:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false" style="width:500px;height:200px;padding:10px;">
		<div class="easyui-tree" id="stree"></div>
	</div>
	<script>
		function submitForm(){
			if ($('#ff').form('validate')){
				//console.log($('#ff').serJSON());
				alert($('#ff').serJSON());
				$.ajaxSetup({cache:false});
				$.ajax( {
			        url : "/SaveFormDataServlet.do?ClassName=sei.system.Base&OPType=<%=((ID==null)?"Insert":"Update")%>",
			        dataType : "text",
			        type : "post",
			        data : {
			        	data:$('#ff').serJSON()
			        },
			        error : function(msg) { //若Ajax处理失败后返回的信息
			            alert("保存错误，请稍后重试！");
			        },
			        success : function(text) { //若Ajax处理成功后返回的信息
			        	if(text==1){
			        		alert('保存成功！');
			        	}else if(text==-1){
			        		alert("保存错误，请稍后再试！");
			        	}else if(text==-2){
			        		alert("保存错误，由于你长时间没有操作,已经超过系统等待时限，请重新登陆后再试！");
			        	}else if(text==-3){
			        		alert("保存错误，权限不够，你无权进行本操作！");
			        	}else if(text==-4){
			        		alert("关键字重复，请修改你的输入项后再试！");
			        	}
			        }
			    });
			}
		}
		function edit(){
			for(var i=0;i<ff.length;i++){  
				ff[i].disabled=false;
			}  
		}
		function choice(){
			$('#w').window('open');
			$('#stree').tree({'url' : '/TreeServlet.do?model=<%=model%>','method':'get','onClick' : function(node){alert(node.text);}});
		}
		function dell(){
			$.ajax( {
		        url : "/SaveFormDataServlet.do?ClassName=sei.system.Base&OPType=Delete&ID=<%=ID%>",
		        dataType : "text",
		        type : "post",
		        error : function(msg) { //若Ajax处理失败后返回的信息
		            alert("保存错误，请稍后重试！");
		        },
		        success : function(text) { //若Ajax处理成功后返回的信息
		        	if(text==1){
		        		alert('保存成功！');
		        	}else if(text==-1){
		        		alert("保存错误，请稍后再试！");
		        	}else if(text==-2){
		        		alert("保存错误，由于你长时间没有操作,已经超过系统等待时限，请重新登陆后再试！");
		        	}else if(text==-3){
		        		alert("保存错误，权限不够，你无权进行本操作！");
		        	}else if(text==-4){
		        		alert("关键字重复，请修改你的输入项后再试！");
		        	}
		        }
		    });
		}
	</script>
<%sys.CloseDataBase();}%>
</body>
</html>