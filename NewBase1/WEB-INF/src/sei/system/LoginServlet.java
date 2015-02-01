package sei.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sei.core.DBConnect;
import sei.core.Log;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 3528998516961215833L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		String Type = request.getParameter("Type");
		if (Type == null) {Type = "";}
		int flag = -1;//登录是否成功标志,1:登录成功,-1:账户或密码错误,-2:账户没有启用,-3:IP不对
		if(Type.equals("Login")){
			String UserID = request.getParameter("UserID");
			if (UserID == null) {UserID = "";}
			String Password = request.getParameter("Password");
			if (Password == null) {Password = "";}
			if(!UserID.equals("")&&!Password.equals("")){
				String IP = request.getRemoteAddr();
				DBConnect sys = new DBConnect();
				try {
					sys.pstmt = sys.DoParSQL("select USER_ID,USER_PWD,USER_IP,USER_STATUS from T_SYS_USER where USER_ID=? and USER_PWD=?");
					sys.pstmt.setString(1, UserID);
					sys.pstmt.setString(2, Password);
					sys.rs=sys.pstmt.executeQuery();
					if (sys.rs != null) {
						if (sys.rs.next()) {
							if (sys.rs.getString("USER_ID").trim().equals(UserID)& sys.rs.getString("USER_PWD").trim().equals(Password)) {
								if(sys.rs.getInt("USER_STATUS")==1){
									String LIP = sys.rs.getString("USER_IP").trim();
									if (LIP.equals("") | LIP.equals("*")) {
										flag = 1;
									} else {
										if (LIP.indexOf(IP) != -1) {
											flag = -3;
										} else {
											flag = 1;
										}
									}
								}else{
									flag=-2;
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(flag==1){
						session.setAttribute("UserID", UserID);
						Log.LogDo(sys, UserID, "login", 1, "登陆", "");
					}
					sys.CloseDataBase();
				}
				
			}
		}else if(Type.equals("Logout")){
			session.invalidate();
			flag=1;
		}
		out.print(flag);
		out.flush();
		out.close();
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doGet(request,response);
	}
}
