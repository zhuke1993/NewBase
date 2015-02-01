package sei.core;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class RequestToBean {
	public static void ToBean(HttpServletRequest request,Class<?> cls){

		Field[] fields = cls.getDeclaredFields();
		String value=null;
		for (Field field : fields) {
			try{
				value = request.getParameter(field.getName());
				if(value==null) continue;
//				System.out.println(field.getName()+"==>"+field.getType().getName());
				if(field.getType().getName().equals("java.lang.String")){
					method = cls.getMethod("set"+field.getName(),String.class);
					method.invoke(obj, String.valueOf(value));
				}else if(field.getType().getName().equals("int")){
					method = cls.getMethod("set"+field.getName(),int.class);
					method.invoke(obj, Integer.parseInt(value));
				}else if(field.getType().getName().equals("float")){
					method = cls.getMethod("set"+field.getName(),float.class);
					method.invoke(obj, Float.parseFloat(value));
				}else if(field.getType().getName().equals("boolean")){
					method = cls.getMethod("set"+field.getName(),boolean.class);
					method.invoke(obj, Boolean.parseBoolean(value));
				}else if(field.getType().getName().equals("byte")){
					method = cls.getMethod("set"+field.getName(),byte.class);
					method.invoke(obj, Byte.parseByte(value));
				}else if(field.getType().getName().equals("short")){
					method = cls.getMethod("set"+field.getName(),short.class);
					method.invoke(obj, Short.parseShort(value));
				}else if(field.getType().getName().equals("long")){
					method = cls.getMethod("set"+field.getName(),long.class);
					method.invoke(obj, Long.parseLong(value));
				}else if(field.getType().getName().equals("double")){
					method = cls.getMethod("set"+field.getName(),double.class);
					method.invoke(obj, Double.parseDouble(value));
				}
			}catch(Exception ee){}
		}
		method = cls.getMethod(OPType,String.class);
		flag=(int)(method.invoke(obj, UserID));
		method = cls.getMethod("CloseDataBase");
		method.invoke(obj);
		if(flag==1){
			response.sendRedirect(request.getContextPath()+"/system/success.html");
		}else if(flag==-3){
			response.sendRedirect(request.getContextPath()+"/system/nopv.html");
		}else if(flag==-4){
			response.sendRedirect(request.getContextPath()+"/system/keyfail.html");
		}else {
			response.sendRedirect(request.getContextPath()+"/system/fail.html");
		}
		return;
	
	}
}
