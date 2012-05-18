package chat.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chat.user.User;

public class RestrictPageFilter implements Filter {

	FilterConfig fc;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		fc = filterConfig;
	}
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		String pageRequested = req.getRequestURL().toString();
		
		User user = (User)session.getAttribute("user");
		
		if((user != null && user.isLoggedIn()) || pageRequested.contains("login.xhtml") ||
				pageRequested.contains("register.xhtml"))
			chain.doFilter(request, response);
		else
		{
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
		}
	}
	
	@Override
	public void destroy() {}

}
