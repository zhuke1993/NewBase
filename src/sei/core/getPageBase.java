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

public class getPageBase extends HttpServlet{
	private static final long serialVersionUID = 3143462610134141399L;
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
//				if(filed.equals("page")){
//					page=Integer.valueOf(request.getParameter("page"));
//					if(page==0)page=1;
//				}else if(filed.equals("rows")){
//					rows=Integer.valueOf(request.getParameter("rows"));
//				}else 
				if(!filed.equals("page") && !filed.equals("rows") && !filed.equals("ffmodel") && !filed.equals("id") && !filed.equals("base_type") && !filed.equals("base_type1")){
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
			
			filedvalue=Tools.getSQL(mm,"and", par.toString());
			if(!filedvalue.equals(""))filedvalue=" and "+filedvalue;
			//filedvalue=Tools.getSQL(mm,"and", par.toString());
			
//			sys.pstmt=sys.DoParSQL(Tf.total +" where parent_id='0' and base_type=? "+filedvalue);
//			i=0;
//			sys.pstmt.setString(++i, ffmodel);
//			for(String temp:Val){
//				sys.pstmt.setString(++i, temp);
//		    }
//			sys.rs=sys.pstmt.executeQuery();
//			if(sys.rs.next()){
//				o.append(sys.rs.getInt(1));
//			}
			
//			sys.pstmt=sys.DoParSQL(Tf.PageRows+" where parent_id='0' and base_type=? "+filedvalue+" order by sys_id asc,parent_id asc limit ?,?");
//			i=0;
//			sys.pstmt.setString(++i, ffmodel);
//			for(String temp:Val){
//				sys.pstmt.setString(++i, temp);
//		    }
//			sys.pstmt.setInt((++i), ((page-1)* rows));
//			sys.pstmt.setInt((++i), rows);
//			sys.rs=sys.pstmt.executeQuery();
			sys.pstmt=sys.con.prepareStatement("SELECT base_id as id,base_name,parent_id as _parentId,base_type1,base_status,base_ext1,base_ext2,base_ext3,base_ext4,base_ext5,base_ext6,base_ext7,base_ext8,base_ext9,base_ext10,base_ext11,base_ext12,base_ext13,base_ext14,base_ext15,base_ext16,base_ext17,base_ext18,base_ext19,base_ext20,base_memo,(select count(*) from t_sys_base t  where t.parent_id=T_SYS_BASE.base_id and base_type=?"+((base_type1.equals(""))?"":" and base_type1=?")+") as count FROM T_SYS_BASE WHERE base_type=?"+((base_type1.equals(""))?"":" and base_type1=?")+" and parent_id=? "+filedvalue+" ORDER BY BASE_ORDER ASC");
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
			if(sys.rs!=null){
				ResultSetMetaData rsmd = sys.rs.getMetaData();
				int cols = rsmd.getColumnCount();
				//o.append("\",\"rows\":[");
				o.append("[");
				ff=true;
				while (sys.rs.next()){
					if (ff){
						ff=false;
						o.append("{");
					}else{
						o.append(",{");
					}
					for(i=1;i<cols;i++){
						if(!rsmd.getColumnLabel(i).toLowerCase().equals("_parentid") || !sys.rs.getString(i).equals("0")){
							if(i>1)o.append(",");
							o.append("\"").append(rsmd.getColumnLabel(i)).append("\":\"").append(sys.rs.getString(i)).append("\"");
						}
					}
					if(sys.rs.getInt(cols)>0){
						o.append(",\"state\":\"closed\"");
					}
					o.append("}");
				}
				//o.append("]}");
				o.append("]");
			}
			//com.alibaba.fastjson.util.TypeUtils.compatibleWithJavaBean = true;
			//mm=JSON.toJSONString(sys,filter);	
			out.print(o.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		if(sys!=null)sys.CloseDataBase();
		out.close();
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doGet(request,response);
	}
}
