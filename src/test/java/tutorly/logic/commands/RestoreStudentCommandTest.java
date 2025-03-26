package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;
import static tutorly.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.person.Person;

public class RestoreStudentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws CommandException {
        Person personToRestore = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.deletePerson(personToRestore);
        RestoreStudentCommand restoreCommand = new RestoreStudentCommand(INDEX_FIRST_PERSON);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        restoreCommand.execute(model);

        // assert people list is the same
        assertTrue(model.getFilteredPersonList().contains(personToRestore));
    }

    @Test
    public void equals() {
        RestoreStudentCommand restoreFirstCommand = new RestoreStudentCommand(INDEX_FIRST_PERSON);
        RestoreStudentCommand restoreSecondCommand = new RestoreStudentCommand(INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(restoreFirstCommand.equals(restoreFirstCommand));

        // same values -> returns true
        RestoreStudentCommand restoreFirstCommandCopy = new RestoreStudentCommand(INDEX_FIRST_PERSON);
        assertTrue(restoreFirstCommand.equals(restoreFirstCommandCopy));

        // different types -> returns false
        assertFalse(restoreFirstCommand.equals(1));

        // null -> returns false
        assertFalse(restoreFirstCommand.equals(null));

        // same person -> returns true
        assertTrue(restoreFirstCommand.equals(restoreSecondCommand));
    }

    @Test
    public void toStringTest() {
        RestoreStudentCommand restoreCommand = new RestoreStudentCommand(INDEX_FIRST_PERSON);
        assertEquals(restoreCommand.toString(), "tutorly.logic.commands.RestoreStudentCommand{targetId=tutorly."
                + "commons.core.index.Index{zeroBasedIndex=0}}");
    }
}
