package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorly.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tutorly.logic.Messages;
import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.person.Identity;
import tutorly.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIdentity_success() {
        Identity identity = new Identity();
        identity.setId(1);
        Person personToDelete = identity.getPerson(model).get();
        DeleteCommand deleteCommand = new DeleteCommand(identity);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIdentity_throwsCommandException() {
        Identity identity = new Identity();
        identity.setId(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(identity);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }

    //    @Test
    //    public void execute_validIndexFilteredList_success() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
    //
    //        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
    //                Messages.format(personToDelete));
    //
    //        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    //        expectedModel.deletePerson(personToDelete);
    //        showNoPerson(expectedModel);
    //
    //        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_invalidIndexFilteredList_throwsCommandException() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        Index outOfBoundIndex = INDEX_SECOND_PERSON;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
    //
    //        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        Identity identityFirst = new Identity();
        Identity identitySecond = new Identity();
        identityFirst.setId(1);
        identitySecond.setId(2);

        DeleteCommand deleteFirstCommand = new DeleteCommand(identityFirst);
        DeleteCommand deleteSecondCommand = new DeleteCommand(identitySecond);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(identityFirst);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different person -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Identity identity = new Identity();
        identity.setId(1);
        DeleteCommand deleteCommand = new DeleteCommand(identity);
        String expected = DeleteCommand.class.getCanonicalName() + "{identity=" + identity + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(ab -> p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
