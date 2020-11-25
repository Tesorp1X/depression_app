package noteService;

import java.sql.Date;
import java.util.List;

/**
 * Now computes only overall sum for that period, but we can add categories in da future.
 * @author Tesorp1X
 */
public class ReportClass {

    private int overall_sum = 0;

    private final Date start_date;
    private final Date end_date;
    private final List<Note> notesUsedInReport;

    public ReportClass(Date start_date, Date end_date, List<Note> notesUsedInReport) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.notesUsedInReport = notesUsedInReport;
        prepareReport();
    }

    private void prepareReport() {

        for (var note : notesUsedInReport) {
            overall_sum += note.getValue();
            //there you can add category
        }
    }

    //Getters
    public int getOverall_sum() {
        return overall_sum;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public List<Note> getNotesUsedInReport() {
        return notesUsedInReport;
    }


}
