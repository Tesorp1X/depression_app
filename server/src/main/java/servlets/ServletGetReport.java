package servlets;

import dbService.NoSuchUserException;
import noteService.NoteService;
import noteService.ReportClass;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

        if (!user_id.matches("\\d+")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain;charset=utf-8");
            response.getWriter().println("Bad request! Invalid user_id.");
        }

        try {
            ReportClass report = noteService.getReport(Long.parseLong(user_id), start_date, end_date);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/plain;charset=utf-8");

            PrintWriter writer = response.getWriter();
            writer.println("Expense notes for period from " + start_date + " until " + end_date + " are:");
            for (var note : report.getNotesUsedInReport()) {
                writer.println(note.toString());
            }
            writer.println("Total of : " + report.getOverall_sum() + "RUR.");
            writer.close();


        } catch (NoSuchUserException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain;charset=utf-8");
            response.getWriter().println("Bad request! No such user with id " + user_id + ".");

        } catch (IllegalArgumentException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain;charset=utf-8");
            response.getWriter().println("ERROR: Date must be in YYYY-MM-DD format!");
        }

    }
}
