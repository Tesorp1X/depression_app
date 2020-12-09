package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbService.NoSuchUserException;
import noteService.NoteService;
import noteService.ReportClass;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tesorp1X
 * Url: /GetReport
 * Param: < user_id  & start_date > [& end_date ]
 * Date formats supported: { yyyy-MM-dd; yyyy.MM.dd; dd-MM-yyyy; dd.MM.yyyy }.
 */
public class ServletGetReport extends HttpServlet {

    private final NoteService noteService;

    public ServletGetReport(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user_id = request.getParameter("user_id");
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");

        Map<String, Object> reportDataMap = new HashMap<>();

        if (!user_id.matches("\\d+")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            reportDataMap.put("ERROR_STATUS", "Invalid user_id.");
        }

        if (start_date != null && end_date == null) {
            //TODO: change to and of the month maybe?
            end_date = start_date;
        }

        try {
            if (start_date == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                reportDataMap.put("ERROR_STATUS", "Date must be in YYYY-MM-DD format!");
            } else {
                ReportClass report = noteService.getReport(Long.parseLong(user_id), start_date, end_date);
                response.setStatus(HttpServletResponse.SC_OK);
                reportDataMap.put("ERROR_STATUS", "OK.");
                reportDataMap.put("report", report);
            }

        } catch (NoSuchUserException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            reportDataMap.put("ERROR_STATUS", "No such user.");

        } catch (IllegalArgumentException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            reportDataMap.put("ERROR_STATUS", e.toString());
        }

        //Warping data into JSON.
        ObjectMapper objectMapper = new ObjectMapper();
        String json_string = objectMapper.writeValueAsString(reportDataMap);

        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = response.getWriter();
        out.print(json_string);
        out.flush();

    }
}
