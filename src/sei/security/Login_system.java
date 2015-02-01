package sei.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login_system extends HttpServlet{
	private static final long serialVersionUID = -9038513743941886474L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        PrintWriter out=response.getWriter();
        String Type = request.getParameter("Type");
        if (Type == null) {Type = "";}
        if(Type.equals("Login")){
//        	int flag=Login.Verification(request);
//        	if(flag==1){
//        		out.print("<script>window.location.href='/main.shtml'</script>");
//        	}else{
//        		out.print(flag);
//        	}
        	out.print(Login.Verification(request));
        }else if(Type.equals("Logout")){
        	Login.Logout(request);
            out.print("<script>window.location.href='/'</script>");
        }
        out.flush();
        out.close();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        doGet(request,response);
    }
}
