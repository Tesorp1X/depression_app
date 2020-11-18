package noteService;

import java.util.Date;

public class Note {
    
    private int value;
    private String name;
    private String description;
    private long user_id;
    private long note_id;
    private Date date;

    public Note(int value, String name, String description, long user_id, long note_id, Date date) {
        this.value = value;
        this.name = name;
        this.description = description;
        this.user_id = user_id;
        this.note_id = note_id;
        this.date = date;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public long getUser_id() {
        return this.user_id;
    }

    public long getNote_id() {
        return this.note_id;
    }
    
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date newDate) {
        this.date = newDate;
    }

    public void setNote_id(long newId) {
        this.note_id = newId;
    }

    public void setUser_id(long newId) {
        this.user_id = newId;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    @Override
    public String toString() {
        return "Expense Note [id = " + note_id
                + " name = " + name
                + " value = " + value
                + " date = " + date.toString()
                + " description = " + description
                + " user_id = " + user_id + "]";
    }

}
