package filter;

import DAO.IUserDao;
import DAO.UserDao;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(filterName = "UserNamePasswordFilter")
public class UserNamePasswordFilter implements Filter{
    IUserDao userDao =new UserDao();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String userName = null;
        String password = null;
        for (Cookie cookie :req.getCookies()){
            if (cookie.getName().equalsIgnoreCase("userName")){
                userName = cookie.getValue();
            }
            if (cookie.getName().equalsIgnoreCase("password")){
                password = cookie.getValue();
            }
        }
        if (userDao.login(userName,password)!=null){
            chain.doFilter(req,resp);
            return;
        }else {
            resp.sendRedirect("/product");
        }
    }
}
