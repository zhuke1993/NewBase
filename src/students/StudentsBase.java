package students;


import sei.core.Privilege;
import sei.core.Pv;
import sei.core.ToHtml;
import sei.log.Log;
import sei.util.Tools;
public class StudentsBase extends Privilege {
	private String MODEL="xsxx";
    private String XSID = "";
    private String NJ = "";
    private int STATUS = 0;
    private String XY_ID = "";
    private String XY_NAME = "";
    private String ZY_ID = "";
    private String ZY_NAME = "";
    private String BJ = "";
    private String XH = "";
    private String SFZH = "";
    private String XM = "";
    private String XMPY = "";
    private String XMCYM = "";
    private String XB = "";
    private String CSRQ = "";
    private String JG = "";
    private String MZ = "";
    private String GJ = "";
    private String HYZK = "";
    private String ZZMM = "";
    private String JKZK = "";
    private String ZJXY = "";
    private String XX = "";
    private String LDH = "";
    private String QSH = "";
    private String LXDH = "";
    private String DZYJ = "";
    private String JTDZ = "";
    private String JTLXFS = "";
    private String KSH="";
    private String TZSH="";
    private String FDY_ID = "";
    private String FDY_NAME = "";
    
    public void setMODEL(String MODEL){this.MODEL=MODEL;}
    public String getMODEL() {return MODEL;}
    public void setXSID(String XSID) {this.XSID = Tools.getSaveStr(XSID,7);}
    public String getXSID() {return XSID;}
    public void setNJ(String NJ) {this.NJ = Tools.getSaveStr(NJ,4);}
    public String getNJ() {return NJ;}
    public void setSTATUS(int STATUS) {this.STATUS = STATUS;}
    public int getSTATUS() {return STATUS;}
    public void setXY_ID(String XY_ID) {this.XY_ID = Tools.getSaveStr(XY_ID,10);}
    public String getXY_ID() {return XY_ID;}
    public void setXY_NAME(String XY_NAME) {this.XY_NAME = Tools.getSaveStr(XY_NAME,50);}
    public String getXY_NAME() {return XY_NAME;}
    public void setZY_ID(String ZY_ID) {this.ZY_ID = Tools.getSaveStr(ZY_ID,10);}
    public String getZY_ID() {return ZY_ID;}
    public void setZY_NAME(String ZY_NAME) {this.ZY_NAME = Tools.getSaveStr(ZY_NAME,50);}
    public String getZY_NAME() {return ZY_NAME;}
    public void setBJ(String BJ) {this.BJ = Tools.getSaveStr(BJ,20);}
    public String getBJ() {return BJ;}
    public void setXH(String XH) {this.XH = Tools.getSaveStr(XH,10);}
    public String getXH() {return XH;}
    public void setSFZH(String SFZH) {this.SFZH = Tools.getSaveStr(SFZH,18);}
    public String getSFZH() {return SFZH;}
    public void setXM(String XM) {this.XM = Tools.getSaveStr(XM,30);}
    public String getXM() {return XM;}
    public void setXMPY(String XMPY) {this.XMPY = Tools.getSaveStr(XMPY,60);}
    public String getXMPY() {return XMPY;}
    public void setXMCYM(String XMCYM) {this.XMCYM = Tools.getSaveStr(XMCYM,30);}
    public String getXMCYM() {return XMCYM;}
    public void setXB(String XB) {this.XB = Tools.getSaveStr(XB,2);}
    public String getXB() {return XB;}
    public void setCSRQ(String CSRQ) {this.CSRQ = Tools.getSaveStr(CSRQ,10);}
    public String getCSRQ() {return CSRQ;}
    public void setJG(String JG) {this.JG = Tools.getSaveStr(JG,20);}
    public String getJG() {return JG;}
    public void setMZ(String MZ) {this.MZ = Tools.getSaveStr(MZ,20);}
    public String getMZ() {return MZ;}
    public void setGJ(String GJ) {this.GJ = Tools.getSaveStr(GJ,20);}
    public String getGJ() {return GJ;}
    public void setHYZK(String HYZK) {this.HYZK = Tools.getSaveStr(HYZK,10);}
    public String getHYZK() {return HYZK;}
    public void setZZMM(String ZZMM) {this.ZZMM = Tools.getSaveStr(ZZMM,20);}
    public String getZZMM() {return ZZMM;}
    public void setJKZK(String JKZK) {this.JKZK = Tools.getSaveStr(JKZK,20);}
    public String getJKZK() {return JKZK;}
    public void setZJXY(String ZJXY) {this.ZJXY = Tools.getSaveStr(ZJXY,20);}
    public String getZJXY() {return ZJXY;}
    public void setXX(String XX) {this.XX = Tools.getSaveStr(XX,10);}
    public String getXX() {return XX;}
    public void setLDH(String LDH) {this.LDH = Tools.getSaveStr(LDH,20);}
    public String getLDH() {return LDH;}
    public void setQSH(String QSH) {this.QSH = Tools.getSaveStr(QSH,20);}
    public String getQSH() {return QSH;}
    public void setLXDH(String LXDH) {this.LXDH = Tools.getSaveStr(LXDH,20);}
    public String getLXDH() {return LXDH;}
    public void setDZYJ(String DZYJ) {this.DZYJ = Tools.getSaveStr(DZYJ,50);}
    public String getDZYJ() {return DZYJ;}
    public void setJTLXFS(String JTLXFS) {this.JTLXFS = Tools.getSaveStr(JTLXFS,20);}
    public String getJTLXFS() {return JTLXFS;}
    public void setJTDZ(String JTDZ) {this.JTDZ = Tools.getSaveStr(JTDZ,100);}
    public String getJTDZ() {return JTDZ;}
    public void setKSH(String KSH) {this.KSH = Tools.getSaveStr(KSH,20);}
    public String getKSH() {return KSH;}
    public void setTZSH(String TZSH) {this.TZSH = Tools.getSaveStr(TZSH,10);}
    public String getTZSH() {return TZSH;}
    public void setFDY_ID(String FDY_ID) {this.FDY_ID = Tools.getSaveStr(FDY_ID,10);}
    public String getFDY_ID() {return FDY_ID;}
    public void setFDY_NAME(String FDY_NAME) {this.FDY_NAME = Tools.getSaveStr(FDY_NAME,30);}
    public String getFDY_NAME() {return FDY_NAME;}

    public Pv LoadData(String UserID,String ID,String OPType)
    {
		Pv pv=new Pv();
		if (OPType.equals(Pv.Browser)){
			pv=getPvPageEdit(getMODEL(),UserID,"XY_ID","XH","T_STUDENTS_BASE",ID,Pv.Browser,Pv.Edit,Pv.Del);
		}else if(OPType.equals(Pv.Edit)){
			pv=getPvPageEdit(getMODEL(),UserID,"XY_ID","XH","T_STUDENTS_BASE",ID,Pv.Edit);
		}else if(OPType.equals(Pv.Add)){
			pv=getPvPageEdit(getMODEL(),UserID,"XY_ID","XH","T_STUDENTS_BASE",ID,Pv.Add);
			return pv;
		}
		if(ID==null)return pv;
		if(ID.trim().equals(""))return pv;
		if (pv.PR_BROWSE || pv.PR_EDIT){
	        try {
	        	pstmt=DoParSQL("select * from T_STUDENTS_BASE where XH=?");
	        	pstmt.setString(1, ID);
	        	rs=pstmt.executeQuery();
				if(rs!=null)if (rs.next())
				{
	                setXSID(rs.getString("XSID"));
	                setNJ(rs.getString("NJ"));
	                setSTATUS(rs.getInt("STATUS"));
	                setXY_ID(rs.getString("XY_ID"));
	                setXY_NAME(rs.getString("XY_NAME"));
	                setZY_ID(rs.getString("ZY_ID"));
	                setZY_NAME(rs.getString("ZY_NAME"));
	                setBJ(rs.getString("BJ"));
	                setXH(rs.getString("XH"));
	                setSFZH(rs.getString("SFZH"));
	                setXM(rs.getString("XM"));
	                setXMPY(rs.getString("XMPY"));
	                setXMCYM(rs.getString("XMCYM"));
	                setXB(rs.getString("XB"));
	                setCSRQ(rs.getString("CSRQ"));
	                setJG(rs.getString("JG"));
	                setMZ(rs.getString("MZ"));
	                setGJ(rs.getString("GJ"));
	                setHYZK(rs.getString("HYZK"));
	                setZZMM(rs.getString("ZZMM"));
	                setJKZK(rs.getString("JKZK"));
	                setZJXY(rs.getString("ZJXY"));
	                setXX(rs.getString("XX"));
	                setLDH(rs.getString("LDH"));
	                setQSH(rs.getString("QSH"));
	                setLXDH(rs.getString("LXDH"));
	                setDZYJ(rs.getString("DZYJ"));
	                setJTDZ(rs.getString("JTDZ"));
	                setJTLXFS(rs.getString("JTLXFS"));
	                setKSH(rs.getString("KSH"));
	                setTZSH(rs.getString("TZSH"));
	                setFDY_ID(rs.getString("FDY_ID"));
	                setFDY_NAME(rs.getString("FDY_NAME"));
	             }
	        } catch (Exception e) {
	        	Log.LogWrite(2,getMODEL()+"==>LoadData()", e);
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
        	pstmt=DoParSQL("select count(*) as v from t_students_base where XH=?");
        	pstmt.setString(1, getXH());
            rs = pstmt.executeQuery();
            if (rs.next()) n=rs.getInt(1);
        } catch (Exception ex) {}
        if (n>0){return -4;}
        try{
        	pstmt=DoParSQL("INSERT INTO t_students_base(XSID,NJ,STATUS,XY_ID,XY_NAME,ZY_ID,ZY_NAME,BJ,XH,SFZH,XM,XMPY,XMCYM,XB,CSRQ,JG,MZ,GJ,HYZK,ZZMM,JKZK,ZJXY,XX,LDH,QSH,LXDH,DZYJ,JTLXFS,JTDZ,KSH,TZSH,FDY_ID,FDY_NAME,PUSER_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	pstmt.setString(1, getXSID());
        	pstmt.setString(2, getNJ());
        	pstmt.setInt(3, getSTATUS());
        	pstmt.setString(4, getXY_ID());
        	pstmt.setString(5, getXY_NAME());
        	pstmt.setString(6, getZY_ID());
        	pstmt.setString(7, getZY_NAME());
        	pstmt.setString(8, getBJ());
        	pstmt.setString(9, getXH());
        	pstmt.setString(10, getSFZH());
        	pstmt.setString(11, getXM());
        	pstmt.setString(12, getXMPY());
        	pstmt.setString(13, getXMCYM());
        	pstmt.setString(14, getXB());
        	pstmt.setString(15, getCSRQ());
        	pstmt.setString(16, getJG());
        	pstmt.setString(17, getMZ());
        	pstmt.setString(18, getGJ());
        	pstmt.setString(19, getHYZK());
        	pstmt.setString(20, getZZMM());
        	pstmt.setString(21, getJKZK());
        	pstmt.setString(22, getZJXY());
        	pstmt.setString(23, getXX());
        	pstmt.setString(24, getLDH());
        	pstmt.setString(25, getQSH());
        	pstmt.setString(26, getLXDH());
        	pstmt.setString(27, getDZYJ());
        	pstmt.setString(28, getJTLXFS());
        	pstmt.setString(29, getJTDZ());
        	pstmt.setString(30, getKSH());
        	pstmt.setString(31, getTZSH());
        	pstmt.setString(32, getFDY_ID());
        	pstmt.setString(33, getFDY_NAME());
        	pstmt.setString(34,userid);
        	Count=pstmt.executeUpdate();
            Log.LogDo(this, userid, getMODEL(), 5, "新增=>"+getXH(),"");
        }catch(Exception e){
        	Count=-1;
        	Log.LogWrite(2,getMODEL()+"==>Insert()", e);
        }
        return Count;
    }
    public int Update(String userid)
    {
        int Count=-1;
       	Pv pv=getPvPageEdit(getMODEL(),userid,"XY_ID","XH","t_students_base",getXH(),Pv.Edit);
       	if(!pv.PR_EDIT){return -3;}
        try{
        	con.setAutoCommit(false);
        	pstmt=DoParSQL("UPDATE t_students_base SET XSID=?,NJ=?,STATUS=?,XY_ID=?,XY_NAME=?,ZY_ID=?,ZY_NAME=?,BJ=?,SFZH=?,XM=?,XMPY=?,XMCYM=?,XB=?,CSRQ=?,JG=?,MZ=?,GJ=?,HYZK=?,ZZMM=?,JKZK=?,ZJXY=?,XX=?,LDH=?,QSH=?,LXDH=?,DZYJ=?,JTLXFS=?,JTDZ=?,KSH=?,TZSH=?,FDY_ID=?,FDY_NAME=? WHERE XH=?");
        	pstmt.setString(1, getXSID());
        	pstmt.setString(2, getNJ());
        	pstmt.setInt(3, getSTATUS());
        	pstmt.setString(4, getXY_ID());
        	pstmt.setString(5, getXY_NAME());
        	pstmt.setString(6, getZY_ID());
        	pstmt.setString(7, getZY_NAME());
        	pstmt.setString(8, getBJ());
        	pstmt.setString(9, getSFZH());
        	pstmt.setString(10, getXM());
        	pstmt.setString(11, getXMPY());
        	pstmt.setString(12, getXMCYM());
        	pstmt.setString(13, getXB());
        	pstmt.setString(14, getCSRQ());
        	pstmt.setString(15, getJG());
        	pstmt.setString(16, getMZ());
        	pstmt.setString(17, getGJ());
        	pstmt.setString(18, getHYZK());
        	pstmt.setString(19, getZZMM());
        	pstmt.setString(20, getJKZK());
        	pstmt.setString(21, getZJXY());
        	pstmt.setString(22, getXX());
        	pstmt.setString(23, getLDH());
        	pstmt.setString(24, getQSH());
        	pstmt.setString(25, getLXDH());
        	pstmt.setString(26, getDZYJ());
        	pstmt.setString(27, getJTLXFS());
        	pstmt.setString(28, getJTDZ());
        	pstmt.setString(29, getKSH());
        	pstmt.setString(30, getTZSH());
        	pstmt.setString(31, getFDY_ID());
        	pstmt.setString(32, getFDY_NAME());
        	pstmt.setString(33, getXH());
        	Count=pstmt.executeUpdate();
            Log.LogDo(this, userid, getMODEL(), 6, "修改=>"+getXH(), "");
            con.commit();
        }catch(Exception e){
        	try{con.rollback();}catch(Exception x){};
        	Count=-1;
        	Log.LogWrite(2,getMODEL()+"==>Update()", e);
        }
        try{con.setAutoCommit(true);}catch(Exception e){}        
        return Count;
    }
    public int Delete(String userid,String DocID)
    {
        int Count=-1;
    	try {              	
        	con.setAutoCommit(false);
        	//int sum=(DocID.length-1);
        	pstmt=DoParSQL("delete from t_students_base where XH =? "+Tools.getAddSQL("And",getDeleteScope(userid,getMODEL(),"XY_ID","XH")));
			//for(int i=0;i<sum;i++){
				pstmt.setString(1, DocID);
				pstmt.addBatch();
			//}
			Count=(pstmt.executeBatch()).length;
        	con.commit();
        	//if (Count==0){Count=-3;} else  if (sum>Count){Count=-4;} 
        }catch(Exception e){
        	try{con.rollback();}catch(Exception x){};
        	Count=-1;
        	Log.LogWrite(2,"Notic==>Delete()", e);
        }
    	try{con.setAutoCommit(true);}catch(Exception e){}
        return Count;
    }
}
