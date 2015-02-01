package sei.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sei.Base;
import sei.security.Privilege;
import sei.security.PrivilegeObj;

public class getChoiceData extends HttpServlet {
	private static final long serialVersionUID = -6757514385631739493L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8");
		String UserID=(String)request.getSession(false).getAttribute("UserID");
		if(UserID==null || UserID.equals(""))return;
		String RoleID=(String)request.getSession(false).getAttribute("RoleID");
		String model =request.getParameter("model");
		if(model==null || model.equals(""))return;
		
		DBConnect sys=new DBConnect();
		try{
			//查看是否有列表查看权限
			ModelObj Tf=Base.ModelObj.get(model);
			if(Tf!=null){
				PrivilegeObj pvobj=Privilege.getPrivilegeObj(RoleID,model);
				if(((pvobj.doBrowser==0)?false:true)){
					String mm=Privilege.getList_Scope(sys,UserID, pvobj.doBrowser, pvobj.browser_scope, Tf.DeptPv, Tf.UserPv);
					
				}
			}
		}catch(Exception e){}
		sys.CloseDataBase();
	}
}
