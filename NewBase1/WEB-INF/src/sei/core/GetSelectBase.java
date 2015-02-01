package sei.core;

import java.io.IOException;
import java.io.PrintWriter;
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

public class GetSelectBase extends HttpServlet{
	private static final long serialVersionUID = 5092455633598024204L;
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
		//String model=request.getParameter("model");
		String ffmodel =request.getParameter("ffmodel");
		if(ffmodel==null || ffmodel.equals("")){
			return;
		}

		String id=request.getParameter("id");
		if(id==null)id="0";

		String base_type=request.getParameter("base_type");
		if(base_type==null)base_type=ffmodel;
		String base_type1=request.getParameter("base_type1");
		if(base_type1==null)base_type1="";
		
		
		PrintWriter out = response.getWriter();
		try{
			PrivilegeObj pvobj=Privilege.getPrivilegeObj(RoleID,ffmodel);
			if(!((pvobj.doBrowser==0)?false:true)){
				Privilege.NoLogin((HttpServletResponse)response);
				return;
			}
			ModelObj Tf=Base.ModelObj.get(ffmodel);
			if(Tf==null)return ;
			
			String filed="",filedvalue="";
			StringBuffer par=new StringBuffer();
			Enumeration<String> pp=request.getParameterNames();
			LinkedList<String> Val=new LinkedList<String>();
			int i=0;
			String[] tmp = new String[2];
			sys=new DBConnect();
			while(pp.hasMoreElements()){
				filed=pp.nextElement().toLowerCase();//字段名称
				if(!filed.equals("ffmodel") && !filed.equals("id") && !filed.equals("base_type") && !filed.equals("base_type1")){
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
			
			String mm=Privilege.getList_Scope(sys,UserID, pvobj.doBrowser, pvobj.browser_scope, Tf.DeptPv, Tf.UserPv);
			
			filedvalue=Tools.getSQL(mm,"and", par.toString());
			if(!filedvalue.equals(""))filedvalue=" and "+filedvalue;
			
			sys.pstmt=sys.con.prepareStatement("SELECT BASE_ID,BASE_NAME,(select count(*) from t_sys_base t  where t.parent_id=T_SYS_BASE.base_id and base_type=?"+((base_type1.equals(""))?"":" and base_type1=?")+") FROM T_SYS_BASE WHERE base_type=?"+((base_type1.equals(""))?"":" and base_type1=?")+" and parent_id=? "+filedvalue+" ORDER BY BASE_ORDER ASC");
			i=0;
			sys.pstmt.setString((++i),base_type);
			if(base_type1.equals("")){
				sys.pstmt.setString((++i),base_type);
			}else{
				sys.pstmt.setString((++i),base_type1);
				sys.pstmt.setString((++i),base_type);
				sys.pstmt.setString((++i),base_type1);
			}
			sys.pstmt.setString((++i),id);
			for(String temp:Val){
				sys.pstmt.setString(++i, temp);
		    }
			sys.rs=sys.pstmt.executeQuery();
			StringBuffer o=new StringBuffer();
			if(sys.rs!=null){
				o.append("[");
				i=0;
				while(sys.rs.next()){
					if (i==0){
						i=1;
					}else{
						o.append(",");
					}
					if(sys.rs.getInt(3)>0){
						//out.print("{\"id\":\""+sys.rs.getString(1)+"\",\"text\":\""+sys.rs.getString(2)+"\",\"state\":\"closed\",\"attributes\":{\"data-href\":\""+request.getContextPath()+page+"?model="+model+"&ID="+sys.rs.getString(1)+"\"}}");
						//o.append("{\"id\":\"").append(sys.rs.getString(1)).append("\",\"text\":\"").append(sys.rs.getString(2)).append("\",\"state\":\"closed\",\"attributes\":{\"data-href\":\"").append("url:'/getChoiceBase.do?model=").append(model).append("&parent_id=").append(sys.rs.getString(1)).append("'\"}}");
						o.append("{\"id\":\"").append(sys.rs.getString(1)).append("\",\"text\":\"").append(sys.rs.getString(2)).append("\",\"state\":\"closed\"}");
					}else{
						//out.print("{\"id\":\""+sys.rs.getString(1)+"\",\"text\":\""+sys.rs.getString(2)+"\",\"state\":\"open\",\"attributes\":{\"data-href\":\"url:'/getChoiceBase.do?model="+model+"&parent_id="+sys.rs.getString(1)+"'\"}}");
						o.append("{\"id\":\"").append(sys.rs.getString(1)).append("\",\"text\":\"").append(sys.rs.getString(2)).append("\",\"state\":\"open\"}");
					}
				}
				o.append("]");
			}
			out.print(o.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		if(sys!=null)sys.CloseDataBase();
		out.flush();
		out.close();
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doGet(request,response);
	}
}
