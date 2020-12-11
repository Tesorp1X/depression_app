package servlets;

import dbService.NoSuchUserException;
import noteService.Note;
import noteService.NoteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Tesrp1X
 * Now returns only list of all notes. URL params only user_id.
 * Url : /GetListOfNotes.
 * url_params : user_id ; name ; start_date = YYYY-MM-DD ; end_date = YYYY-MM-DD.
 */
public class ServletGetListOfNotes extends HttpServlet {
    //TODO: add start_date and end_date option.
    private final NoteService noteService;

    public ServletGetListOfNotes(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Map<String, Object> listMap = new HashMap<>();
        String user_id = request.getParameter("user_id");
        String note_name = request.getParameter("name");
        note_name = (note_name != null) ? note_name.replace("\"", "") : null;
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");

        response.setContentType("application/json;charset=utf-8");


        if (!user_id.matches("\\d+")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            listMap.put("ERROR_STATUS", "Invalid user_id.");
        }

        try {

            List<Note> notes;

            if (start_date == null || end_date == null) {
                notes = noteService.getAllUserNotes(Long.parseLong(user_id), note_name);
            } else {
                notes = noteService.getAllUserNotesInTimePeriod(Long.parseLong(user_id), start_date, end_date);
            }
            //TODO: warp into json
            //TODO: Handle empty lists(no notes for user or no notes with given name)
            if (notes.isEmpty() || notes == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                listMap.put("ERROR_STATUS", "This user don't have any notes yet.");
            } else {
                response.setStatus(HttpServletResponse.SC_OK);

                listMap.put("ERROR_STATUS", "OK");
                listMap.put("notes", notes);
            }

        } catch (NoSuchUserException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            listMap.put("ERROR_STATUS", "No such user with id " + user_id + ".");

        } catch (IllegalArgumentException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            listMap.put("ERROR_STATUS", "Date must be in YYYY-MM-DD format!");
        }

        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(listMap);
            
        writer.print(json);
        writer.flush();

    }
}
