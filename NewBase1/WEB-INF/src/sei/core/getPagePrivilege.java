package sei.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sei.Base;
import sei.security.Privilege;
import sei.security.PrivilegeObj;
import sei.util.Tools;

public class getPagePrivilege extends HttpServlet{
	private static final long serialVersionUID = 3799102280447336030L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8"); 
		String UserID=(String)request.getSession(false).getAttribute("UserID");
		String RoleID=(String)request.getSession(false).getAttribute("RoleID");
		if(UserID==null || RoleID==null){
			Privilege.NoLogin((HttpServletResponse)response);
			return;
		}
		String id=request.getParameter("id");
		if(id==null)id="0";
		DBConnect sys=null;
		PrintWriter out = response.getWriter();
		try{
			PrivilegeObj pvobj=Privilege.getPrivilegeObj(RoleID,"privilege");
			if(!((pvobj.doBrowser==0)?false:true)){
				Privilege.NoLogin((HttpServletResponse)response);
				return;
			}
			ModelObj Tf=Base.ModelObj.get("privilege");
			if(Tf==null)return ;
			
//			Integer rows =20,page=1;
			String filed="",filedvalue="";
			StringBuffer par=new StringBuffer();
			Enumeration<String> pp=request.getParameterNames();
			LinkedList<String> Val=new LinkedList<String>();
			int i=0;
			String[] tmp = new String[2];
			boolean ff=true;
			sys=new DBConnect();
			while(pp.hasMoreElements()){
				filed=pp.nextElement();//字段名称
				if(!filed.equals("page") && !filed.equals("rows") && !filed.equals("ffmodel") && !filed.equals("id")){
					filedvalue=request.getParameter(filed);//字段内容
					tmp=Tools.getSplitOptionValue(filedvalue);
					if(tmp!=null){
						if(!tmp[1].equals("")){
							if(tmp[0].toLowerCase().startsWith("tree(")){
								tmp[0]="getTreeChilds"+tmp[0].substring(4);
								sys.pstmt=sys.DoParSQL("SELECT CONCAT('("+filed+"=''',REPLACE("+tmp[0]+",',',''' or "+filed+"='''),''')')");
								sys.pstmt.setString(1,tmp[1]);
								sys.rs=sys.pstmt.executeQuery();
								if(sys.rs!=null){
									if(sys.rs.next()){
										if(par.length()>0){
											par.append(" and ");
										}
										par.append(sys.rs.getString(1));
									}
								}
							}else{
								if(par.length()>0){
									par.append(" and ");
								}
								par.append(" ").append(filed).append(" ").append(tmp[0]).append(" ?");
								Val.add(tmp[1]);//字段内容
							}
						}
					}
				}
			}
			
			
			//StringBuffer o=new StringBuffer("{\"total\":\"");
			StringBuffer o=new StringBuffer();
			
			String mm=Privilege.getList_Scope(sys,UserID, pvobj.doBrowser, pvobj.browser_scope, Tf.DeptPv, Tf.UserPv);
			filedvalue=Tools.getAddSQL("where ", Tools.getSQL(mm,"and", par.toString()));
			
			if(id.equals("0")){
				sys.pstmt=sys.con.prepareStatement("SELECT base_id as id,base_name as text,parent_id as _parentId FROM T_SYS_BASE WHERE base_type='system' and parent_id=? "+filedvalue+" ORDER BY BASE_ORDER ASC");
				sys.pstmt.setString(1,id);
				sys.rs=sys.pstmt.executeQuery();
				if(sys.rs!=null){
					o.append("[");
					ff=true;
					while (sys.rs.next()){
						if (ff){
							ff=false;
						}else{
							o.append(",");
						}
						o.append("{\"id\":\"").append(sys.rs.getString(1)).append("\",\"text\":\"").append(sys.rs.getString(2)).append("\",\"state\":\"closed\"}");
					}
					o.append("]");
				}
			}else{
				sys.pstmt=sys.con.prepareStatement("SELECT obj_id as id,(select obj_name from t_sys_obj where t_sys_obj.obj_id=t_sys_privilege.obj_id) as text,pr_browse,pr_add,pr_edit,pr_del,pr_audit,pr_memo FROM T_SYS_Privilege where EXISTS (select 1 from t_sys_obj where obj_id=t_sys_privilege.obj_id and sys_id=?)  "+filedvalue+" ORDER BY obj_id ASC");
				i=0;
				sys.pstmt.setString(++i, id);
				for(String temp:Val){
					sys.pstmt.setString(++i, temp);
			    }
				sys.rs=sys.pstmt.executeQuery();
				if(sys.rs!=null){
					o.append("[");
					ff=true;
					while (sys.rs.next()){
						if (ff){
							ff=false;
						}else{
							o.append(",");
						}
						o.append("{\"id\":\"").append(sys.rs.getString(1)).append("\",\"text\":\"").append(sys.rs.getString(2)).append("\",\"_parentId\":\"").append(id).append("\",\"pr_browse\":\"").append(sys.rs.getString(3)).append("\",\"pr_add\":\"").append(sys.rs.getString(4)).append("\",\"pr_edit\":\"").append(sys.rs.getString(5)).append("\",\"pr_del\":\"").append(sys.rs.getString(6)).append("\",\"pr_audit\":\"").append(sys.rs.getString(7)).append("\",\"pr_memo\":\"").append(sys.rs.getString(8)).append("\"}");
					}
					o.append("]");
				}				
			}
			out.print(o.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		if(sys!=null)sys.CloseDataBase();
		out.close();
	}
}
