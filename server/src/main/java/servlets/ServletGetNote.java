package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dbService.NoSuchNoteException;
import dbService.NoSuchUserException;
import dbService.dataSets.NoteDataSet;

import javax.servlet.http.HttpServletRequest;

import noteService.Note;
import noteService.NoteService;

/**
 * @author KyMaKa
 * URL: /GetNote
 * Param: name & user_id || note_id
 */
public class ServletGetNote extends HttpServlet {

    private final NoteService noteService;

    public ServletGetNote(NoteService noteService) {
        this.noteService = noteService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> getNoteMap = new HashMap<>();
        String name;
        long user_id;
        long note_id;
        Note note = null;
        List<Note> notes = null;

        name = request.getParameter("name");
        name = (name != null) ? name.replace("\"", "") : null;
        if (name == null) {
            note_id = Long.getLong(request.getParameter("note_id"));

            try {
                note = noteService.getNoteById(note_id);
                response.setStatus(HttpServletResponse.SC_OK);
                getNoteMap.put("ERROR_STATUS", "OK");
			} catch (NoSuchNoteException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				getNoteMap.put("ERROR_STATUS", "Can't find note. " + e.getMessage());
			}
        } else {
            user_id = Long.getLong(request.getParameter("user_id"));
            try {
                notes = noteService.getAllUserNotes(user_id, name);
                response.setStatus(HttpServletResponse.SC_OK);
                getNoteMap.put("ERROR_STATUS", "OK");
			} catch (NoSuchUserException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				getNoteMap.put("ERROR_STATUS", "Can't get note: " + e.getMessage());
			}
        }


        if (response.getStatus() == HttpServletResponse.SC_OK && notes == null) {
            assert note != null;
            getNoteMap.put("note", note.toString());
        } else {
            if (notes != null) {
                getNoteMap.put("notes", notes);
            } else {
                getNoteMap.put("ERROR_STATUS", "Can't find note with such parameters");
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getNoteMap);

        PrintWriter writer = response.getWriter();
        writer.print(json);
    }
    
}
