package tutorly.model.attendancerecord;

import static java.util.Objects.requireNonNull;

import tutorly.model.uniquelist.UniqueList;
import tutorly.model.uniquelist.exceptions.ElementNotFoundException;

/**
 * A list of attendance records that enforces uniqueness between its elements and does not allow nulls.
 */
public class UniqueAttendanceRecordList extends UniqueList<AttendanceRecord> {

    @Override
    protected boolean isDistinct(AttendanceRecord a, AttendanceRecord b) {
        return !a.isSameRecord(b);
    }

    @Override
    public void remove(AttendanceRecord toRemove) {
        requireNonNull(toRemove);
        boolean hasRemoved = this.internalList.removeIf(record -> record.isSameRecord(toRemove));
        if (!hasRemoved) {
            throw new ElementNotFoundException();
        }
    }
}
