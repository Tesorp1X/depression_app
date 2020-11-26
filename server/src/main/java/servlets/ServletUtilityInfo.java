package servlets;

import dbService.DBService;
import dbService.dataSets.AdminDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ServletUtilityInfo extends HttpServlet {

    private final DBService dbService;

    public ServletUtilityInfo(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String whom = request.getParameter("whom");

        if (action.equals("get")) {
            if (whom.equals("admins")) {
                List<AdminDataSet> listOfAdmins = dbService.getListOfAdmins();
                response.setStatus(HttpServletResponse.SC_OK);

            }
        }

    }
}
