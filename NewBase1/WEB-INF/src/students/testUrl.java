package students;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sei.core.DBConnect;
import sei.util.Tools;

public class testUrl extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		
		
		// out.print("");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String model = request.getParameter("model");
		
		
		if(model.equals("soft")){
			out.print("[{\"id\":1,\"text\":\"学工系统\",\"selected\":true},{\"id\":\"2\",\"text\":\"学工系统\"}]");
			System.out.println("[{\"id\":\"1\",\"text\":\"学工系统\",\"selected\":ture}]");
		}else{
			out.print("[{\"id\":1,\"text\":\"网上党校系统\",\"selected\":true}]");
			System.out.println("[{\"id\":\"1\",\"text\":\"网上党校系统\",\"selected\":ture}]");
		}
	}

}
