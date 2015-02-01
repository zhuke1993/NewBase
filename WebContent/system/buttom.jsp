<%@page contentType="text/html;charset=utf-8"%>
    </form>
    <div style="text-align:center;padding:5px" id="cc">
    	<%=BT%>	
    </div>
    </div>
</div>
<script>
	function submitForm(){
		if(validate.val())ff.submit();
	}
	function edit(){window.location.href="<%=request.getRequestURI()%>?OPType=Update&ID=<%=ID%>";}
	function dell(){
		var myFlag = window.confirm("删除后将不能恢复，真的要删除吗？");
	    if(myFlag){
			$$.ajax({
				url : "/SaveForm.do?Cls=<%=sys.getClass().getName()%>&model=<%=sys.getMODEL()%>&OPType=Delete&ID=<%=ID%>",
				params: "",
				dataType:"text",
				success: function (msg){
					if(msg==1){
						alert("删除成功！");
						window.parent.location.reload();
					}else if(msg==-1){
						alert("由于系统或网络错误，无法删除该记录，请稍后再试！");
					}else if(msg==-2){
						alert("由于系统未登陆或长时间未操作，导致系统等待超时，系统或网络错误，无法删除该记录，请稍后再试！");
					}else if(msg==-3){
						alert("由于权限不够，你不能删除该记录！");
					}
//					window.document.body.innerHTML=msg;
				}
			});
	    }
	}
</script>
<%sys.CloseDataBase();%>