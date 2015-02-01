<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>管理平台</title>
    <link href="system/easyui/themes/default/easyui.css" rel="stylesheet"/>
    <link href="system/easyui/themes/icon.css" rel="stylesheet"/>
    <script src="system/easyui/jquery.min.js"></script>
    <script src="system/easyui/jquery.easyui.min.js"></script>
	<script src="system/easyui/locale/easyui-lang-zh_CN.js"></script>
    <style>
        .panel-body{overflow: hidden;padding: 0 !important}
        #about{padding: 10px !important}
        #about p{text-indent: 10px;margin-top: 0;font-size: 14px}
        #about h2{font-weight: normal;margin: 10px 0 0}
        #about ul{margin: 0;padding: 0;list-style: none}
        #about li{font-size: 14px;margin-top: 10px}
        .layout-split-west{border-right: 1px solid #E6EEF8 !important}
        .tabs{padding-left: 1px !important}
    </style>
    <link rel="stylesheet" href="system/css/main.css" />
    <meta charset="utf-8">
</head>

<body class="easyui-layout">
    <div id="about" class="easyui-dialog" title="关于系统" style="width:550px;height:300px;"  
        data-options="iconCls:'icon-man',resizable:false,modal:true,closed:true">  
        <h2>开发团队:</h2>
        <p>本系统由重庆邮电大学数字媒体实验室开发和维护，数字媒体实验室将保留最终解释权限。如果有任何意见可以向我们联系，联系人：熊仕勇</p>
        <ul>
            <li>· 数字媒体官网：<a href="#">http://wwww.wwww.www</a></li>
            <li>· 联系我们：<a href="#">http://wwww.wwww.www</a></li>
            <li>· 意见反馈：<a href="#">http://wwww.wwww.www</a></li>
        </ul>
    </div>
    <div data-options="region:'north'" style="height:82px;overflow:hidden;border:0">
        <div class="pg-header">
            <div class="banner">
                <div class="hd-left">
                    <img src="system/img/logo.png" alt="logo" />
                </div>
                <div class="hd-right">
                    <div class="hd-tips">
                        <a href="#" class="help" id="ab-system">关于系统</a>
                        <a href="#" class="about">退出登陆</a>
                    </div>
                </div>
                <div class="hd-mask"></div>
            </div>

            <div class="hd-welbar">
                <p class="fl ml10">欢迎登陆,Admin</p>
                <p class="fr mr10">当前日期：2014/11/13 
                </p>

            </div>
        </div>

    </div>

    <div title="导航" data-options="region:'west',split:true" style="width:150px;padding-top:10px;padding-left:5px" id="menu">
        <div class="easyui-tree" id="tree-items">

        </div>
    </div>
    <div data-options="region:'center'" style="padding:5px;background:#eee;">
        <div id="tabs" class="easyui-tabs" fit="true" border="false">

        </div>
    </div>

    <!-- menu[west]-->

</body>
<script type="text/javascript">
$(function() {
    window.showEdit = function(){
        $("#dlg").dialog('open');
    }
    showEdit();
    //初始化
    $('#tree-items').tree({
        'url': 'UserMenu.do',
        'method':'get',
        'onClick': function(node) {
            if (node.attributes && node.attributes["data-href"]) {
                if (!$('#tabs').tabs('exists', node['text'])) {
                    var href = node.attributes["data-href"]
                    $('#tabs').tabs('add', {
                        title: node['text'],
                        content: '<iframe frameborder="no" border="0" srcoll="yes" src="' + href + '" style="html,body{height: 100%;}body {margin: 2px;overflow: hidden;}"></iframe>',
                        closable: true
                    });
                } else {
                    $('#tabs').tabs('select', node.text);
                }
            }

        }
    });

    //事件绑定
    $('#about').click(function() {
        $('#win-about').window({
            width: '600px',
            height: '200px',
            modal: true,
            title: '关于',
            minimizable: false,
            maximizable: false
        }).window('open');
    })

    $('#help').click(function() {
        $('#win-help').window({
            width: '600px',
            height: '200px',
            modal: true,
            title: '关于',
            minimizable: false,
            maximizable: false
        }).window('open');
    });

    $("#ab-system").click(function(){
        $("#about").dialog("open");
    });
})
</script>

</html>
