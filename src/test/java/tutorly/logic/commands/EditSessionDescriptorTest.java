package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.logic.commands.CommandTestUtil.DESC_SESSION_1;
import static tutorly.logic.commands.CommandTestUtil.DESC_SESSION_2;
import static tutorly.logic.commands.CommandTestUtil.VALID_SUBJECT_2;
import static tutorly.logic.commands.CommandTestUtil.VALID_TIMESLOT_2;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.EditSessionCommand.EditSessionDescriptor;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.testutil.EditSessionDescriptorBuilder;

public class EditSessionDescriptorTest {

    @Test
    public void equals() throws ParseException {
        // same values -> returns true
        EditSessionDescriptor descriptorWithSameValues = new EditSessionDescriptor(DESC_SESSION_1);
        assertTrue(DESC_SESSION_1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_SESSION_1.equals(DESC_SESSION_1));

        // null -> returns false
        assertFalse(DESC_SESSION_1.equals(null));

        // different types -> returns false
        assertFalse(DESC_SESSION_1.equals(5));

        // different values -> returns false
        assertFalse(DESC_SESSION_1.equals(DESC_SESSION_2));

        // different timeslot -> returns false
        EditSessionDescriptor editedSession = new EditSessionDescriptorBuilder(DESC_SESSION_1)
                .withTimeslot(VALID_TIMESLOT_2).build();
        assertFalse(DESC_SESSION_1.equals(editedSession));

        // different subject -> returns false
        editedSession = new EditSessionDescriptorBuilder(DESC_SESSION_1)
                .withSubject(VALID_SUBJECT_2).build();
        assertFalse(DESC_SESSION_1.equals(editedSession));
    }

    @Test
    public void isAnyFieldEdited() throws ParseException {
        // no change
        EditSessionDescriptor editSessionDescriptor = new EditSessionDescriptor();
        assertFalse(editSessionDescriptor.isAnyFieldEdited());

        // with values
        editSessionDescriptor = new EditSessionDescriptorBuilder(DESC_SESSION_1)
                .withTimeslot(VALID_TIMESLOT_2).build();
        assertTrue(editSessionDescriptor.isAnyFieldEdited());
    }

    @Test
    public void toStringMethod() {
        EditSessionDescriptor editSessionDescriptor = new EditSessionDescriptor();
        String expectedString = "tutorly.logic.commands.EditSessionCommand.EditSessionDescriptor{"
                + "timeslot=null, "
                + "subject=null"
                + "}";

        assertEquals(expectedString, editSessionDescriptor.toString());
    }
}
