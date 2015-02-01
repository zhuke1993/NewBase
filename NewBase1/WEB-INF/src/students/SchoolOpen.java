package students;

import sei.core.Log;
import sei.core.Privilege;
import sei.core.Pv;
import sei.util.Tools;

public class SchoolOpen extends Privilege {
    private String MODEL="kxsj";
	private String XQ="";
    private String OPEN_TIME="";
    private String PUSER_ID="";
    private String PUSER_NAME="";
    private String PUSER_TIME="";

    public void setMODEL(String MODEL){this.MODEL=MODEL;}
    public String getMODEL(){return MODEL;}

    public void setXQ(String XQ){this.XQ=XQ;}
    public String getXQ(){return XQ;}
    
    public void setOPEN_TIME(String OPEN_TIME){this.OPEN_TIME=OPEN_TIME;}
    public String getOPEN_TIME(){return OPEN_TIME;}
    
    public void setPUSER_ID(String PUSER_ID){this.PUSER_ID=PUSER_ID;}
    public String getPUSER_ID(){return PUSER_ID;}
    
    public void setPUSER_NAME(String PUSER_NAME){this.PUSER_NAME=PUSER_NAME;}
    public String getPUSER_NAME(){return PUSER_NAME;}
    
    public void setPUSER_TIME(String PUSER_TIME){this.PUSER_TIME=PUSER_TIME;}
    public String getPUSER_TIME(){return PUSER_TIME;}

    
    public String getCurrXQ(){
		int CurYear=Integer.parseInt(Tools.getDateTime("yyyy"));
		int CurrMonth=Integer.parseInt(Tools.getDateTime("MM"));
		if(CurrMonth>7){
			return CurYear+"1";
		}else{
			return (CurYear-1)+"2";
		}
    }

    public Pv LoadData(String UserID,String ID,String OPType){
        Pv pv=new Pv();
        if (OPType.equals(Pv.Browser)){
            pv=getPvPageEdit(getMODEL(),UserID,"XY_ID","XH","t_Students_Arward",ID,Pv.Browser,Pv.Edit,Pv.Del);
        }else if(OPType.equals(Pv.Edit)){
            pv=getPvPageEdit(getMODEL(),UserID,"XY_ID","XH","t_Students_Arward",ID,Pv.Edit);
        }else if(OPType.equals(Pv.Add)){
            pv=getPvPageEdit(getMODEL(),UserID,"XY_ID","XH","t_Students_Arward",ID,Pv.Add);
            return pv;
        }
        if(ID==null)return pv;
        if(ID.trim().equals(""))return pv;
        if (pv.PR_BROWSE || pv.PR_EDIT){
        try {
            pstmt=DoParSQL("select * from t_students_schoolopen where xq=?");
            pstmt.setString(1, ID);
            rs=pstmt.executeQuery();
             if (rs.next())
             {
            	setXQ(rs.getString("XQ"));
                setOPEN_TIME(rs.getString("OPEN_TIME"));
                setPUSER_ID(rs.getString("PUSER_ID"));
                setPUSER_NAME(rs.getString("PUSER_NAME"));
                setPUSER_TIME(rs.getString("PUSER_TIME"));
             }
        } catch (Exception e) {
            Log.LogWrite(2,getMODEL()+"==>LoadData()", e);
            e.printStackTrace();
        }
        }
        return pv;
    }

    public int Insert(String userid)
    {
        int Count=-1,n=0;
        Pv pv=getPvPageEdit(getMODEL(),userid,"","","","",Pv.Add);
        if(!pv.PR_ADD){return -3;}
        try {
            pstmt=DoParSQL("select count(*) as v from t_students_schoolopen where xq=?");
            pstmt.setString(1, getXQ());
            rs = pstmt.executeQuery();
            if (rs.next()) n=rs.getInt(1);
        } catch (Exception ex) {}
        if (n>0){return -4;}
        try{
            pstmt=DoParSQL("INSERT INTO t_students_schoolopen(XQ,OPEN_TIME,PUSER_ID) values(?,?,?))");
            pstmt.setString(1, getXQ());
            pstmt.setString(2, getOPEN_TIME());
            pstmt.setString(3, userid);
            Count=pstmt.executeUpdate();
            Log.LogDo(this, userid, getMODEL(), 5, "新增=>"+getXQ(),"");
        } catch (Exception e) {
        	Count = -1;
            Log.LogWrite(2,getMODEL()+"==>Insert()", e);
         	e.printStackTrace();
        }
        return Count;
    }

    public int Update(String userid)
    {
        int Count=-1;
        Pv pv=getPvPageEdit(getMODEL(),userid,"","puser_id","t_students_schoolopen",getXQ(),Pv.Edit);
        if(!pv.PR_EDIT){return -3;}
        try {
            pstmt=DoParSQL("update t_students_schoolopen set OPEN_TIME=? where xq=?");
            pstmt.setString(1, getOPEN_TIME());
            pstmt.setString(2, getXQ());
            Count=pstmt.executeUpdate();
            Log.LogDo(this, userid, getMODEL(), 6, "修改=>"+getXQ(), "");
        } catch (Exception e) {
        	Count = -1;
        	e.printStackTrace();
        	Log.LogWrite(2,getMODEL()+"==>Update()", e);}
        return Count;
    }

    public int Delete(String userid,String DocID){
        int Count = 0;
        try {
            pstmt=DoParSQL("delete from t_students_schoolopen where xq=? "+ Tools.getAddSQL("And", getDeleteScope(userid, getMODEL(), "", "PUSER_ID")));
            pstmt.setString(1, DocID);
            Count=pstmt.executeUpdate();
        }catch(Exception e){
        	e.printStackTrace();
			Count = -1;
        	Log.LogWrite(2,getMODEL()+"==>Delete()", e);
        }
        return Count;
    }
   
}
