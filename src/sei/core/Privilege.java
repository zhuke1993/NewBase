package sei.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sei.Base;
import sei.log.Log;
import sei.util.Tools;


public class Privilege extends DBConnect {
    /**
     * 
     * @param User_ID	用户id
     * @param cat 权限类型（0-7）
     * @param scope 指定的组织或者用户的id）
     * @param pDeptID 指定使用部门编号进行控制的字段名称
     * @param pUser_ID 指定使用用户编号进行控制的字段名称
     * @return 返回相应的user_id所关联权限的用户id或者部门id的sql语句的范围
     */
    private String MyPr_Scope(String User_ID, int cat, String scope,String pDeptID,String pUser_ID) {
    	if(pDeptID==null)pDeptID="";
    	if(pUser_ID==null)pUser_ID="";
    	if(pDeptID.equals("")&&pUser_ID.equals(""))pUser_ID="PUSER_ID";
        String s = "";
        PreparedStatement pstmt=null;
        ResultSet rs = null;
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
            			rs=DoSQL(pstmt,rs,"SELECT CONCAT('("+pUser_ID+"=''',group_concat(user_id SEPARATOR ''' or "+pUser_ID+"='''),''')') FROM T_SYS_USER WHERE DEPT_ID = (SELECT DEPT_ID FROM T_SYS_USER WHERE USER_ID ='" + User_ID + "')");
		            	if(rs!=null){
		            		if(rs.next()){
		            			s=rs.getString(1);
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
            			rs=DoSQL(pstmt,rs,"SELECT CONCAT('("+pUser_ID+"=''',group_concat(user_id SEPARATOR ''' or "+pUser_ID+"='''),''')') from T_SYS_USER where FIND_IN_SET(DEPT_ID,getTreeChilds('dept',1,(SELECT DEPT_ID FROM T_SYS_USER WHERE USER_ID='"+User_ID+"')))>0");
		            	if(rs!=null){
		            		if(rs.next()){
		            			s=rs.getString(1);
		            		}
		            	}
            		}else{
            			rs=DoSQL(pstmt,rs,"SELECT CONCAT('("+pDeptID+"=''',REPLACE(getTreeChilds('dept',1,(SELECT DEPT_ID FROM T_SYS_USER WHERE USER_ID='"+User_ID+"')),',',''' or "+pDeptID+"='''),''')')");
		            	if(rs!=null){
		            		if(rs.next()){
		            			s=rs.getString(1);
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
                		rs=DoSQL(pstmt,rs,"SELECT CONCAT('("+pUser_ID+"=''',group_concat(user_id SEPARATOR ''' or "+pUser_ID+"='''),''')') FROM T_SYS_USER WHERE DEPT_ID IN('"+scope.replace(",","','")+"')");
		            	if(rs!=null){
		            		if(rs.next()){
		            			s=rs.getString(1);
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
        try {if(rs!=null)rs.close();}catch (Exception e) {}
        try {if(pstmt!=null)pstmt.close();}catch (Exception e) {}
        return s;
    }
    
    /**
     * 
     * @param User_ID 用户的id
     * @param cat 权限类型（0-7）
     * @param scope 指定的组织或者用户的id
    // * @param DocSQL sql语句（包含选择的列、表以及操作的数据，例如：select count(*) from t_user where user_id = 'admin'）
     * @return 返回相应的user_id所关联权限（"0","1"）
     */
    private boolean MyPrIsCanBrowse_EDIT(String User_ID, int cat, String scope,String Table, String PkID,String pDeptID,String pUser_ID) {
    	boolean s = false;
        try {
            switch (cat) {
                case 0://不能编辑数据；
                    break;
                case 6://能编辑所有数据；
                    s = true;
                    break;
                default:
                    PreparedStatement pstmt=null;
                    ResultSet rs = null;
                    pstmt = DoParSQL(pstmt,"select count(*) from "+ Table + " Where " + Base.Table_PkCol.get(Table.toUpperCase())+"=?" + Tools.getAddSQL(" AND ",MyPr_Scope(User_ID,cat,scope,pDeptID,pUser_ID)));
                    pstmt.setString(1, PkID);
                    rs=pstmt.executeQuery();
                    if (rs != null) if (rs.next()) if (rs.getInt(1) > 0) s = true;
                    try {rs.close();}catch (Exception e) {}
                    try {pstmt.close();}catch (Exception e) {}
                    break;
            }
        } catch (Exception e) {
        	Log.LogWrite(2,"Privilege==>MyPrIsCanBrowse_EDIT()方法错误.参数为："+User_ID+","+cat+","+scope+","+PkID, e);
        }
        return s;
    }
    /**
     * 提取是否能操作该模块的操作权限。
     *
     * @param User_ID 用户编号
     * @param Obj_ID  模块编号
     */
    public boolean getPvCanDo(String User_ID, String Obj_ID){
    	boolean ok=false;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        try {
            rs = DoSQL(pstmt,rs,"Select PR_AUDIT From T_SYS_Privilege Where (OBJ_ID='" + Obj_ID + "') AND ROLE_ID =(SELECT ROLE_ID FROM T_SYS_USER WHERE USER_ID='" + User_ID + "')");
            if (rs != null) if (rs.next()) {
            	if(rs.getInt(1)>0)ok=true;
            }
        } catch (Exception e) {
        	Log.LogWrite(2,"Privilege==>getPvCanDo()方法错误.参数为："+User_ID+","+Obj_ID, e);
        }
        try {rs.close();}catch (Exception e) {}
        try {pstmt.close();}catch (Exception e) {}
        return ok;
    }
    
    /**
     * 提取用于普通列表页面的操作权限。
     *
     * @param CurrUser_ID 用户编号
     * @param Obj_ID  模块编号
     * @param pDeptID 通过部门id编号进行权限控制
     * @param pUser_ID 通用用户pUser_ID进行权限控制
     * @return PvList权限类型  0表示不能操作，1表示能操作
     */
    public Pv getPvPageList(String Obj_ID,String CurrUser_ID,String pDeptID,String pUser_ID,String... pvs) {
    	Pv Rt=new Pv();
    	StringBuffer SQL=new StringBuffer();
    	SQL.append("Select ");
    	boolean ff=true;
    	for (String item:pvs){
    		if(ff){
    			ff=false;
    			SQL.append(item);
    		}else{
    			SQL.append(",").append(item);
    		}
		}
    	SQL.append(" From T_SYS_Privilege Where (OBJ_ID=?) AND ROLE_ID =(SELECT ROLE_ID FROM T_SYS_USER WHERE USER_ID=?)");
    	try {
     		pstmt=DoParSQL(SQL.toString());
			pstmt.setString(1, Obj_ID);
			pstmt.setString(2, CurrUser_ID);
     		rs=pstmt.executeQuery();
     		if (rs != null) if (rs.next()) {
     			for (String item:pvs){
    				if(item.equals(Pv.Browser)){
    					Rt.PR_BROWSE=((rs.getByte("PR_BROWSE")==0)?false:true);
    				}else if(item.equals("PR_BROWSE_SCOPE")){
    					Rt.PR_BROWSE_SCOPE=MyPr_Scope(CurrUser_ID, rs.getByte("PR_BROWSE"), rs.getString("PR_BROWSE_SCOPE"),pDeptID,pUser_ID);
    				}else if(item.equals(Pv.Add)){
    					Rt.PR_ADD=((rs.getByte(Pv.Add)==0)?false:true);
    				}else if(item.equals(Pv.Del)){
    					Rt.PR_DEL=((rs.getByte(Pv.Del)==0)?false:true);
     				}else if(item.equals(Pv.Audit)){
     					Rt.PR_AUDIT=((rs.getByte(Pv.Audit)==0)?false:true);
     				}
    			}
			}
        } catch (Exception e) {
            Log.LogWrite(2,"Privilege==>getPvPageList()方法错误.参数为："+CurrUser_ID+","+Obj_ID, e);
        }
        try {rs.close();}catch (Exception e) {}
        try {pstmt.close();}catch (Exception e) {}
      return Rt;
    }
    public Pv getPvPageEdit(String Obj_ID,String CurrUser_ID,String pDeptID,String pUser_ID,String Table, String PkID,String... pvs){
    	Pv Rt=new Pv();
    	StringBuffer SQL=new StringBuffer();
    	SQL.append("Select ");
    	boolean ff=true;
    	for (String item:pvs){
    		if(ff){
    			ff=false;
    			SQL.append(item);
    		}else{
    			SQL.append(",").append(item);
    		}
    		if(item.equals(Pv.Browser)){
				SQL.append(",PR_BROWSE_SCOPE");
			}else if(item.equals(Pv.Edit)){
				SQL.append(",PR_EDIT_SCOPE");
			}else if(item.equals(Pv.Del)){
				SQL.append(",PR_DEL_SCOPE");
			}else if(item.equals(Pv.Audit)){
				SQL.append(",PR_AUDIT_SCOPE");
			}
		}
    	SQL.append(" From T_SYS_Privilege Where (OBJ_ID=?) AND ROLE_ID =(SELECT ROLE_ID FROM T_SYS_USER WHERE USER_ID=?)");
    	try {
    		pstmt=DoParSQL(SQL.toString());
    		pstmt.setString(1, Obj_ID);
    		pstmt.setString(2, CurrUser_ID);
    		rs=pstmt.executeQuery();
    		if (rs != null) if (rs.next()) {
    			for (String item:pvs){
    				if(item.equals(Pv.Browser)){
    					Rt.PR_BROWSE=MyPrIsCanBrowse_EDIT(CurrUser_ID, rs.getByte(Pv.Browser), rs.getString("PR_BROWSE_SCOPE"),Table, PkID, pDeptID, pUser_ID);
    				}else if(item.equals(Pv.Add)){
    					Rt.PR_ADD=((rs.getByte(Pv.Add)==0)?false:true);
    				}else if(item.equals(Pv.Edit)){
    					Rt.PR_EDIT=MyPrIsCanBrowse_EDIT(CurrUser_ID, rs.getByte(Pv.Edit), rs.getString("PR_EDIT_SCOPE"),Table, PkID, pDeptID, pUser_ID);
    				}else if(item.equals(Pv.Del)){
    					Rt.PR_DEL=MyPrIsCanBrowse_EDIT(CurrUser_ID, rs.getByte(Pv.Del), rs.getString("PR_DEL_SCOPE"),Table, PkID, pDeptID, pUser_ID);
    				}else if(item.equals(Pv.Audit)){
    					Rt.PR_AUDIT=MyPrIsCanBrowse_EDIT(CurrUser_ID, rs.getByte(Pv.Audit), rs.getString("PR_AUDIT_SCOPE"),Table, PkID, pDeptID, pUser_ID);
    				}
    			}
    		}
    	} catch (Exception e) {
    		Log.LogWrite(2,"Privilege==>getPvPageEdit()方法错误.参数为："+CurrUser_ID+","+Obj_ID, e);
    	}

    	return Rt;
    }
    /**
     * 提取用于编辑页面的操作权限。
     *
     * @param User_ID 用户编号
     * @param Obj_ID  模块编号
     * @param IsDEPT 通过部门id还是用户id（0：用户id，1：部门id）
     * @param Table 该模块对于的表名称
     * @param DocSQL 检测条件,用于定位该条记录
     * @param Type 当前状态,新增状态:type=''或者type='Insert';Type='Update':编辑状态
     * @return PvList权限类型  0表示不能操作，1表示能操作
     */
//    public PvPageEdit getPvPageEdit(String User_ID, String Obj_ID,String Table, String DocSQL,String Type,String pDeptID,String pUser_ID) {
//    	PvPageEdit Rt=new PvPageEdit();
//    	try {
//    		rs = pstmt.executeQuery("Select PR_BROWSE,PR_BROWSE_SCOPE,PR_ADD,PR_EDIT,PR_EDIT_SCOPE From T_Privilege Where (OBJ_ID='" + Obj_ID + "') AND ROLE_ID =(SELECT ROLE_ID FROM T_USER WHERE USER_ID='" + User_ID + "')");
//    		if (rs != null) if (rs.next()) {
//    			//临时存储
//    			byte PR_EDIT=rs.getByte("PR_EDIT");
//    			String PR_EDIT_SCOPE=rs.getString("PR_EDIT_SCOPE");
//    			Rt.PR_ADD=(byte)((rs.getByte(1)==0)?0:1);
//    			//查看能不能看这条数据,能看返回Rt.PR_BROWSE=1
//    			if(Type.equals("")||Type.equals("Insert")){//新增
//    				Rt.PR_BROWSE=1;
//    			}else{	    			
//	    			Rt.PR_BROWSE=MyPrIsCanBrowse_EDIT(User_ID, rs.getByte("PR_BROWSE"), rs.getString("PR_BROWSE_SCOPE"),Table, DocSQL, pDeptID, pUser_ID);
//	    			if(Rt.PR_BROWSE==1){//能看,则检测是否能编辑
//	    				 Rt.PR_EDIT=MyPrIsCanBrowse_EDIT(User_ID, PR_EDIT, PR_EDIT_SCOPE,Table, DocSQL, pDeptID, pUser_ID);
//	    			}
//    			}
//    		}
//    	} catch (Exception e) {
//    		Log.LogWrite(2,"Privilege==>getPvPageEdit()方法错误.参数为："+User_ID+","+Obj_ID, e);
//    	}
//      return Rt;
//    }
    
    /**
     * 提取删除一条或多条数据库记录的权限。用于删除记录时调用
     *
     * @param CurrUser_ID 用户编号
     * @param Obj_ID  模块编号
     * @return 提取删除一条或多条数据库记录的权限,返回限制SQL子条件
     */
    public String getDeleteScope(String Obj_ID,String CurrUser_ID,String pDeptID,String pUser_ID){
    	String Rt="";
     	try {
     		pstmt=DoParSQL("Select PR_DEL,PR_DEL_SCOPE From T_SYS_Privilege Where (OBJ_ID=?) AND ROLE_ID =(SELECT ROLE_ID FROM T_SYS_USER WHERE USER_ID=?)");
     		pstmt.setString(1, Obj_ID);
     		pstmt.setString(2, CurrUser_ID);
    		rs = pstmt.executeQuery();
    		if (rs != null) if (rs.next()) {
    			Rt=MyPr_Scope(CurrUser_ID, rs.getByte("PR_DEL"), rs.getString("PR_DEL_SCOPE"), pDeptID, pUser_ID);
    		}
    	} catch (Exception e) {
    		Log.LogWrite(2,"Privilege==>getDeleteScope()方法错误.参数为："+CurrUser_ID+","+Obj_ID, e);
    	}
    	return Rt;
    }
}
