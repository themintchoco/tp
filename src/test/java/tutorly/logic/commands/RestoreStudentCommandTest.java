package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;
import static tutorly.testutil.TypicalIdentities.IDENTITY_FIRST_PERSON;
import static tutorly.testutil.TypicalIdentities.IDENTITY_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.person.Identity;
import tutorly.model.person.Person;
import tutorly.testutil.PersonBuilder;

public class RestoreStudentCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIdentity_success() throws CommandException {
        Person personToRestore = model.getPersonById(IDENTITY_FIRST_PERSON.getId(), false).get();
        model.deletePerson(personToRestore);
        RestoreStudentCommand restoreCommand = new RestoreStudentCommand(IDENTITY_FIRST_PERSON);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        restoreCommand.execute(model);

        // assert people list is the same
        assertTrue(model.getFilteredPersonList().contains(personToRestore));
    }

    @Test
    public void execute_invalidIdentity_throwsCommandException() {
        Identity identity = new Identity(model.getFilteredPersonList().size() + 1);
        RestoreStudentCommand restoreCommand = new RestoreStudentCommand(identity);

        assertCommandFailure(restoreCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void execute_duplicateName_throwsCommandException() {
        Person personToRestore = model.getPersonById(IDENTITY_FIRST_PERSON.getId(), false).get();
        model.deletePerson(personToRestore);

        Person editedPerson = model.getPersonById(IDENTITY_SECOND_PERSON.getId(), false).get();
        model.setPerson(editedPerson,
                new PersonBuilder(editedPerson).withName(personToRestore.getName().fullName).build());
        RestoreStudentCommand restoreCommand = new RestoreStudentCommand(new Identity(personToRestore.getName()));

        assertCommandFailure(restoreCommand, model, Messages.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void equals() {
        RestoreStudentCommand restoreFirstCommand = new RestoreStudentCommand(IDENTITY_FIRST_PERSON);
        RestoreStudentCommand restoreSecondCommand = new RestoreStudentCommand(IDENTITY_SECOND_PERSON);

        // same object -> returns true
        assertEquals(restoreFirstCommand, restoreFirstCommand);

        // same values -> returns true
        RestoreStudentCommand restoreFirstCommandCopy = new RestoreStudentCommand(IDENTITY_FIRST_PERSON);
        assertEquals(restoreFirstCommand, restoreFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, restoreFirstCommand);

        // null -> returns false
        assertNotEquals(null, restoreFirstCommand);

        // different person -> returns false
        assertNotEquals(restoreFirstCommand, restoreSecondCommand);
    }

    @Test
    public void toStringMethod() {
        RestoreStudentCommand restoreCommand = new RestoreStudentCommand(IDENTITY_FIRST_PERSON);
        String expected = RestoreStudentCommand.class.getCanonicalName() + "{identity=" + IDENTITY_FIRST_PERSON + "}";
        assertEquals(expected, restoreCommand.toString());
    }

}
