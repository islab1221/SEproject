package servlet;

import dbconn.FhirClient;
import model.Account;
import org.hl7.fhir.r4.model.Bundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/doctorRecord")
public class doctorRecordServlet extends HttpServlet{
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

        Bundle bundle = fhirClient.getProcedureByDoctor(account.getFHIRid());

        if(bundle.getTotal() != 0)
            request.setAttribute("bundle", bundle);


        request.getRequestDispatcher("/WEB-INF/view/doctor/doctorRecord.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("session", request.getParameter("content"));
        System.out.println(request.getParameter("content"));
        request.setAttribute("content", request.getParameter("content"));
        request.getRequestDispatcher("/WEB-INF/view/doctor/doctorRecord.jsp").forward(request, response);
    }
}
