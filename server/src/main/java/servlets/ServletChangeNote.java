package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbService.NoSuchNoteException;
import noteService.NoteService;

/**
 * @author KyMaKa
 * URL: /ChangeNote
 * Param: note_id & new Name & new Description & new Value
 */
public class ServletChangeNote extends HttpServlet {

    private final NoteService noteService;

    public ServletChangeNote(NoteService noteService) {
        this.noteService = noteService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long note_id = Long.parseLong(request.getParameter("note_id"));
        String newName = request.getParameter("new_name");
        newName = (newName != null) ? newName.replace("\"", "") : null;
        String newDescription = request.getParameter("new_description");
        int newValue = Integer.parseInt(request.getParameter("new_value"));

        response.setContentType("text/plain;charset=utf-8");

        try {
            noteService.changeNote(note_id, newName, newDescription, newValue);
            response.setStatus(HttpServletResponse.SC_OK);
		} catch (NoSuchNoteException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Can't change note: " + e.getMessage());
		}
    }
    
}
