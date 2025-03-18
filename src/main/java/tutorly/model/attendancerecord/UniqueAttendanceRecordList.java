package tutorly.model.attendancerecord;

import tutorly.model.uniquelist.UniqueList;

/**
 * A list of attendance records that enforces uniqueness between its elements and does not allow nulls.
 */
public class UniqueAttendanceRecordList extends UniqueList<AttendanceRecord> {

    @Override
    protected boolean isDistinct(AttendanceRecord a, AttendanceRecord b) {
        return !a.isSameRecord(b);
    }

}
