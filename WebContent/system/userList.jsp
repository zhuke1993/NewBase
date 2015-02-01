<%@page contentType="text/html;charset=utf-8" import="sei.security.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="easyui/themes/default/easyui.css" />
<link rel="stylesheet" href="easyui/themes/icon.css">
<link rel="stylesheet" href="css/common.css" />

<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script src="easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="./js/common.js"></script>
<script type="text/javascript">
	$(
			function() {
				window.ffmodel = "user";
				var searchData = [ {
					id : "user_id",
					text : "用户编号",
					data : [ {
						id : 1,
						text : "是"
					}, {
						id : 2,
						text : "否"
					} ]
				}, {
					id : "user_name",
					text : "用户名称",
					data : "/getdate.do"
				} ];
				$('#btn-search').click(
						function() {
							$('#dg').datagrid(
									{
										queryParams : {
											SYS_ID : '='
													+ $('#SYS_ID').combobox(
															'getValue'),
											ROLE_ID : '='
													+ ($('#ROLE_ID').combotree(
															'getValue') ? $(
															'#ROLE_ID')
															.combotree(
																	'getValue')
															: ""),
											USER_NAME : 'Like%'
													+ $("#USER_NAME").textbox(
															"getValue") + '%',
											DEPT_ID : 'Tree(\'dept\',1,?)'
													+ ($('#DEPT_ID').combotree(
															'getValue') ? $(
															'#DEPT_ID')
															.combotree(
																	'getValue')
															: "")
										}
									});
						});
				window.rowdocid = "";
				var fftype;//1:新增；2:编辑;3:删除

				$.extend($.fn.validatebox.defaults.rules, {
					maxLength : {
						validator : function(value, param) {
							return param[0] >= value.length;
						},
						message : '请输入最大{0}位字符.'
					}
				});

				//底层CRUD方法 --end
				//事件绑定
				function reload() {
					if ($('#dg').hasClass("easyui-datagrid")) {
						$("#dg").datagrid("reload");
					} else if ($('#dg').hasClass("easyui-treegrid")) {
						$("#dg").treegrid("reload");
					}
				}
				window.reloadData = function(conditions, params) {
					$(conditions + ' [data-remoteurl]').each(function() {
						var _this = $(this);
						var url = _this.attr("data-remoteurl");

						if (url.indexOf(".do?") == -1) {
							url = url + "?" + params;
						} else {
							url = url + "&" + params;
						}

						if (_this.hasClass("easyui-combotree")) {
							_this.combotree("reload", "../" + url);
						} else if (_this.hasClass("easyui-combobox")) {
							_this.combobox("reload", "../" + url);
						}
					});
				};

				$("#search-form__search").click(function() {
					$("#search__form").form("submit", {
						success : function() {

						}
					})
				});

				$("#btnbox__reload").click(reload);

				$("#btnbox__edit")
						.click(
								function() {
									var row = $("#dg").datagrid("getSelected");
									if (row) {
										rowdocid = row.id;
										$
												.get(
														"../LoadData.do?ffmodel="
																+ ffmodel,
														"id=" + row.id,
														function(e) {
															if (e.canEdit) {
																fftype = 2;
																$("#btn-save")
																		.show();
																$("#btn-del")
																		.hide();
																$("#btn-edit")
																		.hide();

																$("#dlg")
																		.removeClass(
																				"hide");
																$("#dlg p")
																		.remove();

																$("#dlg")
																		.dialog(
																				"open")
																		.dialog(
																				'setTitle',
																				'修改信息');
																$("#ff").form(
																		"load",
																		e.form);

																$(
																		"#ff [name='PR_BROWSE']")
																		.combobox(
																				function() {
																					url: '../getModelPrivilege.do?rowdocid='
																							+ rowdocid
																				});

															} else {
																$.messager
																		.alert(
																				"提示",
																				"你没有编辑权限！");
															}
														});

									} else {
										$.messager.alert("提示",
												"请首先在表格中选择一行数据后才能进行下一步操作！");
									}

								});
				$("#btnbox__del").click(
						function() {
							var row = $("#dg").datagrid("getSelected");
							if (row.id) {
								$.messager.confirm("提示", "是否删除此条记录",
										function(r) {
											if (r) {
												$.get(
														"../SaveForm.do?ffmodel="
																+ ffmodel
																+ "&fftype=3",
														"rowdocid=" + row.id,
														function(e) {
															$.messager.alert(
																	"提示",
																	e.message);
															if (e.id == 1) {
																reload();
															}
														});
											}
										})
							}
						});

				$("#btnbox__add")
						.click(
								function() {
									fftype = 1;
									$("#btn-save").show();
									$("#btn-del").hide();
									$("#btn-edit").hide();
									$("#dlg").removeClass("hide");
									$("#dlg p").remove();
									$("#dlg input,#dlg textarea").val("");
									$("#dlg").dialog("open").dialog('setTitle',
											'新增信息');
									$("#ff").form("clear");
									$("#dlg input")
											.each(
													function() {
														if ($(this).attr(
																"data-default")) {
															try {
																$(this)
																		.textbox(
																				"setValue",
																				$(
																						this)
																						.attr(
																								"data-default"));
															} catch (e) {
																$(this)
																		.val(
																				$(
																						this)
																						.attr(
																								"data-default"));
															}

														}
													})
								});

				$("#btnbox__see")
						.click(
								function() {
									var row = $("#dg").datagrid("getSelected");
									if (row) {
										rowdocid = row.id;
										$
												.get(
														"../LoadData.do?ffmodel="
																+ ffmodel,
														"id=" + row.id,
														function(e) {
															if (!e) {
																$.messager
																		.alert(
																				"提示",
																				"服务器加载出错！");
																return;
															}
															$("#dlg td p")
																	.remove();
															$("#dlg")
																	.dialog(
																			"open")
																	.dialog(
																			'setTitle',
																			'编辑信息');
															$("#ff").form(
																	"load",
																	e.form);
															$("#dlg").addClass(
																	"hide");
															for ( var i in e.form) {
																if (e.form[i
																		+ "_$"])
																	$(
																			"#ff [name='"
																					+ i
																					+ "']")
																			.parents(
																					"td")
																			.append(
																					"<p>"
																							+ e.form[i
																									+ "_$"]
																							+ "</p>");
																else
																	$(
																			"#ff [name='"
																					+ i
																					+ "']")
																			.parents(
																					"td")
																			.append(
																					"<p>"
																							+ e.form[i]
																							+ "</p>");
															}

															$("#btn-save")
																	.hide();
															if (e.canEdit)
																$("#btn-edit")
																		.show();
															else
																$("#btn-edit")
																		.hide();
															if (e.canDel) {
																$("#btn-del")
																		.show();
																$("#btn-del")
																		.click(
																				function() {
																					//rowdocid = $("#data-delData").val();
																					$.messager
																							.confirm(
																									"提示",
																									"是否删除此条记录",
																									function(
																											r) {
																										if (r) {
																											$
																													.get(
																															"SaveForm.do?ffmodel="
																																	+ ffmodel
																																	+ "&fftype=3",
																															"rowdocid="
																																	+ rowdocid,
																															function(
																																	e) {
																																//var tips = eval("(" + e + ")");
																																$.messager
																																		.alert(
																																				"提示",
																																				e.message);
																																if (e.id == 1) {
																																	$(
																																			"#dlg")
																																			.dialog(
																																					"close");
																																	reload();
																																}
																															});
																										}
																									})
																				});
															} else
																$("#btn-del")
																		.hide();
														});
									} else {
										$.messager.alert("提示", "请先选择");
									}
								});

				$("#btn-save").click(
						function() {
							$("#ff").form(
									"submit",
									{
										url : "../SaveForm.do?ffmodel="
												+ ffmodel + "&fftype=" + fftype
												+ "&rowdocid=" + rowdocid,
										onsubmit : function() {
											return $(this).form("validate");
										},
										success : function(result) {
											result = eval("(" + result + ")");
											$.messager.alert("提示",
													result.message);
											if (result.id == 1) {
												$("#dlg").dialog("close");
												reload();
												// $("#dg").datagrid("load");
												// $("#dlg").addClass("hide");
											}
										}
									});
						});

				$("#btn-edit").click(function() {
					fftype = 2;
					$("#btn-save").show();
					$("#btn-edit").hide();
					$("#btn-del").hide();
					$("#dlg").removeClass("hide");
					$("#dlg td p").remove();
				});

				$('#btn-more').click(function() {
					$('#win-search').window({
						modal : true,
						title : '更多查询',
						minimizable : false,
						maximizable : false
					}).window('open');
					$("#test1").combobox({
						data : searchData,
						onSelect : function(rec) {
							
							$('#cc1').attr('data-text', rec.id);
							for(var i=0;i<searchData.length;i++){
								if(rec.id==searchData[i].id){
									if(typeof searchData[i].data === "string"){
										$("#cc1").combobox({
											onChange: function(rec){
						                		var value = $('#cc1').attr('data-text');
						                		if(rec && value) {
						                			var url = '/MoreInformation.do?model=t_sys_user&filed='+value+'&value='+rec;
						                			$('#cc2').combobox('reload', url);
						                		}
						                	}
										});
									}else{
										$("#cc1").combobox({
											data : searchData[i].data
										});
									}
									
								}
							}
						}
					});
				});

			})
</script>

</head>

<body class="manager">
	<div id="win-search" class="easyui-window"
		data-options="collapsible:false,title:'更多查询',minimizable:false,maximizable:false,closed:true">
		<form id="search-form">
			<table>
				<tr>
					<td><select style="width: 80px" name="cond-name" id="test1"
						class="easyui-combobox"
						data-options="panelHeight:'auto',valueField: 'id',textField: 'text'">

					</select></td>
					<td><select name="cond-oper" class="easyui-combobox"
						data-options="panelHeight:'auto'"><option value="=">等于</option>
							<option value="<>">不等于</option>
							<option value=">">大于</option>
							<option value=">=">大于等于</option>
							<option value="<">小于</option>
							<option value="<=">小于等于</option>
							<option value="like">包含</option></select></td>
					<td><input id="cc1" class="easyui-combobox"
						data-options="valueField: 'id',textField: 'text'" />
					</td>
					<td><select name="cond-bool" class="easyui-combobox"
						data-options="panelHeight:'auto'"><option value=" and ">并且</option>
							<option value=" or ">或者</option></select></td>
				</tr>

				<tr>
					<td><select name="cond-name" class="easyui-combobox"
						data-options="
                	panelHeight:'auto',
                	valueField: 'id',
			        textField: 'text',
			        onSelect: function(rec){   
			            $('#cc2').attr('data-text', rec.id);
			        }">
							<option value="USER_ID">用户编号</option>
							<option value="ROLE_D">角色名称</option>
							<option value="USER_LEAD">是否领导</option>
					</select></td>
					<td><select name="cond-oper" class="easyui-combobox"
						data-options="panelHeight:'auto'"><option value="=">等于</option>
							<option value="<>">不等于</option>
							<option value=">">大于</option>
							<option value=">=">大于等于</option>
							<option value="<">小于</option>
							<option value="<=">小于等于</option>
							<option value="like">包含</option></select></td>
					<td><input id="cc2" class="easyui-combobox" name="dept"
						data-options="
                	valueField: 'id',
			        textField: 'text',
                	onChange: function(rec){
                		var value = $('#cc2').attr('data-text');
                		if(rec && value) {
                			var url = '/MoreInformation.do?model=t_sys_user&filed='+rec+'&value='+value;
                			$('#cc2').combobox('reload', url);
                		}
			        }" />
					</td>
					<td><select name="cond-bool" class="easyui-combobox"
						data-options="panelHeight:'auto'"><option value=" and ">并且</option>
							<option value=" or ">或者</option></select></td>
				</tr>


				<tr>
					<td><select name="cond-name" class="easyui-combobox"
						data-options="
                	panelHeight:'auto',
                	valueField: 'id',
			        textField: 'text',
			        onSelect: function(rec){   
			            $('#cc3').attr('data-text', rec.id);
			        }">
							<option value="USER_ID">用户编号</option>
							<option value="ROLE_D">角色名称</option>
							<option value="USER_LEAD">是否领导</option>
					</select></td>
					<td><select name="cond-oper" class="easyui-combobox"
						data-options="panelHeight:'auto'"><option value="=">等于</option>
							<option value="<>">不等于</option>
							<option value=">">大于</option>
							<option value=">=">大于等于</option>
							<option value="<">小于</option>
							<option value="<=">小于等于</option>
							<option value="like">包含</option></select></td>
					<td><input id="cc3" class="easyui-combobox" name="dept"
						data-options="
                	valueField: 'id',
			        textField: 'text',
                	onChange: function(rec){   
                		var value = $('#cc3').attr('data-text');
                		if(rec && value) {
                			var url = '/MoreInformation.do?model=t_sys_user&filed='+rec+'&value='+value;
                			$('#cc3').combobox('reload', url);
                		}
			        }" />
					</td>
					<td><select name="cond-bool" class="easyui-combobox"
						data-options="panelHeight:'auto'"><option value=" and ">并且</option>
							<option value=" or ">或者</option></select></td>
				</tr>

				<tr>
					<td><select name="cond-name" class="easyui-combobox"
						data-options="
                	panelHeight:'auto',
                	valueField: 'id',
			        textField: 'text',
			        onSelect: function(rec){   
			            $('#cc4').attr('data-text', rec.id);
			        }">
							<option value="USER_ID">用户编号</option>
							<option value="ROLE_D">角色名称</option>
							<option value="USER_LEAD">是否领导</option>
					</select></td>
					<td><select name="cond-oper" class="easyui-combobox"
						data-options="panelHeight:'auto'"><option value="=">等于</option>
							<option value="<>">不等于</option>
							<option value=">">大于</option>
							<option value=">=">大于等于</option>
							<option value="<">小于</option>
							<option value="<=">小于等于</option>
							<option value="like">包含</option></select></td>
					<td><input id="cc3" class="easyui-combobox" name="dept"
						data-options="
                	valueField: 'id',
			        textField: 'text',
                	onChange: function(rec){   
                		var value = $('#cc4').attr('data-text');
                		if(rec && value) {
                			var url = '/MoreInformation.do?model=t_sys_user&filed='+rec+'&value='+value;
                			$('#cc4').combobox('reload', url);
                		}
			        }" />
					</td>
					<td><select name="cond-bool" class="easyui-combobox"
						data-options="panelHeight:'auto'"><option value=" and ">并且</option>
							<option value=" or ">或者</option></select></td>
				</tr>

			</table>
		</form>
		<hr />
		<div class="search-form__btn">
			<a href="#" class="easyui-linkbutton" id="search-form__search"
				iconCls="icon-search">查询</a> <a href="#" class="easyui-linkbutton"
				onclick="javascript:$('#win-search').window('close');"
				iconcls="icon-no">取消</a>
		</div>
	</div>


	<div id="tb" style="padding: 5px 1px 5px">
		<div>
			<div class="btnBox clearfix">
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-reload',plain:true" id="btnbox__reload"
					style="height: 22px">刷新</a> <a class="sep"></a> <a href="#"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true" id="btnbox__add"
					style="height: 22px">新增</a> <a class="sep"></a> <a href="#"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-edit',plain:true" id="btnbox__edit"
					style="height: 22px">修改</a> <a class="sep"></a> <a href="#"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-look',plain:true" id="btnbox__see"
					style="height: 22px">查看</a> <a class="sep"></a> <a href="#"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel',plain:true" id="btnbox__del"
					style="height: 22px">删除</a> 系统:<input class="easyui-combobox"
					id="SYS_ID"
					data-options="url:'GetSelectBase.do?ffmodel=system',method:'get',panelHeight:'auto',valueField: 'id',textField: 'text',editable:false,onSelect: function(rec){reloadData('.btnBox', 'sys_id=' + rec.id)}"
					style="width: 100px" />
				<!--  <select class="easyui-combobox" id="SYS_ID" data-options="panelHeight:'auto',valueField: 'id',textField: 'text',onSelect: function(rec){reloadData('.btnBox', 'sys_id=' + rec.id)}"><option value="" selected="selected"></option><option value="soft">系统1</option><option value="wsdx">系统2</option></select>-->
				<!-- 类型:<input id="USER_TYPE" class="easyui-combobox" style="width: 80px; height: 20px" data-options="url:'/GetSelectData.do?ffmodel=role',method:'get',valueField:'c1',textField:'c2',panelHeight:'auto'"/> -->
				角色:<input id="ROLE_ID" class="easyui-combotree"
					style="width: 80px; height: 20px"
					data-remoteurl="GetSelectBase.do?ffmodel=role"
					data-options="url:'../GetSelectBase.do?ffmodel=role',method:'get',panelHeight:'auto'" />
				姓名:<input id="USER_NAME" class="easyui-textbox"
					style="width: 80px; height: 20px"> 机构:<input id='DEPT_ID'
					class="easyui-combotree"
					data-options="url:'/GetSelectBase.do?ffmodel=dept',method:'get'"
					style="width: 150px; height: 20px"> <a href="#"
					class="easyui-linkbutton" iconCls="icon-search" id="btn-search"
					style="height: 22px">查询</a> <a href="#" class="easyui-linkbutton"
					iconCls="icon-more" id="btn-more" style="height: 22px">更多</a>
			</div>
		</div>
	</div>
	<table id="dg" class="easyui-datagrid"
		style="width: 100%; height: 100%"
		data-options="autoRowHeight:false,pageSize:20,fitColumns:true,striped:true,rownumbers:true,singleSelect:true,pagination:true,url:'/getPage.do?ffmodel=user',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'id',align:'center',width:80">登录名</th>
				<th data-options="field:'user_name',align:'center',width:100">姓名</th>
				<th data-options="field:'user_type',align:'center',width:80">用户类型</th>
				<th data-options="field:'dept_name',align:'center',width:120">机构部门</th>
				<th data-options="field:'role_name',align:'center',width:80">角色</th>
				<th data-options="field:'USER_JOB',align:'center',width:80">职务级别</th>
				<th data-options="field:'USER_LEAD',align:'center',width:60">是否领导</th>
				<th data-options="field:'USER_MOB_PHONE',align:'center',width:120">联系电话</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" title="用户管理" class="easyui-dialog"
		style="width: 650px; height: 450px; padding: 5px 5px;" closed="true"
		buttons="#dlg-buttons" data-options="modal:true">
		<form id="ff" method="post">
			<table align=center style="width: 95%">
				<tr>
					<td align="right" width="10%" class="cx_Title">子系统：</td>
					<td width="40%"><input class="easyui-combobox" name="SYS_ID"
						data-options="url:'../GetSelectBase.do?ffmodel=system',method:'get',required:true,panelHeight:'auto',valueField: 'id',textField: 'text',editable:false,onSelect: function(rec){reloadData('#ff', 'sys_id=' + rec.id)}"
						style="width: 148px" />
					<td align=right nowrap width="10%" class="cx_Title">用户类型：</td>
					<td nowrap width="40%"><select class="easyui-combobox"
						name="USER_TYPE"
						data-options="panelHeight:'auto',required:true,editable:false"
						style="width: 148px"><option value="职能部门">职能部门</option>
							<option value="辅导员">辅导员</option>
							<option value="学生">学生</option></select></td>
				</tr>
				<tr>
					<td align="right" class="cx_Title"><font color=red>*登录名</font>：</td>
					<td><input class="easyui-textbox easyui-validatebox"
						name="USER_ID"
						data-options="required:true,validType:'maxLength[10]'"></td>
					<td align=right class="cx_Title">移动电话：</td>
					<td><input class="easyui-numberbox easyui-validatebox"
						name="USER_MOB_PHONE" data-options="validType:'maxLength[11]'"></td>
				</tr>
				<tr>
					<td align=right class="cx_Title">用户姓名：</td>
					<td><input class="easyui-textbox easyui-validatebox"
						name="USER_NAME"
						data-options="required:true,validType:'maxLength[20]'"></TD>
					<td align=right class="cx_Title">通讯地址：</td>
					<td><input class="easyui-textbox easyui-validatebox"
						name="USER_ADDR" data-options="validType:'maxLength[60]'"></TD>
				</tr>
				<tr valign=middle>
					<td align=right class="cx_Title">密码：</td>
					<td><input class="easyui-textbox easyui-validatebox"
						name="USER_PWD"
						data-options="required:true,validType:'maxLength[15]'"></TD>
					<td align=right class="cx_Title">职务级别：</td>
					<td><input class="easyui-textbox easyui-validatebox"
						name="USER_JOB" data-options="validType:'maxLength[20]'"></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">登录IP：</td>
					<td><input class="easyui-textbox easyui-validatebox"
						name="USER_IP" data-options="validType:'maxLength[500]'"></TD>
					<td align=right class="cx_Title">电子邮件：</td>
					<td><input class="easyui-textbox easyui-validatebox"
						name="USER_EMAIL" data-options="validType:'maxLength[30]'"></td>
				</tr>
				<tr>
					<td align=right class="cx_Title">用户角色：</td>
					<td>
						<!--<input class="easyui-combotree" name="ROLE_ID" data-change data-remoteUrl="/GetSelectBase.do?ffmodel=role" data-options="url:'/GetSelectBase.do?ffmodel=role',method:'get',required:true" style="width:148px"/></TD>-->
						<input name="ROLE_ID" class="easyui-combobox" data-change
						data-remoteUrl="/GetSelectBase.do?ffmodel=role"
						data-options="url:'/GetSelectBase.do?ffmodel=role',method:'get',panelHeight:'auto',required:true,valueField:'id',textField:'text',editable:false"
						style="width: 148px" />
					<td align=right class="cx_Title">用户岗位：</td>
					<td><input class="easyui-textbox easyui-validatebox"
						name="USER_DUTY" data-options="validType:'maxLength[20]'"></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">组织机构：</td>
					<td><input class="easyui-combotree" data-change name="DEPT_ID"
						data-options="url:'/GetSelectBase.do?ffmodel=dept',method:'get',required:true,editable:false"
						style="width: 148px" /></TD>
					<td align=right nowrap class="cx_Title">是否领导：</td>
					<td><select class="easyui-combobox" name="USER_LEAD"
						data-options="panelHeight:'auto',editable:false"
						style="width: 148px"><option value="是">是</option>
							<option value="否">否</option></select></TD>
				</tr>
				<tr>
					<td align=right nowrap class="cx_Title">用户状态：</td>
					<td><select class="easyui-combobox" name="USER_STATUS"
						data-options="panelHeight:'auto',required:true,editable:false"
						style="width: 148px"><option value="0">停用</option>
							<option value="1" selected="selected">启用</option></select></td>
					<td align=right nowrap class="cx_Title">邮政编码：</td>
					<td><input class="easyui-numberbox easyui-validatebox"
						name="USER_POST_CODE" data-options="validType:'maxLength[6]'"></TD>
				</tr>
				<tr>
					<td align=right nowrap class="cx_Title">序号：</td>
					<td><input class="easyui-numberbox easyui-validatebox"
						name="USER_ORDER" data-default="0"
						data-options="required:true,min:0,max:999"></TD>
					<td align=right nowrap class="cx_Title">家庭电话：</td>
					<td><input class="easyui-textbox easyui-validatebox"
						name="USER_HOME_PHONE" data-options="validType:'maxLength[15]'"></TD>
				</tr>
				<tr>
					<td align=right nowrap class="cx_Title">密码有效期：</td>
					<td><input class="easyui-numberbox" name="USER_PSWD_DAYS"
						value="0" data-options="min:0,max:999"></TD>
					<td align=right nowrap class="cx_Title">上次更改密码日期：</td>
					<td></TD>
				</tr>
				<tr>
					<td align=right class="cx_Title">备注：<br></td>
					<td colspan="3"><input class="easyui-textbox" name="USER_MEMO"
						maxlength="100" data-options="multiline:true"
						style="height: 60px; width: 99%"></td>
				</tr>
			</table>

		</form>

	</div>
	<div class="dlg-button" id="dlg-buttons">
		<a href="javascript:void(0)" id="btn-edit" class="easyui-linkbutton"
			iconcls="icon-edit" id="dl">编辑</a> <a href="javascript:void(0)"
			id="btn-del" class="easyui-linkbutton" iconcls="icon-cancel">删除</a> <a
			href="javascript:void(0)" id="btn-save" class="easyui-linkbutton"
			iconcls="icon-save">保存</a> <a href="javascript:void(0)"
			class="easyui-linkbutton"
			onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-del">取消</a>
	</div>
</body>
</html>