// JScript 文件
function DivBase()
{
    this.LoadingDiv=null;//加载进度条DIV
    this.LoadingDivBg=null;//进度条页面的只读DIV
    this.ReadOnly=false;//进度条所在页面是否设为只读
    this.PopDiv=null;//弹出Div窗口
    this.BgDivTop=34;//若添加只读背景，设定只读背景的top值
    this.Opacity=10;//蒙版Div的透明度
}



//当前页面显示loading Div
//msg:加载进度条出现时显示的内容
//readOnly:是否设置当前区域为只读
DivBase.prototype.CreateLoadingDiv = function (msg) {
    var w = 200; //进度条宽度
    var h = 50; //进度条高度
    var bgColor = "#FFFFEB"; //进度条背景颜色
    var borderColor = "#A4C7DB"; //进度条边框颜色
    var fontColor = "Green"; //进度条字体颜色
    var imgSrc = "system/DivImg/loading.gif"; //进度条加载图标路径

    //获取Loading Div在当前页面中心的顶角坐标
    var left = (document.body.clientWidth - w) / 2;
    var top = document.body.scrollTop + (document.body.clientHeight - h) / 2;

    this.LoadingDiv = document.createElement("div");
    this.LoadingDiv.style.cssText = "position: absolute;left:" + left + "px;top:" + top + "px;WIDTH: " + w + "px;HEIGHT: " + h + "px;background-color:" + bgColor + ";border:" + borderColor + " thin solid;text-align:center;z-index:104;";
    this.LoadingDiv.innerHTML = "<table border=0  cellpadding=0 cellspacing=0  height='100%' align=center><tr><td valign=middle style='padding-left:2px;'><img src=\"" + imgSrc + "\" /></td><td style=\"color:" + fontColor + ";font-weight:bold;font-size:12px;padding-left:3px;\"><nobr>" + msg + "</nobr></td></tr></table>";

    if (this.ReadOnly)//添加只读DIV
    {
        if (document.createElement && document.getElementById) {
            var iWidth = document.body.scrollWidth; //获取当前页面的宽度
            var iHeight = document.body.scrollHeight; //获取当前页面的高度
            this.LoadingDivBg = document.createElement("div");
            this.LoadingDivBg.style.cssText = "position:absolute;left:0px;top:" + this.BgDivTop + "px;width:" + iWidth + "px;height:" + iHeight + "px;filter:Alpha(Opacity=" + this.Opacity + ");opacity:" + (this.Opacity / 100) + ";background-color:#eee;z-index:101;";
            document.body.appendChild(this.LoadingDiv);
            document.body.appendChild(this.LoadingDivBg);
            this.LoadingDivBg.innerHTML = "<iframe  style=\"position:absolute; visibility:inherit;top:-2px;width:" + (iWidth + 1) + "px;height:" + (iHeight + 2) + "px; left:-2px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';\" frameborder=\"0\" ></iframe>";
        }
    }
    else
        document.body.appendChild(this.LoadingDiv);

    //页面超时或用户不合法
    if (SysTimeOut()) return false;

    return true;
}


//内容加载完毕后，移除loading Div
//msg：移除loading DIV前显示的信息
//microSecond：信息显示的时间
DivBase.prototype.RemoveLoadingDiv=function(msg)
{
     if(this.ReadOnly)
     {
         document.body.removeChild(this.LoadingDivBg);
         this.LoadingDivBg=null;
     }
     
     document.body.removeChild(this.LoadingDiv);
     if(msg)alert(msg);
     this.LoadingDiv=null;//释放资源
 }
 
 

//功能：构建弹出div
/*
【必】popObjID:目标div ID
【必】title:div的标题
【必】msg:div内容【可为对象或字符串】
【必】w:div宽度
【必】h:div高度
【必】isText:msg是文本或对象
【可】topPadding:div距离顶部距离
【可】readOnly:是否添加只读背景
【可】zIndex:z轴索引
【可】winDivArr:点关闭按钮时需同时关闭的其它DIV 【数组形式，如new Array("id1","id2")】 【可选参数】
【可】是否隐藏关闭按钮
*/
function PopWin(popObjID,title, msg, w, h,isText,topPadding,readOnly,zIndex,winDivArr,hideCloseBT)
{ 
    var titleheight=25;//标题行高度
	var lineHeight="22px";//div类容行高
	var titlecolor = "#ffffff"; // 提示窗口的字体颜色 
	var bgcolor = "#ffffff"; // 提示内容的背景色
	
    var iWidth = document.body.clientWidth;
    var iHeight = document.documentElement.scrollHeight > document.body.scrollHeight ? document.documentElement.scrollHeight : document.body.scrollHeight;

	var left,top;
	
	if(topPadding==null)topPadding=0;

	left = (document.body.clientWidth - w) / 2;

	if (document.documentElement.clientHeight!=0)
	    top = Math.max(document.documentElement.scrollTop,document.body.scrollTop) + (document.documentElement.clientHeight - h) / 2 - topPadding;
	else
	    top = Math.max(document.documentElement.scrollTop, document.body.scrollTop) + (document.body.clientHeight - h) / 2 - topPadding;

	if(top<0)top=1;
	if(left<0)left=1;    
    
    
    
    //添加只读DIV myReadOnlyDiv
    if(readOnly==null)readOnly=true;
    var readOnlyDivID="myReadOnlyDiv_"+popObjID;
    if(readOnly==true)
    {
        if (document.getElementById(readOnlyDivID))
        {
           document.getElementById(readOnlyDivID).innerHTML="<iframe  style=\"position:absolute; visibility:inherit;top:-2px;width:"+(iWidth+1)+"px;height:"+(iHeight+2)+"px; left:-2px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';\" frameborder=\"0\" ></iframe>";
           document.getElementById(readOnlyDivID).style.display="";
        }else{
            var LoadingDivBg = document.createElement("div"); 
            LoadingDivBg.id=readOnlyDivID;
            LoadingDivBg.style.cssText = "position:absolute;left:0px;top:0px;width:"+iWidth+"px;height:"+iHeight+"px;filter:Alpha(Opacity=70);opacity:0.7;background-color:#eee;z-index:101;";
            LoadingDivBg.innerHTML="<iframe style=\"position:absolute; visibility:inherit;top:-2px;width:"+(iWidth+1)+"px;height:"+(iHeight+2)+"px; left:-2px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';\" frameborder=\"0\" ></iframe>";
            document.body.appendChild(LoadingDivBg); 
        } 
    }
    
    //判断div是否存在
    if(GetHtmlObject(popObjID))
    {
    	var tmp=GetHtmlObject(popObjID);
        tmp.childNodes[0].rows[0].cells[1].innerHTML=title;
        tmp.childNodes[0].rows[1].cells[1].childNodes[1].innerHTML="";
        tmp.childNodes[0].rows[1].cells[1].childNodes[1].appendChild(msg);
        tmp.style.left=left+"px";
        tmp.style.top=top+"px";
        tmp.style.width = w + "px";
        tmp.style.height = h + "px";
        tmp.style.display = "";
        msg.focus();
        return;
    }    
    

	if(zIndex==null)zIndex=105;
	var msgObj=document.createElement("div");
    msgObj.id=popObjID;
	msgObj.style.cssText = "position:absolute;font:14px '宋体';background-color:"+bgcolor+";line-height:"+lineHeight+";z-index:"+zIndex+";width:"+w+"px;height:"+h+"px;left:"+left+"px;top:"+top+"px;";
	
	var table = document.createElement("table");
	table.style.cssText = "margin:0px;border:0px;padding:0px;width:100%;height:100%;";
	table.cellSpacing = 0;
    table.cellPadding=0;

	var tr = table.insertRow(-1);
	tr.style.cssText = "height:25px;line-height:25px;cursor:move";
	//左边
	tr.insertCell(-1).style.cssText = "width:3px;background:#CFD7EC url(system/DivImg/title_bg_left.gif) no-repeat 0 0";
	//标题
	var titleBar = tr.insertCell(-1);
	titleBar.style.cssText = "color:"+titlecolor+";font-size:13px;font-weight:bold;background:#CFD7EC url(system/DivImg/title_bg_center.gif) repeat-x 0 0;width:"+(w-22)+"px;text-align:left;";
    titleBar.style.paddingLeft = "10px";
	titleBar.innerHTML = title;
	//关闭按钮
	var closeBtn = tr.insertCell(-1);
	closeBtn.style.cssText = "background:#CFD7EC url(system/DivImg/title_bg_center.gif) repeat-x 0 0;width:16px;vertical-align:middle;padding-top:0px;";
    
    if(hideCloseBT)
	   closeBtn.innerHTML="";
	else
	   closeBtn.innerHTML="<img src=\"system/DivImg/close.gif\" style='cursor:pointer;' />";
	   
	closeBtn.onclick = function()
	{
//	    msgObj.style.display="none";
		document.body.removeChild(msgObj);
	    if(document.getElementById(readOnlyDivID))document.getElementById(readOnlyDivID).style.display="none";
	    
	    //关闭当前div之外的指定div
	    if(winDivArr)
	    {
	       for(var w=0;w<winDivArr.length;w++)
	       {
	           var o=GetHtmlObject(winDivArr[w]);
	           if(o)
	           {
	              o.style.display="none";
	           }
	       }
	    }
	} 	
	
   if(hideCloseBT)closeBtn.onclick = function(){}
	//右边
	tr.insertCell(-1).style.cssText="width:3px;background:#CFD7EC url(system/DivImg/title_bg_right.gif) no-repeat right 0;";
	
	var moveX = 0;
	var moveY = 0;
	var moveTop = 0;
	var moveLeft = 0;
	var moveable = false;
	tr.onmousedown = function() 
	{
		var evt = getEvent();
		moveable = true; 
		moveX = evt.clientX;
		moveY = evt.clientY;
		moveTop = parseInt(msgObj.style.top);
		moveLeft = parseInt(msgObj.style.left);
		tr.setCapture();
	}
		
	tr.onmousemove = function() 
	{
		if (moveable) 
		{
			var evt = getEvent();
			var x = moveLeft + evt.clientX - moveX;
			var y = moveTop + evt.clientY - moveY;
			if ( x > 0 &&( x + w < iWidth) && y > 0 && (y + h < iHeight) ) 
			{
				msgObj.style.left = x + "px";
				msgObj.style.top = y + "px";
			}
		}	
	}
		
	tr.onmouseup = function () 
	{ 
		if (moveable) 
		{ 
			moveable = false; 
			tr.releaseCapture(); 
			moveX = 0;
			moveY = 0;
			moveTop = 0;
			moveLeft = 0;
		}
    }

   //若需要启用div虽滚动条滚动，需启用scrollObj对象，
    if (window.parent.scrollObj!=null) {
        window.parent.scrollObj = popObjID;
        window.parent.divHeight = h; 
    }

	

	
	//内容tr
    var ctTr= table.insertRow(-1);
    ctTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_l.gif) repeat-y 0 0;width:3px;";
	var msgBox = ctTr.insertCell(-1);
	msgBox.style.cssText = "background:url(system/DivImg/content_bg.gif) repeat-x 0 0;padding:0";
	msgBox.colSpan  = 2;
	msgBox.innerHTML="<iframe src=\"javascript:false\" style=\"position:absolute; visibility:inherit;top:-2px;width:"+(w+1)+"px;height:"+(h+2)+"px; left:-2px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';\" frameborder=\"0\" ></iframe>";
	var contentDiv=document.createElement("div");
	contentDiv.style.cssText = "height:" + (h - titleheight - 6) + "px;vertical-align:middle;";
   
    if(isText)//内容为HTML文本
       contentDiv.innerHTML=msg;
    else
       contentDiv.appendChild(msg);
    msgBox.appendChild(contentDiv);
    ctTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_r.gif) repeat-y right 0;width:3px";
	
	

    //底部tr
	var btTr=table.insertRow(-1);
	btTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_lb.gif) no-repeat 0 bottom;width:3px";
	btTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_b.gif) repeat-x 0 bottom;height:3px;font-size:3px";
	btTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_b.gif) repeat-x 0 bottom;height:3px;font-size:3px";
	btTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_rb.gif) no-repeat right bottom;width:3px";

	msgObj.appendChild(table);
    document.body.appendChild(msgObj);


    // 获得事件Event对象，用于兼容IE和FireFox
    function getEvent() {return window.event || arguments.callee.caller.arguments[0];}
} 

//popObjID:弹出Div ID
//title:弹出Div的标题
//tbObj:弹出Div的table对象
//w:弹出Div的宽度
//h:弹出Div的高度
//left:弹出Div的left位置
//top:弹出Div的top位置
function CreateDiv(popObjID,title, tbObj, w, h,left,top)
{ 
	if(left==null)
	   left=(document.body.clientWidth-w)/2;
	if(top==null)
	   top=document.body.scrollTop+(document.body.clientHeight-h)/2;
	   
	if(top<0)top=1;
	if(left<0)left=1;

    //判断div是否存在
    if(GetHtmlObject(popObjID))
    {
        GetHtmlObject(popObjID).style.display="";
        GetHtmlObject(popObjID).style.left=left;
        GetHtmlObject(popObjID).style.top=top;
        GetHtmlObject(popObjID).childNodes[0].rows[0].cells[1].innerHTML=title;
        return;
    }
    
	var titleheight = 18; // 提示窗口标题高度 
	var lineHeight="22px";//div类容行高
	var bordercolor = "#4475B7"; // 提示窗口的边框颜色 
	var titlecolor = "#ffffff"; // 提示窗口的字体颜色 
	var titlebgcolor = "#4475B7"; // 提示窗口的标题背景色
	var bgcolor = "#ffffff"; // 提示内容的背景色
	
    var iWidth = document.body.clientWidth; 
	//var iHeight = document.body.clientHeight; 
	var iHeight = document.body.scrollHeight
	
	var msgObj=document.createElement("div");
    msgObj.id=popObjID;
	msgObj.style.cssText = "position:absolute;font:14px '宋体';background-color:"+bgcolor+";line-height:"+lineHeight+";z-index:105;width:"+w+"px;height:"+h+"px;left:"+left+"px;top:"+top+"px;";
	
	var table = document.createElement("table");
	table.style.cssText = "margin:0px;border:0px;padding:0px;width:100%;height:100%;";
	table.cellSpacing = 0;
    table.cellPadding=0;

	var tr = table.insertRow(-1);
	tr.style.cssText = "height:25px;line-height:25px;cursor:move";
	//左边
	var titleLeft=tr.insertCell(-1);
	titleLeft.style.cssText="width:3px;background:#CFD7EC url(system/DivImg/title_bg_left.gif) no-repeat 0 0";
	//标题
	var titleBar = tr.insertCell(-1);
	titleBar.style.cssText = "color:"+titlecolor+";font-weight:bold;background:#CFD7EC url(system/DivImg/title_bg_center.gif) repeat-x 0 0;width:"+(w-22)+"px";
	titleBar.style.paddingLeft = "10px";
	titleBar.innerHTML = title;
	//关闭按钮
	var closeBtn = tr.insertCell(-1);
	closeBtn.style.cssText = "cursor:pointer;background:#CFD7EC url(system/DivImg/title_bg_center.gif) repeat-x 0 0;width:16px;vertical-align:middle;padding-top:2px;";
	closeBtn.innerHTML="<img src=\"system/DivImg/close.gif\" />";
	closeBtn.onclick = function()
	{
	    msgObj.style.display="none";
	} 
	//右边
	var titleRight=tr.insertCell(-1);
	titleRight.style.cssText="width:3px;background:#CFD7EC url(system/DivImg/title_bg_right.gif) no-repeat right 0;";
	
	var moveX = 0;
	var moveY = 0;
	var moveTop = 0;
	var moveLeft = 0;
	var moveable = false;
	tr.onmousedown = function() 
	{
		var evt = getEvent();
		moveable = true; 
		moveX = evt.clientX;
		moveY = evt.clientY;
		moveTop = parseInt(msgObj.style.top);
		moveLeft = parseInt(msgObj.style.left);
		tr.setCapture();
	}
		
	tr.onmousemove = function() 
	{
		if (moveable) 
		{
			var evt = getEvent();
			var x = moveLeft + evt.clientX - moveX;
			var y = moveTop + evt.clientY - moveY;
			if ( x > 0 &&( x + w < iWidth) && y > 0 && (y + h < iHeight) ) 
			{
				msgObj.style.left = x + "px";
				msgObj.style.top = y + "px";
			}
		}	
	}
		
	tr.onmouseup = function () 
	{ 
		if (moveable) 
		{ 
			moveable = false; 
			tr.releaseCapture(); 
			moveX = 0;
			moveY = 0;
			moveTop = 0;
			moveLeft = 0;
		}
   }


    //内容tr
    var ctTr= table.insertRow(-1);
    ctTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_l.gif) repeat-y 0 0;width:3px;";
	var msgBox = ctTr.insertCell(-1);
	msgBox.style.cssText = "background:url(system/DivImg/content_bg.gif) repeat-x 0 0;padding:0";
	msgBox.colSpan  = 2;
	msgBox.innerHTML="<iframe src=\"javascript:false\" style=\"position:absolute; visibility:inherit;top:-2px;width:"+(w+1)+"px;height:"+(h+2)+"px; left:-2px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';\" frameborder=\"0\" ></iframe>";
	var contentDiv=document.createElement("div");
	contentDiv.style.cssText="overflow-y:auto;height:"+(h-titleheight-8)+";padding:2px 1px 1px 1px;";
    contentDiv.appendChild(tbObj);
    tbObj.style.display="";
    msgBox.appendChild(contentDiv);
    ctTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_r.gif) repeat-y right 0;width:3px";
	
	//底部tr
	var btTr=table.insertRow(-1);
	btTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_lb.gif) no-repeat 0 bottom;width:3px";
	btTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_b.gif) repeat-x 0 bottom;height:3px;font-size:3px";
	btTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_b.gif) repeat-x 0 bottom;height:3px;font-size:3px";
	btTr.insertCell(-1).style.cssText="background:url(system/DivImg/win_rb.gif) no-repeat right bottom;width:3px";
	
	msgObj.appendChild(table);
	document.body.appendChild(msgObj);


    // 获得事件Event对象，用于兼容IE和FireFox
    function getEvent() {return window.event || arguments.callee.caller.arguments[0];}
} 


function ClosePopDiv(id,winObj)
{  
   var readOnlyDivid="myReadOnlyDiv_"+id;
   if(winObj)
   {
       if(winObj.document.getElementById(id))
       {
          winObj.document.getElementById(id).style.display="none";
          if(winObj.document.getElementById(readOnlyDivid))winObj.document.getElementById(readOnlyDivid).style.display="none";
       }
   }
   else
   {
      if(GetHtmlObject(id))
      {
         GetHtmlObject(id).style.display="none";
         if(document.getElementById(readOnlyDivid))document.getElementById(readOnlyDivid).style.display="none";
      }
   }   
}



//创建button
function CreateButton(value,eventMethod,className)
{
    var bt=document.createElement("input");
    bt.type="button";
    bt.value=value;
    bt.className=className==null?"button":className;
    if(eventMethod)
       bt.onclick=eventMethod;
    return bt;
}

//创建iframe
function CreateFrame(popObjID,src,allowScroll,winObj)
{
    if(!winObj)winObj=this;
    var frm=winObj.document.createElement("iframe");
    frm.id="Iframe"+popObjID;
    frm.name="Iframe"+popObjID;
    frm.src=src;
    if(allowScroll=='auto')
      frm.scrolling="auto";
    else{  
        if(allowScroll)
          frm.scrolling="yes";
        else
          frm.scrolling="no";
    }
    frm.frameBorder=0;
    frm.marginHeight="0px";
    frm.marginWidth="0px";
    frm.width="99%";
    frm.height="99%";
    return frm;
}


//div切换功能
function easytabs(menunr, activeIndex,idName,linkName)
{
 for(var i=1;i<=menunr;i++)
 {
     var obj=document.getElementById(idName+i);
     var obj1=document.getElementById(linkName+i);
     if(i!=activeIndex)
     {
       obj.style.display="none";
       obj1.className='tab'+i;
     }else{
       obj.style.display="";
       obj1.className='tab'+activeIndex+' tabactive';
     }
 }
}

function GetHtmlObject(id)
{
    return document.getElementById(id);
}
//关闭div设置
function closeAdd(popObjID)
{
//	var popObjID="DivAddEdit";
	var my = document.getElementById(popObjID);
    if (my != null){
        my.parentNode.removeChild(my);
    }
    my=null;
//	document.getElementById(popObjID).style.display="none";
    if(document.getElementById("myReadOnlyDiv_"+popObjID))document.getElementById("myReadOnlyDiv_"+popObjID).style.display="none";
	if(popObjID!="SelectValue")window.parent.fraMain.win.refresh(window.parent.fraMain.win.curentwin);
} 