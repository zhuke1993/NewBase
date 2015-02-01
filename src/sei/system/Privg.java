package sei.system;


import sei.core.Pv;
import sei.core.Privilege;
import sei.log.Log;
import sei.util.Tools;
public class Privg extends Privilege
{
    private String MODEL="privilege";
    private short PR_ID;
	private String ROLE_ID;
	private String OBJ_ID;
	private short PR_BROWSE=0;
	private String PR_BROWSE_SCOPE="";
    private String PR_BROWSE_SCOPE_NAME="";
	private short PR_ADD=0;	
	private short PR_EDIT=0;
	private String PR_EDIT_SCOPE="";
	private String PR_EDIT_SCOPE_NAME="";
	private short PR_DEL=0;
	private String PR_DEL_SCOPE="";
	private String PR_DEL_SCOPE_NAME="";
	private short PR_AUDIT=0;
	private String PR_AUDIT_SCOPE="";
	private String PR_AUDIT_SCOPE_NAME="";
	private String PR_MEMO="";
	public String BROWSE="",EDIT="",DEL="",AUDIT="";
	
	public void setMODEL(String MODEL){this.MODEL=MODEL;}
	public String getMODEL(){return MODEL;}
	public void setPR_ID(short PR_ID){this.PR_ID=PR_ID;}
	public short getPR_ID(){return this.PR_ID;}	
	public void setROLE_ID(String ROLE_ID){this.ROLE_ID=Tools.getSaveStr(ROLE_ID,10);}
	public String getROLE_ID(){return this.ROLE_ID;}	
	public void setOBJ_ID(String OBJ_ID){this.OBJ_ID=Tools.getSaveStr(OBJ_ID,10);}
	public String getOBJ_ID(){return this.OBJ_ID;}	
	public void setPR_BROWSE(short PR_BROWSE){this.PR_BROWSE=PR_BROWSE;}
	public short getPR_BROWSE(){return this.PR_BROWSE;}	
	public void setPR_BROWSE_SCOPE(String PR_BROWSE_SCOPE){this.PR_BROWSE_SCOPE=Tools.getSaveStr(PR_BROWSE_SCOPE,3000);}
	public String getPR_BROWSE_SCOPE(){return this.PR_BROWSE_SCOPE;}
    public void setPR_BROWSE_SCOPE_NAME(String PR_BROWSE_SCOPE_NAME){this.PR_BROWSE_SCOPE_NAME=PR_BROWSE_SCOPE_NAME;}
    public String getPR_BROWSE_SCOPE_NAME(){return this.PR_BROWSE_SCOPE_NAME;}	
	public void setPR_ADD(short PR_ADD){this.PR_ADD=PR_ADD;}
	public short getPR_ADD(){return this.PR_ADD;}	
	public void setPR_EDIT(short PR_EDIT){this.PR_EDIT=PR_EDIT;}
	public short getPR_EDIT(){return this.PR_EDIT;}	
	public void setPR_EDIT_SCOPE(String PR_EDIT_SCOPE){this.PR_EDIT_SCOPE=Tools.getSaveStr(PR_EDIT_SCOPE,3000);}
	public String getPR_EDIT_SCOPE(){return this.PR_EDIT_SCOPE;}
    public void setPR_EDIT_SCOPE_NAME(String PR_EDIT_SCOPE_NAME){this.PR_EDIT_SCOPE_NAME=PR_EDIT_SCOPE_NAME;}
    public String getPR_EDIT_SCOPE_NAME(){return this.PR_EDIT_SCOPE_NAME;}	
	public void setPR_DEL(short PR_DEL){this.PR_DEL=PR_DEL;}
	public short getPR_DEL(){return this.PR_DEL;}	
	public void setPR_DEL_SCOPE(String PR_DEL_SCOPE){this.PR_DEL_SCOPE=Tools.getSaveStr(PR_DEL_SCOPE,3000);}
	public String getPR_DEL_SCOPE(){return this.PR_DEL_SCOPE;}
    public void setPR_DEL_SCOPE_NAME(String PR_DEL_SCOPE_NAME){this.PR_DEL_SCOPE_NAME=PR_DEL_SCOPE_NAME;}
    public String getPR_DEL_SCOPE_NAME(){return this.PR_DEL_SCOPE_NAME;}	
	public void setPR_AUDIT(short PR_AUDIT){this.PR_AUDIT=PR_AUDIT;}
	public short getPR_AUDIT(){return this.PR_AUDIT;}	
	public void setPR_AUDIT_SCOPE(String PR_AUDIT_SCOPE){this.PR_AUDIT_SCOPE=Tools.getSaveStr(PR_AUDIT_SCOPE,3000);}
	public String getPR_AUDIT_SCOPE(){return this.PR_AUDIT_SCOPE;}
    public void setPR_AUDIT_SCOPE_NAME(String PR_AUDIT_SCOPE_NAME){this.PR_AUDIT_SCOPE_NAME=PR_AUDIT_SCOPE_NAME;}
    public String getPR_AUDIT_SCOPE_NAME(){return this.PR_AUDIT_SCOPE_NAME;}	
	public void setPR_MEMO(String PR_MEMO){this.PR_MEMO=Tools.getSaveStr(PR_MEMO,100);}
	public String getPR_MEMO(){return this.PR_MEMO;}
	
	public Pv LoadData(String UserID,String ID,String OPType)
    {
    	Pv pv=new Pv();
    	if (OPType.equals(Pv.Browser)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_PRIVILEGE",ID,Pv.Browser,Pv.Edit,Pv.Del);
    	}else if(OPType.equals(Pv.Edit)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_PRIVILEGE",ID,Pv.Edit);
    	}else if(OPType.equals(Pv.Add)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_PRIVILEGE",ID,Pv.Add);
    		return pv;
    	}
    	if(ID==null)return pv;
    	if(ID.trim().equals(""))return pv;
    	if (pv.PR_BROWSE || pv.PR_EDIT){
    		try{
    			pstmt=DoParSQL("select * from T_SYS_PRIVILEGE where PR_ID=?");
	        	pstmt.setString(1, ID);
	        	rs=pstmt.executeQuery();
	            String m="";
	            if(rs!=null)if (rs.next())
	            {
	            	setPR_ID(rs.getShort("RR_ID"));
	                setROLE_ID(rs.getString("ROLE_ID"));
	                setOBJ_ID(rs.getString("OBJ_ID"));
	                setPR_BROWSE(rs.getShort("PR_BROWSE"));
	                setPR_BROWSE_SCOPE(rs.getString("PR_BROWSE_SCOPE"));
	               // setPR_BROWSE_SCOPE_NAME(rs.getString("PR_BROWSE_SCOPE_NAME"));
	                setPR_ADD(rs.getShort("PR_ADD"));	                
	                setPR_EDIT(rs.getShort("PR_EDIT"));
	                setPR_EDIT_SCOPE(rs.getString("PR_EDIT_SCOPE"));
	                //setPR_EDIT_SCOPE_NAME(rs.getString("PR_EDIT_SCOPE_NAME"));
	                setPR_DEL(rs.getShort("PR_DEL"));
	                setPR_DEL_SCOPE(rs.getString("PR_DEL_SCOPE"));
	               // setPR_DEL_SCOPE_NAME(rs.getString("PR_DEL_SCOPE_NAME"));
	                setPR_AUDIT(rs.getShort("PR_AUDIT"));
	                setPR_AUDIT_SCOPE(rs.getString("PR_AUDIT_SCOPE"));
	               // setPR_AUDIT_SCOPE_NAME(rs.getString("PR_AUDIT_SCOPE_NAME"));
	                setPR_MEMO(rs.getString("PR_MEMO"));	                	              
	             }	            
	            if(!getPR_BROWSE_SCOPE().equals(""))
	            {
		    	    m=getPR_BROWSE_SCOPE().replace(",","','");
		    	    if(getPR_BROWSE()==5){//指定部门
		    	    	rs = DoSQL(" select BASE_NAME from t_sys_base where base_type='dept' and BASE_ID IN ('"+m+"')");
		            }else if(getPR_BROWSE()==4){//指定人员
		            	rs = DoSQL(" select USER_NAME from t_sys_user where USER_ID IN ('"+m+"')");
		            }		    	    
		    	    m="";
		    	    if(rs!=null)while (rs.next())
		    	    {
		    	        m=m+";"+rs.getString(1);
		    	    }
		    	    if (m.length()>0)  this.PR_BROWSE_SCOPE_NAME=m.substring(1);
	            }
	            if(!getPR_EDIT_SCOPE().equals(""))
	            {
		    	    m=getPR_EDIT_SCOPE().replace(",","','");
		    	    if(getPR_EDIT()==5){//指定部门
		    	    	rs = DoSQL(" select BASE_NAME from t_sys_BASE where base_type='dept' and BASE_ID IN ('"+m+"')");
		            }else if(getPR_EDIT()==4){//指定人员
		            	rs = DoSQL(" select USER_NAME from t_sys_user where USER_ID IN ('"+m+"')");
		            }
		    	    m="";
		    	    if(rs!=null)while (rs.next())
		    	    {
		    	        m=m+";"+rs.getString(1);
		    	    }
		    	    if (m.length()>0)  this.PR_EDIT_SCOPE_NAME=m.substring(1);
	            }
	            if(!getPR_DEL_SCOPE().equals(""))
	            {
		    	    m=getPR_DEL_SCOPE().replace(",","','");
		    	    if(getPR_DEL()==5){//指定部门
		    	    	rs = DoSQL(" select BASE_NAME from t_sys_base where base_type='dept' and BASE_ID IN ('"+m+"')");
		            }else if(getPR_DEL()==4){//指定人员
		            	rs = DoSQL(" select USER_NAME from t_sys_user where USER_ID IN ('"+m+"')");
		            }
		    	    m="";
		    	    if(rs!=null)while (rs.next())
		    	    {
		    	        m=m+";"+rs.getString(1);
		    	    }
		    	    if (m.length()>0)  this.PR_DEL_SCOPE_NAME=m.substring(1);
	            }
	            if(!getPR_AUDIT_SCOPE().equals(""))
	            {
		    	    m=getPR_AUDIT_SCOPE().replace(",","','");
		    	    if(getPR_AUDIT()==5){//指定部门
		    	    	rs = DoSQL("select BASE_NAME from t_sys_base where base_type='dept' and BASE_ID IN ('"+m+"')");
		            }else if(getPR_AUDIT()==4){//指定人员
		            	rs = DoSQL("select USER_NAME from t_sys_user where USER_ID IN ('"+m+"')");
		            }
		    	    m="";
		    	    if(rs!=null)while (rs.next())
		    	    {
		    	        m=m+";"+rs.getString(1);
		    	    }
		    	    if (m.length()>0)  this.PR_AUDIT_SCOPE_NAME=m.substring(1);
	            }	            
	        } catch (Exception e) {
	        	Log.LogWrite(2,"Privilege==>LoadData()", e);
	        }
    	}
    	return pv;
	 }
	
//	添加对象
	//返回值 -1：表示插入失败   -2:表示有重复的值	
	public int Insert(String userid)
	{
    	int Count=-1,n=0;
       	Pv pv=getPvPageEdit(getMODEL(),userid,"","","","",Pv.Add);
       	if(!pv.PR_ADD){return -3;}
        try {
        	pstmt=DoParSQL("select count(*) as v from T_SYS_PRIVILEGE where ROLE_ID=? and OBJ_ID=?");
        	pstmt.setString(1, getROLE_ID());
        	pstmt.setString(2, getOBJ_ID());
            rs = pstmt.executeQuery();
            if (rs.next()) n=rs.getInt(1);
        } catch (Exception ex) {}
        if (n>0){return -4;}
    	try{
    		pstmt=DoParSQL("insert into T_SYS_PRIVILEGE(ROLE_ID,OBJ_ID,PR_BROWSE,PR_BROWSE_SCOPE,PR_ADD,PR_EDIT,PR_EDIT_SCOPE,PR_DEL,PR_DEL_SCOPE,PR_AUDIT,PR_AUDIT_SCOPE,PR_MEMO,PUSER_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	pstmt.setString(1, getROLE_ID());
        	pstmt.setString(2, getOBJ_ID());
        	pstmt.setShort(3,getPR_BROWSE());
        	pstmt.setString(4,getPR_BROWSE_SCOPE());
        	pstmt.setShort(5,getPR_ADD());
        	pstmt.setShort(6,getPR_EDIT());
        	pstmt.setString(7,getPR_EDIT_SCOPE());
        	pstmt.setShort(8,getPR_DEL());
        	pstmt.setString(9,getPR_DEL_SCOPE());
        	pstmt.setShort(10,getPR_AUDIT());
        	pstmt.setString(11,getPR_AUDIT_SCOPE());
        	pstmt.setString(12,getPR_MEMO());
        	pstmt.setString(13,userid);
	    	Count=pstmt.executeUpdate();
	    	Log.LogDo(this, userid, getMODEL(), 5, "新增=>"+getROLE_ID()+" -> "+getOBJ_ID(), "");
	    }catch(Exception e){
	    	Count=-1;
	    	Log.LogWrite(2,"Privilege==>Insert()", e);
	    }
	    return Count;
	}
	
	//修改对象
	public int Update(String userid)
	{
        int Count=-1;
       	Pv pv=getPvPageEdit(getMODEL(),userid,"","PUSER_ID","T_SYS_PRIVILEGE",String.valueOf(getPR_ID()),Pv.Edit);
       	if(!pv.PR_EDIT){return -3;}
	    try{
        	pstmt=DoParSQL("update T_SYS_PRIVILEGE set PR_BROWSE=?,PR_BROWSE_SCOPE=?,PR_ADD=?,PR_EDIT=?,PR_EDIT_SCOPE=?,PR_DEL=?,PR_DEL_SCOPE=?,PR_AUDIT=?,PR_AUDIT_SCOPE=?,PR_MEMO=? where PR_ID=?");
        	pstmt.setShort(1,getPR_BROWSE());
        	pstmt.setString(2,getPR_BROWSE_SCOPE());
        	pstmt.setShort(3,getPR_ADD());
        	pstmt.setShort(4,getPR_EDIT());
        	pstmt.setString(5,getPR_EDIT_SCOPE());
        	pstmt.setShort(6,getPR_DEL());
        	pstmt.setString(7,getPR_DEL_SCOPE());
        	pstmt.setShort(8,getPR_AUDIT());
        	pstmt.setString(9,getPR_AUDIT_SCOPE());
        	pstmt.setString(10,getPR_MEMO());
        	pstmt.setShort(11,getPR_ID());
        	Count=pstmt.executeUpdate();
	    	Log.LogDo(this, userid, getMODEL(), 6, "修改=>"+getROLE_ID()+" -> "+getOBJ_ID(), "");
	    }catch(Exception e){
	    	Count=-1;
	    	Log.LogWrite(2,"Privilege==>Update()", e);
	    }	    
	    return Count;
	}
	
	public int Delete(String userid,String[] DocID)
	 {
        int Count=0;
        try {
        	con.setAutoCommit(false);
        	int sum=(DocID.length-1);
			pstmt=DoParSQL("delete from T_SYS_PRIVILEGE where PR_ID =? "+Tools.getAddSQL("And",getDeleteScope(userid,"privilege","","PUSER_ID")));
			for(int i=0;i<sum;i++){
				pstmt.setShort(1, Short.parseShort(DocID[i]));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
			if (sum>Count){Count=-4;}else if(Count==sum) Count=0;else if (Count==0){Count=-3;}
        }catch(Exception e){
        	try {con.rollback();} catch (Exception x) {}
			Count = -1;
        	Log.LogWrite(2,"Privilege==>Delete()", e);
        }
        try{con.setAutoCommit(true);}catch(Exception e){}
        return Count;
	 }
}