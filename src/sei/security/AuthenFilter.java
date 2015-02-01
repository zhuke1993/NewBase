package sei.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sei.util.Tools;

public class AuthenFilter implements Filter{

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		//HttpServletRequest request = (HttpServletRequest) req;
		//HttpSession session = request.getSession();
		//System.out.println(((HttpServletRequest) req).getRequestURI());
		String RoleID=(String)((HttpServletRequest) req).getSession().getAttribute("RoleID");
		if(RoleID==null){
			Privilege.NoLogin((HttpServletResponse)resp);
			//((HttpServletResponse)resp).sendRedirect("/");
		}else{
			String ffmodel=(String)((HttpServletRequest) req).getParameter("ffmodel");
			if(ffmodel==null || ffmodel.equals("")){
				ffmodel=Tools.getPageModelName(((HttpServletRequest) req).getRequestURI(),"List.jsp");
			}
			ffmodel=ffmodel.toLowerCase();
			if(!ffmodel.equals("")){//是列表页面
				//查看是否有列表查看权限
				PrivilegeObj pvobj=Privilege.getPrivilegeObj(RoleID,ffmodel);
				if(((pvobj.doBrowser==0)?false:true)){
					req.setAttribute("doAdd", pvobj.doAdd);
				}else{
					((HttpServletResponse)resp).sendRedirect("system/nopv.html");
					return;
				}
			}else{//是编辑页面
//				HttpServletRequest request=((HttpServletRequest) req);
//				model=Tools.getPageModelName(request.getRequestURI(),"Edit.jsp");
//				String ID=request.getParameter("ID");
//				String OPType=request.getParameter("OPType");if(OPType==null)OPType="";
//				if(ID==null){
//					OPType="Insert";
////					((HttpServletResponse)resp).sendRedirect("nopv.html");
////					return;
//				}else{
//					
//				}
				
			}
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
