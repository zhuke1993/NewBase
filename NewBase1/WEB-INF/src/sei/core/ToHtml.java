package sei.core;

import java.lang.reflect.Method;

import sei.Base;
import sei.core.ToTarget;
import sei.core.ToTargetOBJ;
public class ToHtml {
	public static void SaveHtml(String ModelName) {
		try{
			ToTarget m =Base.ModeltoHtml.get(ModelName);
			if(m!=null){
				for(int i=0;i<m.Target.size();i++){
					ToTargetOBJ k=m.Target.get(i);
					Class<?> cls = Class.forName(k.Class);
					Method f = cls.getMethod(k.Method, new Class[]{ToTargetOBJ.class});
					f.invoke(f, k);
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
}
