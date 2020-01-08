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
import java.text.SimpleDateFormat;

@WebServlet("/doctorReservation")
public class doctorReservationServlet extends HttpServlet{
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

        Bundle bundle = fhirClient.getServiceRequestByDoctor(account.getFHIRid());
        request.setAttribute("bundle", bundle);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        request.setAttribute("format", simpleDateFormat);


        request.getRequestDispatcher("/WEB-INF/view/doctor/doctorReservation.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("session", request.getParameter("content"));
        System.out.println(request.getParameter("content"));
        request.setAttribute("content", request.getParameter("content"));
        request.getRequestDispatcher("/WEB-INF/view/doctor/doctorReservation.jsp").forward(request, response);
    }
}
