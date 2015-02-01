function Trim(str) {return str.replace(/(^\s*)|(\s*$)/g, "");}//删除前后的空格
function Ltrim(str){return str.replace(/(^\s*)/g, "");}//去掉左边的空格
function Rtrim(str){return str.replace(/(\s*$)/g, ""); }//去掉右边的空格
function Ctrim(str){return str.replace(/ /g, "");}//去掉中间的空格

function onMouseOverPic(obj){obj.background='../images/new16.jpg';}
function onMouseOutPic(obj){obj.background='../images/zs_54.gif';}
function SetColor(cur){cur.style.backgroundColor="#FFFF00";cur.style.cursor='hand';}
function GetColor(cur){cur.style.backgroundColor=((cur.sectionRowIndex==0)?"#D0E9ED":(cur.sectionRowIndex%2==0)?"#F5FAFA":"#FFFFFF")}
function dropRow(tb,row){if(confirm('您确定要删除此信息么？')){tb.deleteRow(row);}}//删除表格一行
function changerow(tb,row1,row2)//交换表格行的位置
{
	try{
		if(row1>row2){
			if (row1<=2) return;
		}else{
			if(row1>=tb.RowCount) return;
		}
		tb.moveRow(row1,row2);
	}catch(e){}
}
function selectTree(Page,MODE,pid,RootChoice,LastLevel,FieldID,FieldName){
	try{	
		var a=window.showModalDialog("../common/SelectTreeOneDept.jsp?Page="+Page+"&MODE="+MODE+"&pid="+pid+"&RootChoice="+RootChoice+"&LastLevel="+LastLevel,'',"dialogWidth:410px;dialogHeight:310px;center:1;scroll:0;help:0;status:0");
		if (a!=null){
			if(FieldID!=null)FieldID.value=a[0];
			if(FieldName!=null)FieldName.value=a[1];
		}
	}catch(e){}	
}
function selectList(Page,FieldID,FieldName)
{
	try{   
		var a=window.showModalDialog("../common/Select.jsp?Page="+Page,'',"dialogWidth:800px;dialogHeight:650px;center:1;scroll:0;help:0;status:0");
	    if (a==null) return;
	    if(FieldID!=null)FieldID.value=a[0]; 
	    if(FieldName!=null)FieldName.value=a[1]; 
	}catch(e){}
}

function Dell(){
	try{
		var c=0;
		var mycheckbox1 = document.frm.myCheckBox;
		for(var i=0;i<mycheckbox1.length;i++){
			if(mycheckbox1[i].checked==true){c=1;break;}
		}
		if (c==0){
			alert("你没有选择要删除的信息，请在要删除的信息左面方框那勾上后再删除！");
		}else{
	        var myFlag = window.confirm("删除操作将会把你打勾了的信息从列表中删除，\n该操作将不能恢复，是否进行删除操作？");
	        if(myFlag){
	        	document.frm.Act.value="Dell";
	        	document.frm.submit();
	        }
	    }
	}catch(e){}
}
function Audit(){
	try{
		var c=0;
		var mycheckbox1 = document.frm.myCheckBox;
		for(var i=0;i<mycheckbox1.length;i++){
			if(mycheckbox1[i].checked==true){c=1;break;}
		}	
		if (c==0){
			alert("你没有选择要审核的信息，请在要删除的信息左面方框那勾上后再删除！");
		}else{
	        var myFlag = window.confirm("审核操作将会把你打勾了的信息从列表中审核，\n该操作将不能恢复，是否进行审核操作？");
	        if(myFlag){
	        	document.frm.Act.value="Audit";
	        	document.frm.submit();
	        }
	    }
	}catch(e){}
}
function CancelAudit(){
	try{
		var c=0;
		var mycheckbox1 = document.frm.myCheckBox;
		for(var i=0;i<mycheckbox1.length;i++){
			if(mycheckbox1[i].checked==true){c=1;break;}
		}	
		if (c==0){
			alert("你没有选择要审核的信息，请在要删除的信息左面方框那勾上后再删除！");
		}else{
	        var myFlag = window.confirm("审核操作将会把你打勾了的信息从列表中审核，\n该操作将不能恢复，是否进行审核操作？");
	        if(myFlag){
	        	document.frm.Act.value="CancelAudit";
	        	document.frm.submit();
	        }
	    }
	}catch(e){}
	}
function PrintPage(page){//打印表
	mywindow=window.open(page,'','status=yes,menubar=yes,scrollbars=yes,toolbar=yes,resizable=yes,width=750,height=400');
	mywindow.status=":::::::::::::::::::::::::★请按Ctr+P进行打印设置★:::::::::::::::::::::::::::::::"; 
}
function SetColor(cur){cur.style.backgroundColor="#FFFF00";cur.style.cursor='hand';}
function GetColor(cur){cur.style.backgroundColor=((cur.sectionRowIndex==0)?"#D0E9ED":(cur.sectionRowIndex%2==0)?"#F5FAFA":"#FFFFFF")}

//全选或者反全选checkBox的功能
function checkAll(){
	var boxs = document.frm.myCheckBox;
	var allBox = document.frm.allCheckBox;
	var isChecked = false;
	if(allBox.checked == false){
		allBox.checked = false;
		isChecked = false;
	}else{
		allBox.checked = true;
		isChecked = true;
	}
	for(var i=0; i<boxs.length; i++){
		boxs[i].checked = isChecked;
	}
}

//通过Id，如果已经选中，则取消选中，如果没有选择，则勾选
//调用的时候注意Id是字符串还是数字
function selectOrCancel(checkBox, Id){
	var boxs = checkBox;
	for(var i=0; i<boxs.length; i++){
		if(boxs[i].value==Id){
			if(boxs[i].checked == false){
				boxs[i].checked = true;
			}else{
				boxs[i].checked = false;
			}
			break;
		}
	}
}

//数据表格的双击事件
function doubleClick(Page, Id){
	window.location.href = Page+"?Type=See&ID="+Id;
}

//清除已经勾选的box
function clearChecked(checkBox){
	for(var i=0; i<checkBox.length; i++){
		checkBox[i].checked=undefined;
	}
}

//通过id，勾选它的checkBox，然后提交“删除”
function deleteOne(checkBox, Id){
	//清除之前勾选的box
	clearChecked(checkBox);
	
	var box = document.frm.myCheckBox;
	for(var i=0; i<box.length; i++){
		if(box[i].value==Id){
			box[i].checked=true;
			break;
		}
	}
	Dell();
}

/**
 * 限制数字输入
 * @param obj
 */
function clearNoNum(obj){  
	 obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	 obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
	 obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
	 obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

/**
 * 下面将没有进行计算的总额进行计算（模拟鼠标点击即可）
 */
function autoCountMoney(){
  	var amounts = document.getElementsByName("AMOUNT");
  	for(var i=0; i<amounts.length; i++){
  		amounts[i].click();
  	}
}

/**
 * 验证身份证
 */
function checkIdcard(CardNo){
	//CardNo = Trim(CardNo.replace(/ /g, ""));
	if (CardNo.length == 15) {
		pattern= /^\d{15}$/;
	    if (pattern.exec(CardNo)==null)return false;
	    
	    var year =  CardNo.substring(6,8);   
	    var month = CardNo.substring(8,10);if(parseFloat(month)<0 || parseFloat(month)>12)return false;
	    var day = CardNo.substring(10,12);if(parseFloat(day)<0 || parseFloat(day)>31)return false;
	    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));  
	    // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
	    if(temp_date.getYear()!=parseFloat(year)||temp_date.getMonth()!=parseFloat(month)-1||temp_date.getDate()!=parseFloat(day)){   
	        return false;
	    }else{   
	    	return true;   
	    }
	    
	}else if (CardNo.length == 18) {
		pattern= /^\d{17}(\d|x|X)$/;
	    if (pattern.exec(CardNo)==null)return false;
	    
	    var year =  CardNo.substring(6,10);
	    var month = CardNo.substring(10,12);if(parseFloat(month)<0 || parseFloat(month)>12)return false;
	    var day = CardNo.substring(12,14);if(parseFloat(day)<0 || parseFloat(day)>31)return false;   
	    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
	    // 这里用getFullYear()获取年份，避免千年虫问题   
	    if(temp_date.getFullYear()!=parseFloat(year)||temp_date.getMonth()!=parseFloat(month)-1||temp_date.getDate()!=parseFloat(day)){   
	        return false;   
	    }else{   
	        return true;   
	    }  
	}else{
		return false;
	}
} 
/**
 * 选择Base树形节点
 model：类型，为BASE_TYPE的值
 model1：子类型，为BASE_TYPE1的值
 pid：父节点编号，如果拿从根节点整个的树，值为0
 filed1：拿出的编号即BASE_ID的值要放到的地方
 filed2：拿出的编号名称即BASE_NAME的值要放到的地方
 RootChoice:是否允许选择跟节点，为1是可以选择，为0是不能选择
 LastLevel：是否允许选择最后的叶子节点，为1是只能选择叶子节点，为0是树干也可以选择
 First：值为1时是第一次调用
 All：是否展示该节点下的所有信息，为0不加权限，为1加权限
 */
function SelctTree(model,model1,pid,filed1,filed2,RootChoice,LastLevel,First,All)
{
	var m=window.showModalDialog("../common/SelectTreeOneDept.jsp?Page=Base.jsp&MODE="+model+"&MODE1="+model1+"&pid="+pid+"&RootChoice="+RootChoice+"&LastLevel="+LastLevel+"&First="+First+"&All="+All,'',"dialogWidth:410px;dialogHeight:310px;center:1;scroll:0;help:0;status:0");
	if (m!=null){
		filed1.value=m[0][0];
		filed2.value=m[0][1];
	}
}
function Query(PageView,QueryArray){
	var tmp='';
	tmp=window.showModalDialog("../common/query.htm",QueryArray,"dialogWidth:410px;dialogHeight:310px;center:1;scroll:0;help:0;status:0");
	if (tmp!=null){
		//window.showModelessDialog('../common/working.htm','','dialogWidth:410px;dialogHeight:200px;center:1;scroll:0;help:0;status:0');
		while(tmp.indexOf("Like '%")>0){
			tmp=tmp.replace("Like '%","Like '~!");
			tmp=tmp.replace("%')","~!')");
		}
		window.location=PageView+"?SQL="+tmp;
	}  
}
function SelctMulDept(f1,f2)
{try{
 	var m=window.showModalDialog("../common/SelectMulDept.jsp",'',"dialogWidth:480px;dialogHeight:380px;center:1;scroll:1;help:0;status:0");
	if (m!=null)
	{
 		f1.value="";
		f2.value="";
		for(i=0;i<m.length;i++)
		{
			var w=m[i].split("|");
			if (i==0){
				f1.value =w[0];
				f2.value =w[1];
			}else{
				f1.value =f1.value +";"+w[0];
				f2.value =f2.value +";"+w[1];
			}
		}
	}
	}catch(e){}
 }

 function SelctMulPerson(f1,f2)
 {try{
 	var m=window.showModalDialog("../common/SelectMulPerson.jsp",'',"dialogWidth:530px;dialogHeight:420px;center:1;scroll:1;help:0;status:0");
	if (m!=null){
		f1.value="";
		f2.value="";
		for(i=0;i<m.length;i++)
		{
			var w=m[i].split("|");
			if (i==0){
				f1.value =w[0];
				f2.value =w[1];
			}else{
				f1.value =f1.value +";"+w[0];
				f2.value =f2.value +";"+w[1];
			}
		}
	}
	}catch(e){}	
 }
function SelctMulPersons(f1,f2){
 try{
 	var m=window.showModalDialog("../common/SelectMulPerson1.jsp",'',"dialogWidth:530px;dialogHeight:420px;center:1;scroll:1;help:0;status:0");
	if (m!=null){
		f1.value="";
		f2.value="";
		for(i=0;i<m.length;i++)
		{
			var w=m[i].split("|");
			if (i==0){
				f1.value =w[0];
				f2.value =w[1];
			}else{
				f1.value =f1.value +";"+w[0];
				f2.value =f2.value +";"+w[1];
			}
		}
	}
 }catch(e){}	
}