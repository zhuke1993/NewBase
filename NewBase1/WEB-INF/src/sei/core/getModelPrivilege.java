package sei.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sei.security.Privilege;

public class getModelPrivilege extends HttpServlet{
	private static final long serialVersionUID = 5059845643700733310L;
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8"); 
		String UserID=(String)request.getSession(false).getAttribute("UserID");
		String RoleID=(String)request.getSession(false).getAttribute("RoleID");
		if(UserID==null || RoleID==null){
			Privilege.NoLogin((HttpServletResponse)response);
			return;
		}
		String rowdocid =request.getParameter("rowdocid");
		if(rowdocid==null || rowdocid.equals("")){
			return;
		}
		DBConnect sys=new DBConnect();
		StringBuffer o=new StringBuffer();
		PrintWriter out = response.getWriter();
		try{
			o.append("["+Privilege.ModelPrivilege[0]+","+Privilege.ModelPrivilege[1]+","+Privilege.ModelPrivilege[2]+"]");
			
//			sys.pstmt=sys.DoParSQL("select obj_privmodule,obj_privtype from t_sys_obj where obj_id=?");
//			sys.pstmt.setString(1, rowdocid);
//			sys.rs=sys.pstmt.executeQuery();
//			if(sys.rs!=null){
//				o.append("[");
//				boolean ff=true;
//				if (sys.rs.next()){
//					String[] mm=sys.rs.getString(1).split(",");
//					if(mm.length>0){
//						if (ff){
//							ff=false;
//							o.append("{");
//						}else{
//							o.append(",{");
//						}
//						o.append("\"id\":\"").append(sys.rs.getString(1)).append("\",\"text\":\"").append(sys.rs.getString(2)).append("\"");
//						o.append("}");
//					}else{
//						
//					}
//				}
//				o.append("]");
//			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		sys.CloseDataBase();
		out.print(o.toString());
		out.flush();
		out.close();
	}
}
