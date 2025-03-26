package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.Assert.assertThrows;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.MATH_SESSION;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tutorly.commons.core.GuiSettings;
import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.AddressBook;
import tutorly.model.Model;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.ReadOnlyUserPrefs;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.filter.Filter;
import tutorly.model.person.Name;
import tutorly.model.person.Person;
import tutorly.model.session.Session;
import tutorly.testutil.PersonBuilder;
import tutorly.testutil.SessionBuilder;

public class AssignStudentCommandTest {
    private final Person validPerson = new PersonBuilder().build();
    private final Session validSession = new SessionBuilder().build();

    @Test
    public void constructor_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignStudentCommand(null, validSession));
        assertThrows(NullPointerException.class, () -> new AssignStudentCommand(validPerson, null));
    }

    @Test
    public void execute_attendanceRecordAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAttendanceAdded modelStub = new ModelStubAcceptingAttendanceAdded();

        CommandResult commandResult = new AssignStudentCommand(validPerson, validSession).execute(modelStub);
        assertEquals(String.format(AssignStudentCommand.MESSAGE_SUCCESS, Messages.format(validPerson),
                Messages.format(validSession)), commandResult.getFeedbackToUser());

        AttendanceRecord expectedRecord = new AttendanceRecord(
                validPerson.getId(), validSession.getId(), AssignStudentCommand.DEFAULT_PRESENCE);
        assertEquals(Collections.singletonList(expectedRecord), modelStub.attendanceAdded);
    }

    @Test
    public void execute_duplicateAttendanceRecord_throwsCommandException() {
        AssignStudentCommand assignStudentCommand = new AssignStudentCommand(validPerson, validSession);
        ModelStub modelStub = new ModelStubWithAttendanceRecord(
                new AttendanceRecord(validPerson.getId(), validSession.getId(), AssignStudentCommand.DEFAULT_PRESENCE));

        assertThrows(CommandException.class,
                AssignStudentCommand.MESSAGE_DUPLICATE_ASSIGNMENT, () -> assignStudentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        Session firstSession = new SessionBuilder().withId(1).build();
        Session secondSession = new SessionBuilder().withId(2).build();
        AssignStudentCommand assignAliceFirstCommand = new AssignStudentCommand(alice, firstSession);
        AssignStudentCommand assignBobFirstCommand = new AssignStudentCommand(bob, firstSession);

        // same object -> returns true
        assertTrue(assignAliceFirstCommand.equals(assignAliceFirstCommand));

        // same values -> returns true
        AssignStudentCommand assignAliceFirstCommandCopy = new AssignStudentCommand(alice, firstSession);
        assertTrue(assignAliceFirstCommand.equals(assignAliceFirstCommandCopy));

        // different types -> returns false
        assertFalse(assignAliceFirstCommand.equals(1));

        // null -> returns false
        assertFalse(assignAliceFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(assignAliceFirstCommand.equals(assignBobFirstCommand));

        // different session -> returns false
        assertFalse(assignAliceFirstCommand.equals(new AssignStudentCommand(alice, secondSession)));
    }

    @Test
    public void toStringMethod() {
        AssignStudentCommand assignStudentCommand = new AssignStudentCommand(ALICE, MATH_SESSION);
        String expected = AssignStudentCommand.class.getCanonicalName()
                + "{person=" + ALICE + ", session=" + MATH_SESSION + "}";
        assertEquals(expected, assignStudentCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void restorePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Person> getPersonById(int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Person> getPersonByName(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Session> getFilteredSessionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getArchivedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Filter<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSessionList(Filter<Session> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSession(Session toCreate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSession(Session toCreate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAttendanceRecord(AttendanceRecord record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAttendanceRecord(AttendanceRecord record) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single attendance record.
     */
    private class ModelStubWithAttendanceRecord extends ModelStub {
        private final AttendanceRecord record;

        ModelStubWithAttendanceRecord(AttendanceRecord record) {
            requireNonNull(record);
            this.record = record;
        }

        @Override
        public boolean hasAttendanceRecord(AttendanceRecord record) {
            requireNonNull(record);
            return this.record.isSameRecord(record);
        }
    }

    /**
     * A Model stub that always accept the attendance record being added.
     */
    private class ModelStubAcceptingAttendanceAdded extends ModelStub {
        final ArrayList<AttendanceRecord> attendanceAdded = new ArrayList<>();

        @Override
        public boolean hasAttendanceRecord(AttendanceRecord record) {
            requireNonNull(record);
            return attendanceAdded.stream().anyMatch(record::isSameRecord);
        }

        @Override
        public void addAttendanceRecord(AttendanceRecord record) {
            requireNonNull(record);
            attendanceAdded.add(record);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
