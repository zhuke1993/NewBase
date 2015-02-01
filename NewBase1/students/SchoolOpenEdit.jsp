<%@ page contentType="text/html;charset=utf-8" session="true" buffer="none"%>
<%@ page import="sei.core.*,sei.util.*,students.*" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="../system/css/page.css">
    <script type="text/javascript" src="../system/js/page.js"></script>
    <script language="JavaScript" type="text/JavaScript" src="../system/My97DatePicker/WdatePicker.js"></script>
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
        SchoolOpen sys=new SchoolOpen();
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
            <td style="width:80px" align=right class="cx_Title">登记人：</td>
            <td width="120">&nbsp;<%=sys.getPUSER_NAME()%></td>
        </tr>
        <tr>
            <td align=right class="cx_Title">登记时间：</td>
            <td>&nbsp;<%=sys.getPUSER_TIME()%></td>
        </tr>
        <tr>
            <td align=right class="cx_Title">学期：</td>
            <td><INPUT style="WIDTH: 98%" class="textbox" NAME="XQ" readonly value="<%=sys.getCurrXQ()%>"></td>
        </tr>
        <tr>
            <td align=right class="cx_Title">开学时间：</td>
            <td><INPUT style="WIDTH: 98%" class="textbox" name="OPEN_TIME" id="OPEN_TIME" value="<%=sys.getOPEN_TIME()%>" onfocus="WdatePicker()" onKeyPress='return event.keyCode>=48&&event.keyCode<=57||event.keyCode==45'></td>
        </tr>
        <%} else {%>
        <tr>
            <td style="width:80px" align=right class="cx_Title">登记人：</td>
            <td width="120">&nbsp;<%=sys.getPUSER_NAME()%></td>
        </tr>
        <tr>
            <td align=right class="cx_Title">登记时间：</td>
            <td>&nbsp;<%=sys.getPUSER_TIME()%></td>
        </tr>
        <tr>
            <td width="50" class="cx_Title" align=right>学期：</td>
            <td>&nbsp;<%=sys.getXQ()%></td>
        </tr>
        <tr>
            <td align=right class="cx_Title">开学时间：</td>
            <td>&nbsp;<%=sys.getOPEN_TIME()%></td>
        </tr>
        <%}%>
    </table>
</form>
<div style="text-align:center;padding:5px;height:27px;margin:5px" id="cc">
    <%=BT%>
</div>
<script>
    $('#XM').combogrid({selectOnNavigation:true})
    function submitForm(){
        var c1=document.getElementById("OPEN_TIME");
        if(c1.value==""){ alert("请选择开学日期！");return; }
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