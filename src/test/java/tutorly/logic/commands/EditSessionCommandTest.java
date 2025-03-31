package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorly.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorly.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorly.testutil.TypicalAddressBook.ENGLISH_SESSION;
import static tutorly.testutil.TypicalAddressBook.MATH_SESSION;
import static tutorly.testutil.TypicalAddressBook.MATH_SESSION_OVERLAP;
import static tutorly.testutil.TypicalAddressBook.MATH_TIMESLOT_OVERLAP;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tutorly.logic.Messages;
import tutorly.logic.commands.EditSessionCommand.EditSessionDescriptor;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.session.Session;
import tutorly.testutil.EditSessionDescriptorBuilder;
import tutorly.testutil.SessionBuilder;


public class EditSessionCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_sucess() throws ParseException, CommandException {
        Session editedSession = new SessionBuilder(MATH_SESSION).withSubject("Science").build();
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder(editedSession).build();
        EditSessionCommand editCommand = new EditSessionCommand(MATH_SESSION.getId(), descriptor);

        String expectedMessage = String.format(EditSessionCommand.MESSAGE_EDIT_SESSION_SUCCESS,
                Messages.format(editedSession));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setSession(MATH_SESSION, editedSession);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSessionId_throwsCommandException() {
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder(MATH_SESSION).build();
        EditSessionCommand editCommand = new EditSessionCommand(999, descriptor);

        String expectedMessage = String.format(Messages.MESSAGE_SESSION_NOT_FOUND);

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_sessionOverlap_throwsCommandException() {
        Session editedSession = new SessionBuilder(ENGLISH_SESSION).withTimeslot(MATH_TIMESLOT_OVERLAP).build();
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder(editedSession).build();
        EditSessionCommand editCommand = new EditSessionCommand(ENGLISH_SESSION.getId(), descriptor);

        String expectedMessage = String.format(Messages.MESSAGE_SESSION_OVERLAP);

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_sameSessionOverlap_sucess() {
        Session editedSession = new SessionBuilder(MATH_SESSION).withTimeslot(MATH_TIMESLOT_OVERLAP).build();
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder(editedSession).build();
        EditSessionCommand editCommand = new EditSessionCommand(MATH_SESSION.getId(), descriptor);

        String expectedMessage = String.format(EditSessionCommand.MESSAGE_EDIT_SESSION_SUCCESS,
                Messages.format(editedSession));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setSession(MATH_SESSION, editedSession);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }
}
