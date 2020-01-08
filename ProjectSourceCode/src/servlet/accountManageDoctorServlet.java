package servlet;

import dbconn.DbConn;
import dbconn.FhirClient;
import model.Account;
import org.hl7.fhir.r4.model.Practitioner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

@WebServlet("/accountManageDoctor")
public class accountManageDoctorServlet extends HttpServlet {
    private FhirClient client;
    private final Connection pcon = DbConn.getConnection(DbConn.MEDICALRECORD);
    @Override
    public void init() throws ServletException {
        client = new FhirClient();
        super.init();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String accountSearchDoctor = request.getParameter("reportId");
        if(accountSearchDoctor == null){
            accountSearchDoctor = "";
        }
        if(!accountSearchDoctor.equals("")){
            searchDoctor(request,response,accountSearchDoctor);
        }else{
            String sql = "SELECT * FROM account WHERE permission = 'doctor'";
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            Account account = null;
            Vector v = new Vector();
            Vector birthday = new Vector();
            Vector id_account = new Vector();
            try {
                preparedStatement = pcon.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    account = (Account) session.getAttribute(resultSet.getString("id_account"));
                    int fhirid = resultSet.getInt("FHIRid");
                    Practitioner practitioner = client.getPractitioner(fhirid);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(practitioner.getBirthDate());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    v.addElement(practitioner);
                    birthday.addElement(simpleDateFormat.format(calendar.getTime()));
                    id_account.addElement(resultSet.getString("id_account"));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            request.setAttribute("v", v);
            request.setAttribute("birthday",birthday );
            request.setAttribute("id_account",id_account );

            request.getRequestDispatcher("/WEB-INF/view/admin/accountManageDoctor.jsp").forward(request, response);
            System.out.println("GET");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        System.out.println("POST");
        String accountDeleteDoctor= request.getParameter("deleteId");
        if(accountDeleteDoctor == null){
            accountDeleteDoctor = "";
        }
        if(!accountDeleteDoctor.equals("")){
            String sql = "DELETE FROM account WHERE id_account = ?";
            PreparedStatement preparedStatement = null;
            try{
                preparedStatement = pcon.prepareStatement(sql);
                preparedStatement.setString(1, accountDeleteDoctor);
                preparedStatement.execute();
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            String accountDoctor= request.getParameter("accountDoctor");
            String nameDoctor = request.getParameter("nameDoctor");
            String sexualDoctor = request.getParameter("sexualDoctor");
            String addressDoctor = request.getParameter("addressDoctor");
            String[] birthdayDoctor = request.getParameter("birthdayDoctor").split("-");
            int[] birthday = {0,0,0};
            for(int i=0;i<3;i++){
                birthday[i]=Integer.parseInt(birthdayDoctor[i]);
            }
            String idDoctor = request.getParameter("idDoctor");
            String hospital = request.getParameter("hospital");
            String department = request.getParameter("department");
            String priorityDoctor = request.getParameter("priorityDoctor");
            String FHIRidURL = client.createDoctor(nameDoctor, addressDoctor, idDoctor, birthday, sexualDoctor, hospital, department);
            String[] FHIRid = FHIRidURL.split("/");
            String sql = "INSERT INTO account (id_account, password, permission , FHIRid) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = null;
            int i = 1;
            try {
                preparedStatement = pcon.prepareStatement(sql);
                preparedStatement.setString(i++, accountDoctor);
                preparedStatement.setString(i++, idDoctor);
                preparedStatement.setString(i++, priorityDoctor);
                preparedStatement.setInt(i++, Integer.parseInt(FHIRid[5]));
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        doGet(request,response);
        //request.getRequestDispatcher("/WEB-INF/view/admin/accountManageDoctor.jsp").forward(request,response);
    }

    protected void searchDoctor(HttpServletRequest request, HttpServletResponse response,String accountSearchDoctor)throws IOException, ServletException{
        HttpSession session = request.getSession();
        String sql = "SELECT * FROM account WHERE permission = 'doctor' AND id_account = '" +  accountSearchDoctor +"'";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = null;
        Vector v = new Vector();
        Vector birthday = new Vector();
        Vector id_account = new Vector();
        try{
            preparedStatement = pcon.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account = (Account) session.getAttribute(accountSearchDoctor);
                int fhirid = resultSet.getInt("FHIRid");
                Practitioner practitioner = client.getPractitioner(fhirid);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(practitioner.getBirthDate());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                v.addElement(practitioner);
                birthday.addElement(simpleDateFormat.format(calendar.getTime()));
                id_account.addElement(resultSet.getString("id_account"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if(v.size()!=0){
            request.setAttribute("v", v);
            request.setAttribute("birthday",birthday );
            request.setAttribute("id_account",id_account );
        }
        request.getRequestDispatcher("/WEB-INF/view/admin/accountManageDoctor.jsp").forward(request, response);
    }
}