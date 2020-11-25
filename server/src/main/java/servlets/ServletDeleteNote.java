package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        response.setContentType("text/plain;charset=utf-8");

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
			} catch (NoSuchUserException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println("Can't delete note: " + e.getMessage());
			}
        } else {
            note_id = Long.parseLong(request.getParameter("note_id"));
            deleted = noteService.deleteNote(note_id);
        }

        if (deleted) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Note with id: " + note_id + " deleted!");
        } else {
            if (response.getStatus() == HttpServletResponse.SC_OK) {
                PrintWriter writer = response.getWriter();
                for (var note : notes) {
                    writer.println(note.toString());
                }
                writer.close();
            }
        }
    }
    
}
