package sei.system;


import sei.core.Privilege;
import sei.core.Pv;
import sei.log.Log;
import sei.util.Tools;

public class Diary extends Privilege {
    private String MODEL="diary";
    private String UUID = "0";
    private String XQ = "";
    private String USER_ID = "";
    private String USER_NAME = "";
    private String DEPT_ID = "";
    private String DEPT_NAME = "";
    private String CONTENT = "";
    private String TIME = "";
    private String TYPE_ID = "";
    private String TYPE_NAME = "";
    private String PUSER_TIME = "";

    public void setMODEL(String MODEL){this.MODEL=MODEL;}
    public String getMODEL() {return MODEL;}
    public void setUUID(String UUID) {this.UUID = UUID;}
    public String getUUID() {return UUID;}
    public void setUSER_ID(String USER_ID) {this.USER_ID = USER_ID;}
    public String getUSER_ID() {return USER_ID;}
    public void setXQ(String XQ) {this.XQ = XQ;}
    public String getXQ() {return XQ;}
    public void setUSER_NAME(String USER_NAME) {this.USER_NAME = USER_NAME;}
    public String getUSER_NAME() {return USER_NAME;}
    public void setDEPT_ID(String DEPT_ID) {this.DEPT_ID = DEPT_ID;}
    public String getDEPT_ID() {return DEPT_ID;}
    public void setDEPT_NAME(String DEPT_NAME) {this.DEPT_NAME = DEPT_NAME;}
    public String getDEPT_NAME() {return DEPT_NAME;}
    public void setCONTENT(String CONTENT) {this.CONTENT = CONTENT;}
    public String getCONTENT() {return CONTENT;}
    public void setTIME(String TIME) {this.TIME = TIME;}
    public String getTIME() {return TIME;}

    public void setTYPE_ID(String TYPE_ID) {this.TYPE_ID = TYPE_ID;}
    public String getTYPE_ID() {return TYPE_ID;}
    public void setTYPE_NAME(String TYPE_NAME) {this.TYPE_NAME = TYPE_NAME;}
    public String getTYPE_NAME() {return TYPE_NAME;}
    public void setPUSER_TIME(String PUSER_TIME) {this.PUSER_TIME = PUSER_TIME;}
    public String getPUSER_TIME() {return PUSER_TIME;}
    
    
    public Pv LoadData(String UserID,String ID,String OPType)
    {
    	Pv pv=new Pv();
    	if (OPType.equals(Pv.Browser)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","USER_ID","T_SYS_DIARY",ID,Pv.Browser,Pv.Edit,Pv.Del);
    	}else if(OPType.equals(Pv.Edit)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","USER_ID","T_SYS_DIARY",ID,Pv.Edit);
    	}else if(OPType.equals(Pv.Add)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","USER_ID","T_SYS_DIARY",ID,Pv.Add);
    		return pv;
    	}
    	if(ID==null)return pv;
    	if(ID.trim().equals(""))return pv;
    	if (pv.PR_BROWSE || pv.PR_EDIT){
	    	try {
	    		pstmt=DoParSQL("select * from t_sys_diary where USER_id=?");
	        	pstmt.setString(1, ID);
	        	rs=pstmt.executeQuery();
	        	if(rs!=null)if (rs.next())
				{
	                setUSER_ID(rs.getString("USER_ID"));
	                setXQ(rs.getString("XQ"));
	                setUSER_NAME(rs.getString("USER_NAME"));
	                setDEPT_ID(rs.getString("DEPT_ID"));
	                setDEPT_NAME(rs.getString("DEPT_NAME"));
	                setTIME(rs.getString("TIME"));
	                setTYPE_ID(rs.getString("TYPE_ID"));
	                setTYPE_NAME(rs.getString("TYPE_NAME"));
	                setCONTENT(rs.getString("CONTENT"));
	                setPUSER_TIME(rs.getString("PUSER_TIME"));
				}
	    	 } catch (Exception e) {
		        	Log.LogWrite(2,getMODEL()+"==>LoadData()", e);
		    }
	    }
	    return pv;
	}
    public String getCurrXQ(){
		int CurYear=Integer.parseInt(Tools.getDateTime("yyyy"));
		int CurrMonth=Integer.parseInt(Tools.getDateTime("MM"));
		if(CurrMonth>7){
			return CurYear+"1";
		}else{
			return (CurYear-1)+"2";
		}

    }
    public int Insert(String userid)
    {
    	int Count=-1;
       	Pv pv=getPvPageEdit(getMODEL(),userid,"","","","",Pv.Add);
       	if(!pv.PR_ADD){return -3;}
        try{
        	pstmt=DoParSQL("insert into t_sys_diary(XQ,USER_ID,TIME,CONTENT,TYPE_ID,TYPE_NAME) values(?,?,?,?,?,?)");
        	pstmt.setString(1, getXQ());
        	pstmt.setString(2,userid);
        	pstmt.setString(3,getTIME());
        	pstmt.setString(4,getCONTENT());
        	pstmt.setString(5,getTYPE_ID());
        	pstmt.setString(6,getTYPE_NAME());
 
        	Count=pstmt.executeUpdate();
        	Log.LogDo(this, userid, getMODEL(), 5, "新增=>"+userid, "");
        }catch(Exception e){
        	Count=-1;
        	Log.LogWrite(2,getMODEL()+"==>Insert()", e);
        }        
        return Count;
    }


    public int Update(String userid){
        int Count=-1;
       	Pv pv=getPvPageEdit(getMODEL(),userid,"DEPT","USER_ID","T_SYS_DIARY",getUSER_ID(),Pv.Edit);
       	if(!pv.PR_EDIT){return -3;}
        try {
        	pstmt=DoParSQL("update T_SYS_DIARY set CONTENT=?,time=?,TYPE_ID=?,TYPE_NAME=? where uuid=?");
        	pstmt.setString(1,getCONTENT());
        	pstmt.setString(2,getTIME());
        	pstmt.setString(3,getTYPE_ID());
        	pstmt.setString(4,getTYPE_NAME());
        	pstmt.setString(5,getUUID());
        	Count=pstmt.executeUpdate();
            Log.LogDo(this, getUUID(), getMODEL(), 6, "修改=>"+this.UUID, "");
        } catch (Exception e) {
        	Count = -1;
        	Log.LogWrite(2,getMODEL()+"==>Update()", e);
        }
        return Count;
    }
    public int Delete(String userid,String DocID)
    {
        int Count=-1;
    	Pv pv=getPvPageEdit(getMODEL(),userid,"DEPT_ID","USER_ID","T_SYS_DIARY",DocID,Pv.Del);
    	if(!pv.PR_DEL){return -3;}
    	try {
        	 con.setAutoCommit(false);

        	 pstmt=DoParSQL("delete from T_SYS_DIARY where UUID=?"+Tools.getAddSQL("And",getDeleteScope(userid,getMODEL(),"","USER_ID")));
        	 pstmt.setString(1, DocID);
        	 Count=pstmt.executeUpdate();
         	 Log.LogDo(this, userid, getMODEL(), 7, "删除=>"+DocID, "");      	 
        	 con.commit();
        	 if (Count==0){Count=-3;}
        }catch(Exception e){
        	e.printStackTrace();
        	try{con.rollback();}catch(Exception x){};
        	Count=-1;
        	Log.LogWrite(2,getMODEL()+"==>Delete()", e);
        }
        try{con.setAutoCommit(true);}catch(Exception e){}
        return Count;
    }
}

