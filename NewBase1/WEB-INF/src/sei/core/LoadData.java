package sei.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import sei.Base;
import sei.security.Privilege;
import sei.security.PrivilegeObj;
import sei.util.Tools;

public class LoadData extends HttpServlet{
	private static final long serialVersionUID = 1867635049497479011L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8"); 
		String UserID=(String)request.getSession(false).getAttribute("UserID");
		String RoleID=(String)request.getSession(false).getAttribute("RoleID");
		if(UserID==null || RoleID==null){
			Privilege.NoLogin((HttpServletResponse)response);
			return;
		}
		DBConnect sys=null;
		//rows=Integer.valueOf(request.getParameter("rows"));
		//page=Integer.valueOf(request.getParameter("page"));
		//String model=request.getParameter("moffmodel;
		String ffmodel =request.getParameter("ffmodel");
		if(ffmodel==null || ffmodel.equals("")){return;}

		String id=request.getParameter("id");
		if(id==null || id.equals("")){return;}
		
		ModelObj Tf=Base.ModelObj.get(ffmodel);
		if(Tf==null)return ;
		
		PrintWriter out = response.getWriter();
		
		try{
			PrivilegeObj pvobj=Privilege.getPrivilegeObj(RoleID,ffmodel);
			if(!((pvobj.doBrowser==0)?false:true)){
				Privilege.NoLogin((HttpServletResponse)response);
				return;
			}
			sys=new DBConnect();
			
			//String mm=JSON.toJSONString(sys);
			byte edit=0;
			sys.pstmt=sys.DoParSQL(Tf.loaddata+ Tools.getAddSQL(" and ",Privilege.getList_Scope(sys,UserID, pvobj.doEdit, pvobj.edit_scope, Tf.DeptPv, Tf.UserPv)));
			sys.pstmt.setString(1, id);
			sys.rs=sys.pstmt.executeQuery();
			if(sys.rs!=null){
				if(sys.rs.next()){
					edit=1;
				}
			}
			
			byte delete=0;
			sys.pstmt=sys.DoParSQL(Tf.loaddata+ Tools.getAddSQL(" and ",Privilege.getList_Scope(sys,UserID, pvobj.doDelete, pvobj.delete_scope, Tf.DeptPv, Tf.UserPv)));
			sys.pstmt.setString(1, id);
			sys.rs=sys.pstmt.executeQuery();
			if(sys.rs!=null){
				if(sys.rs.next()){
					delete=1;
				}
			}
			sys.pstmt=sys.DoParSQL(Tf.loaddata+ Tools.getAddSQL(" and ",Privilege.getList_Scope(sys,UserID, pvobj.doBrowser, pvobj.browser_scope, Tf.DeptPv, Tf.UserPv)));
			sys.pstmt.setString(1, id);
			sys.rs=sys.pstmt.executeQuery();
			if(sys.rs!=null){
				if(sys.rs.next()){
					StringBuffer o=new StringBuffer("{\"canEdit\":"+edit+",\"canDel\":"+delete+",\"form\":{");
					ResultSetMetaData rsmd = sys.rs.getMetaData();
					int cols = rsmd.getColumnCount();
					for(int i=1;i<=cols;i++){
						//System.out.println(rsmd.getColumnLabel(i).toUpperCase()+"==>"+sys.rs.getString(i));
						if(i>1)o.append(",");
						o.append("\"").append(rsmd.getColumnLabel(i).toUpperCase()).append("\":\"").append(Tools.FiledValue(sys.rs.getString(i))).append("\"");
					}
					o.append("}}");
					out.print(o.toString());
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(sys!=null)sys.CloseDataBase();
		out.flush();
		out.close();

	}
}
