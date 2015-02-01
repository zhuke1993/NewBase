var isSubmit=0;
var $$ = function() {
	var status=true;
    var createOpp = function() {
            var xmlHttp;
            if (window.ActiveXObject) xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            else xmlHttp = new XMLHttpRequest();
            return xmlHttp;
        },
        ajax = function(setting) {
        	if (!status)return;
            var method = setting.method || "post",
            callback = setting.success || function() {},
            params = setting.params || "",
            dataType = setting.dataType || "",
            beforeSend = setting.beforeSend || undefined,
            asnyc = setting.asnyc || true,
            error = setting.error || function(){},
            url = setting.url || function(){},
            xhr = createOpp();
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                	status=true;
                     if(dataType == "json")
                        callback.call(this,eval('(' + xhr.responseText + ')'));
                    else
                        callback.call(this, xhr.responseText);
                } else if(xhr.readyState == 1 && beforeSend){
                    beforeSend.call(this, xhr);
                }
            };
            xhr.open(method.toUpperCase(), setting.url, asnyc);
            if (method == "post")xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.send(params);
        },
        post = function(url, params, callback) {
            ajax({"url" : url,"params":params,"success":callback});
        },
        get = function(url, params, callback) {
            ajax({"method":"get","url" : url,"params":params,"success":callback});
        },
        getJSON = function(url, params, callback){
            ajax({"method":"get","dataType":"json","url" : url,"params":params,"success":callback});
        };

    return {
        ajax: ajax,
        post: post,
        get : get,
        getJSON : getJSON
    }
}();
//===============
var attr = function(node, key, value){
    if(value){
        node.setAttribute(key, value);
    }
    return node.getAttribute(key);
};
var isArray = function(obj) { 
    return Object.prototype.toString.call(obj) === '[object Array]'; 
};
var each = function(obj, callback){
    for(var i in obj){
        if (obj.hasOwnProperty(i)){
            callback(i, obj[i]);
        }
        
    }
};
var dom =function(){

    var Class = {
        hasClass : function(obj, cls) {
            return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
        },

        addClass : function(obj, cls) {
            if (!this.hasClass(obj, cls)) obj.className += " " + cls;
        },

        removeClass : function(obj, cls) {
            if (Class.hasClass(obj, cls)) {
                var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
                obj.className = obj.className.replace(reg, ' ');
            }
        }
    },


    $ = function(selector, context){
        context = context || document;

        var result = [],
            idElem = new RegExp("^#[a-zA-Z0-9\\-]*"),
            classElem = new RegExp("^\\.{1}[a-zA-Z0-9\\-]*"),
            tagElem = new RegExp("^[a-zA-Z0-9\\-]*$");
        _selector = selector.substring(1,selector.length);

        var getByClass = function(className){
                var result = [],
                    nodes = context.getElementsByTagName("*");
                for(var i = 0;i < nodes.length;i++ ){
                    if( Class.hasClass(nodes[i],className) ){
                        result[result.length] = nodes[i];
                    }
                }
                return result;
            };

        //如果已经是dom返回自身
        if (selector.nodeType) return selector;

        //如果不是dom元素，nodeType == 9 为document ==1 为普通的dom
        if (!context.nodeType || (context.nodeType != 9 && context.nodeType != 1)){
            return result;
        }

        //是id选择器
        if (selector.match(idElem) && selector.match(idElem)[0].length == selector.length){
            result[result.length] = context.getElementById(_selector);
            return result;
        } else if (selector.match(classElem) && selector.match(classElem)[0].length == selector.length){
            //如果是类选择器
            if(document.getElementsByClassName)
                return context.getElementsByClassName(_selector);
            return getByClass(_selector);
        } else if (selector.match(tagElem) && selector.match(tagElem)[0].length == selector.length){
            //如果是名称选择器
            var selectors = context.getElementsByTagName(selector),ele = [];
            try{
                return Array.prototype.slice.call(selectors, 0);
            }catch(e){
                for(var e = 0;e < selectors.length;e++){
                    ele[ele.length] = selectors[e];
                }
                return ele;
            }
            
        } else {
            //其他选择器 兼容ie8+
            if(document.querySelectorAll)
                return context.querySelectorAll(selector);
            return [];
        }


    };
    return {
        $ : $,
        Class : Class
    }
}();
//==============

var css = function(){
	var pfx = function(style){
		//得到符合的前缀
		var s = "Webkit,Moz,O,ms,Khtml",
			pre = s.split(","),
			tNode = document.createElement("div");
		for(var i = 0;i < pre.length;i ++) {
			//重新组装style
			var reStyle = pre[i] + style.charAt(0).toUpperCase() + style.substr(1);

			if( tNode.style[reStyle] != undefined){
				return reStyle;
			}
		}
		return style;
	},
	
	setCss = function(node, styles){
		if ( isArray(node) ){
			each(node,function(k, v){
				each(styles, function(style, value){
					v.style[pfx(style)] = value;
				});
			});
		} else if (typeof styles === 'object'){
			each(styles, function(style, value){
				node.style[pfx(style)] = value;
			});
		}
	},
	getCss = function(node, style){
		//兼容ie ff
		var styles = window.getComputedStyle ? window.getComputedStyle(node, null) : node.currentStyle;
		return styles [pfx(style)];
	}

	return {
		setCss : setCss,
		getCss : getCss
	}
}();
//============================
var validate = (function(){
	var vali = function(){
		var all = document.getElementsByTagName("*");
		var sign = false;
		for(var i = 0 ;i < all.length ;i ++){
			if( options = attr(all[i], "data-options") ){
				try{
					var json = eval( "(" + options + ")" ),
						type = json['type'] || false,
						msg = json['msg'] || "输入格式有误！",
						required = json['required'] || false,
						value = all[i].value;
					if ( (required && value) || !required ){
						if ( type == "num" ){
							sign = /^[0-9]*$/.test(value);
						} else if ( type == "char" ) {
							sign = /^\w+$/.test(value);
						} else {
							sign = true;
						}
					}
					if(!sign) {
						alert(msg);
						return false;
					}
				}catch(e){throw new Error("json格式错误!");return false;}
			}
		}
		return sign;
	};
	return {
		val : vali
	}
})();
//==================
var datagrid = (function(setting){
	var grid ,
		tbodyInner,
		setting,
		tbody,
		total;
	var init = function(setting_){
		setting = setting_;
		table = dom.$(setting.id)[0];
		setting.width = setting.width || "auto";
	    setting.params = setting.params || "";

	    css.setCss(dom.$('th'), {width : setting.width});
	    grid = dom.$(setting.id)[0];
	    tbody = dom.$("tbody", grid)[0];
	    tbodyInner = tbody.innerHTML;
	    //分页数目
		var row = dom.$('.pagination-page-list')[0].value;
		load();

		dom.$('.first')[0].onclick = function(){
			datagrid.load(1);
		}
		dom.$('.last')[0].onclick = function(){
		    datagrid.load(parseInt(total/row)+1);
		}
		dom.$('.next')[0].onclick = function(){
		    datagrid.load(page+1);
		}
		dom.$('.prev')[0].onclick = function(){
		    datagrid.load(page-1);
		}
		dom.$('.pagination-page-list')[0].onchange = function(e){
		    load();
		}
		dom.$('.search')[0].onclick = function(e){
		    load();
		}
	},
	load = function(page){
		page = page || 1;
		if (page > total) page = total;
		if (page < 1) page =1;
		var rows = dom.$('.pagination-page-list')[0].value;
		while (grid.rows.length > 1){
			grid.deleteRow(1);
		}

		$$.getJSON(setting.url +"?rows=" + rows + "&page=" + page + "&" + setting.params,"", function(e){
			total = e['total'];
			dom.$('#pages')[0].innerText = "页 共"+(parseInt(total/rows)+1)+"页";
			dom.$('#page')[0].value = page;
	        each (e["rows"], function(k, v){
	        	var tr = document.createElement("tr");
	        	each(setting.field, function(key, value){
	        		var td = document.createElement("td");
	        		td.innerHTML = v[value] ;
	        		tr.appendChild(td);
	        	});
	        	
	        	tbody.appendChild(tr);
	        });
	    });
	}
	
    return {
    	init : init,
    	load : load
    }
})();