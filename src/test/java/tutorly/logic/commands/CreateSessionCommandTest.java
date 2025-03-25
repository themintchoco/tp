package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tutorly.commons.core.GuiSettings;
import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.ReadOnlyUserPrefs;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.filter.Filter;
import tutorly.model.person.Person;
import tutorly.model.session.Session;
import tutorly.model.session.UniqueSessionList;

/**
 * Test class for CreateSessionCommand.
 */
public class CreateSessionCommandTest {

    private ModelStub model;
    private Session session;
    private CreateSessionCommand createSessionCommand;

    @BeforeEach
    void setUp() {
        model = new ModelStub();
        session = new Session(101, LocalDate.of(2025, 3, 18), "Mathematics");
        createSessionCommand = new CreateSessionCommand(session);
    }

    @Test
    void execute_success() throws CommandException {
        CommandResult result = createSessionCommand.execute(model);
        assertEquals(String.format(CreateSessionCommand.MESSAGE_SUCCESS, Messages.format(session)),
                result.getFeedbackToUser());
    }

    @Test
    void execute_duplicateSession_throwsException() throws CommandException {
        createSessionCommand.execute(model); // Add session first
        assertThrows(CommandException.class, () -> createSessionCommand.execute(model));
    }

    @Test
    void equals_sameObject() {
        assertEquals(createSessionCommand, createSessionCommand);
    }

    @Test
    void equals_differentObjectSameData() {
        CreateSessionCommand commandCopy = new CreateSessionCommand(session);
        assertEquals(createSessionCommand, commandCopy);
    }

    @Test
    void equals_differentObjects() {
        Session differentSession = new Session(102, LocalDate.of(2025, 3, 19), "Science");
        CreateSessionCommand differentCommand = new CreateSessionCommand(differentSession);
        assertNotEquals(createSessionCommand, differentCommand);
    }

    @Test
    void toStringTest() {
        String expected = "CreateSessionCommand{toCreate=" + session + "}";
        assertTrue(createSessionCommand.toString().contains(expected));
    }

    /**
     * A simple stub class for Model that uses an in-memory UniqueSessionList.
     */
    private static class ModelStub implements Model {
        private final UniqueSessionList sessions = new UniqueSessionList();

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return null;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return null;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
        }

        @Override
        public Path getAddressBookFilePath() {
            return null;
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public boolean hasPerson(Person person) {
            return false;
        }

        @Override
        public void deletePerson(Person target) {
        }

        @Override
        public void restorePerson(Person target) {
        }

        @Override
        public void addPerson(Person person) {
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return null;
        }

        @Override
        public ObservableList<Person> getArchivedPersonList() {
            return null;
        }

        @Override
        public void updateFilteredPersonList(Filter<Person> predicate) {
        }

        @Override
        public boolean hasSession(Session session) {
            return sessions.contains(session);
        }

        @Override
        public void addSession(Session session) {
            sessions.add(session);
        }

        @Override
        public boolean hasAttendanceRecord(AttendanceRecord record) {
            return false;
        }

        @Override
        public void addAttendanceRecord(AttendanceRecord record) {
        }
    }
}
