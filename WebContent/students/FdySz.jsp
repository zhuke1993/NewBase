<html>
<%@ page contentType="text/html;charset=utf-8" buffer="none"%>
<%@ page import="sei.core.*" %>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../system/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../system/easyui/themes/icon.css">
    <script type="text/javascript" src="../system/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../system/easyui/jquery.easyui.min.js"></script>
    <script src="../system/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body leftMargin=40 topMargin=40 bgcolor=#FFFFFF>
<%
	String UserID= (String)session.getAttribute("UserID"); 
	if(UserID==null){
        out.print("<SCRIPT>top.location.href='/';</SCRIPT>");
        return;
    }else{
		Privilege sys=new Privilege();
       // Pv pv=sys.getPvPageList("fdysz", UserID, "", "", Pv.Browser);
		if (sys.getPvCanDo(UserID, "fdysz")==false){
			out.print("<SCRIPT>window.location.href='../system/nopv.html';</SCRIPT>");
		}else{
	
%>
<div class="easyui-panel" title="辅导员带班设置" style="width:400px">
    <div style="padding:10px 60px 20px 60px">
        <form id="ff" method="post">
            <table cellpadding="5">
                <tr>
                    <td>年级:</td>
                    <td><select id="NJ" class="easyui-combobox" style="width:100px;"><option value="" selected></option><%=students.StuTools.getNJ("")%></select></td>
                </tr>
                <tr>
                    <td>专业:</td>
                    <td><input class="easyui-combotree" ID='ZY_ID' data-options="url:'/TreeServlet.do?model=zy',method:'get',onSelect:function(node){var tree = $(this).tree;var isLeaf = tree('isLeaf', node.target);if (!isLeaf){$('#ZY_ID').combotree('clear');}}" style="width:200px;"></td>
                </tr>
                <tr>
                    <td>辅导员:</td>
                    <td><select id="FDY_ID" class="easyui-combogrid" style="width:100%" data-options="
			panelWidth: 600,
			idField: 'c1',
			textField: 'c2',
			url: '/getPage.do?model=user&rows=10&user_type==$辅导员&Fileds=user_id,user_name,DEPT_NAME',
			method: 'get',
			mode: 'remote',
			pagination : true,
			striped: true,
			rownumbers: true,
			columns: [[
				{field:'c1',title:'编号',width:80},
				{field:'c2',title:'姓名',width:80},
				{field:'c3',title:'学院',width:100,align:'center'}
			]],
			keyHandler: {
				up: function() {},
                down: function() {},
                enter: function() {},
                query: function(q) {
                    //动态搜索
                    $('#XM').combogrid('grid').datagrid('reload', {'user_name':'Like$%'+q+'%'});
                    $('#XM').combogrid('setValue', q);
                }
			},
			fitColumns: true
		"/></td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitForm()">下一步</a>
        </div>
    </div>
</div>
<script>
    $('#FDY_ID').combogrid({selectOnNavigation:true})
    function submitForm(){
        $('#ff').form('submit');
    }
</script>
</body>
<%}sys.CloseDataBase();}%>
</html>