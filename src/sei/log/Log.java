package sei.log;

import java.io.File;
import java.io.FileWriter;

import sei.Base;
import sei.core.DBConnect;

public class Log {
    /**
     * 写日志。
     * @param Type 日志类型.0:一般信息;1:警告;2:错误
     * @param ErrInfo 错误模块信息
     * @param e 具体错误内容，
     */
	private static FileWriter log0;
	private static FileWriter log1;
	private static FileWriter log2;
	public static void init(){
		try{
			File f0=new File(Base.logPath+File.separator+"LogInfo.txt");
			if(!f0.exists())f0.createNewFile();
			File f1=new File(Base.logPath+File.separator+"LogWarm.txt");
			if(!f1.exists())f1.createNewFile();
			File f2=new File(Base.logPath+File.separator+"LogErr.txt");
			if(!f2.exists())f2.createNewFile();
			
			log0=new FileWriter(f0,true);
			log1=new FileWriter(f1,true);
			log2=new FileWriter(f2,true);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void Close(){
		try{log0.close();}catch(Exception e){}
		try{log1.close();}catch(Exception e){}
		try{log2.close();}catch(Exception e){}
	}
	
	public static void LogWrite(int Type,String ErrInfo,Exception e){
		try{
			if(Type==0){//警告
				log0.write("["+sei.util.Tools.getDateTime("yyyy-MM-dd HH:mm:ss")+"] ["+ErrInfo+"] ["+e.toString()+" ==> "+e.getStackTrace()[0].toString()+" ]\r\n");
			}else if(Type==1){//错误
				log1.write("["+sei.util.Tools.getDateTime("yyyy-MM-dd HH:mm:ss")+"] ["+ErrInfo+"] ["+e.toString()+" ==> "+e.getStackTrace()[0].toString()+" ]\r\n");
			}else{
				log2.write("["+sei.util.Tools.getDateTime("yyyy-MM-dd HH:mm:ss")+"] ["+ErrInfo+"] ["+e.toString()+" ==> "+e.getStackTrace()[0].toString()+" ]\r\n");
			}
			e.printStackTrace();
		}catch(Exception e1){}
	}
	
	public static void LogDo(DBConnect db,String User,String Mode,int doid,String dorem,String memo){
		if(Base.Log==1){
			try{
				db.pstmt=db.DoParSQL("INSERT INTO T_SYS_LOG(model,doid,dorem,puser_id,memo)VALUE(?,?,?,?,?)");
				db.pstmt.setString(1, Mode);
				db.pstmt.setInt(2, doid);
				db.pstmt.setString(3, sei.util.Tools.getSaveStr(dorem,500));
				db.pstmt.setString(4, User);
				db.pstmt.setString(5, sei.util.Tools.getSaveStr(memo,100));
				db.pstmt.executeUpdate();
			}catch(Exception e){e.printStackTrace();}
		}
	}
}
