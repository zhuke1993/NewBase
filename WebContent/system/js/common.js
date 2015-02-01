//配置data-delData ->删除的时候根据哪个字段来删除，推荐设置为key字段
//配置data-remoteUrl ->数据会根据选定的模块自动重新加载，同时需要指定url值 例子：data-remoteUrl="./test.do"

//cfData 返回json示例
/*cfData = {
            "canEdit": 0,
            "canDel": 0,
            form: {}
        }*/
$(function() {
	alert("..");
    window.rowdocid="";
    var fftype;//1:新增；2:编辑;3:删除

    $.extend($.fn.validatebox.defaults.rules, {     
        maxLength: {     
            validator: function(value, param){     
                return param[0] >= value.length;     
            },     
            message: '请输入最大{0}位字符.'    
        }     
    });   
    
    //底层CRUD方法 --end
    //事件绑定
    function reload(){
    	if($('#dg').hasClass("easyui-datagrid")) {
        	$("#dg").datagrid("reload");
    	}else if($('#dg').hasClass("easyui-treegrid")) {
    		$("#dg").treegrid("reload");
    	}
    }
    window.reloadData = function(conditions, params) {
    	$(conditions + ' [data-remoteurl]').each(function(){
    		var _this = $(this);
    		var url = _this.attr("data-remoteurl");
    		
    		if(url.indexOf(".do?") == -1) {
    			url = url + "?" + params;
    		}else{
    			url = url + "&" + params;
    		}

    		if(_this.hasClass("easyui-combotree")) {
    			_this.combotree("reload", url);
        	}else if(_this.hasClass("easyui-combobox")) {
        		_this.combobox("reload", url);
        	}	
    	})
    }
    
    $("#search-form__search").click(function(){
    	$("#search__form").form("submit", {
    		success: function(){
    			
    		}
    	})
    });
    
    $("#btnbox__reload").click(reload);
    
    $("#btnbox__edit").click(function(){
    	var row = $("#dg").datagrid("getSelected");
        if (row) {
        	rowdocid=row.id;
            $.get("/LoadData.do?ffmodel="+ffmodel, "id=" + row.id, function(e) {
            	if(e.canEdit){
            		fftype=2;
                	$("#btn-save").show();
                    $("#btn-del").hide();
                    $("#btn-edit").hide();
                    
                    $("#dlg").removeClass("hide");
                    $("#dlg p").remove();
                    
                    $("#dlg").dialog("open").dialog('setTitle', '修改信息');
            		$("#ff").form("load", e.form);
            		
            		
            		$("#ff [name='PR_BROWSE']").combobox(function(){
						url:'getModelPrivilege.do?rowdocid='+rowdocid
					});
		
            	}else{
            		$.messager.alert("提示", "你没有编辑权限！");
            	}
            });
            
        }else{
        	$.messager.alert("提示", "请首先在表格中选择一行数据后才能进行下一步操作！");
        }
    	
    });
    $("#btnbox__del").click(function() {
    	var row = $("#dg").datagrid("getSelected");
        if (row.id) {
        	$.messager.confirm("提示", "是否删除此条记录", function(r) {
                if (r) {
                     $.get("SaveForm.do?ffmodel="+ffmodel+"&fftype=3", "rowdocid=" + row.id, function(e) {
                        $.messager.alert("提示", e.message);
                        if (e.id == 1) {
                        	reload();
                        }
                    });
                }
            })
        }
    });
    
    $("#btnbox__add").click(function() {
    	fftype=1;
    	$("#btn-save").show();
        $("#btn-del").hide();
        $("#btn-edit").hide();
        $("#dlg").removeClass("hide");
        $("#dlg p").remove();
        $("#dlg input,#dlg textarea").val("");
        $("#dlg").dialog("open").dialog('setTitle', '新增信息');
        $("#ff").form("clear");
        $("#dlg input").each(function(){
        	if($(this).attr("data-default")) {
        		try{
        			$(this).textbox("setValue", $(this).attr("data-default"));
        		}catch(e){
        			$(this).val($(this).attr("data-default"));
        		}
        		
        	}
        })
    });

    $("#btnbox__see").click(function() {
        var row = $("#dg").datagrid("getSelected");
        if (row) {
        	rowdocid=row.id;
            $.get("LoadData.do?ffmodel="+ffmodel, "id=" + row.id, function(e) {
                if (!e) {
                    $.messager.alert("提示", "服务器加载出错！");
                    return;
                }
                $("#dlg td p").remove();
                $("#dlg").dialog("open").dialog('setTitle', '编辑信息');
                $("#ff").form("load", e.form);
                $("#dlg").addClass("hide");
                for (var i in e.form) {
                	if(e.form[i+"_$"]) 
                		$("#ff [name='" + i + "']").parents("td").append("<p>" + e.form[i+"_$"] + "</p>");
                	else
                		$("#ff [name='" + i + "']").parents("td").append("<p>" + e.form[i] + "</p>");
                }
                
                $("#btn-save").hide();
                if (e.canEdit) $("#btn-edit").show();
                else $("#btn-edit").hide();
                if (e.canDel) {
                    $("#btn-del").show();
                    $("#btn-del").click(function() {
                    	//rowdocid = $("#data-delData").val();
                        $.messager.confirm("提示", "是否删除此条记录", function(r) {
                            if (r) {
                                $.get("SaveForm.do?ffmodel="+ffmodel+"&fftype=3", "rowdocid=" + rowdocid, function(e) {
                                    //var tips = eval("(" + e + ")");
                                    $.messager.alert("提示", e.message);
                                    if (e.id == 1) {
                                        $("#dlg").dialog("close");
                                        reload();
                                    }
                                });
                            }
                        })
                    });
                } else $("#btn-del").hide();
            });
        }else{
        	$.messager.alert("提示", "请先选择");
        }
    });

    $("#btn-save").click(function() {
        $("#ff").form("submit", {
            url: "SaveForm.do?ffmodel="+ffmodel+"&fftype="+fftype+"&rowdocid="+rowdocid,
            onsubmit: function() {
                return $(this).form("validate");
            },
            success: function(result) {
             	result=eval("(" + result + ")");
            	$.messager.alert("提示", result.message);
                if (result.id == 1) {
                    $("#dlg").dialog("close");
                    reload();
                   // $("#dg").datagrid("load");
                   // $("#dlg").addClass("hide");
                }
            }
        });
    });

    $("#btn-edit").click(function(){
    	fftype=2;
        $("#btn-save").show();
        $("#btn-edit").hide();
        $("#btn-del").hide();
        $("#dlg").removeClass("hide");
        $("#dlg td p").remove();
    });


    $('#btn-more').click(function() {
        $('#win-search').window({
            modal: true,
            title: '更多查询',
            minimizable: false,
            maximizable: false
        }).window('open');
    });

    
})
