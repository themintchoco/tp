package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tutorly.testutil.Assert.assertThrows;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.BENSON;
import static tutorly.testutil.TypicalAddressBook.BENSON_ATTEND_MATH;
import static tutorly.testutil.TypicalAddressBook.ENGLISH_SESSION;
import static tutorly.testutil.TypicalAddressBook.MATH_SESSION;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.person.Identity;

public class UnassignSessionCommandTest {
    private static final int INVALID_ID = 999;
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Identity bensonIdentity = new Identity(BENSON.getId());

    @Test
    public void constructor_nullArg_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignSessionCommand(null, 1));
    }

    @Test
    public void execute_attendanceRecordRemovedByModel_removeSuccessful() throws Exception {
        CommandResult commandResult = new UnassignSessionCommand(bensonIdentity, MATH_SESSION.getId()).execute(model);
        assertEquals(String.format(UnassignSessionCommand.MESSAGE_SUCCESS, BENSON.getName().fullName,
                Messages.format(MATH_SESSION)), commandResult.getFeedbackToUser());

        assertFalse(model.hasAttendanceRecord(BENSON_ATTEND_MATH));
    }

    @Test
    public void execute_invalidIdentity_throwsCommandException() {
        UnassignSessionCommand unassignSessionCommand =
                new UnassignSessionCommand(new Identity(INVALID_ID), ENGLISH_SESSION.getId());

        assertThrows(
                CommandException.class, Messages.MESSAGE_PERSON_NOT_FOUND, () -> unassignSessionCommand.execute(model));
    }

    @Test
    public void execute_invalidSessionId_throwsCommandException() {
        UnassignSessionCommand unassignSessionCommand = new UnassignSessionCommand(bensonIdentity, INVALID_ID);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_SESSION_ID, () -> unassignSessionCommand.execute(model));
    }

    @Test
    public void execute_missingAttendanceRecord_throwsCommandException() {
        UnassignSessionCommand unassignSessionCommand =
                new UnassignSessionCommand(bensonIdentity, ENGLISH_SESSION.getId());

        assertThrows(CommandException.class,
                UnassignSessionCommand.MESSAGE_MISSING_ASSIGNMENT, () -> unassignSessionCommand.execute(model));
    }

    @Test
    public void equals() {
        UnassignSessionCommand unassignBensonMathCommand =
                new UnassignSessionCommand(bensonIdentity, MATH_SESSION.getId());
        UnassignSessionCommand unassignAliceMathCommand =
                new UnassignSessionCommand(new Identity(ALICE.getId()), MATH_SESSION.getId());

        // same object -> returns true
        assertEquals(unassignBensonMathCommand, unassignBensonMathCommand);

        // same values -> returns true
        UnassignSessionCommand unassignBensonMathCommandCopy =
                new UnassignSessionCommand(bensonIdentity, MATH_SESSION.getId());
        assertEquals(unassignBensonMathCommand, unassignBensonMathCommandCopy);

        // different types -> returns false
        assertNotEquals(1, unassignBensonMathCommand);

        // null -> returns false
        assertNotEquals(null, unassignBensonMathCommand);

        // different person -> returns false
        assertNotEquals(unassignBensonMathCommand, unassignAliceMathCommand);

        // different session -> returns false
        assertNotEquals(unassignBensonMathCommand, new UnassignSessionCommand(bensonIdentity, ENGLISH_SESSION.getId()));
    }

    @Test
    public void toStringMethod() {
        UnassignSessionCommand unassignSessionCommand =
                new UnassignSessionCommand(bensonIdentity, MATH_SESSION.getId());
        String expected = UnassignSessionCommand.class.getCanonicalName()
                + "{identity=" + bensonIdentity + ", sessionId=" + MATH_SESSION.getId() + "}";
        assertEquals(expected, unassignSessionCommand.toString());
    }
}
