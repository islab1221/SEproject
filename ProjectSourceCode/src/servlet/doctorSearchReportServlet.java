package servlet;

import dbconn.FhirClient;
import org.hl7.fhir.r4.model.Bundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/doctorSearchReport")
public class doctorSearchReportServlet extends HttpServlet{
    FhirClient fhirClient;
    @Override
    public void init() throws ServletException {
        fhirClient = new FhirClient();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/view/doctor/doctorSearchReport.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        Bundle medicationStatement;

        try {
            if(!id.equals(""))
                medicationStatement = fhirClient.searchSpecifiedMedicationStatement(id);
            else
                medicationStatement = new Bundle();
        }catch (ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException e){
            medicationStatement = new Bundle();
        }


        request.setAttribute("bundle", medicationStatement);

        request.getRequestDispatcher("/WEB-INF/view/doctor/doctorSearchReport.jsp").forward(request, response);
    }
}
