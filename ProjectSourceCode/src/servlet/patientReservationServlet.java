package servlet;

import dao.impl.FHIRDaoImpl;
import dbconn.FhirClient;
import model.Account;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Practitioner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/patientReservation")
public class patientReservationServlet extends HttpServlet {
    FhirClient fhirClient;
    @Override
    public void init() throws ServletException {
        fhirClient = new FhirClient();
        super.init();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        Bundle bundle = fhirClient.getServiceRequest(account.getFHIRid());

        List<Practitioner> NTindoc = new ArrayList<>();
        List<Practitioner> NTpdoc = new ArrayList<>();
        List<Practitioner> Kindoc = new ArrayList<>();
        List<Practitioner> Kpdoc = new ArrayList<>();

        List<Integer> FHIRid = null;
        try {
            FHIRid = new FHIRDaoImpl().getFHIRidList("doctor");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Practitioner practitioner;
        for (int id:FHIRid) {
            practitioner = fhirClient.getPractitioner(id);
            switch (practitioner.getQualificationFirstRep().getIdentifierFirstRep().getValue()){
                case "臺大醫院":
                    if(practitioner.getQualificationFirstRep().getCode().getText().equals("泌尿科"))
                        NTpdoc.add(practitioner);
                    else
                        NTindoc.add(practitioner);
                    break;
                case "高雄榮總":
                    if(practitioner.getQualificationFirstRep().getCode().getText().equals("泌尿科"))
                        Kpdoc.add(practitioner);
                    else
                        Kindoc.add(practitioner);
                    break;
                default:
                    break;
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        request.setAttribute("format", simpleDateFormat);
        request.setAttribute("fhirid",FHIRid);
        request.setAttribute("NTindoc",NTindoc);
        request.setAttribute("NTpdoc",NTpdoc);
        request.setAttribute("Kindoc",Kindoc);
        request.setAttribute("Kpdoc",Kpdoc);
        request.setAttribute("bundle", bundle);



        request.getRequestDispatcher("/WEB-INF/view/patient/patientReservation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");
        int FHIRid = account.getFHIRid();

        String name = request.getParameter("chinese_name");
        String id = request.getParameter("id");
        String date[] = request.getParameter("date").split("-");
        String hospital = request.getParameter("hospital");
        String department = request.getParameter("department");

        String temp[] = request.getParameter("doctor").split("/");

        fhirClient.createServiceRequest(Integer.parseInt(temp[1]),FHIRid,date,department,hospital);



        this.doGet(request, response);


    }
}
