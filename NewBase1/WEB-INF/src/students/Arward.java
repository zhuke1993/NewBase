package students;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import sei.core.Log;
import sei.core.Privilege;
import sei.core.Pv;
import sei.util.Tools;
public class Arward extends Privilege {
    private String MODEL="arward";//权限模块名称
    private String UUID="" ;
    private String PARENT_ID="" ;
    private String XQ="";
    private String TYPE="";
    private String STATUS="";
    private String XH="";
    private String XM="";
    private String NJ="";
    private String XY_ID="";
    private String XY_NAME="";
    private String ZY_ID="";
    private String ZY_NAME="";
    private String BJ="";
    private String ZB_ID="";
    private String ZB_NAME="";
    private float FS=0 ;
    private String TIME1="";
    private String TIME2="";
    private String SY="";
    private String CONTENT="";
    private int ATTFILE=0 ;
    private String ATTSHOWFILENAME="";
    private String ATTSAVEFILENAME="";
    private String MEMO="";
    private String AUDITUSER_ID="";
    private String AUDITUSER_NAME="";
    private String AUDITTIME="";
    private String PUSER_ID="";
    private String PUSER_NAME="";
    private String PUSER_TIME="";

    public void setMODEL(String MODEL){this.MODEL=MODEL;}
    public String getMODEL(){return MODEL;}
    public void setUUID(String UUID) {this.UUID = Tools.getSaveStr(UUID,20);}
    public String getUUID() {return UUID;}
    public void setPARENT_ID(String PARENT_ID) {this.PARENT_ID = Tools.getSaveStr(PARENT_ID,20);}
    public String getPARENT_ID() {return PARENT_ID;}
    public void setXQ(String XQ) {this.XQ = Tools.getSaveStr(XQ,10);}
    public String getXQ() {return XQ;}
    public void setTYPE(String TYPE) {this.TYPE = Tools.getSaveStr(TYPE,20);}
    public String getTYPE() {return TYPE;}
    public void setSTATUS(String STATUS) {this.STATUS = Tools.getSaveStr(STATUS,10);}
    public String getSTATUS() {return STATUS;}
    public void setXH(String XH) {this.XH = Tools.getSaveStr(XH,10);}
    public String getXH() {return XH;}
    public void setXM(String XM) {this.XM = Tools.getSaveStr(XM,30);}
    public String getXM() {return XM;}
    public void setNJ(String NJ) {this.NJ = Tools.getSaveStr(NJ,8);}
    public String getNJ() {return NJ;}
    public void setXY_ID(String XY_ID) {this.XY_ID = Tools.getSaveStr(XY_ID,10);}
    public String getXY_ID() {return XY_ID;}
    public void setXY_NAME(String XY_NAME) {this.XY_NAME = Tools.getSaveStr(XY_NAME,50);}
    public String getXY_NAME() {return XY_NAME;}
    public void setZY_ID(String ZY_ID) {this.ZY_ID = Tools.getSaveStr(ZY_ID,10);}
    public String getZY_ID() {return ZY_ID;}
    public void setZY_NAME(String ZY_NAME) {this.ZY_NAME = Tools.getSaveStr(ZY_NAME,50);}
    public String getZY_NAME() {return ZY_NAME;}
    public void setBJ(String BJ) {this.BJ = Tools.getSaveStr(BJ,10);}
    public String getBJ() {return BJ;}
    public void setZB_ID(String ZB_ID) {this.ZB_ID = Tools.getSaveStr(ZB_ID,10);}
    public String getZB_ID() {return ZB_ID;}
    public void setZB_NAME(String ZB_NAME) {this.ZB_NAME = Tools.getSaveStr(ZB_NAME,60);}
    public String getZB_NAME() {return ZB_NAME;}
    public void setFS(float FS) {this.FS = FS;}
    public float getFS() {return FS;}
    public void setTIME1(String TIME1) {this.TIME1 = Tools.getSaveStr(TIME1,10);}
    public String getTIME1() {return TIME1;}
    public void setTIME2(String TIME2) {this.TIME2 = Tools.getSaveStr(TIME2,10);}
    public String getTIME2() {return TIME2;}
    public void setSY(String SY) {this.SY = Tools.getSaveStr(SY,255);}
    public String getSY() {return SY;}
    public void setCONTENT(String CONTENT) {this.CONTENT = Tools.getSaveStr(CONTENT,3000);}
    public String getCONTENT() {return CONTENT;}
    public void setATTFILE(int ATTFILE) {this.ATTFILE = ATTFILE;}
    public int getATTFILE() {return ATTFILE;}
    public void setATTSHOWFILENAME(String ATTSHOWFILENAME) {this.ATTSHOWFILENAME = Tools.getSaveStr(ATTSHOWFILENAME,300);}
    public String getATTSHOWFILENAME() {return ATTSHOWFILENAME;}
    public void setATTSAVEFILENAME(String ATTSAVEFILENAME) {this.ATTSAVEFILENAME = Tools.getSaveStr(ATTSAVEFILENAME,300);}
    public String getATTSAVEFILENAME() {return ATTSAVEFILENAME;}
    public void setMEMO(String MEMO) {this.MEMO = Tools.getSaveStr(MEMO,3000);}
    public String getMEMO() {return MEMO;}
    public void setAUDITUSER_ID(String AUDITUSER_ID) {this.AUDITUSER_ID = Tools.getSaveStr(AUDITUSER_ID,10);}
    public String getAUDITUSER_ID() {return AUDITUSER_ID;}
    public void setAUDITUSER_NAME(String AUDITUSER_NAME) {this.AUDITUSER_NAME = Tools.getSaveStr(AUDITUSER_NAME,30);}
    public String getAUDITUSER_NAME() {return AUDITUSER_NAME;}
    public void setAUDITTIME(String AUDITTIME) {this.AUDITTIME = Tools.getSaveStr(AUDITTIME,20);}
    public String getAUDITTIME() {return AUDITTIME;}
    public void setPUSER_ID(String PUSER_ID) {this.PUSER_ID = Tools.getSaveStr(PUSER_ID,10);}
    public String getPUSER_ID() {return PUSER_ID;}
    public void setPUSER_NAME(String PUSER_NAME) {this.PUSER_NAME = Tools.getSaveStr(PUSER_NAME,30);}
    public String getPUSER_NAME() {return PUSER_NAME;}
    public void setPUSER_TIME(String PUSER_TIME) {this.PUSER_TIME = Tools.getSaveStr(PUSER_TIME,19);}
    public String getPUSER_TIME() {return PUSER_TIME;}

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
                  pstmt=DoParSQL("select * from t_Students_Arward where UUID=?");
                  pstmt.setString(1, ID);
                  rs=pstmt.executeQuery();
                  if(rs!=null)if (rs.next()){
	                setUUID(rs.getString("UUID"));
	                setPARENT_ID(rs.getString("PARENT_ID"));
	                setXQ(rs.getString("XQ"));
	                setTYPE(rs.getString("TYPE"));
	                setSTATUS(rs.getString("STATUS"));
	                setXH(rs.getString("XH"));
	                setXM(rs.getString("XM"));
	                setNJ(rs.getString("NJ"));
	                setXY_ID(rs.getString("XY_ID"));
	                setXY_NAME(rs.getString("XY_NAME"));
	                setZY_ID(rs.getString("ZY_ID"));
	                setZY_NAME(rs.getString("ZY_NAME"));
	                setBJ(rs.getString("BJ"));
	                setZB_ID(rs.getString("ZB_ID"));
	                setZB_NAME(rs.getString("ZB_NAME"));
	                setFS(rs.getFloat("FS"));
	                setTIME1(rs.getString("TIME1"));
	                setTIME2(rs.getString("TIME2"));
	                setSY(rs.getString("SY"));
	                setCONTENT(rs.getString("CONTENT"));
	                setATTFILE(rs.getInt("ATTFILE"));
	                setATTSHOWFILENAME(rs.getString("ATTSHOWFILENAME"));
	                setATTSAVEFILENAME(rs.getString("ATTSAVEFILENAME"));
	                setMEMO(rs.getString("MEMO"));
	                setAUDITUSER_ID(rs.getString("AUDITUSER_ID"));
	                setAUDITUSER_NAME(rs.getString("AUDITUSER_NAME"));
	                setAUDITTIME(rs.getString("AUDITTIME"));
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


    public int Insert(String userid,HttpServletRequest request) {
        int Count = -1;
        Pv pv=getPvPageEdit(getMODEL(),userid,"","","","",Pv.Add);
        if(!pv.PR_ADD){return -3;}
        try{
            request.setCharacterEncoding("utf-8");
            RequestContext requestContext = new ServletRequestContext(request);
            UUID=Tools.getUUID("Ard");
        	if(FileUpload.isMultipartContent(requestContext)){
    			DiskFileItemFactory factory = new DiskFileItemFactory();
    			File file=new File(sei.Base.AttFileSavePath);
    		    if(!file.exists()){file.mkdirs();}
    			factory.setRepository(file);
    			ServletFileUpload upload = new ServletFileUpload(factory);
    			upload.setSizeMax(-1);
    			List<?> items =null;
    			try {
    				items = upload.parseRequest(request);
    			} catch (Exception e) {
    				return -1;
     			}

    			Iterator<?> it1 = items.iterator();
    			while(it1.hasNext()){
    				FileItem fileItem1 = (FileItem) it1.next();
    				if(fileItem1.isFormField()){
    					if(fileItem1.getFieldName().equals("STATUS")){
    						setSTATUS(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("TYPE")){
    						setTYPE(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("ZB_BH")){
    						setZB_ID(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("ZB_NAME")){
    						setZB_NAME(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("XH")){
    						setXH(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("XM")){
    						setXM(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("XQ")){
    						setXQ(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("FS")){
    						setFS(Float.parseFloat(fileItem1.getString("utf-8")));
    					}else if(fileItem1.getFieldName().equals("TIME1")){
    						setTIME1(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("SY")){
    						setSY(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("CONTENT")){
    						setCONTENT(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("MEMO")){
    						setMEMO(fileItem1.getString("utf-8"));
    					}
    				}
    			}
    			Iterator<?> it = items.iterator();
    			while(it.hasNext()){
    				FileItem fileItem = (FileItem) it.next();
    				if(!fileItem.isFormField()){
    					if(fileItem.getName()!=null && fileItem.getSize()!=0){
    						try{
    							ATTSHOWFILENAME=fileItem.getName();
    							String EXTNAME=ATTSHOWFILENAME.substring(ATTSHOWFILENAME.lastIndexOf("."));
    							ATTSAVEFILENAME=UUID+EXTNAME;
								file = new File(sei.Base.AttFileSavePath+File.separator+ATTSAVEFILENAME);
								fileItem.write(file);
								ATTFILE=1;
							}catch(Exception e){
								return -1;
							}
    					}
    				}  
    			}
    			String UserTYPE=Tools.getUser_Type(this, userid);
    			pstmt=DoParSQL("insert into t_SYS_arward(UUID,XQ,TYPE,XH,XM,ZB_ID,ZB_NAME,FS,TIME1,SY,CONTENT,ATTFILE,MEMO,STATUS,PUSER_ID,ATTSHOWFILENAME,ATTSAVEFILENAME,AUDITUSER_ID,AUDITTIME) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			pstmt.setString(1,getUUID());
    			pstmt.setString(2,getXQ());
    			pstmt.setString(3,getTYPE());
    			pstmt.setString(4,getXH());
    			pstmt.setString(5,getXM());
    			pstmt.setString(6,getZB_ID());
    			pstmt.setString(7,getZB_NAME());
    			pstmt.setFloat(8,getFS());
    			pstmt.setString(9,getTIME1());
    			pstmt.setString(10,getSY());
    			pstmt.setString(11,getCONTENT());
    			pstmt.setInt(12,getATTFILE());
    			pstmt.setString(13,getMEMO());
    			pstmt.setString(14,getSTATUS());
    			pstmt.setString(15,userid);
    			pstmt.setString(16,ATTSHOWFILENAME);
    			pstmt.setString(17,ATTSAVEFILENAME);
	            if(UserTYPE.equals("学生")){//学生
	            	pstmt.setString(18,"");
	            	pstmt.setString(19,"");
	        	}else {//辅导员
	        		pstmt.setString(18,userid);
	        		pstmt.setString(19,Tools.getDateTime("yyyy-MM-dd"));
	        	}
	            Count=pstmt.executeUpdate();
        	}
        	Log.LogDo(this, userid, getMODEL(), 5, "新增=>"+getUUID(),"");
        } catch (Exception e) {
        	Count = -1;
        	Log.LogWrite(2,"T_Students_Arward==>Insert()", e);
         }
        return Count;
    }

    public int Update(String userid,HttpServletRequest request){
    	int Count = -1;
    	Pv pv=getPvPageEdit(getMODEL(),userid,"XY_ID","XH","t_students_arward",getUUID(),Pv.Edit);
        if(!pv.PR_ADD){return -3;}
        try{
            request.setCharacterEncoding("utf-8");
            RequestContext requestContext = new ServletRequestContext(request);
         	if(FileUpload.isMultipartContent(requestContext)){
    			DiskFileItemFactory factory = new DiskFileItemFactory();
    			File file=new File(sei.Base.AttFileSavePath);
    		    if(!file.exists()){file.mkdirs();}
    			factory.setRepository(file);
    			ServletFileUpload upload = new ServletFileUpload(factory);
    			upload.setSizeMax(-1);
    			List<?> items =null;
    			try {
    				items = upload.parseRequest(request);
    			} catch (Exception e) {
    				return -1;
     			}

    			Iterator<?> it1 = items.iterator();
    			while(it1.hasNext()){
    				FileItem fileItem1 = (FileItem) it1.next();
    				if(fileItem1.isFormField()){
    					if(fileItem1.getFieldName().equals("STATUS")){
    						setSTATUS(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("TYPE")){
    						setTYPE(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("ZB_BH")){
    						setZB_ID(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("ZB_NAME")){
    						setZB_NAME(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("XH")){
    						setXH(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("XM")){
    						setXM(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("XQ")){
    						setXQ(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("FS")){
    						setFS(Float.parseFloat(fileItem1.getString("utf-8")));
    					}else if(fileItem1.getFieldName().equals("TIME1")){
    						setTIME1(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("SY")){
    						setSY(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("CONTENT")){
    						setCONTENT(fileItem1.getString("utf-8"));
    					}else if(fileItem1.getFieldName().equals("MEMO")){
    						setMEMO(fileItem1.getString("utf-8"));
    					}
    				}
    			}
    			Iterator<?> it = items.iterator();
    			while(it.hasNext()){
    				FileItem fileItem = (FileItem) it.next();
    				if(!fileItem.isFormField()){
    					if(fileItem.getName()!=null && fileItem.getSize()!=0){
    						try{
    							ATTSHOWFILENAME=fileItem.getName();
    							String EXTNAME=ATTSHOWFILENAME.substring(ATTSHOWFILENAME.lastIndexOf("."));
    							ATTSAVEFILENAME=Tools.getUUID("Ard")+EXTNAME;
								file = new File(sei.Base.AttFileSavePath+File.separator+ATTSAVEFILENAME);
								fileItem.write(file);
								ATTFILE=1;
							}catch(Exception e){
								return -1;
							}
    					}
    				}  
    			}
    			String UserTYPE=Tools.getUser_Type(this, userid);
    			String OldFileName=Tools.getName(this, "select ATTSAVEFILENAME from t_students_arward where uuid='"+getUUID()+"'");
	            pstmt=DoParSQL("update t_students_arward set XQ=?,STATUS=?,XH=?,XM=?,ZB_ID=?,ZB_NAME=?,FS=?,TIME1=?,SY=?,CONTENT=?,ATTFILE=?,MEMO=?,AUDITUSER_ID=?,AUDITUSER_NAME=?,AUDITTIME=?"+((getATTFILE()==1)?",ATTSHOWFILENAME=?,ATTSAVEFILENAME=?":"")+" where uuid=?");
	            pstmt.setString(1,getXQ());
	            pstmt.setString(2,getSTATUS());
	            pstmt.setString(3,getXH());
	            pstmt.setString(4,getXM());
	            pstmt.setString(5,getZB_ID());
	            pstmt.setString(6,getZB_NAME());
	            pstmt.setFloat(7,getFS());
	            pstmt.setString(8,getTIME1());
	            pstmt.setString(9,getSY());
	            pstmt.setString(10,getCONTENT());
	            pstmt.setInt(11,getATTFILE());
	            pstmt.setString(12,getMEMO());
	            if(ATTFILE==1){
	            	pstmt.setString(15,ATTSHOWFILENAME);
		            pstmt.setString(16,ATTSAVEFILENAME);
		            pstmt.setString(17,getUUID());
	            }else{
	            	pstmt.setString(15,getUUID());
	            }
	            if(UserTYPE.equals("学生")){//学生
	        		pstmt.setString(13,"");
	        		pstmt.setString(14,"");
	        	}else {//辅导员
	        		pstmt.setString(13,userid);
	        		pstmt.setString(14,Tools.getDateTime("yyyy-MM-dd"));
	        	}
	            
	            Count=pstmt.executeUpdate();
	            if(ATTFILE==1){
		            file = new File(sei.Base.AttFileSavePath+File.separator+OldFileName);
		            if (file.isFile())file.delete();
	            }
        	}
        	Log.LogDo(this, userid, getMODEL(), 5, "新增=>"+getUUID(),"");
        } catch (Exception e) {
        	Count = -1;
        	Log.LogWrite(2,"T_Students_Arward==>Insert()", e);
         }
        return Count;
    }

    public int Delete(String userid,String[] DocID){
        int Count=-1;
        try {
        	con.setAutoCommit(false);
        	int sum=(DocID.length-1);
        	pstmt=DoParSQL("delete from t_Students_Arward where UUID =? "+Tools.getAddSQL("And",getDeleteScope(userid,getMODEL(),"DEPT_ID","PSER_ID")));
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
        	Log.LogWrite(2,getMEMO()+"==>Delete()", e);
        }
        try{con.setAutoCommit(true);}catch(Exception e){}
        return Count;
    }

}

