package servlet;

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
@WebServlet("/doctorReport")
public class doctorReportServlet extends HttpServlet{
    FhirClient fhirClient;
    @Override
    public void init() throws ServletException {
        fhirClient = new FhirClient();
        super.init();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/view/doctor/doctorReport.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        int FHIRid = account.getFHIRid();
        Practitioner practitioner = fhirClient.getPractitioner(FHIRid);

        String name = request.getParameter("chinese_name");
        String patientId = request.getParameter("patientId");
        String diseaseName = request.getParameter("diseaseName");
        String medicalId = request.getParameter("medicalId");
        String medicalName = request.getParameter("medicalName");
        String medicalDay = request.getParameter("medicalDay");
        String medicalAmount = request.getParameter("medicalAmount");
        String doctorTalk;
        if(request.getParameter("doctorTalk").equals(""))
            doctorTalk = "無意見";
        else
            doctorTalk = request.getParameter("doctorTalk");

        fhirClient.createMedicationStatement(FHIRid,name,practitioner.getQualificationFirstRep().getIdentifier().get(0).getValue(),diseaseName,medicalName,medicalId,medicalDay,medicalAmount,patientId,doctorTalk);
        System.out.println("幹你老師");
        request.getRequestDispatcher("/WEB-INF/view/doctor/doctorReport.jsp").forward(request, response);
    }
}
