package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import noteService.NoteService;

/**
 * @author KyMaKa
 * URL: /AddNote
 */
public class ServletAddNote extends HttpServlet {
    private NoteService noteService;

    public ServletAddNote(NoteService noteService) {
        this.noteService = noteService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=utf-8");

        String name;
        String description; 
        int value;
        long user_id;
        
        name = request.getParameter("name");
        description = request.getParameter("description");
        value = Integer.parseInt(request.getParameter("value"));
        user_id = Long.parseLong(request.getParameter("user_id"));

        long status = noteService.addNote(name, description, value, user_id);

        if (status == -1) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().println("Can't add new note.");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("New note successfully added!");
        }
    }
    
}
