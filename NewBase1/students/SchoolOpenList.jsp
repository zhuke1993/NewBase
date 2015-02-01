<%@page contentType="text/html;charset=utf-8" session="true" import="sei.core.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../system/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../system/easyui/themes/icon.css">
    <script type="text/javascript" src="../system/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../system/easyui/jquery.easyui.min.js"></script>
    <script src="../system/easyui/locale/easyui-lang-zh_CN.js"></script>
    <style>html,body{height: 100%;}body {margin: 2px;overflow: hidden;}</style>
</head>
<body marginheight="10">
<%
    String UserID= (String)session.getAttribute("UserID");
    if(UserID==null){
        out.print("<SCRIPT>top.location.href='/';</SCRIPT>");
        return;
    }else{
        Privilege sys=new Privilege();
        Pv pv=sys.getPvPageList("kxsj", UserID, "", "PUSER_ID", Pv.Browser,Pv.Add);
        if(!pv.PR_BROWSE){
            out.print("<div><p style='font-size:14px'>对不起，权限不够，你无权进行本操作！</p></div>");
            sys.CloseDataBase();
            return;
        }
        String Type = request.getParameter("Type");if (Type == null)Type= "";
%>
<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <%if(pv.PR_ADD){%><a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">新增</a><%}%>
        <a href="#" class="easyui-linkbutton" iconCls="icon-sum" onclick="see()">查看</a>
    </div>
</div>
<table id="dg" class="easyui-datagrid" style="width:100%;height: 100%" data-options="autoRowHeight:false,pageSize:20,fitColumns:true,striped:true,rownumbers:true,singleSelect:true,pagination:true,url:'/getPage.do?model=kxsj',method:'get',toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'c1',align:'center',width:80">学期</th>
        <th data-options="field:'c2',align:'center',width:150">开学时间</th>
        <th data-options="field:'c3',align:'center',width:80">登记人</th>
        <th data-options="field:'c4',align:'center',width:150">登记时间</th>
    </tr>
    </thead>
</table>

<script type="text/javascript">
    function see(){top.OpenWindow('/students/SchoolOpenEdit.jsp?ID='+$("#dg").datagrid("getSelected").c1,'EditWindow','开学时间',700,400);}
    <%if(pv.PR_ADD){%>function add(){top.OpenWindow('/students/SchoolOpenEdit.jsp','EditWindow','开学时间',700,400);}<%}%>
</script>
<%sys.CloseDataBase();}%>
</body>
</html>