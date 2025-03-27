package tutorly.ui;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import tutorly.commons.util.ObservableListUtil;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;
import tutorly.model.session.Session;

/**
 * Panel containing the list of attendance records.
 */
public class AttendanceRecordListPanel extends ListPanel<AttendanceRecord> {

    private final ObservableList<Person> students;

    /**
     * Creates a {@code AttendanceRecordListPanel} with the given records, students, and selected sessions.
     */
    public AttendanceRecordListPanel(ObservableList<AttendanceRecord> records,
            ObservableList<Person> students, ObservableList<Session> sessions) {
        super(ObservableListUtil.filteredList(records,
                record -> sessions.stream().anyMatch(session -> session.getId() == record.getSessionId())
                        && students.stream().anyMatch(student -> student.getId() == record.getStudentId()),
                List.of(students, sessions)));

        this.students = students;
    }

    @Override
    protected UiPart<Region> getItemGraphic(AttendanceRecord record) {
        Optional<Person> recordStudent = students.filtered(student -> student.getId() == record.getStudentId())
                .stream().findFirst();
        assert recordStudent.isPresent();

        return new AttendanceRecordCard(record, recordStudent.get());
    };

}
