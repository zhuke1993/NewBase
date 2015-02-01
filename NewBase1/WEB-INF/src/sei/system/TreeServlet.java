package sei.system;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sei.core.DBConnect;

public class TreeServlet extends HttpServlet{
	private static final long serialVersionUID = 8846744416603389886L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String UserID=(String)request.getSession(false).getAttribute("UserID");		
		if(UserID==null)return;
		response.setContentType("text/json;charset=utf-8"); 
		PrintWriter out = response.getWriter();
		String page=request.getParameter("page");if (page==null)page="";//对应的jsp页面
		String id = request.getParameter("id");if (id==null)id="0";
		String model=request.getParameter("model");if (model==null)model="";//模块,对应base_type
		//String model1=request.getParameter("model1");if(model1==null)model1="";if(!model1.equals(""))model1=" and base_type1='"+model1+"' ";//哪个模块,即base_type1字段
		String model1=request.getParameter("model1");if(model1==null)model1="";//if(!model1.equals(""))model1=" and base_type1=?";//哪个模块,即base_type1字段
		String LastLevel=request.getParameter("LastLevel");if (LastLevel==null)LastLevel="";//值为1就只能选择最后的叶节点
		String First=request.getParameter("First");if (First==null)First="0";//值为1时是第一次调用
		String All=request.getParameter("All");if(All==null)All="";//是否展示该节点下的所有信息，为0不加权限，为1加权限

		DBConnect sys= new DBConnect();
		StringBuffer o=new StringBuffer();
		try{
			sys.pstmt=sys.con.prepareStatement("SELECT BASE_ID,BASE_NAME,(select count(*) from t_sys_base t  where t.parent_id=T_SYS_BASE.base_id and base_type=?"+((model1.equals(""))?"":" and base_type1=?")+") FROM T_SYS_BASE WHERE base_type=?"+((model1.equals(""))?"":" and base_type1=?")+" and PARENT_ID=? ORDER BY BASE_ORDER ASC");
			sys.pstmt.setString(1,model);
			if(model1.equals("")){
				sys.pstmt.setString(2,model);
				sys.pstmt.setString(3,id);
			}else{
				sys.pstmt.setString(2,model1);
				sys.pstmt.setString(3,model);
				sys.pstmt.setString(4,model1);
				sys.pstmt.setString(5,id);
			}
			sys.rs=sys.pstmt.executeQuery();
			if(sys.rs!=null){
				o.append("[");
				int i=0;
				while(sys.rs.next()){
					if (i==0){
						i=1;
					}else{
						o.append(",");
					}
					if(sys.rs.getInt(3)>0){
						//out.print("{\"id\":\""+sys.rs.getString(1)+"\",\"text\":\""+sys.rs.getString(2)+"\",\"state\":\"closed\",\"attributes\":{\"data-href\":\""+request.getContextPath()+page+"?model="+model+"&ID="+sys.rs.getString(1)+"\"}}");
						o.append("{\"id\":\"").append(sys.rs.getString(1)).append("\",\"text\":\"").append(sys.rs.getString(2)).append("\",\"state\":\"closed\",\"attributes\":{\"data-href\":\"").append(request.getContextPath()).append(page).append("?model=").append(model).append("&ID=").append(sys.rs.getString(1)).append("\"}}");
					}else{
						//out.print("{\"id\":\""+sys.rs.getString(1)+"\",\"text\":\""+sys.rs.getString(2)+"\",\"state\":\"open\",\"attributes\":{\"data-href\":\""+request.getContextPath()+page+"?model="+model+"&ID="+sys.rs.getString(1)+"\"}}");
						o.append("{\"id\":\"").append(sys.rs.getString(1)).append("\",\"text\":\"").append(sys.rs.getString(2)).append("\",\"state\":\"open\",\"attributes\":{\"data-href\":\"").append(request.getContextPath()).append(page).append("?model=").append(model).append("&ID=").append(sys.rs.getString(1)).append("\"}}");
					}
				}
				o.append("]");
			}
		}catch(Exception e){e.printStackTrace();}
		sys.CloseDataBase();
		out.print(o.toString());
		out.flush();
		out.close();
	}
}