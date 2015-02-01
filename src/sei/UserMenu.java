package sei;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserMenu extends HttpServlet {

	private static final long serialVersionUID = 1424762353587641833L;

	public void destroy() {
		super.destroy(); 
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String UserID=(String)request.getSession(false).getAttribute("UserID");	
		if(UserID==null){
			response.sendRedirect(request.getContextPath()+"/");
			//request.getRequestDispatcher("/index.shtml").forward(request, response);
			return;
		}
		response.setContentType("text/json;charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.print("[");
		out.print("{\"id\":\"base\",\"text\":\"基础设置\",\"state\":\"closed\"}");
		//out.print(",{\"id\":\"base01\",\"text\":\"日志类型\",\"state\":\"open\",\"attributes\":{\"data-href\":\"/MainTreeServlet.do?model=rizhi&page=/system/BaseEdit.jsp\"}}");
		out.print(",{\"id\":\"base01\",\"text\":\"日志类型\",\"state\":\"open\",\"attributes\":{\"data-href\":\"/system/baseList.jsp?ffmodel=dept\"}}");
		out.print(",{\"id\":\"base01\",\"text\":\"日志类型\",\"state\":\"open\",\"attributes\":{\"data-href\":\"/system/baseList.jsp?ffmodel=dept\"}}");

		
		out.print(",{\"id\":\"sys\",\"text\":\"系统管理\",\"state\":\"closed\"}");
		out.print(",{\"id\":\"sys01\",\"text\":\"用户管理\",\"state\":\"open\",\"attributes\":{\"data-href\":\"system/userList.jsp\"}}");
		out.print(",{\"id\":\"base01\",\"text\":\"部门管理\",\"state\":\"open\",\"attributes\":{\"data-href\":\"/system/deptList.jsp?ffmodel=dept\"}}");
		out.print(",{\"id\":\"base01\",\"text\":\"角色管理\",\"state\":\"open\",\"attributes\":{\"data-href\":\"/system/roleList.jsp?ffmodel=role\"}}");

		out.print(",{\"id\":\"base01\",\"text\":\"权限管理\",\"state\":\"open\",\"attributes\":{\"data-href\":\"/system/privilegeList.jsp\"}}");

		out.print("]");
//		String id = request.getParameter("id");if (id==null)id="0";
//		DBConnect sys= new DBConnect();
//		try{
//			sys.pstmt=sys.con.prepareStatement("SELECT BASE_ID,BASE_NAME,(select count(*) from t_sys_base t where t.parent_id=T_SYS_BASE.base_id),BASE_EXT1 FROM T_SYS_BASE WHERE base_type='menu' and PARENT_ID=? ORDER BY BASE_ORDER ASC");
//			sys.pstmt.setString(1,id);
//			sys.rs=sys.pstmt.executeQuery();
//			if(sys.rs!=null){
//				out.print("[");
//				int i=0;
//				while(sys.rs.next()){
//					if(sys.rs.getInt(3)>0){
//						if (i==0){
//							out.print("{\"id\":\""+sys.rs.getString(1)+"\",\"text\":\""+sys.rs.getString(2)+"\",\"state\":\"closed\"}");
//							i=1;
//						}else{
//							out.print(",{\"id\":\""+sys.rs.getString(1)+"\",\"text\":\""+sys.rs.getString(2)+"\",\"state\":\"closed\"}");
//						}
//					}else{
//						if (i==0){
//							out.print("{\"id\":\""+sys.rs.getString(1)+"\",\"text\":\""+sys.rs.getString(2)+"\",\"state\":\"open\",\"attributes\":{\"data-href\":\""+sys.rs.getString(4)+"\"}}");
//							i=1;
//						}else{
//							out.print(",{\"id\":\""+sys.rs.getString(1)+"\",\"text\":\""+sys.rs.getString(2)+"\",\"state\":\"open\",\"attributes\":{\"data-href\":\""+sys.rs.getString(4)+"\"}}");
//						}						
//					}
//				}
//				out.print("]");
//			}
//		}catch(Exception e){e.printStackTrace();}
//		sys.CloseDataBase();
		out.flush();
		out.close();
	}
}