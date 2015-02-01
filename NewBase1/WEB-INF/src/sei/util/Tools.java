package sei.util;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;

import sei.core.DBConnect;

public class Tools {
	public static void putFiledValue(PreparedStatement psmt,int order,int Type,String Value){
		
	}
	public static String FiledValue(String valString){
		if (valString==null)return "";
		if (valString.trim().equals(""))return "";
		return valString.replace("\"", "\\\"");
	}
	public static String getPageModelName(String Uri,String pra) {
		if (Uri!=null){
			if(Uri.lastIndexOf("/")>0){
				Uri=Uri.substring(Uri.lastIndexOf("/")+1);
			}
			if(Uri.lastIndexOf(pra)>0){
				Uri=Uri.substring(0,Uri.lastIndexOf(pra));
			}else{
				Uri="";
			}
		}
		return Uri;
	}
	public static String[] getSplitOptionValue(String value){
		if (value==null)return null;
		if(value.equals(""))return null;
		String[] tmp = new String[2];
		if(value.length()>=4){
			tmp[0] = value.substring(0, 4);
			if(tmp[0].toLowerCase().equals("like")){
				tmp[0] = "like";
				tmp[1] = value.substring(4);
				if(tmp[1].equals("%%")||tmp[1].equals("%")) return null;
				return tmp;
			}else if(value.length()>=5){
				tmp[0] = value.substring(0, 5);
				if(tmp[0].toLowerCase().equals("tree(")){
					int p=value.indexOf(")", 5);
					if(p>0){
						tmp[0] =value.substring(0, p+1);
						tmp[1]=value.substring(p+1);
						if(tmp[1].equals("")){
							return null;
						}else{
							return tmp;
						}
					}
				}
			}
		}
		if(value.startsWith(">=")||value.startsWith("<=")||value.startsWith("in")||value.startsWith("<>")||value.startsWith("!=")){
			tmp[0] = value.substring(0, 2);
			tmp[1] = value.substring(2);
			return tmp;
		}else if(value.startsWith(">")||value.startsWith("<")||value.startsWith("=")){
			tmp[0] = value.substring(0, 1);
			tmp[1] = value.substring(1);
			return tmp;
		}
		tmp[0]="=";
		tmp[1]=value;
		return tmp;
		//return null;
	}
	public static String getUser_Type(DBConnect sys,String User_id)//返回用户类型
	{
		try {
			sys.rs=sys.DoSQL("select USER_TYPE FROM T_SYS_USER WHERE USER_ID='"+User_id+"'");
	        if(sys.rs.next()) {
	        	User_id=sys.rs.getString(1);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			User_id="";
		}
		return User_id;
	}
	
	public static String getName(DBConnect sys,String SQL) {
        String str = "";
        try {
            sys.rs = sys.DoSQL(SQL);
            if (sys.rs != null) {
                while (sys.rs.next()) {
                    str = str + ";" + sys.rs.getString(1);
                }
            }
            if (!str.equals("")) str = str.substring(1);
        } catch (Exception e) {
            str = "";
        }
        return str;
    }
	
	/**
     * 返回以HeadStr打头的唯一编号,格式为:HeadStr+2位随机字符+数字
     * @param HeadStr 编号的头部
     * @param Model 模块名称,具体值位T_SEQ表中的TYPE值
     * @return 返回唯一编号
     */    
	synchronized static public String getSEQID(Statement stmt,ResultSet rs,String HeadStr,String Model) {
		String allChar = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
		Random random = new Random();
		allChar=HeadStr+allChar.charAt(random.nextInt(25))+""+allChar.charAt(random.nextInt(25));		
		try {
			rs = stmt.executeQuery("select SEQID FROM T_SEQ WHERE TYPE='"+Model+"'");
	        if(rs.next()) {
	        	allChar=allChar+rs.getLong(1);
	        }
	        stmt.execute("UPDATE T_SEQ SET SEQID=SEQID+1 WHERE TYPE='"+Model+"'");
		} catch (Exception e) {
			allChar = allChar+"00"+allChar.charAt(random.nextInt(25))+""+allChar.charAt(random.nextInt(25));
			e.printStackTrace();
		}
		return allChar;
	}
	
	public static String getAddSQL(String AndOr, String SQL) {
    	if (SQL==null) return "";
        if (!SQL.trim().equals("")) {
            SQL = " " + AndOr + " " + SQL;
        }
        return SQL;
    }
    public static String getSQL(String SQL, String AndOr, String Pr) {
        if (SQL == null) SQL = "";
        if (Pr == null) Pr = "";
        if ((SQL.trim().equals("")) & (!Pr.trim().equals(""))) return SQL = Pr;
        if ((!SQL.trim().equals("")) & (!Pr.trim().equals(""))) return SQL = SQL + " " + AndOr + " " + Pr;
        //SQL=getSQLLike(SQL);
        return SQL;
    }
	/**
	 * 删除input字符串中的html格式
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
		}
		return str;
	}
	
	   
		public static String getCharset(String s,String fromCharset1,String toCharset2){
		     if(s == null)return "";
		     try{
		         String convert = new String(s.getBytes(fromCharset1), toCharset2);
		         return convert;
		     }catch(Exception e){}           
		     return s;
		}
	    /**
	     * 返回数组中指定下标的指定长度的字符串。
	     * @param srcArray 源字符串数组
	     * @param Index 元数组中的第几个下标
	     * @param LimitStrMaxlength 要截取的最大长度
	     * @return 返回最大LimitStrMaxlength指定长度的字符串
	     */	
	    public static String getSaveArryStr(String[] srcArray, int Index,int LimitStrMaxlength) {
	    	if(srcArray==null){return "";}  //sql server,mysql		
	    	if(srcArray.length>Index){
	    		return getSaveStr(srcArray[Index],LimitStrMaxlength);
	    	}else{
	    		return "";
	    	}
	    }
	    /**
	     * 返回指定长度的字符串。
	     * @param srcString 源字符串
	     * @param LimitStrMaxlength 要截取的最大长度
	     * @return 返回最大LimitStrMaxlength指定长度的字符串
	     */
	    public static String getSaveStr(String srcString,int LimitStrMaxlength){
	    	return getSaveStr(srcString,LimitStrMaxlength,true,true);
	    }
	    /**
	     * 返回指定长度的字符串。
	     * @param srcString 源字符串
	     * @param LimitStrMaxlength 要截取的最大长度
	     * @param IsReturnSpace 当值为null时，是否返回空字符串,为true:要返回;false:不返回
	     * @param KillDouHao 是否去掉字符串中的单引号，为true:要去掉;false:不去掉
	     * @return 返回最大LimitStrMaxlength指定长度的字符串
	     */    
	    public static String getSaveStr(String srcString,int LimitStrMaxlength,boolean IsReturnSpace,boolean KillDouHao){
	    	if (srcString==null){
	    		if(IsReturnSpace){
	    			return "";  //sql server,mysql
	    		}
	    	}
	    	if (KillDouHao){
	    		srcString=srcString.replace("'","");
//	    		srcString=srcString.replace(",","");
	    		srcString=srcString.replace("\"","");
	    	}
	        if(LimitStrMaxlength!=0){
		        char[] cc=srcString.toCharArray();
		        int intLen=0;
		        int i;
		        for(i=0;i<cc.length;i++){
		            if((int)cc[i]>255){intLen=intLen+2;}else{intLen++;}
		            if (intLen>=LimitStrMaxlength){break;}
		        }        
		        if (intLen==LimitStrMaxlength)i++;
		        srcString=srcString.substring(0,i);
	        }
	        if (srcString.equals("")) {
	    		if(IsReturnSpace){
	    			return "";  //sql server,mysql
	    		}
	        }
	        return srcString;
	    }
	    
	   /**
     * 在当前时间上做加减
     * @param field 取1加1年,取2加半年,取3加一季度,取4加一周,取5加一天
     * @param value 具体加的数值
     * @return 返回还原后的标准SQL Like 语句
     */
	public static String getYearAdd(int field,int value){
		GregorianCalendar gc=new GregorianCalendar(); 
		gc.setTime(new Date()); 
		gc.add(field,value);
		return new SimpleDateFormat("yyyy-MM-dd").format(gc.getTime());
	}
	
    /**
     * 返回指定格式的当前时间
     * @param format 时间格式,如"yyyy-MM-dd HH:mm:ss"
     * @return 返回指定格式的当前时间
     */
    public static String getDateTime(String format)//"yyyy-MM-dd HH:mm:ss"
    {
     	SimpleDateFormat s= new SimpleDateFormat(format);
    	Calendar now=Calendar.getInstance();
        return s.format(now.getTime()).toString();
    }
    

    /**
     * 提供字符串到数字的检查。
     * @param NumberString 要检查和转换的数字(字符串形式)
     * @return 转换后的byte类型数字值
     */
    public static byte getByteNumber(String NumberString)
    {
    	if (NumberString==null) return 0;
    	NumberString=NumberString.trim();
    	if (NumberString.equals("")) return 0;
    	try{
    		return Byte.parseByte(NumberString);
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    /**
     * 提供字符串到数字的检查。
     * @param NumberString 要检查和转换的数字(字符串形式)
     * @return 转换后的int类型数字值
     */
    public static int getIntNumber(String NumberString)
    {
    	if (NumberString==null) return 0;
    	NumberString=NumberString.trim();
    	if (NumberString.equals("")) return 0;
    	try{
    		return Integer.parseInt(NumberString);
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    /**
     * 提供字符串到数字的检查。
     * @param NumberString 要检查和转换的数字(字符串形式)
     * @return 转换后的long类型数字值
     */
    public static long getLongNumber(String NumberString)
    {
    	if (NumberString==null) return 0;
    	NumberString=NumberString.trim();
    	if (NumberString.equals("")) return 0;
    	try{
    		return Long.parseLong(NumberString);
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    
    /**
     * 提供字符串到数字的检查。
     * @param NumberString 要检查和转换的数字(字符串形式)
     * @return 转换后的float类型数字值
     */
    public static float getFloatNumber(String NumberString)
    {
    	if (NumberString==null) return 0;
    	NumberString=NumberString.trim();
    	if (NumberString.equals("")) return 0;
    	try{
    		return Float.parseFloat(NumberString);
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    /**
     * 提供字符串到数字的检查。
     * @param NumberString 要检查和转换的数字(字符串形式)
     * @return 转换后的float类型数字值
     */
    public static double getDoubleNumber(String NumberString)
    {
    	if (NumberString==null) return 0;
    	NumberString=NumberString.trim();
    	if (NumberString.equals("")) return 0;
    	try{
    		return Double.parseDouble(NumberString);
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

     /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

     /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){return div(v1,v2,10);}

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){throw new IllegalArgumentException("The scale must be a positive integer or zero");}
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

     /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){
        if(scale<0){throw new IllegalArgumentException("The scale must be a positive integer or zero");}
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的格式化小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @param Format 输出的格式 如保留小数点后2位"#.00"
     * @return 四舍五入后的格式化结果
     */
    public static String round(double v,int scale,String Format){
        if(scale<0){throw new IllegalArgumentException("The scale must be a positive integer or zero");}
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
	    DecimalFormat df1 = new DecimalFormat(Format);
	    return df1.format(b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 提供精确的格式化小数位处理。
     * @param v 需要四舍五入的数字
     * @param Format 输出的格式 如保留小数点后2位"#.00"
     * @return 四舍五入后的格式化结果
     */
    public static String round(double v,String Format){
	    DecimalFormat df1 = new DecimalFormat(Format);
	    return df1.format(v);    	
    }
    
    /**
     * @param prefix 表述生成UUID的模块名称 比如 News1714a67f-d0da-44a4-ab30-3934e6abc184
     * @return 生成uuid
     */
    public static String getUUID(String prefix)
    {
    	UUID uuid = UUID.randomUUID();
    	return prefix+uuid.toString().replace("-", "").substring(0, 16);
    }
    
	 /**
     * 返回指定身份证的性别。
     * @param CardNumber 身份证号
     * @return 返回性别，男或者女
     */	
    public static String getSexFromCardNumber(String CardNumber) {
    	try{
	        if (CardNumber.length()==18)
	        {
	            if ((CardNumber.charAt(16))%2==0){return "女";}else{return "男";}
	        }else{
	            if ((CardNumber.charAt(14))%2==0){return "女";}else{return "男";}
	        }
    	}catch(Exception e){return "";}
    }
    
    /**
     * 返回指定身份证的的年龄。
     * @param BIRTHDAY 出生日期
     * @return 返回性别，男或者女
     */	
    public static int getAge(String BIRTHDAY) {
    	try{
    		SimpleDateFormat s= new SimpleDateFormat("MM-dd");
    		Calendar cal = Calendar.getInstance(); 
    		int yearNow = cal.get(Calendar.YEAR); 
    		yearNow=yearNow-Integer.parseInt(BIRTHDAY.substring(0,4));
    		if(s.format(cal.getTime()).toString().compareTo(BIRTHDAY.substring(4))<0){
    			yearNow--;
    		}
    		return yearNow;
    	}catch(Exception e){return 0;}
    }
    
    //创建已填充数据的下拉列表框
    public static String buildListItem(DBConnect sys,String SQL, String Value,boolean IsSelect) {
        String str = "";
        int i = 0;
        try {
            sys.rs = sys.DoSQL(SQL);
            while (sys.rs.next()) {
            	if(IsSelect){
	                if (Value.equals("")) {
	                    if (i == 0) {
	                      //  FirstValue = rs.getString(1).trim();
	                        i = 1;
	                        str = str + "<option value='" + sys.rs.getString(1) + "' selected >" + sys.rs.getString(2) + "</option>";
	                    } else {
	                        str = str + "<option value='" + sys.rs.getString(1) + "'>" + sys.rs.getString(2) + "</option>";
	                    }
	                } else {
	                    if (sys.rs.getString(1).equals(Value)) {
	                        str = str + "<option value='" + sys.rs.getString(1) + "' selected >" + sys.rs.getString(2) + "</option>";
	                    } else {
	                        str = str + "<option value='" + sys.rs.getString(1) + "'>" + sys.rs.getString(2) + "</option>";
	                    }
	                }
            	}else{
                    if (sys.rs.getString(1).equals(Value)) {
                        str = str + "<option value='" + sys.rs.getString(1) + "' selected >" + sys.rs.getString(2) + "</option>";
                    } else {
                        str = str + "<option value='" + sys.rs.getString(1) + "'>" + sys.rs.getString(2) + "</option>";
                    }            	
            	}
            }
        } catch (Exception e) {
        	
        }
        return str;
    }
    
}
