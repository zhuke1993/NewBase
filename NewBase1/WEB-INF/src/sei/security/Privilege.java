package sei.security;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sei.core.DBConnect;

public class Privilege {
	public static String[] ModelPrivilege ={"{\"id\":0,\"text\":\"浏览权限\"}","{\"id\":1,\"text\":\"新增权限\"}","{\"id\":2,\"text\":\"修改权限\"}","{\"id\":3,\"text\":\"删除权限\"}","{\"id\":4,\"text\":\"审核权限\"}"};
	public static String[] ObjPrivilege ={"{\"id\":0,\"text\":\"不能操作数据\"}","{\"id\":1,\"text\":\"只能操作自己的数据\"}","{\"id\":2,\"text\":\"能操作本部门数据\"}","{\"id\":3,\"text\":\"能操作本部门及下属部门数据\"}","{\"id\":4,\"text\":\"能操作指定人员数据\"}","{\"id\":5,\"text\":\"能操作指定部门数据\"}","{\"id\":6,\"text\":\"能操作所有数据\"}","{\"id\":7,\"text\":\"能操作本系统数据\"}"};
	private static HashMap<String, PrivilegeObj> RolePrivilege=new HashMap<String, PrivilegeObj>();//角色权限
	public void putValue(String key, PrivilegeObj value) {
		RolePrivilege.put(key, value);
	}
	public static PrivilegeObj getPrivilegeObj(String Role,String model){
		return RolePrivilege.get(Role+"-"+model);
	}
	public static void NoLogin(HttpServletResponse resp) {
		try {resp.sendRedirect("/");} catch (Exception e) {}
	}
	public  static boolean getAdd(HttpServletRequest Request) {
		boolean tt=false;
		try{
			if (Request.getAttribute("doAdd")!=null){
				if(Request.getAttribute("doAdd").toString().equals("1")){
					tt=true;
				}
			}
		}catch(Exception e){}
		return tt;
	}

	/**
     * 
     * @param User_ID	用户id
     * @param cat 权限类型（0-7）
     * @param scope 指定的组织或者用户的id）
     * @param pDeptID 指定使用部门编号进行控制的字段名称
     * @param pUser_ID 指定使用用户编号进行控制的字段名称
     * @return 返回相应的user_id所关联权限的用户id或者部门id的sql语句的范围
     */
	public static String getList_Scope(DBConnect sys,String User_ID, int cat, String scope,String pDeptID,String pUser_ID) {
    	if(pDeptID==null)pDeptID="";
    	if(pUser_ID==null)pUser_ID="";
    	if(pDeptID.equals("")&&pUser_ID.equals(""))pUser_ID="PUSER_ID";
        String s = "";
        switch (cat) {
            case 0://0：不能编辑数据；
                s = "(1=-1)";
                break;
            case 1://1：只能编辑自己写的数据；
           		s = "("+pUser_ID+"='" + User_ID + "')";
                break;
            case 2://2：能编辑本组织结构数据；
            	try{
            		if(pDeptID.equals("")){
            			sys.rs=sys.DoSQL(sys.pstmt,sys.rs,"SELECT CONCAT('("+pUser_ID+"=''',group_concat(user_id SEPARATOR ''' or "+pUser_ID+"='''),''')') FROM T_SYS_USER WHERE DEPT_ID = (SELECT DEPT_ID FROM T_SYS_USER WHERE USER_ID ='" + User_ID + "')");
		            	if(sys.rs!=null){
		            		if(sys.rs.next()){
		            			s=sys.rs.getString(1);
		            		}
		            	}
            		}else{
            			s=pDeptID+"=(SELECT DEPT_ID FROM T_SYS_USER WHERE USER_ID ='" + User_ID + "')";
            		}
            	}catch(Exception e){s="(1=-1)";}
           		break;
            case 3://3：能操作本组织及下属组织的数据
           		//s = "(PUSER_ID IN(SELECT user_id from t_user where FIND_IN_SET(DEPT_ID,getTreeChilds('dept',1,(SELECT DEPT_ID FROM t_user WHERE USER_ID='"+User_ID+"')))>0))";//3：能编辑本组织结构及下属组织结构数据；
                try{
            		if(pDeptID.equals("")){
	            		//s="PDEPT_ID IN(SELECT DEPT_ID FROM T_USER WHERE USER_ID ='" + User_ID + "')";
            		//System.out.println("SELECT CONCAT('("+pUser_ID+"=''',group_concat(user_id SEPARATOR ''' or "+pUser_ID+"='''),''')') from t_user where FIND_IN_SET(DEPT_ID,getTreeChilds('dept',1,(SELECT base_id from t_base where base_type='dept' and base_bh =(SELECT DEPT_ID FROM t_user WHERE USER_ID='"+User_ID+"'))))>0");
            			sys.rs=sys.DoSQL(sys.pstmt,sys.rs,"SELECT CONCAT('("+pUser_ID+"=''',group_concat(user_id SEPARATOR ''' or "+pUser_ID+"='''),''')') from T_SYS_USER where FIND_IN_SET(DEPT_ID,getTreeChilds('dept',1,(SELECT DEPT_ID FROM T_SYS_USER WHERE USER_ID='"+User_ID+"')))>0");
		            	if(sys.rs!=null){
		            		if(sys.rs.next()){
		            			s=sys.rs.getString(1);
		            		}
		            	}
            		}else{
            			sys.rs=sys.DoSQL(sys.pstmt,sys.rs,"SELECT CONCAT('("+pDeptID+"=''',REPLACE(getTreeChilds('dept',1,(SELECT DEPT_ID FROM T_SYS_USER WHERE USER_ID='"+User_ID+"')),',',''' or "+pDeptID+"='''),''')')");
		            	if(sys.rs!=null){
		            		if(sys.rs.next()){
		            			s=sys.rs.getString(1);
		            		}
		            	}
            		}
            	}catch(Exception e){s="(1=-1)";}
                break;
            case 4://4：能编辑指定人员数据；
                //s = "(PUSER_ID IN ('" + scope.replace(",", "','") + "'))";
            	String[] m=scope.split(",");
            	int cc=m.length;
            	for(int i=0;i<cc;i++){
            		if(s.equals("")){
            			s="("+pUser_ID+"='"+m[i]+"'";
            		}else{
            			s=s+" or "+pUser_ID+"='"+m[i]+"'";
            		}
            	}
            	if(!s.equals(""))s=s+")";
                break;
            case 5://5：能编辑指定组织结构数据； //s="(USER_ID IN(SELECT USER_ID FROM T_USER WHERE DEPT_ID IN('"+scope.replace(";","','")+"')))";break;//5：能编辑指定组织结构数据；
           		//s = "(PUSER_ID IN(SELECT USER_ID FROM T_USER WHERE DEPT_ID IN('"+scope.replace(";","','")+"')))";
                try{
                	if(pDeptID.equals("")){
                		sys.rs=sys.DoSQL(sys.pstmt,sys.rs,"SELECT CONCAT('("+pUser_ID+"=''',group_concat(user_id SEPARATOR ''' or "+pUser_ID+"='''),''')') FROM T_SYS_USER WHERE DEPT_ID IN('"+scope.replace(",","','")+"')");
		            	if(sys.rs!=null){
		            		if(sys.rs.next()){
		            			s=sys.rs.getString(1);
		            		}
		            	}
            		}else{
            			s=pDeptID+" IN('"+scope.replace(",","','")+"')";
            		}
        		}catch(Exception e){s="(1=-1)";}
                break;
            case 6://6：能编辑所有数据；
                s = "";
                break;
            //case 7://7：能操作本单位数据
           		//s = "(PUSER_ID IN(SELECT USER_ID FROM T_USER WHERE DEPT_ID IN(SELECT DEPT_ID FROM T_DEPT WHERE TREE_ID LIKE '" + Tools.getDEPT_ID(stmt,rs,User_ID, 2)[2] + "%')))";//7：能编辑本单位数据；
            //    break;
        }
//        try {if(rs!=null)rs.close();}catch (Exception e) {}
//        try {if(pstmt!=null)pstmt.close();}catch (Exception e) {}
        return s;
    }
//	public static boolean getListdoBrowser(String Role,String model){//能否查看列表页面
//		PrivilegeObj pv=RolePrivilege.get(Role+"-"+model);
//		return pv.doBrowser;
//	}
//	public static boolean getEditdoBrowser(String Role,String model){//能否查看编辑页面
//		PrivilegeObj pv=RolePrivilege.get(Role+"-"+model);
//		return pv.doBrowser;
//	}
}
