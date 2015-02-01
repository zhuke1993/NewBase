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
$(function(){
	window.ffmodel="<%=request.getParameter("ffmodel")%>";
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
			</div>
		</div>
	</div>
	<table id="dg" class="easyui-treegrid" style="width: 100%; height: 100%" data-options="idField:'id',treeField:'base_name',url:'/getPageBase.do?ffmodel=dept',method:'get',autoRowHeight:false,pageSize:20,fitColumns:true,striped:true,rownumbers:true,singleSelect:true,toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'id',align:'center',width:30">编号</th>
				<th data-options="field:'base_name',width:100">名称</th>
				<th data-options="field:'base_type1',align:'center',width:30">类型</th>
				<th data-options="field:'base_order',align:'center',width:30">排序</th>
				<th data-options="field:'base_ext1',align:'center',width:60">联系人</th>
				<th data-options="field:'base_ext2',align:'center',width:80">联系电话</th>
				<th data-options="field:'base_memo',width:120">备注</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" title="信息" class="easyui-dialog" style="width: 300px; height: 350px" closed="true" buttons="#dlg-buttons" data-options="modal:true">
		<form id="ff" method="post">
			<table style="width:100%">
				<tr>
					<td align="right" width="30%" class="cx_Title">子系统：</td>
					<td width="70%"><input class="easyui-combobox" name="SYS_ID" data-options="url:'/GetSelectBase.do?ffmodel=system',method:'get',required:true,panelHeight:'auto',valueField: 'id',textField: 'text',editable:false" style="width:148px"/>
				</tr>
				<tr>
					<td align="right" width="30%" class="cx_Title">上级部门：</td>
					<td width="70%"><input class="easyui-combotree" name="PARENT_ID" data-options="url:'/GetSelectBase.do?ffmodel=dept',method:'get',required:true,valueField: 'id',textField: 'text',editable:false" style="width:148px"/>
				</tr>
				<tr>
					<td align=right class="cx_Title">部门编号：</td>
					<td><input class="easyui-textbox easyui-validatebox" name="BASE_ID" data-options="required:true,validType:'maxLength[10]'"></TD>
				</tr>
				<tr>
					<td align="right" class="cx_Title">部门名称：</td>
					<td><input class="easyui-textbox easyui-validatebox" name="BASE_NAME" data-options="required:true,validType:'maxLength[80]'"></td>
				</tr>
				<tr>
					<td align=right nowrap class="cx_Title">排序：</td>
					<td><input class="easyui-numberbox" name="BASE_ORDER" data-default="0" data-options="required:true,min:0,max:999"></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">联系人：</td>
					<td><input class="easyui-textbox easyui-validatebox" name="BASE_EXT1" data-options="validType:'maxLength[100]'"></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">联系电话：</td>
					<td><input class="easyui-textbox easyui-validatebox" name="BASE_EXT2" data-options="validType:'maxLength[50]'"></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">备注：<br></td>
					<td><input class="easyui-textbox" name="BASE_MEMO" maxlength="100" data-options="multiline:true" style="height: 60px; width: 99%"></td>
				</tr>
			</table>
			<input type="hidden" name="BASE_TYPE" data-default="role"/>
		</form>
		
	</div>
	<div class="dlg-button" id="dlg-buttons">
		<a href="javascript:void(0)" id="btn-edit" class="easyui-linkbutton" iconcls="icon-edit" id="dl">编辑</a>
		<a href="javascript:void(0)" id="btn-del" class="easyui-linkbutton" iconcls="icon-cancel">删除</a>
		<a href="javascript:void(0)" id="btn-save" class="easyui-linkbutton" iconcls="icon-save">保存</a> <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-del">取消</a>
	</div>
</body>
</html>