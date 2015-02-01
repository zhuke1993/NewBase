package sei.security;

import sei.core.DBConnect;

public class PrivilegeLoad {
	public void Load(){
		//PreparedStatement pstmt=null;
        //ResultSet rs = null;
        DBConnect sys=new DBConnect();
        try {
        	Privilege pv=new Privilege();
        	boolean ff=false;
            sys.rs = sys.DoSQL("SELECT role_id,OBJ_ID, PR_BROWSE,pr_browse_scope,PR_ADD,PR_EDIT,pr_edit_scope,PR_DEL,pr_del_scope,pr_audit,pr_audit_scope FROM t_sys_privilege WHERE  EXISTS(SELECT 1 from t_sys_base where base_type='role' and base_id=t_sys_privilege.role_id) ORDER BY role_id asc,obj_id asc");
            if (sys.rs != null) while (sys.rs.next()) {
            	ff=false;
            	PrivilegeObj obj=Privilege.getPrivilegeObj(sys.rs.getString(1),sys.rs.getString(2));
            	if(obj==null){
            		obj=new PrivilegeObj();
            		ff=true;
            	}
                obj.doBrowser=sys.rs.getByte("PR_BROWSE");
//                obj.browser_scope=MyPr_Scope(sys.rs.getByte("PR_BROWSE"), sys.rs.getString("PR_BROWSE_SCOPE"),Base.ModelObj.get(sys.rs.getByte("OBJ_ID")).DeptPv,Base.ModelObj.get(sys.rs.getByte("OBJ_ID")).UserPv);
                obj.browser_scope=sys.rs.getString("PR_BROWSE_SCOPE");
                obj.doAdd=sys.rs.getByte("PR_ADD");
                obj.doEdit=sys.rs.getByte("PR_EDIT");
                obj.edit_scope=sys.rs.getString("pr_edit_scope");
                obj.doDelete=sys.rs.getByte("PR_DEL");
                obj.delete_scope=sys.rs.getString("pr_del_scope");
                obj.doAudit=sys.rs.getByte("PR_AUDIT");
                obj.audit_scope=sys.rs.getString("pr_audit_scope");
                if(ff)pv.putValue(sys.rs.getString(1)+"-"+sys.rs.getString(2),obj);
            }
        }catch (Exception e){}
        sys.CloseDataBase();
	}
}
