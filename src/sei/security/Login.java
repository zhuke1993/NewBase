package sei.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sei.core.DBConnect;
import sei.log.Log;

public class Login {
	public static int Verification(HttpServletRequest request){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			e.getMessage();
			return -9;
		}
        HttpSession session=request.getSession();
        int flag = -1;//登录是否成功标志,1:登录成功,-1:账户或密码错误,-2:账户没有启用,-3:IP不对,-9:未知错误
        String UserID = request.getParameter("UserID");
        if (UserID == null) {UserID = "";}
        String Password = request.getParameter("Password");
        if (Password == null) {Password = "";}
        if(!UserID.equals("")&&!Password.equals("")){
        	String RoleID="";
            String IP = request.getRemoteAddr();
            DBConnect sys = new DBConnect();
            try {
                sys.pstmt = sys.DoParSQL("select USER_ID,USER_PWD,USER_IP,USER_STATUS,Role_ID from T_SYS_USER where USER_ID=? and USER_PWD=?");
                sys.pstmt.setString(1, UserID);
                sys.pstmt.setString(2, Password);
                sys.rs=sys.pstmt.executeQuery();
                if (sys.rs != null) {
                    if (sys.rs.next()) {
                        if (sys.rs.getString("USER_ID").trim().equals(UserID)& sys.rs.getString("USER_PWD").trim().equals(Password)) {
                            if(sys.rs.getInt("USER_STATUS")==1){
                                String LIP = sys.rs.getString("USER_IP").trim();
                                RoleID=sys.rs.getString(5);
                                if (LIP.equals("") | LIP.equals("*")) {
                                    flag = 1;
                                } else {
                                    if (LIP.indexOf(IP) != -1) {
                                        flag = -3;
                                    } else {
                                        flag = 1;
                                    }
                                }
                            }else{
                                flag=-2;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(flag==1){
                    session.setAttribute("UserID", UserID);
                    session.setAttribute("RoleID", RoleID);
                    Log.LogDo(sys, UserID, "login", 1, "登陆", "");
                }
                sys.CloseDataBase();
            }
        }
		return flag;
	}
	
	public static void Logout(HttpServletRequest request){
		HttpSession session=request.getSession();
        session.removeAttribute("UserID");
        session.removeAttribute("RoleID");
        session.invalidate();
	}
}
