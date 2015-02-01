package sei.system;


import sei.core.Privilege;
import sei.core.Pv;
import sei.core.ToHtml;
import sei.log.Log;
import sei.util.Tools;
public class Notic extends Privilege
{
    public Notic(){	}
	private String MODEL="";
    private String NC_ID="";
    private String NC_MODE_NAME;
    private String NC_MODE="";
    private short NC_CAT=0;
    private String NC_GRADE="";
    private short NC_SCOPE=0;
    private String NC_USER="";
    private String NC_USER_NAME;
    private String NC_TITLE="";
    private String NC_BODY;
    private String USER_ID="";
    private String USER_TIME="";
    private int NC_CLICK=0;
    private String NC_MEMO="";
    private String DEPT_ID="";
    private String DEPT_NAME="";
    private String PUSER_ID="";
    private String PUSER_NAME="";
    private String PUSER_TIME="";

	public void setMODEL(String MODEL){this.MODEL=MODEL;}
	public String getMODEL(){return MODEL;}
    public void setNC_ID(String NC_ID){this.NC_ID=NC_ID;}
    public String getNC_ID(){return NC_ID;}
    public void setNC_MODE(String NC_MODE){this.NC_MODE=NC_MODE;}
    public String getNC_MODE(){return NC_MODE;}
    public void setNC_CAT(short NC_CAT){this.NC_CAT=NC_CAT;}
    public short getNC_CAT(){return NC_CAT;}
    public void setNC_GRADE(String NC_GRADE){this.NC_GRADE=NC_GRADE;}
    public String getNC_GRADE(){return NC_GRADE;}
    public void setNC_SCOPE(short NC_SCOPE){this.NC_SCOPE=NC_SCOPE;}
    public short getNC_SCOPE(){return NC_SCOPE;}
    public void setNC_USER(String NC_USER){this.NC_USER=Tools.getSaveStr(NC_USER,3000);}
	public String getNC_USER()
	{
		if(this.NC_USER==null){
			this.NC_USER="";
		}else{
			if (!this.NC_USER.trim().equals("")){			
				if (!this.NC_USER.substring(0,1).equals(";"))this.NC_USER=";"+this.NC_USER;
				if (!this.NC_USER.substring(this.NC_USER.length()-1).equals(";"))this.NC_USER=this.NC_USER+";";
			}
		}
		return this.NC_USER;
	}
    public void setNC_TITLE(String NC_TITLE){this.NC_TITLE=Tools.getSaveStr(NC_TITLE,100);}
    public String getNC_TITLE(){return NC_TITLE;}
    public void setNC_BODY(String NC_BODY){this.NC_BODY=NC_BODY;}
    public String getNC_BODY(){return ((NC_BODY==null)?"":NC_BODY);}
    public void setUSER_ID(String USER_ID){this.USER_ID=USER_ID;}
    public String getUSER_ID(){return USER_ID;}
    public void setUSER_TIME(String USER_TIME){this.USER_TIME=USER_TIME;}
    public String getUSER_TIME(){return USER_TIME;}
    public void setNC_CLICK(int NC_CLICK){this.NC_CLICK=NC_CLICK;}
    public int getNC_CLICK(){return NC_CLICK;}
    public void setNC_MEMO(String NC_MEMO){this.NC_MEMO=Tools.getSaveStr(NC_MEMO,50);}
    public String getNC_MEMO(){return NC_MEMO;}    

	public void setNC_MODE_NAME(String NC_MODE_NAME){this.NC_MODE_NAME=NC_MODE_NAME;}
	public String getNC_MODE_NAME()	{if(this.NC_MODE_NAME==null) return ""; else return this.NC_MODE_NAME;}	
	public void setNC_USER_NAME(String NC_USER_NAME){this.NC_USER_NAME=NC_USER_NAME;}
	public String getNC_USER_NAME(){if(this.NC_USER_NAME==null) return ""; else return this.NC_USER_NAME;}

    public void setPUSER_ID(String PUSER_ID){this.PUSER_ID=PUSER_ID;}
    public String getPUSER_ID(){return PUSER_ID;}   
    public void setPUSER_NAME(String PUSER_NAME){this.PUSER_NAME=PUSER_NAME;}
    public String getPUSER_NAME(){return PUSER_NAME;}   
    public void setDEPT_ID(String DEPT_ID){this.DEPT_ID=DEPT_ID;}
    public String getDEPT_ID(){return DEPT_ID;}   
    public void setDEPT_NAME(String DEPT_NAME){this.DEPT_NAME=DEPT_NAME;}
    public String getDEPT_NAME(){return DEPT_NAME;}
    public void setPUSER_TIME(String PUSER_TIME){this.PUSER_TIME=PUSER_TIME;}
    public String getPUSER_TIME(){return PUSER_TIME;}
    
    public Pv LoadData(String UserID,String ID,String OPType)
	{
    	Pv pv=new Pv();
    	if (OPType.equals(Pv.Browser)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_NOTIC",ID,Pv.Browser,Pv.Edit,Pv.Del);
    	}else if(OPType.equals(Pv.Edit)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_NOTIC",ID,Pv.Edit);
    	}else if(OPType.equals(Pv.Add)){
    		pv=getPvPageEdit(getMODEL(),UserID,"DEPT_ID","PUSER_ID","T_SYS_NOTIC",ID,Pv.Add);
    		return pv;
    	}
    	if(ID==null)return pv;
    	if(ID.trim().equals(""))return pv;
    	if (pv.PR_BROWSE || pv.PR_EDIT){
	       try {
	    	   pstmt=DoParSQL("select * from t_sys_NOTIC where NC_ID=?");
	    	   pstmt.setString(1, ID);
	    	   rs=pstmt.executeQuery();
	    	   if (rs.next())
	           {	              
	                setNC_ID(rs.getString("NC_ID"));
	                setNC_CAT(rs.getShort("NC_CAT"));
	                setNC_GRADE(rs.getString("NC_GRADE"));
	                setNC_SCOPE(rs.getShort("NC_SCOPE"));
	                setNC_USER(rs.getString("NC_USER"));
	                setNC_TITLE(rs.getString("NC_TITLE"));
	                setNC_BODY(rs.getString("NC_BODY"));
	                setUSER_TIME(rs.getString("PUSER_TIME"));
	                setNC_CLICK(rs.getInt("NC_CLICK"));
	                setNC_MEMO(rs.getString("NC_MEMO"));
	                
	                setDEPT_ID(rs.getString("DEPT_ID"));
	                setDEPT_NAME(rs.getString("DEPT_NAME"));
	                setPUSER_ID(rs.getString("PUSER_ID"));
	                setPUSER_NAME(rs.getString("PUSER_NAME"));
	                setPUSER_TIME(rs.getString("PUSER_TIME"));
	             }
	            String m="";
	            if(!getNC_USER().equals(""))
	            {
		    	    m=getNC_USER().replace(";","','");		    	    
		    	    if (getNC_SCOPE()==1){ //指定部门
		    	        rs = DoSQL(" select BASE_NAME from T_SYS_BASE where BASE_TYPE='dept' AND BASE_ID IN ('"+m+"')");		    	        
		    	    }else if (getNC_SCOPE()==2){  //指定人员
		    	        rs = DoSQL(" select USER_NAME from T_SYS_USER where USER_ID IN ('"+m+"')");
		    	    }
		    	    m="";
		    	    if(rs!=null)while (rs.next())
		    	    {
		    	        m=m+";"+rs.getString(1);
		    	    }
		    	    if (m.length()>0)  this.NC_USER_NAME=m.substring(1);
	            }
	       } catch (Exception e){
	    	   Log.LogWrite(2,"Notic==>LoadData()", e);
	       }
    	}
    	return pv;
	 }
	
	
//	添加
	//返回值 -1：表示插入失败   -2:表示有重复的值	
    public int Insert(String userid)
	{
    	int Count=-1;
       	Pv pv=getPvPageEdit(getMODEL(),userid,"","","","",Pv.Add);
       	if(!pv.PR_ADD){return -3;}
	    try{
			pstmt=DoParSQL("insert into T_SYS_NOTIC(NC_ID,NC_MODE,NC_CAT,NC_GRADE,NC_SCOPE,NC_USER,NC_TITLE,NC_BODY,PUSER_ID,NC_MEMO) values(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1,getNC_ID());
			pstmt.setString(2,getNC_MODE());
			pstmt.setShort(3,getNC_CAT());
			pstmt.setString(4,getNC_GRADE());
			pstmt.setShort(5,getNC_SCOPE());
			pstmt.setString(6,getNC_USER());
			pstmt.setString(7,getNC_TITLE());
			pstmt.setCharacterStream(8,new java.io.StringReader(getNC_BODY()),getNC_BODY().length()); //数据流
			pstmt.setString(9,userid);
			pstmt.setString(10,getNC_MEMO());
			Count=pstmt.executeUpdate();
            Log.LogDo(this, userid, getMODEL(), 5, "新增=>"+getNC_ID(), getNC_TITLE());
            ToHtml.SaveHtml(getMODEL());
	    }catch(Exception e){
	    	Count=-1;
	    	Log.LogWrite(2,"Notic==>Insert()", e);
	    }
	    return Count;
	}
	
	//修改
    public int Update(String userid)
	{    
        int Count=-1;
       	Pv pv=getPvPageEdit(getMODEL(),userid,"DEPT","PUSER_ID","T_SYS_NOTIC",getUSER_ID(),Pv.Edit);
       	if(!pv.PR_EDIT){return -3;}
	    try{
	    	//this.setNC_BODY(upFileToDB(session,this.getNC_BODY(),ID));
			pstmt=DoParSQL("update T_SYS_NOTIC set NC_CAT=?,NC_GRADE=?,NC_SCOPE=?,NC_USER=?,NC_TITLE=?,NC_BODY=?,NC_MEMO=? where NC_ID=?");	
			pstmt.setShort(1,getNC_CAT());
			pstmt.setString(2,getNC_GRADE());
			pstmt.setShort(3,getNC_SCOPE());
			pstmt.setString(4,getNC_USER());			
			pstmt.setString(5,getNC_TITLE());
			pstmt.setCharacterStream(6,new java.io.StringReader(getNC_BODY()),getNC_BODY().length()); //数据流
			pstmt.setString(7,getNC_MEMO());
			pstmt.setString(8,getNC_ID());
			Count=pstmt.executeUpdate();

			//Count=Count+stmt.executeUpdate("Update T_ATTFILE SET ATT_STATUS=1 Where OTHER_ID='"+this.ID+"'");
			Log.LogDo(this, userid, getMODEL(), 6, "修改=>"+getNC_ID(), getNC_TITLE());
			ToHtml.SaveHtml(getMODEL());
	    }catch(Exception e){
	    	Count=-1;
	    	Log.LogWrite(2,"Notic==>Delete()", e);
	    }
	    try{pstmt.close();}catch(Exception e){}
	    return Count;
	}
    public int Delete(String userid,String[] DocID)
    {
        int Count=0;
    	try {              	
        	con.setAutoCommit(false);
        	int sum=(DocID.length-1);
        	pstmt=DoParSQL("delete from T_SYS_NOTIC where NC_ID =? "+Tools.getAddSQL("And",getDeleteScope(userid,getMODEL(),"DEPT_ID","PSER_ID")));
			for(int i=0;i<sum;i++){
				pstmt.setString(1, DocID[i]);
				pstmt.addBatch();
			}
			Count=(pstmt.executeBatch()).length;
        	con.commit();
        	ToHtml.SaveHtml(getMODEL());
        if (Count==0){Count=-3;} else  if (sum>Count){Count=-4;} 
        }catch(Exception e){
        	try{con.rollback();}catch(Exception x){};
        	Count=-1;
        	Log.LogWrite(2,"Notic==>Delete()", e);
        }
    	try{con.setAutoCommit(true);}catch(Exception e){}
        return Count;
    }
//	public String upFileToDB(HttpSession session,String html,String otherid)throws Exception{
//		try{
//		ArrayList<?> al=(ArrayList<?>)session.getAttribute("upfiles");
//		if(al==null||al.size()==0)return html;
//		else{
//			String real=session.getServletContext().getRealPath("/");
//			Iterator<?> it=al.iterator();
//			String filePath;
//			PreparedStatement pst;
//			File f;
//			String file;
//			int index;
//			FileInputStream fis;
//			while(it.hasNext()){
//				file=(String)it.next();
//				index=html.indexOf(file);
//				if(index<0)
//					continue;
//				else{
//					f=new File(real+file);
//					filePath="pubimg.do?pubid="+file.substring(file.lastIndexOf("/")+1,file.lastIndexOf("."));
//					html=html.replaceAll(file,filePath);
//					pst=con.prepareStatement("insert into T_ATTFILE(ATT_TYPE,OTHER_ID,ATT_TITLE,ATT_EXNAME,ATT_SIZE,ATT_PATH,ATT_STATUS,ATT_BODY)values(1,'"+otherid+"','"+file.substring(file.lastIndexOf("/")+1,file.lastIndexOf("."))+"','"+file.substring(file.lastIndexOf(".")+1)+"',"+f.length()+",'"+filePath+"',"+1+",?)");
//					fis=new FileInputStream(f);
//					pst.setBinaryStream(1,fis,fis.available());
//					pst.executeUpdate();
//					pst.close();
//					fis.close();
//					f.delete();
//				}
//			}
//		}
//		}catch(Exception e){
//			Log.LogWrite(2,"Notic==>upFileToDB()", e);
//		}
//		return html;
//	}
//    public int verty(String userid,String[] DocID,String MODE){
//    	int Count=-1;
//    	String[] priv=getPrivilege(userid,MODE,"PR_EDIT");
//    	if (priv[0].equals("0")){return -3;}
//        String del="'"+DocID[0]+"'";
//        for(int i=1;i<DocID.length;i++) del=del+",'"+DocID[i]+"'";
//        try {
//            Count=stmt.executeUpdate("UPDATE T_NOTIC set NC_STATUS=1 Where NC_ID IN ("+del+")");
//            if (Count==0){Count=-3;} else  if (DocID.length>Count){Count=-4;}
//        }catch(Exception e){
//        	Count=-1;
//        	Log.LogWrite(2,"Notic==>verty()", e);
//        }
//    	return Count;
//    }
}
