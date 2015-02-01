package sei.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdk.nashorn.internal.objects.annotations.Where;

import org.omg.CORBA.TCKind;

import sei.Base;
import sei.security.Privilege;
import sei.security.PrivilegeObj;
import sei.util.Tools;

public class SaveForm extends HttpServlet{
	private static final long serialVersionUID = -8908148430260646154L;
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//response.setCharacterEncoding("utf-8");
//		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8"); 
		String UserID=(String)request.getSession(false).getAttribute("UserID");
		String RoleID=(String)request.getSession(false).getAttribute("RoleID");
		PrintWriter out=response.getWriter();
		if(UserID==null || RoleID==null){
			//Privilege.NoLogin((HttpServletResponse)response);
			out.print("{\"id\":2,\"message\":\"你已经下线，请重新登陆后再试！\"}");
			return;
		}
		String ffmodel =request.getParameter("ffmodel");
		if(ffmodel==null || ffmodel.equals("")){
			return;
		}
		String fftype =request.getParameter("fftype");
		if(fftype==null || fftype.equals("")){
			return;
		}
		
		
		int flag = -1;//保存成功标志,1:成功,-1:未知错误,-2:未登陆,-3:没有权限,-4关键字重复,
		//System.out.println(request.getHeader("Referer"));
		if(fftype.equals("1")){//新增
			PrivilegeObj pvobj=Privilege.getPrivilegeObj(RoleID,ffmodel);
			if(!((pvobj.doAdd==0)?false:true)){
				Privilege.NoLogin((HttpServletResponse)response);
				return;
			}
			ModelObj Tf=Base.ModelObj.get(ffmodel);
			if(Tf==null)return ;
			
			DBConnect sys=new DBConnect();
			Statement stmt=null;
			try{
				stmt=sys.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				sys.rs=stmt.executeQuery("select * from "+Tf.Table+" LIMIT 0,0");
				sys.rs.moveToInsertRow();
				String filed="";
				Enumeration<String> pp=request.getParameterNames();
				while(pp.hasMoreElements()){
					filed=pp.nextElement();//字段名称
					if(!filed.equals("ffmodel") && !filed.equals("fftype") && !filed.equals("rowdocid")){
						Object val=request.getParameter(filed);//字段内容
						//System.out.println(filed+"  ==> "+val.toString());
						if(val!=null){
							try{
								sys.rs.updateObject(filed, val);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
				sys.rs.insertRow();
				flag=1;
			}catch(Exception e){
				e.printStackTrace();
			}
			try{sys.rs.close();}catch(Exception e){}
			try{stmt.close();}catch(Exception e){}

//			String filed="",filedvalue="";
//			Enumeration<String> pp=request.getParameterNames();
//			LinkedList<String> Val=new LinkedList<String>();
//			StringBuffer par=new StringBuffer();
//			StringBuffer pval=new StringBuffer();
//			//ArrayList<Integer> FieldType=new ArrayList<Integer>();//暂时没用
//			while(pp.hasMoreElements()){
//				filed=pp.nextElement().toUpperCase();//字段名称
//				Integer tmp=Tf.FiledType.get(filed);
//				if(tmp!=null){//有这个字段
//					//FieldType.add(tmp);
//					if(par.length()>0){par.append(",");pval.append(",");}
//					par.append(filed);pval.append("?");
//					filedvalue=request.getParameter(filed);//字段内容
//					//System.out.println(filed+" ==> "+tmp);
//					if(tmp==-5 || tmp==-6 || tmp==-7 || tmp==3 || tmp==8 || tmp==6 || tmp==4 || tmp==2 || tmp==7 || tmp==5){//数字
//						if (filedvalue==null || filedvalue.trim().equals(""))filedvalue="0";
//					}else{
//						if (filedvalue==null)filedvalue="";
//					}
//					Val.add(filedvalue);
//				}
//			}
//			DBConnect sys=new DBConnect();
//			try{
//				sys.pstmt=sys.DoParSQL("insert into "+Tf.Table+"("+par+")values("+pval+")");
//				flag=0;
//				for(String temp:Val){
//					sys.pstmt.setString(++flag, temp);
//			    }
//				flag=-1;
//				flag=sys.pstmt.executeUpdate();
//			}catch(Exception e){
//				e.printStackTrace();
//			}
			sys.CloseDataBase();
		}else if(fftype.equals("2")){//修改
			String rowdocid =request.getParameter("rowdocid");
			if(rowdocid==null || rowdocid.equals("")){
				return;
			}
			
			PrivilegeObj pvobj=Privilege.getPrivilegeObj(RoleID,ffmodel);
			if(!((pvobj.doBrowser==0)?false:true)){
				Privilege.NoLogin((HttpServletResponse)response);
				return;
			}
			ModelObj Tf=Base.ModelObj.get(ffmodel);
			if(Tf==null)return ;
			
			DBConnect sys=new DBConnect();
			Statement stmt=null;
			try{
				stmt=sys.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				sys.rs=stmt.executeQuery("select * from "+Tf.Table+" where "+Tf.PrimaryKey+"='"+rowdocid+"'"+ Tools.getAddSQL(" and ",Privilege.getList_Scope(sys,UserID, pvobj.doEdit, pvobj.edit_scope, Tf.DeptPv, Tf.UserPv)));
//				sys.pstmt=sys.DoParSQL(Tf.loaddata+ Tools.getAddSQL(" and ",Privilege.getList_Scope(sys,UserID, pvobj.doEdit, pvobj.edit_scope, Tf.DeptPv, Tf.UserPv)));
//				sys.pstmt.setString(1, rowdocid);
//				sys.rs=sys.pstmt.executeQuery();
				if(sys.rs!=null){
					if(sys.rs.next()){
						String filed="";
						Enumeration<String> pp=request.getParameterNames();
						while(pp.hasMoreElements()){
							filed=pp.nextElement();//字段名称
							if(!filed.equals("ffmodel") && !filed.equals("fftype") && !filed.equals("rowdocid")){
								Object val=request.getParameter(filed);//字段内容
								//System.out.println(filed+"  ==> "+val.toString());
								if(val!=null){
									try{
										sys.rs.updateObject(filed, val);
									}catch(Exception e){
										e.printStackTrace();
									}
								}
							}
//							Integer tmp=Tf.FiledType.get(filed);
//							if(tmp!=null){//有这个字段
//								if(par.length()>0){par.append(",");}
//								par.append(filed).append("=?");
//								filedvalue=request.getParameter(filed);//字段内容
//								//System.out.println(filed+" ==> "+tmp);
//								if(tmp==-5 || tmp==-6 || tmp==-7 || tmp==3 || tmp==8 || tmp==6 || tmp==4 || tmp==2 || tmp==7 || tmp==5){//数字
//									if (filedvalue==null || filedvalue.trim().equals(""))filedvalue="0";
//								}else{
//									if (filedvalue==null)filedvalue="";
//								}
//								Val.add(filedvalue);
//								System.out.println(filed+" ==> "+filedvalue);
//							}
						}
						sys.rs.updateRow();
						flag=1;
					}else{
						flag=-3;
					}
				}else{
					flag=-3;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{sys.rs.close();}catch(Exception e){}
			try{stmt.close();}catch(Exception e){}
			
//			String filed="",filedvalue="";
//			Enumeration<String> pp=request.getParameterNames();
//			LinkedList<String> Val=new LinkedList<String>();
//			StringBuffer par=new StringBuffer();
//			while(pp.hasMoreElements()){
//				filed=pp.nextElement().toUpperCase();//字段名称
//				Integer tmp=Tf.FiledType.get(filed);
//				if(tmp!=null){//有这个字段
//					if(par.length()>0){par.append(",");}
//					par.append(filed).append("=?");
//					filedvalue=request.getParameter(filed);//字段内容
//					//System.out.println(filed+" ==> "+tmp);
//					if(tmp==-5 || tmp==-6 || tmp==-7 || tmp==3 || tmp==8 || tmp==6 || tmp==4 || tmp==2 || tmp==7 || tmp==5){//数字
//						if (filedvalue==null || filedvalue.trim().equals(""))filedvalue="0";
//					}else{
//						if (filedvalue==null)filedvalue="";
//					}
//					Val.add(filedvalue);
//					System.out.println(filed+" ==> "+filedvalue);
//				}
//			}
//			
//			try{
//				sys.pstmt=sys.DoParSQL("update "+Tf.Table+" set "+par+" where "+Tf.PrimaryKey+"=?");
//				flag=0;
//				for(String temp:Val){
//					sys.pstmt.setString(++flag, temp);
//			    }
//				sys.pstmt.setString(++flag, rowdocid);
//				flag=sys.pstmt.executeUpdate();
//				sys.CloseDataBase();
//				out.print(flag);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
			sys.CloseDataBase();
			
		}else if(fftype.equals("3")){//删除
			String rowdocid =request.getParameter("rowdocid");
			if(rowdocid==null || rowdocid.equals("")){
				return;
			}
			
			PrivilegeObj pvobj=Privilege.getPrivilegeObj(RoleID,ffmodel);
			if(!((pvobj.doBrowser==0)?false:true)){
				Privilege.NoLogin((HttpServletResponse)response);
				return;
			}
			ModelObj Tf=Base.ModelObj.get(ffmodel);
			if(Tf==null)return ;
			
			DBConnect sys=new DBConnect();
			Statement stmt=null;
			try{
				stmt=sys.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				sys.rs=stmt.executeQuery("select * from "+Tf.Table +" Where "+Tf.PrimaryKey+" ='"+rowdocid+"'"+ Tools.getAddSQL(" and ",Privilege.getList_Scope(sys,UserID, pvobj.doDelete, pvobj.delete_scope, Tf.DeptPv, Tf.UserPv)));
				if(sys.rs!=null){
					if(sys.rs.next()){
						sys.rs.deleteRow();
						flag=1;
					}else{
						flag=-3;
					}
				}else{
					flag=-3;
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			try{sys.rs.close();}catch(Exception e){}
			try{stmt.close();}catch(Exception e){}
//			try{
//				sys.pstmt=sys.DoParSQL("delete from "+Tf.Table+" where "+Tf.PrimaryKey+" = ?");
//				sys.pstmt.setString(1, rowdocid);
//				flag=sys.pstmt.executeUpdate();
//				sys.CloseDataBase();
//				out.print(flag);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
			sys.CloseDataBase();
		}
		if(flag==1){
			out.print("{\"id\":1,\"message\":\"操作成功！\"}");
		}else if(flag==-1){
			out.print("{\"id\":-1,\"message\":\"操作失败！\"}");
		}else if(flag==-3){
			out.print("{\"id\":-3,\"message\":\"由于您的权限不够,操作失败！\"}");
		}
		out.flush();
		out.close();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}
}
