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
        Privg sys=new Privg();
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
        String obj_id=request.getParameter("obj_id");if(obj_id==null)obj_id="";
        String ROLE_ID=request.getParameter("ROLE_ID");if(ROLE_ID==null)ROLE_ID="";
%>
<form id="ff" class="form" action="/SaveForm.do?Cls=<%=sys.getClass().getName()%>&OPType=<%=OPType%>" method="post" >
    <table align=center class="table_list">
	<TR>
		<TD align=right class="cx_Title">角色名称：</TD>
		<TD colspan="2"><%=Tools.getName(sys,"select base_name from t_base where base_type='role' and base_id='"+ROLE_ID+"'")%></TD>
	</TR>
	<TR>
		<TD align=right class="cx_Title">对象名称：</TD>
		<TD colspan="2"><%=Tools.getName(sys,"select OBJ_NAME from T_SYS_OBJ where OBJ_ID='"+obj_id+"'")%><input type="hidden" name="OBJ_ID" value="<%=obj_id%>"></TD>
	</TR>

<%
	String temp1="",temp2="";
    short temp=0;
	try{
    	sys.rs=sys.DoSQL("SELECT OBJ_PRIVMODULE,OBJ_PRIVTYPE FROM T_SYS_OBJ WHERE OBJ_ID='"+obj_id+"'");
	    if (sys.rs!=null){if (sys.rs.next()){temp2=sys.rs.getString(1);temp1=sys.rs.getString(2);}}
	    String[] PRIVMODULE=temp2.split(",");
	    String[] PRIVTYPE=temp1.split(";");
	
	    for (int i=0;i<PRIVMODULE.length;i++)
	    {
	    	out.print("<TR><TD align=right class='cx_Title'>"+Tools.getName(sys,"select PRIVMODULE_NAME from T_SYS_PRIVMODULE where PRIVMODULE_ID='"+PRIVMODULE[i]+"'")+"：</TD>");
	    	if(PRIVMODULE[i].equals("0")){
	    		temp=sys.getPR_BROWSE();temp1="PR_BROWSE";temp2=sys.getPR_BROWSE_SCOPE_NAME();
	    	}else if(PRIVMODULE[i].equals("1")){
	    		temp=sys.getPR_ADD();temp1="PR_ADD";
	    	}else if(PRIVMODULE[i].equals("2")){
	    		temp=sys.getPR_EDIT();temp1="PR_EDIT";temp2=sys.getPR_EDIT_SCOPE_NAME();
	    	}else if(PRIVMODULE[i].equals("3")){
	    		temp=sys.getPR_DEL();temp1="PR_DEL";temp2=sys.getPR_DEL_SCOPE_NAME();
	    	}else if(PRIVMODULE[i].equals("4")){
	    		temp=sys.getPR_AUDIT();temp1="PR_AUDIT";temp2=sys.getPR_AUDIT_SCOPE_NAME();
	    	}
            if(ID==null || OPType.equals("Update")){
		    	out.print("<td><select name='"+temp1+"' onChange=\"SelectChange(frm."+temp1+".value,'"+temp1+"')\">"+Tools.buildListItem(sys,"SELECT PRIVTYPE_ID,PRIVTYPE_NAME FROM T_sys_PRIVTYPE WHERE PRIVTYPE_ID IN('"+PRIVTYPE[i].replace(",","','")+"') order by PRIVTYPE_ID asc",String.valueOf(temp),true)+"</select></td>");
		    	out.print("<td><div id='"+temp1+"_div' style='display:"+(((temp==4|temp==5))?"":"none")+"'><input type='text' readonly name='"+temp1+"_SCOPE_NAME' size='30' value='"+temp2+"'><input style='WIDTH: 28;height:18' onClick='Select(frm."+temp1+".value,frm."+temp1+"_SCOPE,frm."+temp1+"_SCOPE_NAME)' type='button' value='选择'></div></TD>");
	    	}else{
	    		//if (i==1){ //对于新增功能
	    		//	if (temp.equals("0")){out.print("<TD colspan='2'>不能新增数据</TD>");}else{out.print("<TD colspan='2'>能新增数据</TD>");}
	    		//}else{ //对于其它功能
					out.print("<TD>"+Tools.getName(sys,"select PRIVTYPE_NAME from T_sys_PRIVTYPE where PRIVTYPE_ID='"+temp+"'")+"</TD>");
					if(temp==4|temp==5){out.print("<td>"+temp2+"</td>");}
				//}
			}
			out.print("</TR>");
	    }
	}catch(Exception e){e.printStackTrace();}
%>
	<TR>
		<TD align=right class="cx_Title">备注：</TD>
		<TD colspan="2"><% if(ID==null || OPType.equals("Update")){%><INPUT NAME="PR_MEMO" STYLE="width:100%" maxlength=100 value="<%=sys.getPR_MEMO()%>"><%;}else %><%=sys.getPR_MEMO()%></TD>
	</TR>
</table>
<%=BT%>
<INPUT NAME="PR_BROWSE_SCOPE" type="hidden" VALUE="<%=sys.getPR_BROWSE_SCOPE()%>">
<INPUT NAME="PR_EDIT_SCOPE" type="hidden" VALUE="<%=sys.getPR_EDIT_SCOPE()%>">
<INPUT NAME="PR_DEL_SCOPE" type="hidden" VALUE="<%=sys.getPR_DEL_SCOPE()%>">
<INPUT NAME="PR_AUDIT_SCOPE" type="hidden" VALUE="<%=sys.getPR_AUDIT_SCOPE()%>">
<%sys.CloseDataBase();}%>
</form>
</body>
</html>