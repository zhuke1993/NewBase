<%@page contentType="text/html;charset=utf-8" import="sei.security.*"%>
<!DOCTYPE html>
<html><head>
<meta charset="UTF-8">
<link rel="stylesheet" href="easyui/themes/default/easyui.css" />
<link rel="stylesheet" href="easyui/themes/icon.css">
<link rel="stylesheet" href="css/common.css" />

<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script src="easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="./js/common.js"></script>
<script type="text/javascript">


function formatProgress(value){
    return "<img src='./icons/pv"+value+".png'/>";
}
$(function(){
	window.ffmodel="privilege";
	$('#btn-search').click(function(){
		if(!$('#SYS_ID').combobox('getValue')){
			$.messager.alert("提示", "请选择系统！");
			return;
		}
		if(!$('#ROLE_ID').combotree('getValue')){
			$.messager.alert("提示", "请选择角色！");
			return;
		}
		$('#dg').datagrid({
			url: '/getPage.do?ffmodel='+ffmodel,
			queryParams: {
				SYS_ID: '=' + $('#SYS_ID').combobox('getValue'),
		        ROLE_ID: '=' + ($('#ROLE_ID').combotree('getValue')?$('#ROLE_ID').combotree('getValue'):"")
		    }
		});
	});	
	$("#btnbox__edit").click(function(){
		if(rowdocid){
			//$("#ff [name='PR_BROWSE']").combobox(function(){
			//	url:'/getModelPrivilege.do?rowdocid='+rowdocid
			//});
			//alert(rowdocid);
			$("#ff [name='PR_BROWSE']").combobox('reload','/getModelPrivilege.do?rowdocid='+rowdocid);
			//$('#PR_BROWSE').combobox('reload','/getModelPrivilege.do?rowdocid='+rowdocid);
		}
	});
})
</script>

</head>

<body class="manager">
   <div id="tb" style="padding: 5px 1px 5px">
		<div>
			<div class="btnBox clearfix">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" id="btnbox__reload" style="height: 22px">刷新</a>
				<a class="sep"></a> 
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="btnbox__add" style="height: 22px">新增</a>
				<a class="sep"></a> 
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="btnbox__edit" style="height: 22px">修改</a>
				<a class="sep"></a> 
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-look',plain:true" id="btnbox__see" style="height: 22px">查看</a>
				<a class="sep"></a> 
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" id="btnbox__del" style="height: 22px">删除</a>
				系统:<input id="SYS_ID" class="easyui-combobox" data-options="required:true,url:'/GetSelectBase.do?ffmodel=system',method:'get',panelHeight:'auto',valueField: 'id',textField: 'text',editable:false,onSelect: function(rec){reloadData('.btnBox', 'sys_id=' + rec.id)}" style="width:100px"/>
				角色:<input id="ROLE_ID" class="easyui-combotree" style="width:120px; height: 20px" data-remoteurl="/GetSelectBase.do?ffmodel=role" data-options="required:true,url:'/GetSelectBase.do?ffmodel=role',method:'get',panelHeight:'auto'"/>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="btn-search" style="height: 22px">查询</a>
			</div>
		</div>
	</div>
	<table id="dg" class="easyui-datagrid" style="width: 100%; height: 100%" data-options="autoRowHeight:false,pageSize:20,fitColumns:true,striped:true,rownumbers:true,singleSelect:true,pagination:true,method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'id',align:'center',width:30,hidden:true">编号</th>
				<th data-options="field:'obj_id',align:'center',width:30">编号</th>
				<th data-options="field:'obj_name',width:100">名称</th>
				<th data-options="field:'pr_browse',align:'center',formatter:formatProgress,width:30">查看权限</th>
				<th data-options="field:'pr_add',align:'center',formatter:formatProgress,width:30">新增权限</th>
				<th data-options="field:'pr_edit',align:'center',formatter:formatProgress,width:30">修改权限</th>
				<th data-options="field:'pr_del',align:'center',formatter:formatProgress,width:30">删除权限</th>
				<th data-options="field:'pr_audit',align:'center',formatter:formatProgress,width:30">定制权限</th>
				<th data-options="field:'pr_memo',width:120">备注</th>
			</tr>
		</thead>
	</table>

	<div id="dlg" title="信息" class="easyui-dialog" style="width: 300px; height: 350px" closed="true" buttons="#dlg-buttons" data-options="modal:true">
		<form id="ff" method="post">
			<table style="width:100%">
				<tr>
					<td align=right class="cx_Title" style="widows:30%">子系统：</td>
					<td><input class="easyui-textbox" name="SYS_NAME" data-options="disabled:true"></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">模块编号：</td>
					<td><input class="easyui-textbox" name="OBJ_ID" data-options="disabled:true"></TD>
				</tr>
				<tr>
					<td align="right" class="cx_Title">模块名称：</td>
					<td><input class="easyui-textbox" name="OBJ_NAME" data-options="disabled:true"></td>
				</tr>
				<tr>
					<td align=right class="cx_Title">查看权限：</td>
					<td><input class="easyui-combobox" name="PR_BROWSE" id="PR_BROWSE" data-options="required:true"></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">新增权限：</td>
					<td><input class="easyui-combobox" name="PR_ADD" data-options="required:true"></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">修改权限：</td>
					<td><input class="easyui-combobox" name="PR_EDIT" data-options="required:true"></TD>
				</tr>
								<tr>
					<td align=right class="cx_Title">删除权限：</td>
					<td><input class="easyui-combobox" name="PR_DEL" data-options="required:true"></TD>
				</tr>
								<tr>
					<td align=right class="cx_Title">定制权限：</td>
					<td><input class="easyui-combobox" name="PR_AUDIT" data-options="required:true"></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">备注：<br></td>
					<td><input class="easyui-textbox" name="BASE_MEMO" maxlength="100" data-options="multiline:true" style="height: 60px; width: 99%"></td>
				</tr>
			</table>
			<input type="hidden" name="SYS_ID" readonly/>
		</form>
		
	</div>
	<div class="dlg-button" id="dlg-buttons">
		<a href="javascript:void(0)" id="btn-edit" class="easyui-linkbutton" iconcls="icon-edit" id="dl">编辑</a>
		<a href="javascript:void(0)" id="btn-del" class="easyui-linkbutton" iconcls="icon-cancel">删除</a>
		<a href="javascript:void(0)" id="btn-save" class="easyui-linkbutton" iconcls="icon-save">保存</a> <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-del">取消</a>
	</div>
</body>
</html>