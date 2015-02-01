package students;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import sei.util.Tools;

public class StuTools {
	public static String getXQ(String curr)//返回学期
	{
		if(curr==null)curr="";
		StringBuilder mm=new StringBuilder();
		int CurYear=Integer.parseInt(Tools.getDateTime("yyyy"));
		int CurrMonth=Integer.parseInt(Tools.getDateTime("MM"));
		if(CurrMonth>7){
			mm.append("<option value='" + CurYear+ "2'"+((curr.equals(CurYear+"2"))?" selected":"")+">" + CurYear+ "2</option>");
			mm.append("<option value='" + CurYear+ "1'"+((curr.equals(CurYear+"1"))?" selected":"")+">" + CurYear+ "1</option>");
		}else{
			mm.append("<option value='" + CurYear+ "1'"+((curr.equals(CurYear+"1"))?" selected":"")+">" + CurYear+ "1</option>");
		}
		for(int i=1;i<4;i++){
			CurYear=CurYear-1;
			mm.append("<option value='" + CurYear+ "1'"+((curr.equals(CurYear+"1"))?" selected":"")+">" + CurYear+ "1</option>");
			mm.append("<option value='" + CurYear+ "2'"+((curr.equals(CurYear+"2"))?" selected":"")+">" + CurYear+ "2</option>");
		}
		return mm.toString();
	}
	public static String getNJ(String curr)//返回年级
	{
		if(curr==null)curr="";
		StringBuilder mm=new StringBuilder();
		int CurYear=Integer.parseInt(Tools.getDateTime("yyyy"));
		mm.append("<option value='" + CurYear+ "'"+((curr.equals(CurYear+""))?" selected":"")+">" + CurYear+ "</option>");
		for(int i=1;i<=4;i++){
			CurYear=CurYear-1;
			mm.append("<option value='" + CurYear+ "'"+((curr.equals(CurYear+""))?" selected":"")+">" + CurYear+ "</option>");
		}
		return mm.toString();
	}
	public static String GetWeekNum(String open_time){
		String now_time="";
	    long quot = 0;
	    
	    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date dd = new Date();
		now_time = ft.format(dd);
		
		try {
			Date date1 = ft.parse( open_time );
			Date date2 = ft.parse( now_time );
	    	quot = date2.getTime() - date1.getTime();
	    	quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot/7+1+"";
	}

}
