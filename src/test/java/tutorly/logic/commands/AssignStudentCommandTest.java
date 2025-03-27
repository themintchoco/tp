package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.Assert.assertThrows;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.BENSON;
import static tutorly.testutil.TypicalAddressBook.ENGLISH_SESSION;
import static tutorly.testutil.TypicalAddressBook.MATH_SESSION;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Identity;

public class AssignStudentCommandTest {
    private static final int INVALID_ID = 999;
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Identity bensonIdentity = new Identity(BENSON.getId());

    @Test
    public void constructor_nullArg_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignStudentCommand(null, 1));
    }

    @Test
    public void execute_attendanceRecordAcceptedByModel_addSuccessful() throws Exception {
        CommandResult commandResult = new AssignStudentCommand(bensonIdentity, ENGLISH_SESSION.getId()).execute(model);
        assertEquals(String.format(AssignStudentCommand.MESSAGE_SUCCESS, BENSON.getName().fullName,
                Messages.format(ENGLISH_SESSION)), commandResult.getFeedbackToUser());

        AttendanceRecord expectedRecord = new AttendanceRecord(
                BENSON.getId(), ENGLISH_SESSION.getId(), AssignStudentCommand.DEFAULT_PRESENCE);
        assertTrue(model.hasAttendanceRecord(expectedRecord));
    }

    @Test
    public void execute_invalidIdentity_throwsCommandException() {
        AssignStudentCommand assignStudentCommand =
                new AssignStudentCommand(new Identity(INVALID_ID), ENGLISH_SESSION.getId());

        assertThrows(
                CommandException.class, Messages.MESSAGE_PERSON_NOT_FOUND, () -> assignStudentCommand.execute(model));
    }

    @Test
    public void execute_invalidSessionId_throwsCommandException() {
        AssignStudentCommand assignStudentCommand = new AssignStudentCommand(bensonIdentity, INVALID_ID);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_SESSION_ID, () -> assignStudentCommand.execute(model));
    }

    @Test
    public void execute_duplicateAttendanceRecord_throwsCommandException() {
        AssignStudentCommand assignStudentCommand = new AssignStudentCommand(bensonIdentity, MATH_SESSION.getId());

        assertThrows(CommandException.class,
                AssignStudentCommand.MESSAGE_DUPLICATE_ASSIGNMENT, () -> assignStudentCommand.execute(model));
    }

    @Test
    public void equals() {
        AssignStudentCommand assignBensonMathCommand = new AssignStudentCommand(bensonIdentity, MATH_SESSION.getId());
        AssignStudentCommand assignAliceMathCommand =
                new AssignStudentCommand(new Identity(ALICE.getId()), MATH_SESSION.getId());

        // same object -> returns true
        assertEquals(assignBensonMathCommand, assignBensonMathCommand);

        // same values -> returns true
        AssignStudentCommand assignBensonMathCommandCopy =
                new AssignStudentCommand(bensonIdentity, MATH_SESSION.getId());
        assertEquals(assignBensonMathCommand, assignBensonMathCommandCopy);

        // different types -> returns false
        assertNotEquals(1, assignBensonMathCommand);

        // null -> returns false
        assertNotEquals(null, assignBensonMathCommand);

        // different person -> returns false
        assertNotEquals(assignBensonMathCommand, assignAliceMathCommand);

        // different session -> returns false
        assertNotEquals(assignBensonMathCommand, new AssignStudentCommand(bensonIdentity, ENGLISH_SESSION.getId()));
    }

    @Test
    public void toStringMethod() {
        AssignStudentCommand assignStudentCommand = new AssignStudentCommand(bensonIdentity, MATH_SESSION.getId());
        String expected = AssignStudentCommand.class.getCanonicalName()
                + "{identity=" + bensonIdentity + ", sessionId=" + MATH_SESSION.getId() + "}";
        assertEquals(expected, assignStudentCommand.toString());
    }

}
