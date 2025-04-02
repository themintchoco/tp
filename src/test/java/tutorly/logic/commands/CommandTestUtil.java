package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorly.logic.parser.CliSyntax.PREFIX_DATE;
import static tutorly.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorly.logic.parser.CliSyntax.PREFIX_FEEDBACK;
import static tutorly.logic.parser.CliSyntax.PREFIX_MEMO;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorly.logic.parser.CliSyntax.PREFIX_TIMESLOT;
import static tutorly.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tutorly.commons.core.index.Index;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.AddressBook;
import tutorly.model.Model;
import tutorly.model.filter.NameContainsKeywordsFilter;
import tutorly.model.person.Person;
import tutorly.testutil.EditPersonDescriptorBuilder;
import tutorly.testutil.EditSessionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ID_AMY = "1";
    public static final String VALID_ID_BOB = "2";
    public static final String VALID_ID_SESSION = "1";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_MEMO_AMY = "Amy requires more help in Math";
    public static final String VALID_MEMO_BOB = "Bob requires more help in English";
    public static final String VALID_DATE = "18 Mar 2025";
    public static final String VALID_TIMESLOT = "18 Mar 2025 10:00-12:00";
    public static final String VALID_SUBJECT = "Math";
    public static final String VALID_TIMESLOT_2 = "19 Mar 2025 14:00-16:00";
    public static final String VALID_SUBJECT_2 = "English";
    public static final String VALID_FEEDBACK = "Good job!";

    public static final String ID_DESC_SESSION = " " + PREFIX_SESSION + VALID_ID_SESSION;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String MEMO_DESC_AMY = " " + PREFIX_MEMO + VALID_MEMO_AMY;
    public static final String MEMO_DESC_BOB = " " + PREFIX_MEMO + VALID_MEMO_BOB;
    public static final String DATE_DESC = " " + PREFIX_DATE + VALID_DATE;
    public static final String TIMESLOT_DESC = " " + PREFIX_TIMESLOT + VALID_TIMESLOT;
    public static final String SUBJECT_DESC = " " + PREFIX_SUBJECT + VALID_SUBJECT;
    public static final String FEEDBACK_DESC = " " + PREFIX_FEEDBACK + VALID_FEEDBACK;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_MEMO_DESC = " " + PREFIX_MEMO; // empty string not allowed for memos
    public static final String INVALID_TIMESLOT_DESC = " " + PREFIX_TIMESLOT + "2025-03-18"; // invalid date format

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditStudentCommand.EditPersonDescriptor DESC_AMY;
    public static final EditStudentCommand.EditPersonDescriptor DESC_BOB;
    public static final EditSessionCommand.EditSessionDescriptor DESC_SESSION_1;
    public static final EditSessionCommand.EditSessionDescriptor DESC_SESSION_2;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        try {
            DESC_SESSION_1 = new EditSessionDescriptorBuilder()
                    .withTimeslot(VALID_TIMESLOT).withSubject(VALID_SUBJECT).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            DESC_SESSION_2 = new EditSessionDescriptorBuilder()
                    .withTimeslot(VALID_TIMESLOT_2).withSubject(VALID_SUBJECT_2).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsFilter(Collections.singletonList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
