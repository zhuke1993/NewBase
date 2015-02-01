package sei;

import java.util.HashMap;

import sei.core.ModelObj;
import sei.core.ToTarget;

public class Base {
	public static byte Ver=2; //数据库类型。 0:SQL Server; 1:Oracle;2:mysql
	public static String logPath = ""; //日志文件写目录
	public static byte AttFileSaveToDB=1;//1:存到数据库中，0：存为磁盘文件，文件目录为AttFileSavePath指定的目录
	public static String AttFileSavePath="";
	public static String ConnectPool="";//连接池名称
	public static String DatabaseDriver ="";
	public static String CnnStr ="";
	public static String UserName="";
	public static String PassWord ="";
	public static byte Log=0; //0:不启用日志记录,1:启用日志记录
	
//	public static int pageSize =30;
	public static String RootPath="";
    public static HashMap<String, ToTarget> ModeltoHtml=new HashMap<String, ToTarget>();//用于静态化
    //public static HashMap<String, String> Table_PkCol=new HashMap<String, String>();//查找表主键
    public static HashMap<String, ModelObj> ModelObj=new HashMap<String, ModelObj>();//查询显示的字段
    //public static HashMap<String, PrivilegeObj> RolePrivilege=new HashMap<String, PrivilegeObj>();//角色权限
}
