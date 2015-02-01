package sei.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sei.core.Privilege;
import sei.core.Pv;

public class MainTreeServlet extends HttpServlet{
	private static final long serialVersionUID = 2455001409242518903L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String UserID=(String)request.getSession(false).getAttribute("UserID");
		if(UserID==null){
			response.sendRedirect("/");
			return;
		}
		String model=request.getParameter("model");if (model==null)return;//模块,对应base_type
		String page=request.getParameter("page");if (page==null)return;//对应的jsp页面

		Privilege sys=new Privilege();
		Pv pv=sys.getPvPageList(model,UserID,"","PUSER_ID",Pv.Browser,Pv.Add);
		if (!pv.PR_BROWSE){
			response.sendRedirect(request.getContextPath()+"/system/NoPurview.html");
			sys.CloseDataBase();
			out.close();
			return;
		}
		
		out.print("<head><title></title><meta charset=\"utf-8\"><link href=\""+request.getContextPath()+"/system/easyui/themes/default/easyui.css\" rel=\"stylesheet\" /><link href=\""+request.getContextPath()+"/system/easyui/themes/icon.css\" rel=\"stylesheet\" /><script src=\""+request.getContextPath()+"/system/easyui/jquery.min.js\"></script><script src=\""+request.getContextPath()+"/system/easyui/jquery.easyui.min.js\"></script><script src=\""+request.getContextPath()+"/system/easyui/locale/easyui-lang-zh_CN.js\"></script></head>");
		out.print("<body class=\"easyui-layout\"><div data-options=\"region:'north'\" style=\"height:30px;\">");
		if (pv.PR_ADD)out.print("<a href='#' id='btn-add' class='easyui-linkbutton' iconCls='icon-add'>新增</a>");
//		if (pv.PR_DEL)out.print("<a href='#' id='btn-remove' class='easyui-linkbutton' iconCls='icon-remove'>删除</a>");
		out.println("<a href='#' id='btn-reload' class='easyui-linkbutton' iconCls='icon-reload'>刷新</a>");
		out.print("</div>");
		
		
		out.print("<div data-options=\"region:'west',split:true\" style=\"width:220px;padding-top:20px\" id=\"menu\"><div class=\"easyui-tree\" id=\"left-tree\"></div></div>");
		out.print("<div data-options=\"region:'center'\" style=\"padding:0px;background:#eee;\"><iframe src='system/init.html' width='100%' height='100%' frameborder='0'></iframe></div>");
		
		out.print("</body>");
		out.print("<script>$('#left-tree').tree({'url' : '/TreeServlet.do?model="+model+"&page="+page+"','method':'get','onClick' : function(node){if(node.attributes && node.attributes[\"data-href\"]){$('iframe').attr(\"src\", node.attributes[\"data-href\"]);}}});");
		out.print("$('#btn-reload').click(function(){$('iframe')[0].src='/system/init.html';$('#left-tree').tree(\"reload\");});");
        //out.print("$('#btn-reload').click(function(){Window.location.reload();});");
		if (pv.PR_ADD)out.print("$('#btn-add').click(function(){$('iframe')[0].contentWindow.location.href='/system/"+page+"?model="+model+"';});");
//		if (pv.PR_DEL)out.print("$('#btn-remove').click(function(){$('iframe')[0].contentWindow.Delete();});");
//		out.print("$('#btn-edit').click(function(){$('iframe')[0].contentWindow.edit();});");
       
		out.print("</script></html>");
		sys.CloseDataBase();
		out.flush();
		out.close();
	}
}
