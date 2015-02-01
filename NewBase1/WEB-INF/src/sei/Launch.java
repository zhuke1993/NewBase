package sei;

import java.io.File;
import java.util.Iterator;

import javax.servlet.ServletContextEvent;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import sei.core.DBConnect;
import sei.core.ModelObj;
import sei.core.ToTarget;
import sei.core.ToTargetOBJ;
import sei.log.Log;
import sei.security.PrivilegeLoad;

public class Launch implements javax.servlet.ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		 DBConnect sys=null;
		Base.RootPath=Launch.class.getClassLoader().getResource("").getPath();
		Base.RootPath=Base.RootPath.substring(0, Base.RootPath.indexOf("/WEB-INF/"));
		try{
			File f = new File(java.net.URLDecoder.decode(Launch.class.getResource("").getFile(), "GB2312") + "runConfig.xml");
		    SAXReader reader = new SAXReader();
		    Document doc = reader.read(f);
		    Element root = doc.getRootElement();
		    //Base.logPath=root.elementText("logPath");
		    Base.AttFileSaveToDB=Byte.parseByte(root.elementText("AttFileSaveToDB"));
		    //Base.AttFileSavePath=root.elementText("AttFileSavePath");
		    Base.Log=Byte.parseByte(root.elementText("Log"));
		    
		    Base.DatabaseDriver=root.elementText("DatabaseDriver");
		    Base.CnnStr = root.elementText("CnnStr");
		    Base.UserName=root.elementText("username");
		    Base.PassWord = root.elementText("password");
		    Base.ConnectPool= root.elementText("ConnectPool");
		    //Base.pageSize =Integer.parseInt(root.elementText("pageSize"));

            for (Iterator<?> i = root.elementIterator("tohtml"); i.hasNext();) {
                Element model = (Element) i.next();
                ToTarget TargetModel=new ToTarget();
                if(model.elements().size()==0){
                    ToTargetOBJ obj=new ToTargetOBJ();
                    obj.type=model.attributeValue("type");
                    obj.FromUrl=model.attributeValue("FromUrl");
                    obj.ToHtml=model.attributeValue("ToHtml");
                    obj.Class=model.attributeValue("Class");
                    obj.Method=model.attributeValue("Method");
                    TargetModel.Target.add(obj);
                }else{
                    for (Iterator<?> j = model.elementIterator();j.hasNext();) {
                        Element Taget = (Element) j.next();
                        ToTargetOBJ obj=new ToTargetOBJ();
                        obj.type=Taget.attributeValue("type");
                        obj.FromUrl=Taget.attributeValue("FromUrl");
                        obj.ToHtml=Taget.attributeValue("ToHtml");
                        obj.Class=Taget.attributeValue("Class");
                        obj.Method=Taget.attributeValue("Method");
                        TargetModel.Target.add(obj);
                    }
                }
                String id=model.attributeValue("id");
                String[] mm=id.split(",");
                for(int k=0;k<mm.length;k++){
                    Base.ModeltoHtml.put(mm[k], TargetModel);
                }
            }
            
            sys=new DBConnect();
            
            for (Iterator<?> i = root.elementIterator("model"); i.hasNext();) {
                Element model = (Element) i.next();
                String id=model.attributeValue("id");
                id=id.toLowerCase();
                if(id.indexOf(",")>0){
                	String[] tmp=id.split(",");
                	for(int j=0;j<tmp.length;j++){
                		ModelObj mo=new ModelObj();
//    	                mo.cls=model.attributeValue("cls");
//    	                if(mo.cls==null)mo.cls="";
    	                mo.loaddata=model.attributeValue("loaddata");
    	                mo.select=model.attributeValue("select");
    	                mo.total=model.attributeValue("total");
    	                mo.PageRows=model.attributeValue("PageRows");
    	                mo.DeptPv=model.attributeValue("DeptPv");
    	                mo.UserPv=model.attributeValue("UserPv");
    	                mo.Table=model.attributeValue("table");
    	                mo.PrimaryKey=model.attributeValue("PrimaryKey");
//    	                try{
//	    	                sys.rs=sys.DoSQL("select * from "+mo.Table+" LIMIT 0,1");
//	    	                ResultSetMetaData rsmd = sys.rs.getMetaData();
//	    	        		int cols = rsmd.getColumnCount();
//	    	        		for(int k=1;k<=cols;k++){
//	    	        			mo.FiledType.put(rsmd.getColumnName(k).toLowerCase(), rsmd.getColumnType(k));
//	    	        		}
//    	                }catch(Exception e){}
//    	                try{
//	    	                DatabaseMetaData dbMeta =sys.con.getMetaData();
//	    	                ResultSet pkRSet=dbMeta.getPrimaryKeys(null, null, mo.Table);
//	    	                if(pkRSet.next()){
//		    					mo.PrimaryKey= pkRSet.getString("COLUMN_NAME");
//		    				}else{
//		    					System.out.println("表: "+sys.rs.getString("TABLE_NAME")+" 无主键,请添加主键！");
//		    					//Log.LogWrite(2,"表: "+sys.rs.getString("TABLE_NAME")+" 无主键,请添加主键！", null);
//		    				}
//    	                }catch(Exception e){}
    	        		Base.ModelObj.put(tmp[j],mo);
                	}
                }else{
	                ModelObj mo=new ModelObj();
//	                mo.cls=model.attributeValue("cls");
//	                if(mo.cls==null)mo.cls="";
	                mo.loaddata=model.attributeValue("loaddata");
	                mo.select=model.attributeValue("select");
	                mo.total=model.attributeValue("total");
	                mo.PageRows=model.attributeValue("PageRows");
	                mo.DeptPv=model.attributeValue("DeptPv");
	                mo.UserPv=model.attributeValue("UserPv");
	                mo.Table=model.attributeValue("table");
	                mo.PrimaryKey=model.attributeValue("PrimaryKey");
//	                try{
//    	                sys.rs=sys.DoSQL("select * from "+mo.Table+" LIMIT 0,1");
//    	                ResultSetMetaData rsmd = sys.rs.getMetaData();
//    	        		int cols = rsmd.getColumnCount();
//    	        		for(int k=1;k<=cols;k++){
//    	        			mo.FiledType.put(rsmd.getColumnName(k).toUpperCase(), rsmd.getColumnType(k));
//    	        		}
//	                }catch(Exception e){}
//	                try{
//    	                DatabaseMetaData dbMeta =sys.con.getMetaData();
//    	                ResultSet pkRSet=dbMeta.getPrimaryKeys(null, null, mo.Table);
//    	                if(pkRSet.next()){
//	    					mo.PrimaryKey= pkRSet.getString("COLUMN_NAME");
//	    				}else{
//	    					System.out.println("表: "+sys.rs.getString("TABLE_NAME")+" 无主键,请添加主键！");
//	    					//Log.LogWrite(2,"表: "+sys.rs.getString("TABLE_NAME")+" 无主键,请添加主键！", null);
//	    				}
//	                }catch(Exception e){}
	                Base.ModelObj.put(id,mo);
                }
            }
            
        	if (System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")){
        		Base.logPath=root.elementText("logPath_Windows");
        		Base.AttFileSavePath=root.elementText("AttFileSavePath_Windows");
        	}else{
        		Base.logPath=root.elementText("logPath_Linux");
        		Base.AttFileSavePath=root.elementText("AttFileSavePath_Linux");
        	}
        	f=new File(Base.logPath);
    		if (!f.exists())f.mkdir();
    		f=new File(Base.AttFileSavePath);
			if (!f.exists())f.mkdir();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (Base.DatabaseDriver.indexOf("sqlserver") != -1)
        	Base.Ver = 0;
        else if (Base.DatabaseDriver.indexOf("oracle") != -1)
        	Base.Ver = 1;
        else 
        	Base.Ver = 2;
		

		Log.init();

		
		(new PrivilegeLoad()).Load();
		
//		PreparedStatement pstmt=null;
//        ResultSet rs = null;
//       
//        try {
//        	Privilege pv=new Privilege();
//            sys.rs = sys.DoSQL("select base_id from t_sys_base where base_type='role'");
//            if (sys.rs != null) while (sys.rs.next()) {
//                pstmt=sys.con.prepareStatement("SELECT T_SYS_OBJ.OBJ_ID, T_SYS_PRIVILEGE.PR_BROWSE,T_SYS_PRIVILEGE.PR_ADD, T_SYS_PRIVILEGE.PR_EDIT, T_SYS_PRIVILEGE.PR_DEL,T_SYS_PRIVILEGE.PR_AUDIT FROM t_sys_privilege,t_sys_obj WHERE t_sys_privilege.obj_id=t_sys_obj.obj_id and role_id=?");
//                pstmt.setString(1,sys.rs.getString(1));
//                rs=pstmt.executeQuery();
//                if(rs!=null)while(rs.next()){
//                	PrivilegeObj obj=new PrivilegeObj();
//                    obj.doBrowser=((rs.getByte("PR_BROWSE")==0)?false:true);
//                    obj.doAdd=((rs.getByte("PR_ADD")==0)?false:true);
//                    obj.doUpdate=((rs.getByte("PR_EDIT")==0)?false:true);
//                    obj.doDelete=((rs.getByte("PR_DEL")==0)?false:true);
//                    obj.doAudit=((rs.getByte("PR_AUDIT")==0)?false:true);
//                    pv.putValue(sys.rs.getString(1)+"-"+rs.getString(1),obj);
//                    //Privilege.RolePrivilege.put(sys.rs.getString(1)+"-"+rs.getString(1),obj);
//                }
//                try {if(rs!=null)rs.close();}catch (Exception e) {}
//                try {if(pstmt!=null)pstmt.close();}catch (Exception e) {}
//            }
//        }catch (Exception e){}
//        try {if(rs!=null)rs.close();}catch (Exception e) {}
//        try {if(pstmt!=null)pstmt.close();}catch (Exception e) {}

        
//		ResultSet pkRSet=null;
//		try{
//			DatabaseMetaData dbMeta =sys.con.getMetaData();
//			sys.rs =dbMeta.getTables(null,null, null, null);
//			while (sys.rs.next()){
//				pkRSet =dbMeta.getPrimaryKeys(null, null, sys.rs.getString("TABLE_NAME"));
//				if(pkRSet.next()){
//					Base.Table_PkCol.put(sys.rs.getString("TABLE_NAME").toUpperCase(), pkRSet.getString("COLUMN_NAME"));
//				}else{
//					System.out.println("表: "+sys.rs.getString("TABLE_NAME")+" 无主键,请添加主键！");
//					Log.LogWrite(2,"表: "+sys.rs.getString("TABLE_NAME")+" 无主键,请添加主键！", null);
//				}
//			}
//		}catch(Exception e){}
//		try{pkRSet.close();}catch(Exception e){}
		sys.CloseDataBase();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		Log.Close();		
	}
}
