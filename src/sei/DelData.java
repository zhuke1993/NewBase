package sei;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DelData extends HttpServlet {

	private static final long serialVersionUID = 1424762353587641833L;

	public void destroy() {
		super.destroy(); 
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("{\"id\":\"1\",\"message\":\"成功\"}");
		out.flush();
		out.close();
	}
}