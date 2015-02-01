package sei.util;

import java.math.BigDecimal;

public class Money {
    /**
    * 提供从字符和数字的钱转换为大写金额的钱。
    * @param bigdMoneyNumber 钱的数字。例如0.35
    * @return 返回大写的钱金额。例如：三角伍分
    */
    public static String getChnMoney(String bigdMoneyNumber){return  getChnMoney(new BigDecimal(bigdMoneyNumber));}
    public static String getChnMoney(BigDecimal bigdMoneyNumber)
    {
  	  String[] straChineseUnit = new String[]{ "分", "角", "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
  	  String[] straChineseNumber = new String[]{ "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};//中文数字字符数组
  	  String strChineseCurrency = "";
      boolean bZero = true;//零数位标记
      int ChineseUnitIndex = 0;//中文金额单位下标
      try
      {
        if (bigdMoneyNumber.intValue() == 0) return "零圆整";
        double doubMoneyNumber = Math.round(bigdMoneyNumber.doubleValue() * 100);//处理小数部分，四舍五入
        boolean bNegative = doubMoneyNumber < 0;//是否负数
        doubMoneyNumber = Math.abs(doubMoneyNumber);//取绝对值
        while (doubMoneyNumber > 0)//循环处理转换操作
        { 
          if (ChineseUnitIndex == 2 && strChineseCurrency.length() == 0)strChineseCurrency = strChineseCurrency + "整";//整的处理(无小数位)
          if (doubMoneyNumber % 10 > 0)//非零数位的处理
          {
            strChineseCurrency = straChineseNumber[ (int) doubMoneyNumber % 10] + straChineseUnit[ChineseUnitIndex] + strChineseCurrency;
            bZero = false;
          }else{//零数位的处理
            if (ChineseUnitIndex == 2)//元的处理(个位)
            {
              if (doubMoneyNumber > 0)//段中有数字
              {
                strChineseCurrency = straChineseUnit[ChineseUnitIndex] + strChineseCurrency;
                bZero = true;
              }
            }else if (ChineseUnitIndex == 6 || ChineseUnitIndex == 10)//万、亿数位的处理
            {
            	if (doubMoneyNumber % 1000 > 0) strChineseCurrency = straChineseUnit[ChineseUnitIndex] + strChineseCurrency;//段中有数字
            }
            if (!bZero) strChineseCurrency = straChineseNumber[0] + strChineseCurrency; //前一数位非零的处理
            bZero = true;
          }
          doubMoneyNumber = Math.floor(doubMoneyNumber / 10);
          ChineseUnitIndex++;
        }
        if (bNegative) strChineseCurrency = "负" + strChineseCurrency;//负数的处理
      }
      catch (Exception e){return "";}
      return strChineseCurrency;
    }
}
