<%@page contentType="text/html;charset=utf-8" session="true" %>
<!DOCTYPE HTML>
<html lang="zh">

<head>
    <title></title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="css/page.css">
	<script type="text/javascript" src="js/page.js"></script>
</head>

<body leftmargin="5" topmargin="0">
    <div id="mask"></div>
    <div class="window alert">
        <div class="header">
            <h1 class="panel-title">Basic DataGrid</h1>
        </div>
        <div class="body">
            <p>测试</p>
        </div>
    </div>
    <br>
    <div class="window">
        <div class="header">
            <h1 class="panel-title">Basic DataGrid</h1>
        </div>
        <div class="body">
            <div class="tool">
                <a href="#" class="head-button" data-photo="filesave">打开</a>
                <a href="#" class="head-button" data-photo="search">搜索</a>
            </div>
            <table id="grid-table" class="datagrid">
                <tbody>
                    <tr>
                        <th>Item Id</th>
                        <th>Product</th>
                        <th>List Price</th>
                        <th>Unit Cost</th>
                    </tr>
                </tbody>
            </table>
            <div class="pagination">
                <span>
                    <select class="pagination-page-list">
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="30">30</option>
                        <option value="40">40</option>
                        <option value="50">50</option>
                    </select>
                </span>
                <span>
                    <div class="pagination-btn-separator"></div>
                </span>
                <span>
                    <a href="#" class="go first"></a>
                </span>
                <span>
                    <a href="#" class="go prev"></a>
                </span>
                <span>
                    <div class="pagination-btn-separator"></div>
                </span>
                <span>
                    <p>第</p>
                    <input type="text" id="page"/>
                    <p id="pages">页 共3页</p>
                </span>
                <span>
                    <div class="pagination-btn-separator"></div>
                </span>
                <span>
                    <a href="#" class="go next"></a>
                </span>
                <span>
                    <a href="#" class="go last"></a>

                </span>
                 <span>
                    <div class="pagination-btn-separator"></div>
                </span>
                <span>
                    <a href="#" class="go search"></a>
                </span>
                <span>
                    <div class="pagination-btn-separator"></div>
                </span>

                </tr>
                </table>
            </div>
        </div>
    </div>
</body>
<script>
each(dom.$('.head-button'), function(k, v) {
    if (v.nodeType) css.setCss(v, {background: "url(icons/" + attr(v, "data-photo") + ".png) no-repeat 5px center"})
});

datagrid.init({
    id: "#grid-table",
    field: ['a', 'b', 'c', 'd'],
    width: '25%',
    url: '/getPage.do',
    params: "model=user"
});

dom.$('#test')[0].onclick = function() {
    dom.$('#mask')[0].style.display = "block";
    var al = dom.$('.alert')[0];
    css.setCss(al, {
        'display': 'block',
        'marginLeft': -parseInt(css.getCss(al, "width")) / 2 + "px"
    });
    dom.$('.alert')[0].style.display = 'block';
    dom.$('#mask')[0].onclick = function(){
        dom.$('#mask')[0].style.display = "none";
        dom.$('.alert')[0].style.display = "none";
    }
}
</script>
</html>

