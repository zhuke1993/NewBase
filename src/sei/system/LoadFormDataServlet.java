package sei.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class LoadFormDataServlet extends HttpServlet{
	private static final long serialVersionUID = -7775131091667403934L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8"); 
		String UserID=(String)request.getSession(false).getAttribute("UserID");
		if(UserID==null){
			response.sendRedirect("/");
			return;
		}
		String ID = request.getParameter("ID");if (ID==null)ID="";
		if (ID.equals("")) return;
		PrintWriter out = response.getWriter();
		JsonFilter filter=new JsonFilter();
		Base sys=new Base();
		sys.CloseDataBase();
		String mm="";
		try{
			com.alibaba.fastjson.util.TypeUtils.compatibleWithJavaBean = true;
			mm=JSON.toJSONString(sys,filter);
		}catch(Exception e){
			e.printStackTrace();
		}
		out.print(mm);
		out.close();
		
	}
}
