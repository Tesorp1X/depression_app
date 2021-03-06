package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;

import noteService.Note;
import noteService.NoteService;
import dbService.NoSuchUserException;
import dbService.dataSets.NoteDataSet;

/**
 * @author KyMaKa
 * Url: /DeleteNote
 * Param: name & user_id || note_id
 */
public class ServletDeleteNote extends HttpServlet {

    private final NoteService noteService;

    public ServletDeleteNote(NoteService noteService) {
        this.noteService = noteService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> deleteMap = new HashMap<>();
        long note_id = -1;
        long user_id;
        String name;
        boolean deleted = false;
        List<Note> notes = null;

        name = (request.getParameter("name"));

        if (name != null) {
            //name = request.getParameter("name");
            name = name.replace("\"", "");
            user_id = Long.parseLong(request.getParameter("user_id"));
            try {
                notes = noteService.getAllUserNotes(user_id, name);
                response.setStatus(HttpServletResponse.SC_OK);
                deleteMap.put("ERROR_STATUS", "OK");
			} catch (NoSuchUserException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				deleteMap.put("ERROR_STATUS", "Can't delete note: " + e.getMessage());
			}
        } else {
            note_id = Long.parseLong(request.getParameter("note_id"));
            deleted = noteService.deleteNote(note_id);
        }

        if (notes != null && notes.size() == 1) {
            note_id = notes.get(0).getNote_id();
            deleted = noteService.deleteNote(note_id);
        }

        if (deleted) {
            response.setStatus(HttpServletResponse.SC_OK);
            deleteMap.put("ERROR_STATUS", "OK");
        } else {
            if (notes != null && response.getStatus() == HttpServletResponse.SC_OK) {
                deleteMap.put("notes", notes);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                deleteMap.put("ERROR_STATUS", "No such note.");
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(deleteMap);

        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
    }
    
}
