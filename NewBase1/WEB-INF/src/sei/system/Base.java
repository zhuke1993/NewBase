package sei.system;

import sei.core.Log;
import sei.core.Privilege;
import sei.core.Pv;
import sei.util.Tools;
public class Base extends Privilege{
	private String MODEL="";//权限模块名称
    private String SYS_ID="";
    private String BASE_ID="";
    private String PARENT_ID="0";
    private int BASE_ORDER=0;
    private String PARENT_NAME="系统";
    private String BASE_TYPE="";
    private String BASE_TYPE1="";
//    private String DEPT_ID="";
    private String BASE_NAME="";
    private byte BASE_STATUS=0; 
    private String BASE_EXT1="";
    private String BASE_EXT2="";
    private String BASE_EXT3="";
    private String BASE_EXT4="";
    private String BASE_EXT5="";
    private String BASE_EXT6="";
    private String BASE_EXT7="";
    private String BASE_EXT8="";
    private String BASE_EXT9="";
    private String BASE_EXT10="";
    private String BASE_EXT11="";
    private String BASE_EXT12="";
    private String BASE_EXT13="";
    private String BASE_EXT14="";
    private int BASE_EXT15=0;
    private int BASE_EXT16=0;
    private int BASE_EXT17=0;
    private float BASE_EXT18=0;
    private float BASE_EXT19=0;
    private float BASE_EXT20=0;
    private String BASE_MEMO="";
    private String PUSER_ID="";
    private String PUSER_NAME="";
    private String PUSER_TIME="";
 
	public void setMODEL(String MODEL){this.MODEL=MODEL;}
	public String getMODEL(){return MODEL;}
	public void setSYS_ID(String SYS_ID){this.SYS_ID=SYS_ID;}
	public String getSYS_ID(){return SYS_ID;}
    public void setBASE_ID(String BASE_ID){this.BASE_ID=Tools.getSaveStr(BASE_ID,10);}
    public String getBASE_ID(){return BASE_ID;}
    public void setPARENT_ID(String PARENT_ID){this.PARENT_ID=Tools.getSaveStr(PARENT_ID,10);}
    public String getPARENT_ID(){return PARENT_ID;}
    public void setPARENT_NAME(String PARENT_NAME){this.PARENT_NAME=PARENT_NAME;}
    public String getPARENT_NAME(){return PARENT_NAME;}    
    public void setBASE_ORDER(int BASE_ORDER){this.BASE_ORDER=BASE_ORDER;}
    public int getBASE_ORDER(){return BASE_ORDER;}
    public void setBASE_TYPE(String BASE_TYPE){this.BASE_TYPE=Tools.getSaveStr(BASE_TYPE,10);}
    public String getBASE_TYPE(){return BASE_TYPE;}
    public void setBASE_TYPE1(String BASE_TYPE1){this.BASE_TYPE1=Tools.getSaveStr(BASE_TYPE1,10);}
    public String getBASE_TYPE1(){return BASE_TYPE1;}
  
//    public void setBASE_BH(String BASE_BH){this.BASE_BH=BASE_BH;}
//    public String getBASE_BH(){return BASE_BH;}
    public void setBASE_NAME(String BASE_NAME){this.BASE_NAME=Tools.getSaveStr(BASE_NAME,80);}
    public String getBASE_NAME(){return BASE_NAME;}
    public void setBASE_STATUS(byte BASE_STATUS){this.BASE_STATUS=BASE_STATUS;}
    public byte getBASE_STATUS(){return BASE_STATUS;}
    public void setBASE_EXT1(String BASE_EXT1){this.BASE_EXT1=Tools.getSaveStr(BASE_EXT1,50);}
    public String getBASE_EXT1(){return BASE_EXT1;}
    public void setBASE_EXT2(String BASE_EXT2){this.BASE_EXT2=Tools.getSaveStr(BASE_EXT2,50);}
    public String getBASE_EXT2(){return BASE_EXT2;}
    public void setBASE_EXT3(String BASE_EXT3){this.BASE_EXT3=Tools.getSaveStr(BASE_EXT3,50);}
    public String getBASE_EXT3(){return BASE_EXT3;}
    public void setBASE_EXT4(String BASE_EXT4){this.BASE_EXT4=Tools.getSaveStr(BASE_EXT4,50);}
    public String getBASE_EXT4(){return BASE_EXT4;}
    public void setBASE_EXT5(String BASE_EXT5){this.BASE_EXT5=Tools.getSaveStr(BASE_EXT5,50);}
    public String getBASE_EXT5(){return BASE_EXT5;}
    public void setBASE_EXT6(String BASE_EXT6){this.BASE_EXT6=Tools.getSaveStr(BASE_EXT6,50);}
    public String getBASE_EXT6(){return BASE_EXT6;}
    public void setBASE_EXT7(String BASE_EXT7){this.BASE_EXT7=Tools.getSaveStr(BASE_EXT7,50);}
    public String getBASE_EXT7(){return BASE_EXT7;}
    public void setBASE_EXT8(String BASE_EXT8){this.BASE_EXT8=Tools.getSaveStr(BASE_EXT8,50);}
    public String getBASE_EXT8(){return BASE_EXT8;}
    public void setBASE_EXT9(String BASE_EXT9){this.BASE_EXT9=Tools.getSaveStr(BASE_EXT9,50);}
    public String getBASE_EXT9(){return BASE_EXT9;}
    public void setBASE_EXT10(String BASE_EXT10){this.BASE_EXT10=Tools.getSaveStr(BASE_EXT10,50);}
    public String getBASE_EXT10(){return BASE_EXT10;}
    public void setBASE_EXT11(String BASE_EXT11){this.BASE_EXT11=Tools.getSaveStr(BASE_EXT11,50);}
    public String getBASE_EXT11(){return BASE_EXT11;}
    public void setBASE_EXT12(String BASE_EXT12){this.BASE_EXT12=Tools.getSaveStr(BASE_EXT12,50);}
    public String getBASE_EXT12(){return BASE_EXT12;}
    public void setBASE_EXT13(String BASE_EXT13){this.BASE_EXT13=Tools.getSaveStr(BASE_EXT13,50);}
    public String getBASE_EXT13(){return BASE_EXT13;}
    public void setBASE_EXT14(String BASE_EXT14){this.BASE_EXT14=Tools.getSaveStr(BASE_EXT14,50);}
    public String getBASE_EXT14(){return BASE_EXT14;}
    public void setBASE_EXT15(int BASE_EXT15){this.BASE_EXT15=BASE_EXT15;}
    public int getBASE_EXT15(){return BASE_EXT15;}
    public void setBASE_EXT16(int BASE_EXT16){this.BASE_EXT16=BASE_EXT16;}
    public int getBASE_EXT16(){return BASE_EXT16;}
    public void setBASE_EXT17(int BASE_EXT17){this.BASE_EXT17=BASE_EXT17;}
    public int getBASE_EXT17(){return BASE_EXT17;}
    public void setBASE_EXT18(float BASE_EXT18){this.BASE_EXT18=BASE_EXT18;}
    public float getBASE_EXT18(){return BASE_EXT18;}
    public void setBASE_EXT19(float BASE_EXT19){this.BASE_EXT19=BASE_EXT19;}
    public float getBASE_EXT19(){return BASE_EXT19;}
    public void setBASE_EXT20(float BASE_EXT20){this.BASE_EXT20=BASE_EXT20;}
    public float getBASE_EXT20(){return BASE_EXT20;}
    public void setBASE_MEMO(String BASE_MEMO){this.BASE_MEMO=Tools.getSaveStr(BASE_MEMO,50);}
    public String getBASE_MEMO(){return BASE_MEMO;}
    public void setPUSER_ID(String PUSER_ID){this.PUSER_ID=Tools.getSaveStr(PUSER_ID,10);}
    public String getPUSER_ID(){return PUSER_ID;}
    public void setPUSER_NAME(String PUSER_NAME){this.PUSER_NAME=PUSER_NAME;}
    public String getPUSER_NAME(){return PUSER_NAME;}
    public void setPUSER_TIME(String PUSER_TIME){this.PUSER_TIME=PUSER_TIME;}
    public String getPUSER_TIME(){return PUSER_TIME;}

    public void setBASE_EXT(int i, String BASE_EXT){
    	switch(i){
    	case 1 : setBASE_EXT1(BASE_EXT);
    	case 2 : setBASE_EXT2(BASE_EXT);
    	case 3 : setBASE_EXT3(BASE_EXT);
    	case 4 : setBASE_EXT4(BASE_EXT);
    	case 5 : setBASE_EXT5(BASE_EXT);
    	case 6 : setBASE_EXT6(BASE_EXT);
    	case 7 : setBASE_EXT7(BASE_EXT);
    	case 8 : setBASE_EXT8(BASE_EXT);
    	case 9 : setBASE_EXT9(BASE_EXT);
    	case 10 : setBASE_EXT10(BASE_EXT);
    	case 11 : setBASE_EXT11(BASE_EXT);
    	case 12 : setBASE_EXT12(BASE_EXT);
    	case 13 : setBASE_EXT13(BASE_EXT);
    	case 14 : setBASE_EXT14(BASE_EXT);
    	case 15 : setBASE_EXT15(Tools.getIntNumber(BASE_EXT));
    	case 16 : setBASE_EXT16(Tools.getIntNumber(BASE_EXT));
    	case 17 : setBASE_EXT17(Tools.getIntNumber(BASE_EXT));
    	case 18 : setBASE_EXT18(Tools.getFloatNumber(BASE_EXT));
    	case 19 : setBASE_EXT19(Tools.getFloatNumber(BASE_EXT));
    	default : setBASE_EXT20(Tools.getFloatNumber(BASE_EXT));
    	}
    }
    
    public String getBASE_EXT(int i){
    	switch(i){
    	case 1 : return BASE_EXT1;
    	case 2 : return BASE_EXT2;
    	case 3 : return BASE_EXT3;
    	case 4 : return BASE_EXT4;
    	case 5 : return BASE_EXT5;
    	case 6 : return BASE_EXT6;
    	case 7 : return BASE_EXT7;
    	case 8 : return BASE_EXT8;
    	case 9 : return BASE_EXT9;
    	case 10 : return BASE_EXT10;
    	case 11 : return BASE_EXT11;
    	case 12 : return BASE_EXT12;
    	case 13 : return BASE_EXT13;
    	case 14 : return BASE_EXT14;
    	case 15 : return String.valueOf(BASE_EXT15);
    	case 16 : return String.valueOf(BASE_EXT16);
    	case 17 : return String.valueOf(BASE_EXT17);
    	case 18 : return String.valueOf(BASE_EXT18);
    	case 19 : return String.valueOf(BASE_EXT19);
    	default : return String.valueOf(BASE_EXT20);
    	}
    }
    public Pv LoadData(String UserID,String ID,String OPType)
    {
    	Pv pv=new Pv();
    	if (OPType.equals(Pv.Browser)){
    		pv=getPvPageEdit(getMODEL(),UserID,"","PUSER_ID","T_SYS_BASE",ID,Pv.Browser,Pv.Edit,Pv.Del);
    	}else if(OPType.equals(Pv.Edit)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_BASE",ID,Pv.Edit);
    	}else if(OPType.equals(Pv.Add)){
    		pv=getPvPageEdit(getMODEL(),UserID,"","PUSER_ID","T_SYS_BASE",ID,Pv.Add);
    		return pv;
    	}
    	if(ID==null)return pv;
    	if(ID.trim().equals(""))return pv;
    	if (pv.PR_BROWSE || pv.PR_EDIT){
	        try {
	        	pstmt=DoParSQL("select * from t_sys_base where base_id=?");
	        	pstmt.setString(1, ID);
	        	rs=pstmt.executeQuery();
				if(rs!=null)if (rs.next())
				{
					setSYS_ID(rs.getString("SYS_ID"));
				    setBASE_ID(rs.getString("BASE_ID"));
					setPARENT_ID(rs.getString("PARENT_ID"));
					setBASE_TYPE(rs.getString("BASE_TYPE"));
					setBASE_TYPE1(rs.getString("BASE_TYPE1"));
					setBASE_ORDER(rs.getInt("BASE_ORDER"));
					setBASE_NAME(rs.getString("BASE_NAME"));
					setBASE_STATUS(rs.getByte("BASE_STATUS"));
					setBASE_EXT1(rs.getString("BASE_EXT1"));
					setBASE_EXT2(rs.getString("BASE_EXT2"));
					setBASE_EXT3(rs.getString("BASE_EXT3"));
					setBASE_EXT4(rs.getString("BASE_EXT4"));
					setBASE_EXT5(rs.getString("BASE_EXT5"));
					setBASE_EXT6(rs.getString("BASE_EXT6"));
					setBASE_EXT7(rs.getString("BASE_EXT7"));
					setBASE_EXT8(rs.getString("BASE_EXT8"));
					setBASE_EXT9(rs.getString("BASE_EXT9"));
					setBASE_EXT10(rs.getString("BASE_EXT10"));
					setBASE_EXT11(rs.getString("BASE_EXT11"));
					setBASE_EXT12(rs.getString("BASE_EXT12"));
					setBASE_EXT13(rs.getString("BASE_EXT13"));
					setBASE_EXT14(rs.getString("BASE_EXT14"));
					setBASE_EXT15(rs.getInt("BASE_EXT15"));
					setBASE_EXT16(rs.getInt("BASE_EXT16"));
					setBASE_EXT17(rs.getInt("BASE_EXT17"));
					setBASE_EXT18(rs.getFloat("BASE_EXT18"));
					setBASE_EXT19(rs.getFloat("BASE_EXT19"));
					setBASE_EXT20(rs.getFloat("BASE_EXT20"));
					setBASE_MEMO(rs.getString("BASE_MEMO"));
					setPUSER_ID(rs.getString("PUSER_ID"));
					setPUSER_NAME(rs.getString("PUSER_NAME"));
					setPUSER_TIME(rs.getString("PUSER_TIME"));
					if(PARENT_ID.equals("0")){
						this.PARENT_NAME="系统";
					}else{
						pstmt=DoParSQL("Select BASE_NAME From T_SYS_BASE WHERE BASE_TYPE=? and BASE_ID=?");
						pstmt.setString(1, this.BASE_TYPE);
						pstmt.setString(2, this.PARENT_ID);
						rs=pstmt.executeQuery();
					    if(rs!=null)if(rs.next())this.PARENT_NAME=rs.getString(1);
					}
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
       	Pv pv=getPvPageEdit(getBASE_TYPE(),userid,"","","","",Pv.Add);
       	if(!pv.PR_ADD){return -3;}
        try {
        	pstmt=DoParSQL("select count(*) as v from T_SYS_Base where BASE_ID=?");
        	pstmt.setString(1, getBASE_ID());
            rs = pstmt.executeQuery();
            if (rs.next()) n=rs.getInt(1);
        } catch (Exception ex) {}
        if (n>0){return -4;}
        try{
        	pstmt=DoParSQL("insert into T_SYS_Base(BASE_ID,PARENT_ID,BASE_ORDER,BASE_TYPE,BASE_TYPE1,BASE_NAME,BASE_EXT1,BASE_EXT2,BASE_EXT3,BASE_EXT4,BASE_EXT5,BASE_EXT6,BASE_EXT7,BASE_EXT8,BASE_EXT9,BASE_EXT10,BASE_EXT11,BASE_EXT12,BASE_EXT13,BASE_EXT14,BASE_EXT15,BASE_EXT16,BASE_EXT17,BASE_EXT18,BASE_EXT19,BASE_EXT20,BASE_MEMO,PUSER_ID,SYS_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	pstmt.setString(1, getBASE_ID());
        	pstmt.setString(2, getPARENT_ID());
        	pstmt.setInt(3, getBASE_ORDER());
        	pstmt.setString(4,getBASE_TYPE());
        	pstmt.setString(5,getBASE_TYPE1());
        	pstmt.setString(6,getBASE_NAME());
        	pstmt.setString(7,getBASE_EXT1());
        	pstmt.setString(8,getBASE_EXT2());
        	pstmt.setString(9,getBASE_EXT3());
        	pstmt.setString(10,getBASE_EXT4());
        	pstmt.setString(11,getBASE_EXT5());
        	pstmt.setString(12,getBASE_EXT6());
        	pstmt.setString(13,getBASE_EXT7());
        	pstmt.setString(14,getBASE_EXT8());
        	pstmt.setString(15,getBASE_EXT9());
        	pstmt.setString(16,getBASE_EXT10());
        	pstmt.setString(17,getBASE_EXT11());
        	pstmt.setString(18,getBASE_EXT12());
        	pstmt.setString(19,getBASE_EXT13());
        	pstmt.setString(20,getBASE_EXT14());
        	pstmt.setInt(21,getBASE_EXT15());
        	pstmt.setInt(22,getBASE_EXT16());
        	pstmt.setInt(23,getBASE_EXT17());
        	pstmt.setFloat(24,getBASE_EXT18());
        	pstmt.setFloat(25,getBASE_EXT19());
        	pstmt.setFloat(26,getBASE_EXT20());
        	pstmt.setString(27,getBASE_MEMO());
        	pstmt.setString(28,userid);
        	pstmt.setString(29,getSYS_ID());
            Count=pstmt.executeUpdate();
            Log.LogDo(this, userid, getBASE_TYPE(), 5, "新增=>"+BASE_ID, getBASE_NAME());
        }catch(Exception e){
        	Count=-1;
        	Log.LogWrite(2,getMODEL()+"==>Insert()", e);
        }
        return Count;
    }

    public int Update(String userid)
    {
        int Count=-1;
       	Pv pv=getPvPageEdit(getBASE_TYPE(),userid,"","PUSER_ID","T_SYS_BASE",getBASE_ID(),Pv.Edit);
       	if(!pv.PR_EDIT){return -3;}
        try{
        	con.setAutoCommit(false);
        	pstmt=DoParSQL("update T_SYS_Base set PARENT_ID=?,BASE_ORDER=?,BASE_TYPE=?,BASE_TYPE1=?,BASE_NAME=?,BASE_EXT1=?,BASE_EXT2=?,BASE_EXT3=?,BASE_EXT4=?,BASE_EXT5=?,BASE_EXT6=?,BASE_EXT7=?,BASE_EXT8=?,BASE_EXT9=?,BASE_EXT10=?,BASE_EXT11=?,BASE_EXT12=?,BASE_EXT13=?,BASE_EXT14=?,BASE_EXT15=?,BASE_EXT16=?,BASE_EXT17=?,BASE_EXT18=?,BASE_EXT19=?,BASE_EXT20=?,BASE_MEMO=? WHERE BASE_ID=?");
        	pstmt.setString(1, getPARENT_ID());
        	pstmt.setInt(2,getBASE_ORDER());
        	pstmt.setString(3,getBASE_TYPE());
        	pstmt.setString(4,getBASE_TYPE1());
        	pstmt.setString(5,getBASE_NAME());
        	pstmt.setString(6,getBASE_EXT1());
        	pstmt.setString(7,getBASE_EXT2());
        	pstmt.setString(8,getBASE_EXT3());
        	pstmt.setString(9,getBASE_EXT4());
        	pstmt.setString(10,getBASE_EXT5());
        	pstmt.setString(11,getBASE_EXT6());
        	pstmt.setString(12,getBASE_EXT7());
        	pstmt.setString(13,getBASE_EXT8());
        	pstmt.setString(14,getBASE_EXT9());
        	pstmt.setString(15,getBASE_EXT10());
        	pstmt.setString(16,getBASE_EXT11());
        	pstmt.setString(17,getBASE_EXT12());
        	pstmt.setString(18,getBASE_EXT13());
        	pstmt.setString(19,getBASE_EXT14());
        	pstmt.setInt(20,getBASE_EXT15());
        	pstmt.setInt(21,getBASE_EXT16());
        	pstmt.setInt(22,getBASE_EXT17());
        	pstmt.setFloat(23,getBASE_EXT18());
        	pstmt.setFloat(24,getBASE_EXT19());
        	pstmt.setFloat(25,getBASE_EXT20());
        	pstmt.setString(26,getBASE_MEMO());
        	pstmt.setString(27,getBASE_ID());
        	Count=pstmt.executeUpdate();
            Log.LogDo(this, userid, getBASE_TYPE(), 6, "修改=>"+BASE_ID, getBASE_NAME());
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
    	Pv pv=getPvPageEdit(getMODEL(),userid,"","PUSER_ID","T_SYS_BASE",DocID,Pv.Del);
    	if(!pv.PR_DEL){return -3;}
    	try {
        	 con.setAutoCommit(false);
        	 String mm="";
        	 pstmt=DoParSQL("SELECT BASE_TYPE FROM T_SYS_BASE WHERE BASE_ID=?");
        	 pstmt.setString(1, DocID);
        	 rs=pstmt.executeQuery();
        	 if(rs!=null){
        		 if(rs.next()){
        			 MODEL=rs.getString(1);
        		 }
        	 }
        	 rs=DoSQL("SELECT CONCAT('(BASE_ID=''',REPLACE(getTreeChilds('"+getMODEL()+"',1,'"+DocID+"'),',',''' or BASE_ID='''),''')')");
        	 if(rs!=null){
        		 if(rs.next()){
        			 mm=rs.getString(1);
        		 }
        	 }
        	 mm=Tools.getAddSQL("And",mm);
        	 pstmt=DoParSQL("delete from T_SYS_BASE where base_type=?"+mm+Tools.getAddSQL("And",getDeleteScope(userid,getMODEL(),"","PUSER_ID")));
        	 pstmt.setString(1, getMODEL());
        	 Count=pstmt.executeUpdate();
         	 Log.LogDo(this, userid, MODEL, 7, "删除=>"+DocID, "");      	 
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
