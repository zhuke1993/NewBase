package sei.core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import sei.Base;
import sei.log.Log;

public class DBConnect {
	public Connection con = null;
	//public Statement stmt = null;
	public PreparedStatement pstmt=null;
	public ResultSet rs = null;
	public DBConnect(){
		try {
       	   Context initContext = new InitialContext();
           DataSource ds = (DataSource) initContext.lookup(Base.ConnectPool);
           con=ds.getConnection(); 
           //stmt=con.createStatement();
		} catch (Exception e) {
    	   Log.LogWrite(2, "DBConnect==>通过连接池连接数据库失败,转为直接连接！", e);
    	   try{
    		   Class.forName(Base.DatabaseDriver);
               con = DriverManager.getConnection(Base.CnnStr, Base.UserName, Base.PassWord);
               //stmt=con.createStatement();
    	   }catch(Exception e1){
    		   Log.LogWrite(2, "DBConnect==>直接连接失败！", e);
    	   }
		}		
	}
    public PreparedStatement DoParSQL(PreparedStatement pstmt,String sql){
        try{
            if(pstmt==null || pstmt.isClosed()){
                return con.prepareStatement(sql);
            }else{
                //try {rs.close();}catch (Exception e) {}
                try {pstmt.close();}catch (Exception e) {}
                return con.prepareStatement(sql);
            }
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

	public PreparedStatement DoParSQL(String sql){
		try{
			if(pstmt==null || pstmt.isClosed()){
				return con.prepareStatement(sql);
			}else{
				try {rs.close();}catch (Exception e) {}
				try {pstmt.close();}catch (Exception e) {}
				return con.prepareStatement(sql);
			}
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
    public ResultSet DoSQL(PreparedStatement pstmt,ResultSet rs,String sql){
        try{
            if(pstmt==null || pstmt.isClosed()){
                pstmt=con.prepareStatement(sql);
                return rs=pstmt.executeQuery();
            }else{
                return rs=pstmt.executeQuery(sql);
            }
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
	public ResultSet DoSQL(String sql){
		try{
			if(pstmt==null || pstmt.isClosed()){
				pstmt=con.prepareStatement(sql);
				return rs=pstmt.executeQuery();
			}else{
				return rs=pstmt.executeQuery(sql);
			}
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
    public void CloseDataBase() {   	
    	try {rs.close();}catch (Exception e) {}
//    	try {stmt.close();}catch (Exception e) {}
    	try {pstmt.close();}catch (Exception e) {}
        try {con.close();con = null;}catch (Exception e) {}        
    }
}
