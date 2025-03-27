package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tutorly.logic.commands.CommandTestUtil.DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.DESC_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tutorly.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorly.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorly.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tutorly.testutil.TypicalAddressBook.AMY;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;
import static tutorly.testutil.TypicalIdentities.IDENTITY_FIRST_PERSON;
import static tutorly.testutil.TypicalIdentities.IDENTITY_SECOND_PERSON;
import static tutorly.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import tutorly.logic.Messages;
import tutorly.logic.commands.EditStudentCommand.EditPersonDescriptor;
import tutorly.model.AddressBook;
import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.person.Identity;
import tutorly.model.person.Person;
import tutorly.testutil.EditPersonDescriptorBuilder;
import tutorly.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditStudentCommand.
 */
public class EditStudentCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder(AMY).withId(1).build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditStudentCommand editCommand = new EditStudentCommand(IDENTITY_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getPersonById(1, false).get(), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Identity identityLastPerson = new Identity(model.getAddressBook().getPersonList().size());
        Person lastPerson = model.getPersonById(identityLastPerson.getId(), false).get();

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditStudentCommand editCommand = new EditStudentCommand(identityLastPerson, descriptor);

        String expectedMessage = String.format(
                EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditStudentCommand editCommand = new EditStudentCommand(IDENTITY_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getPersonById(IDENTITY_FIRST_PERSON.getId(), false).get();

        String expectedMessage = String.format(
                EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_outsideFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getPersonById(IDENTITY_SECOND_PERSON.getId(), false).get();
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditStudentCommand editCommand = new EditStudentCommand(IDENTITY_SECOND_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(
                EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getPersonById(IDENTITY_SECOND_PERSON.getId(), false).get(), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getPersonById(IDENTITY_FIRST_PERSON.getId(), false).get();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditStudentCommand editCommand = new EditStudentCommand(IDENTITY_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIdUnfilteredList_failure() {
        Identity identity = new Identity(model.getAddressBook().getPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditStudentCommand editCommand = new EditStudentCommand(identity, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void equals() {
        final EditStudentCommand standardCommand = new EditStudentCommand(IDENTITY_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditStudentCommand commandWithSameValues = new EditStudentCommand(IDENTITY_FIRST_PERSON, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditStudentCommand(IDENTITY_SECOND_PERSON, DESC_AMY));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditStudentCommand(IDENTITY_SECOND_PERSON, DESC_BOB));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditStudentCommand editCommand = new EditStudentCommand(IDENTITY_FIRST_PERSON, editPersonDescriptor);
        String expected = EditStudentCommand.class.getCanonicalName() + "{identity=" + IDENTITY_FIRST_PERSON
                + ", editPersonDescriptor=" + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
