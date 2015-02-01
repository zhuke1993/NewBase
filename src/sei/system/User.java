package sei.system;

import sei.core.Privilege;
import sei.core.Pv;
import sei.log.Log;
import sei.util.Tools;
public class User extends Privilege{
	private String MODEL="user";//权限模块名称
    private String USER_ID="";
    private String USER_NAME="";
    private String USER_PWD="";
    private String USER_IP="";
    private String ROLE_ID="";
    private String ROLE_NAME="";
    private String DEPT_ID="";
    private String DEPT_NAME="";
    private String USER_DUTY="";
    private String USER_JOB="";
    private String USER_LEAD="否";
    private String USER_EMAIL="";
    private String USER_POST_CODE="";
    private String USER_HOME_PHONE="";
    private String USER_MOB_PHONE="";
    private String USER_TYPE="";
    private String USER_ADDR="";
    private String SYS_ID="";
    private String STY_ID="";
    private short USER_STATUS=0;
    private String USER_OPR_DATE="";
    private String USER_MEMO="";
    private String USER_SEX="";
    private int USER_POSTCOUNT=0;
    private String USER_FACE="";
    private String USER_NICKNAME="";
    private short USER_ORDER=0;
    private short USER_PSWD_DAYS=0;
    private String USER_PSWD_DATE="";

	public void setMODEL(String MODEL){this.MODEL=MODEL;}
	public String getMODEL(){return MODEL;}
    public void setUSER_ID(String USER_ID){this.USER_ID=Tools.getSaveStr(USER_ID,10);}
    public String getUSER_ID(){return USER_ID;}
    public void setUSER_NAME(String USER_NAME){this.USER_NAME=Tools.getSaveStr(USER_NAME,20);}
    public String getUSER_NAME(){return USER_NAME;}
    public void setUSER_PWD(String USER_PWD){this.USER_PWD=Tools.getSaveStr(USER_PWD,15);}
    public String getUSER_PWD(){return USER_PWD;}
    public void setUSER_IP(String USER_IP){this.USER_IP=Tools.getSaveStr(USER_IP,500);}
    public String getUSER_IP(){return USER_IP;}
    public void setROLE_ID(String ROLE_ID){this.ROLE_ID=Tools.getSaveStr(ROLE_ID,10);}
    public String getROLE_ID(){return ROLE_ID;}
    public void setROLE_NAME(String ROLE_NAME){this.ROLE_NAME=Tools.getSaveStr(ROLE_NAME,20);}
    public String getROLE_NAME(){return ROLE_NAME;}
    public void setDEPT_ID(String DEPT_ID){this.DEPT_ID=Tools.getSaveStr(DEPT_ID,10);}
    public String getDEPT_ID(){return DEPT_ID;}
    public void setDEPT_NAME(String DEPT_NAME){this.DEPT_NAME=Tools.getSaveStr(DEPT_NAME,80);}
    public String getDEPT_NAME(){return DEPT_NAME;}
    public void setUSER_DUTY(String USER_DUTY){this.USER_DUTY=Tools.getSaveStr(USER_DUTY,20);}
    public String getUSER_DUTY(){return USER_DUTY;}
    public void setUSER_JOB(String USER_JOB){this.USER_JOB=Tools.getSaveStr(USER_JOB,20);}
    public String getUSER_JOB(){return USER_JOB;}
    public void setUSER_LEAD(String USER_LEAD){this.USER_LEAD=Tools.getSaveStr(USER_LEAD,2);}
    public String getUSER_LEAD(){return USER_LEAD;}
    public void setUSER_EMAIL(String USER_EMAIL){this.USER_EMAIL=Tools.getSaveStr(USER_EMAIL,30);}
    public String getUSER_EMAIL(){return USER_EMAIL;}
    public void setUSER_POST_CODE(String USER_POST_CODE){this.USER_POST_CODE=Tools.getSaveStr(USER_POST_CODE,6);}
    public String getUSER_POST_CODE(){return USER_POST_CODE;}
    public void setUSER_HOME_PHONE(String USER_HOME_PHONE){this.USER_HOME_PHONE=Tools.getSaveStr(USER_HOME_PHONE,15);}
    public String getUSER_HOME_PHONE(){return USER_HOME_PHONE;}
    public void setUSER_MOB_PHONE(String USER_MOB_PHONE){this.USER_MOB_PHONE=Tools.getSaveStr(USER_MOB_PHONE,11);}
    public String getUSER_MOB_PHONE(){return USER_MOB_PHONE;}
    public void setUSER_TYPE(String USER_TYPE){this.USER_TYPE=Tools.getSaveStr(USER_TYPE,10);}
    public String getUSER_TYPE(){return USER_TYPE;}
    public void setUSER_ADDR(String USER_ADDR){this.USER_ADDR=Tools.getSaveStr(USER_ADDR,60);}
    public String getUSER_ADDR(){return USER_ADDR;}
    public void setSYS_ID(String SYS_ID){this.SYS_ID=Tools.getSaveStr(SYS_ID,10);}
    public String getSYS_ID(){return SYS_ID;}
    public void setSTY_ID(String STY_ID){this.STY_ID=Tools.getSaveStr(STY_ID,10);}
    public String getSTY_ID(){return STY_ID;}
    public void setUSER_STATUS(short USER_STATUS){this.USER_STATUS=USER_STATUS;}
    public short getUSER_STATUS(){return USER_STATUS;}
    public void setUSER_OPR_DATE(String USER_OPR_DATE){this.USER_OPR_DATE=Tools.getSaveStr(USER_OPR_DATE,20);}
    public String getUSER_OPR_DATE(){return USER_OPR_DATE;}
    public void setUSER_MEMO(String USER_MEMO){this.USER_MEMO=Tools.getSaveStr(USER_MEMO,100);}
    public String getUSER_MEMO(){return USER_MEMO;}
    public void setUSER_SEX(String USER_SEX){this.USER_SEX=USER_SEX;}
    public String getUSER_SEX(){return USER_SEX;}
    public void setUSER_POSTCOUNT(int USER_POSTCOUNT){this.USER_POSTCOUNT=USER_POSTCOUNT;}
    public int getUSER_POSTCOUNT(){return USER_POSTCOUNT;}
    public void setUSER_FACE(String USER_FACE){this.USER_FACE=Tools.getSaveStr(USER_FACE,20);}
    public String getUSER_FACE(){return USER_FACE;}
    public void setUSER_NICKNAME(String USER_NICKNAME){this.USER_NICKNAME=Tools.getSaveStr(USER_NICKNAME,20);}
    public String getUSER_NICKNAME(){return USER_NICKNAME;}
    public void setUSER_ORDER(short USER_ORDER){this.USER_ORDER=USER_ORDER;}
    public short getUSER_ORDER(){return USER_ORDER;}
    public void setUSER_PSWD_DAYS(short USER_PSWD_DAYS){this.USER_PSWD_DAYS=USER_PSWD_DAYS;}
    public short getUSER_PSWD_DAYS(){return USER_PSWD_DAYS;}
    public void setUSER_PSWD_DATE(String USER_PSWD_DATE){this.USER_PSWD_DATE=Tools.getSaveStr(USER_PSWD_DATE,20);}
    public String getUSER_PSWD_DATE(){return USER_PSWD_DATE;}
    
    public void LoadData(String ID)
    {
//    	Pv pv=new Pv();
//    	if (OPType.equals(Pv.Browser)){
//    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_USER",ID,Pv.Browser,Pv.Edit,Pv.Del);
//    	}else if(OPType.equals(Pv.Edit)){
//    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_USER",ID,Pv.Edit);
//    	}else if(OPType.equals(Pv.Add)){
//    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_USER",ID,Pv.Add);
//    		return pv;
//    	}
//    	if(ID==null)return pv;
//    	if(ID.trim().equals(""))return pv;
//    	if (pv.PR_BROWSE || pv.PR_EDIT){
	    	try {
	    		pstmt=DoParSQL("select * from t_sys_USER where USER_id=?");
	        	pstmt.setString(1, ID);
	        	rs=pstmt.executeQuery();
				if(rs!=null)if (rs.next())
				{
	                setUSER_ID(rs.getString("USER_ID"));
	                setUSER_NAME(rs.getString("USER_NAME"));
	                setUSER_PWD(rs.getString("USER_PWD"));
	                setUSER_IP(rs.getString("USER_IP"));
	                setROLE_ID(rs.getString("ROLE_ID"));
	                setROLE_NAME(rs.getString("ROLE_NAME"));
	                setDEPT_ID(rs.getString("DEPT_ID"));
	                setDEPT_NAME(rs.getString("DEPT_NAME"));
	                setUSER_DUTY(rs.getString("USER_DUTY"));
	                setUSER_JOB(rs.getString("USER_JOB"));
	                setUSER_LEAD(rs.getString("USER_LEAD"));
	                setUSER_EMAIL(rs.getString("USER_EMAIL"));
	                setUSER_POST_CODE(rs.getString("USER_POST_CODE"));
	                setUSER_HOME_PHONE(rs.getString("USER_HOME_PHONE"));
	                setUSER_MOB_PHONE(rs.getString("USER_MOB_PHONE"));
	                setUSER_TYPE(rs.getString("USER_TYPE"));
	                setUSER_ADDR(rs.getString("USER_ADDR"));
	                setSYS_ID(rs.getString("SYS_ID"));
	                setSTY_ID(rs.getString("STY_ID"));
	                setUSER_STATUS(rs.getShort("USER_STATUS"));
	                setUSER_OPR_DATE(rs.getString("USER_OPR_DATE"));
	                setUSER_MEMO(rs.getString("USER_MEMO"));
	                setUSER_SEX(rs.getString("USER_SEX"));
	                setUSER_POSTCOUNT(rs.getInt("USER_POSTCOUNT"));
	                setUSER_FACE(rs.getString("USER_FACE"));
	                setUSER_NICKNAME(rs.getString("USER_NICKNAME"));
	                setUSER_ORDER(rs.getShort("USER_ORDER"));
	                
	                setUSER_PSWD_DAYS(rs.getShort("USER_PSWD_DAYS"));
	                setUSER_PSWD_DATE(rs.getString("USER_PSWD_DATE"));
	             }             
	        } catch (Exception e) {
	        	Log.LogWrite(2,"User==>LoadData()", e);
	        }
//    	}
//    	return pv;
    }

    public int Insert(String userid)
    {
    	int Count=-1,n=0;
       	Pv pv=getPvPageEdit(getMODEL(),userid,"","","","",Pv.Add);
       	if(!pv.PR_ADD){return -3;}
        try {
        	pstmt=DoParSQL("select count(*) as v from T_SYS_USER where USER_ID=?");
        	pstmt.setString(1, getUSER_ID());
            rs = pstmt.executeQuery();
            if (rs.next()) n=rs.getInt(1);
        } catch (Exception ex) {}
        if (n>0){return -4;}
        try{
        	pstmt=DoParSQL("INSERT INTO T_SYS_USER(USER_ID,USER_NAME,USER_PWD,USER_IP,ROLE_ID,DEPT_ID,USER_DUTY,USER_JOB,USER_LEAD,USER_EMAIL,USER_POST_CODE,USER_HOME_PHONE,USER_MOB_PHONE,USER_TYPE,USER_ADDR,SYS_ID,STY_ID,USER_STATUS,USER_MEMO,USER_SEX,USER_FACE,USER_NICKNAME,USER_ORDER,USER_PSWD_DAYS,PUSER_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	pstmt.setString(1, getUSER_ID());
        	pstmt.setString(2,getUSER_NAME());
        	pstmt.setString(3,getUSER_PWD());
        	pstmt.setString(4,getUSER_IP());
        	pstmt.setString(5,getROLE_ID());
        	pstmt.setString(6,getDEPT_ID());
        	pstmt.setString(7,getUSER_DUTY());
        	pstmt.setString(8,getUSER_JOB());
        	pstmt.setString(9,getUSER_LEAD());
        	pstmt.setString(10,getUSER_EMAIL());
        	pstmt.setString(11,getUSER_POST_CODE());
        	pstmt.setString(12,getUSER_HOME_PHONE());
        	pstmt.setString(13,getUSER_MOB_PHONE());
        	pstmt.setString(14,getUSER_TYPE());
        	pstmt.setString(15,getUSER_ADDR());
        	pstmt.setString(16,getSYS_ID());
        	pstmt.setString(17,getSTY_ID());
        	pstmt.setShort(18,getUSER_STATUS());
        	pstmt.setString(19,getUSER_MEMO());
        	pstmt.setString(20,getUSER_SEX());
        	pstmt.setString(21,getUSER_FACE());
        	pstmt.setString(22,getUSER_NICKNAME());
        	pstmt.setShort(23,getUSER_ORDER());
        	pstmt.setShort(24,getUSER_PSWD_DAYS());
        	pstmt.setString(25,userid);
        	Count=pstmt.executeUpdate();
        	Log.LogDo(this, userid, getMODEL(), 5, "新增=>"+USER_ID, "");
        }catch(Exception e){
        	Count=-1;
        	Log.LogWrite(2,"User==>Insert()", e);
        }        
        return Count;
    }

    public int Update(String userid)
    {
        int Count=-1;
       	Pv pv=getPvPageEdit(getMODEL(),userid,"DEPT","PUSER_ID","T_SYS_USER",getUSER_ID(),Pv.Edit);
       	if(!pv.PR_EDIT){return -3;}
        try{
        	pstmt=DoParSQL("update T_SYS_USER set USER_NAME=?,USER_PWD=?,USER_IP=?,ROLE_ID=?,DEPT_ID=?,USER_DUTY=?,USER_JOB=?,USER_LEAD=?,USER_EMAIL=?,USER_POST_CODE=?,USER_HOME_PHONE=?,USER_MOB_PHONE=?,USER_TYPE=?,USER_ADDR=?,SYS_ID=?,STY_ID=?,USER_STATUS=?,USER_MEMO=?,USER_SEX=?,USER_FACE=?,USER_NICKNAME=?,USER_ORDER=?,USER_PSWD_DAYS=? WHERE USER_ID=?");
        	pstmt.setString(1,getUSER_NAME());
        	pstmt.setString(2,getUSER_PWD());
        	pstmt.setString(3,getUSER_IP());
        	pstmt.setString(4,getROLE_ID());
        	pstmt.setString(5,getDEPT_ID());
        	pstmt.setString(6,getUSER_DUTY());
        	pstmt.setString(7,getUSER_JOB());
        	pstmt.setString(8,getUSER_LEAD());
        	pstmt.setString(9,getUSER_EMAIL());
        	pstmt.setString(10,getUSER_POST_CODE());
        	pstmt.setString(11,getUSER_HOME_PHONE());
        	pstmt.setString(12,getUSER_MOB_PHONE());
        	pstmt.setString(13,getUSER_TYPE());
        	pstmt.setString(14,getUSER_ADDR());
        	pstmt.setString(15,getSYS_ID());
        	pstmt.setString(16,getSTY_ID());
        	pstmt.setShort(17,getUSER_STATUS());
        	pstmt.setString(18,getUSER_MEMO());
        	pstmt.setString(19,getUSER_SEX());
        	pstmt.setString(20,getUSER_FACE());
        	pstmt.setString(21,getUSER_NICKNAME());
        	pstmt.setShort(22,getUSER_ORDER());
        	pstmt.setShort(23,getUSER_PSWD_DAYS());
        	pstmt.setString(24,getUSER_ID());
        	Count=pstmt.executeUpdate();
            Log.LogDo(this, userid, getMODEL(), 6, "修改=>"+getUSER_ID(), "");
        }catch(Exception e){
        	Count=-1;
        	Log.LogWrite(2,"User==>Update()", e);
        }        
        return Count;
    }

    public int Delete(String userid,String[] DocID)
    {
        int Count=-1;
//    	Pv pv=getPvPageEdit(getMODEL(),userid,"","PUSER_ID","T_SYS_BASE",DocID,Pv.Del);
//    	if(!pv.PR_DEL){return -3;}
        try {
        	con.setAutoCommit(false);
        	int sum=(DocID.length-1);
        	pstmt=DoParSQL("delete from T_SYS_USER where USER_ID =? "+Tools.getAddSQL("And",getDeleteScope(userid,getMODEL(),"DEPT_ID","PSER_ID")));
			for(int i=0;i<sum;i++){
				pstmt.setString(1, DocID[i]);
				pstmt.addBatch();
			}
			Count=(pstmt.executeBatch()).length;
			Log.LogDo(this, userid, getMODEL(), 7, "批量删除=>"+DocID.toString(), "");
			con.commit();
			if (Count==0){Count=-3;} else  if (sum>Count){Count=-4;}
        }catch(Exception e){
        	try {con.rollback();} catch (Exception x) {}
			Count = -1;
        	Log.LogWrite(2,"User==>Delete()", e);
        }
        try{con.setAutoCommit(true);}catch(Exception e){}
        return Count;
    }
}
