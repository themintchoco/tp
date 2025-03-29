package tutorly.model.attendancerecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.Assert.assertThrows;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.ALICE_ATTEND_ENGLISH;
import static tutorly.testutil.TypicalAddressBook.BENSON_ATTEND_MATH;
import static tutorly.testutil.TypicalAddressBook.ENGLISH_SESSION;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tutorly.model.uniquelist.exceptions.DuplicateElementException;
import tutorly.model.uniquelist.exceptions.ElementNotFoundException;
import tutorly.testutil.AttendanceRecordBuilder;

public class UniqueAttendanceRecordListTest {

    private final UniqueAttendanceRecordList uniqueAttendanceRecordList = new UniqueAttendanceRecordList();

    @Test
    public void contains_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceRecordList.contains(null));
    }

    @Test
    public void contains_recordNotInList_returnsFalse() {
        assertFalse(uniqueAttendanceRecordList.contains(ALICE_ATTEND_ENGLISH));
    }

    @Test
    public void contains_recordInList_returnsTrue() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        assertTrue(uniqueAttendanceRecordList.contains(ALICE_ATTEND_ENGLISH));
    }

    @Test
    public void contains_equivalentRecordInList_returnsTrue() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        AttendanceRecord editedRecord = new AttendanceRecordBuilder(ALICE_ATTEND_ENGLISH).withIsPresent(false).build();
        assertTrue(uniqueAttendanceRecordList.contains(editedRecord));
    }

    @Test
    public void add_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceRecordList.add(null));
    }

    @Test
    public void add_duplicateRecord_throwsDuplicateElementException() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        assertThrows(DuplicateElementException.class, () -> uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH));
    }

    @Test
    public void set_nullTargetRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceRecordList.set(null, ALICE_ATTEND_ENGLISH));
    }

    @Test
    public void set_nullEditedRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceRecordList.set(ALICE_ATTEND_ENGLISH, null));
    }

    @Test
    public void set_targetRecordNotInList_throwsElementNotFoundException() {
        assertThrows(ElementNotFoundException.class, () ->
                uniqueAttendanceRecordList.set(ALICE_ATTEND_ENGLISH, ALICE_ATTEND_ENGLISH));
    }

    @Test
    public void set_editedRecordIsSameRecord_success() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        uniqueAttendanceRecordList.set(ALICE_ATTEND_ENGLISH, ALICE_ATTEND_ENGLISH);
        UniqueAttendanceRecordList expectedUniqueAttendanceRecordList = new UniqueAttendanceRecordList();
        expectedUniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        assertEquals(expectedUniqueAttendanceRecordList, uniqueAttendanceRecordList);
    }

    @Test
    public void set_editedRecordIsEquivalent_success() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        AttendanceRecord editedRecord = new AttendanceRecordBuilder(ALICE_ATTEND_ENGLISH).withIsPresent(false).build();
        uniqueAttendanceRecordList.set(ALICE_ATTEND_ENGLISH, editedRecord);
        UniqueAttendanceRecordList expectedAttendanceRecordList = new UniqueAttendanceRecordList();
        expectedAttendanceRecordList.add(editedRecord);
        assertEquals(expectedAttendanceRecordList, uniqueAttendanceRecordList);
    }

    @Test
    public void set_editedRecordIsNotEquivalent_success() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        uniqueAttendanceRecordList.set(ALICE_ATTEND_ENGLISH, BENSON_ATTEND_MATH);
        UniqueAttendanceRecordList expectedAttendanceRecordList = new UniqueAttendanceRecordList();
        expectedAttendanceRecordList.add(BENSON_ATTEND_MATH);
        assertEquals(expectedAttendanceRecordList, uniqueAttendanceRecordList);
    }

    @Test
    public void set_editedRecordIsNotUnique_throwsDuplicateElementException() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        uniqueAttendanceRecordList.add(BENSON_ATTEND_MATH);
        assertThrows(DuplicateElementException.class, () ->
                uniqueAttendanceRecordList.set(ALICE_ATTEND_ENGLISH, BENSON_ATTEND_MATH));
    }

    @Test
    public void remove_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceRecordList.remove(null));
    }

    @Test
    public void remove_recordDoesNotExist_throwsElementNotFoundException() {
        assertThrows(ElementNotFoundException.class, () -> uniqueAttendanceRecordList.remove(ALICE_ATTEND_ENGLISH));
    }

    @Test
    public void remove_existingRecord_removesRecord() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        uniqueAttendanceRecordList.remove(ALICE_ATTEND_ENGLISH);
        UniqueAttendanceRecordList expectedAttendanceRecordList = new UniqueAttendanceRecordList();
        assertEquals(expectedAttendanceRecordList, uniqueAttendanceRecordList);

        // removes record with different isPresent field
        AttendanceRecord aliceAttendEngDiffPresent = new AttendanceRecordBuilder()
                .withPerson(ALICE)
                .withSession(ENGLISH_SESSION)
                .withIsPresent(!ALICE_ATTEND_ENGLISH.getAttendance())
                .build();

        uniqueAttendanceRecordList.add(aliceAttendEngDiffPresent);
        uniqueAttendanceRecordList.remove(ALICE_ATTEND_ENGLISH);
        assertEquals(expectedAttendanceRecordList, uniqueAttendanceRecordList);
    }

    @Test
    public void setAll_nullAttendanceRecordList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAttendanceRecordList.setAll((UniqueAttendanceRecordList) null));
    }

    @Test
    public void setAll_uniqueAttendanceRecordList_replacesOwnListWithProvidedUniqueAttendanceRecordList() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        UniqueAttendanceRecordList expectedAttendanceRecordList = new UniqueAttendanceRecordList();
        expectedAttendanceRecordList.add(BENSON_ATTEND_MATH);
        uniqueAttendanceRecordList.setAll(expectedAttendanceRecordList);
        assertEquals(expectedAttendanceRecordList, uniqueAttendanceRecordList);
    }

    @Test
    public void setAll_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAttendanceRecordList.setAll((List<AttendanceRecord>) null));
    }

    @Test
    public void setAll_list_replacesOwnListWithProvidedList() {
        uniqueAttendanceRecordList.add(ALICE_ATTEND_ENGLISH);
        List<AttendanceRecord> attendanceRecordList = Collections.singletonList(BENSON_ATTEND_MATH);
        uniqueAttendanceRecordList.setAll(attendanceRecordList);
        UniqueAttendanceRecordList expectedAttendanceRecordList = new UniqueAttendanceRecordList();
        expectedAttendanceRecordList.add(BENSON_ATTEND_MATH);
        assertEquals(expectedAttendanceRecordList, uniqueAttendanceRecordList);
    }

    @Test
    public void setAll_listWithDuplicateRecords_throwsDuplicateElementException() {
        List<AttendanceRecord> listWithDuplicateRecords = Arrays.asList(ALICE_ATTEND_ENGLISH, ALICE_ATTEND_ENGLISH);
        assertThrows(DuplicateElementException.class, () ->
                uniqueAttendanceRecordList.setAll(listWithDuplicateRecords));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueAttendanceRecordList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueAttendanceRecordList.asUnmodifiableObservableList().toString(),
                uniqueAttendanceRecordList.toString());
    }
}
