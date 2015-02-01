package students;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sei.core.DBConnect;
import sei.util.Tools;

public class MoreInformation extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// doGet(request, response);
		PrintWriter out = response.getWriter();
		String value = request.getParameter("value");
		System.out.println("返回数据:[{\"id\":\"1\",\"text\":\"" + value
				+ "\",\"selected\":true}]");
		out.print("[{\"id\":\"1\",\"text\":\"1110\",\"selected\":true}]");
		// out.print("");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("...");
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();

			String model = request.getParameter("model");
			String filed = request.getParameter("filed");
			String value = request.getParameter("value");
			String sql = "";
			if (model == null || model.equals("")) {
				return;
			} else {
				model = "t_sys_" + model;
			}
			if (filed == null || filed.equals("")) {
				return;
			}
			if (value == null || value.equals("")) {
				out.print("");
				return;
			}
			DBConnect sys = new DBConnect();
			StringBuffer o = new StringBuffer("[");
			sql = "SELECT " + filed + " FROM " + model + " where " + filed
					+ " like " + "\"" + value + "%\"";
			sys.pstmt = sys.DoParSQL(sql + " limit 0,10");
			sys.rs = sys.pstmt.executeQuery();
			if (sys.rs != null) {
				int i = 0;
				boolean ff = true;
				while (sys.rs.next()) {
					if (ff) {
						ff = false;
						o.append("{");
					} else {
						o.append(",{");
					}
					o.append("\"id\":");
					o.append(++i);
					o.append(",");
					o.append("\"text\":");
					o.append("\"" + sys.rs.getString(filed) + "\"}");
				}
				o.append("]");
			}
			System.out.println(o.toString());
			out.print(o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
