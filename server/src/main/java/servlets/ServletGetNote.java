package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import dbService.NoSuchNoteException;

import javax.servlet.http.HttpServletRequest;

import noteService.NoteService;

public class ServletGetNote extends HttpServlet {

    private NoteService noteService;

    public ServletGetNote(NoteService noteService) {
        this.noteService = noteService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=utf-8");

        String name;
        long user_id;
        long note_id;

        name = request.getParameter("name");
        if (name == null) {
            note_id = Long.getLong(request.getParameter("note_id"));

            try {
                noteService.getNoteById(note_id);
                response.setStatus(HttpServletResponse.SC_OK);
			} catch (NoSuchNoteException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println("Can't find note. " + e.getMessage());
			}
        } else {
            user_id = Long.getLong(request.getParameter("user_id"));
            //TODO: get list of multiple notes with same name.
        }
    }
    
}
