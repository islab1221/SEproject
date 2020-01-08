package servlet;


import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import model.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class loginServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        HttpSession session = request.getSession();

        String inputID = request.getParameter("inputID");
        //String inputPassword = DigestUtils.sha512Hex(request.getParameter("inputPassword"));  //有加密過喔
        String inputPassword = request.getParameter("inputPassword");
        AccountDao accountDao = new AccountDaoImpl();
        try{
            Account account = accountDao.getAccountByRegNumber(inputID);
            if(account == null || !account.isExist()){
                System.out.println("帳號不存在!!!!!!!!!!!!!!!!!");
                request.setAttribute("error", "帳號不存在");
                request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
                return;
            }
            else if(account.authenticate(inputPassword)){
                session.setAttribute("account", account);
                //int roleCode = account.getRoleCode();
                String roleCode = account.getPermission();
                switch(roleCode) {
                    case "manager":
                        request.getRequestDispatcher("/WEB-INF/view/admin/adminMain.jsp").forward(request, response);
                        break;
                    case "doctor":
                        request.getRequestDispatcher("/WEB-INF/view/doctor/doctorMain.jsp").forward(request, response);
                        break;
                    case "patient":
                        request.getRequestDispatcher("/WEB-INF/view/patient/patientMain.jsp").forward(request, response);
                        break;
                }
                return;
            }
        }catch (SQLException e){
            System.out.println("資料庫錯誤，無法關閉連線");
            request.setAttribute("error", "資料庫錯誤，無法關閉連線");
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("login");
    }
}

