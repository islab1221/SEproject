package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/adminMain")
public class adminMainServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/view/admin/adminMain.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        HttpSession session=request.getSession();
        session.setAttribute("session", request.getParameter("content"));
        System.out.println(request.getParameter("content"));
        request.setAttribute("content", request.getParameter("content"));
        request.getRequestDispatcher("/WEB-INF/view/admin/adminMain.jsp").forward(request,response);
    }
}