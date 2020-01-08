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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@WebServlet("/patientData")
public class patientDataServlet extends HttpServlet {
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
        Account account = (Account) session.getAttribute("account");
        int fhirid = account.getFHIRid();

        Patient patient = client.getPatient(fhirid);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(patient.getBirthDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        if(patient != null){

            request.setAttribute("i", patient);
            request.setAttribute("birthday", simpleDateFormat.format(calendar.getTime()));
        }
        request.getRequestDispatcher("/WEB-INF/view/patient/patientData.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("utf-8");

        Account account = (Account) session.getAttribute("account");
        int fhirid = account.getFHIRid();
        String new_password = request.getParameter("new_password");
        if(new_password == null){
            new_password = "";
        }
        if(!new_password.equals("")){
            String sql = "UPDATE account SET password = ? WHERE id_account = ?";
            PreparedStatement preparedStatement = null;
            int i = 1;
            try{
                preparedStatement = pcon.prepareStatement(sql);
                preparedStatement.setString(i++, new_password);
                preparedStatement.setString(i++, account.getId_account());
                preparedStatement.execute();
            } catch (SQLException e) {
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
            String name = request.getParameter("chinese_name");
            String sexual = request.getParameter("sexual");
            String address = request.getParameter("address");
            String[] birthday = request.getParameter("birthday").split("-");
            client.updatePatient(fhirid, name, sexual, address, birthday);
        }
        this.doGet(request, response);
    }
}
