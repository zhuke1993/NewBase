package sei;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormDataTest extends HttpServlet {

	private static final long serialVersionUID = 1424762353587641833L;

	public void destroy() {
		super.destroy(); 
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("{\"SYS_ID\" : \"SYS_ID\",\"USER_TYPE\" : \"USER_TYPE\",\"USER_ID\" : \"USER_ID\",\"USER_MOB_PHONE\" : \"USER_MOB_PHONE\",\"USER_NAME\" : \"USER_NAME\",\"USER_ADDR\" : \"USER_ADDR\"}");
		out.flush();
		out.close();
	}
}