package tutorly.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorly.model.util.SampleDataUtil.getSampleAddressBook;
import static tutorly.model.util.SampleDataUtil.getSampleAttendanceRecords;
import static tutorly.model.util.SampleDataUtil.getSamplePersons;
import static tutorly.model.util.SampleDataUtil.getSampleSessions;

import org.junit.jupiter.api.Test;

import tutorly.model.ReadOnlyAddressBook;

public class SampleDataUtilTest {
    @Test
    public void initializeSampleData_success() {
        ReadOnlyAddressBook ab = getSampleAddressBook();
        assertEquals(ab.getPersonList().size(), getSamplePersons().length);
        assertEquals(ab.getSessionList().size(), getSampleSessions().length);
        assertEquals(ab.getAttendanceRecordsList().size(), getSampleAttendanceRecords().length);
    }
}
