package servlet;

import dbconn.DbConn;
import dbconn.FhirClient;
import model.Account;
import org.hl7.fhir.r4.model.Patient;

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

@WebServlet("/accountManagePatient")
public class accountManagePatientServlet extends HttpServlet{
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
        String accountSearchPatient = request.getParameter("reportId");
        if(accountSearchPatient == null){
            accountSearchPatient = "";
        }
        if(!accountSearchPatient.equals("")){
            searchPatient(request,response,accountSearchPatient);
        }else{
            String sql = "SELECT * FROM account WHERE permission = 'patient'";
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            Account account= null;
            Vector v = new Vector();
            Vector birthday = new Vector();
            Vector id_account = new Vector();
            try {
                preparedStatement = pcon.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    account = (Account) session.getAttribute(resultSet.getString("id_account"));
                    int fhirid = resultSet.getInt("FHIRid");
                    Patient patient = client.getPatient(fhirid);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(patient.getBirthDate());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    v.addElement(patient);
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

            request.getRequestDispatcher("/WEB-INF/view/admin/accountManagePatient.jsp").forward(request, response);
            System.out.println("GET");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        System.out.println("POST");
        String accountDeletePatient= request.getParameter("deleteId");
        if(accountDeletePatient == null){
            accountDeletePatient = "";
        }
        if(!accountDeletePatient.equals("")){
            String sql = "DELETE FROM account WHERE id_account = ?";
            PreparedStatement preparedStatement = null;
            try{
                preparedStatement = pcon.prepareStatement(sql);
                preparedStatement.setString(1, accountDeletePatient);
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
            String accountPatient= request.getParameter("accountPatient");
            String namePatient = request.getParameter("namePatient");
            String sexualPatient = request.getParameter("sexualPatient");
            String addressPatient = request.getParameter("addressPatient");
            String[] birthdayPatient = request.getParameter("birthdayPatient").split("-");
            int[] birthday = {0,0,0};
            for(int i=0;i<3;i++){
                birthday[i]=Integer.parseInt(birthdayPatient[i]);
            }
            String idPatient = request.getParameter("idPatient");
            String priorityPatient = request.getParameter("priorityPatient");
            String FHIRidURL = client.createPatient(namePatient, addressPatient, idPatient, birthday, sexualPatient);
            String[] FHIRid = FHIRidURL.split("/");
            String sql = "INSERT INTO account (id_account, password, permission, FHIRid) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = null;
            int i = 1;
            try {
                preparedStatement = pcon.prepareStatement(sql);
                preparedStatement.setString(i++, accountPatient);
                preparedStatement.setString(i++, idPatient);
                preparedStatement.setString(i++, priorityPatient);
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
        //request.getRequestDispatcher("/WEB-INF/view/admin/accountManagePatient.jsp").forward(request,response);
    }

    protected void searchPatient(HttpServletRequest request, HttpServletResponse response,String accountSearchPatient)throws IOException, ServletException{
        HttpSession session = request.getSession();
        String sql = "SELECT * FROM account WHERE permission = 'patient' AND id_account = '" +  accountSearchPatient +"'";
        System.out.println(accountSearchPatient);
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
                account = (Account) session.getAttribute(accountSearchPatient);
                int fhirid = resultSet.getInt("FHIRid");
                Patient patient = client.getPatient(fhirid);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(patient.getBirthDate());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                v.addElement(patient);
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
        request.getRequestDispatcher("/WEB-INF/view/admin/accountManagePatient.jsp").forward(request, response);
    }
}