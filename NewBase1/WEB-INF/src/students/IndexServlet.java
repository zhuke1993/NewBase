package students;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sei.core.DBConnect;

public class IndexServlet extends HttpServlet{
	private static final long serialVersionUID = -5872483689080731251L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		
		String model = request.getParameter("model");//模块
		if(model==null)return;
		DBConnect sys = new DBConnect();
		try{
			sys.pstmt=sys.DoParSQL("select NC_ID,NC_TITLE,DATE_FORMAT(PUSER_TIME,'%Y-%m-%d') from T_SYS_NOTIC WHERE NC_MODE=? ORDER BY PUSER_TIME DESC limit 0,8");
			sys.pstmt.setString(1, model);
			sys.rs=sys.pstmt.executeQuery();
			if (sys.rs != null) {
				while (sys.rs.next()) {
					out.print("<p><a target='blank' href='students/xw/detail.jsp?ID="+sys.rs.getString(1)+"'>"+sys.rs.getString(2)+"</a><span>"+sys.rs.getString(3)+"</span></p>");
				}
			}
		}catch(Exception e){
			
		}
		sys.CloseDataBase();
		out.flush();
		out.close();
	}
}
