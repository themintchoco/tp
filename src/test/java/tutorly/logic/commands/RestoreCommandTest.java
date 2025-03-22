package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;


import org.junit.jupiter.api.Test;

import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.person.Person;

public class RestoreCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws CommandException {
        Person personToRestore = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.deletePerson(personToRestore);
        RestoreCommand restoreCommand = new RestoreCommand(INDEX_FIRST_PERSON);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        restoreCommand.execute(model);

        // assert people list is the same
        System.out.println(model.getFilteredPersonList());
        assertTrue(model.getFilteredPersonList().contains(personToRestore));
    }

    @Test
    public void equals() {
        RestoreCommand restoreFirstCommand = new RestoreCommand(INDEX_FIRST_PERSON);
        RestoreCommand restoreSecondCommand = new RestoreCommand(INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(restoreFirstCommand.equals(restoreFirstCommand));

        // same values -> returns true
        RestoreCommand restoreFirstCommandCopy = new RestoreCommand(INDEX_FIRST_PERSON);
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
        RestoreCommand restoreCommand = new RestoreCommand(INDEX_FIRST_PERSON);
        assertEquals(restoreCommand.toString(), "tutorly.logic.commands.RestoreCommand{targetIndex=tutorly."
                + "commons.core.index.Index{zeroBasedIndex=0}}");
    }
}
