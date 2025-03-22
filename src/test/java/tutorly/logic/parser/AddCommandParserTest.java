package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static tutorly.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static tutorly.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static tutorly.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static tutorly.logic.commands.CommandTestUtil.INVALID_MEMO_DESC;
import static tutorly.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static tutorly.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static tutorly.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static tutorly.logic.commands.CommandTestUtil.MEMO_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.MEMO_DESC_BOB;
import static tutorly.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static tutorly.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static tutorly.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static tutorly.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tutorly.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static tutorly.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static tutorly.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tutorly.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tutorly.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorly.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorly.logic.parser.CliSyntax.PREFIX_MEMO;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorly.testutil.TypicalAddressBook.AMY;
import static tutorly.testutil.TypicalAddressBook.BOB;

import org.junit.jupiter.api.Test;

import tutorly.logic.Messages;
import tutorly.logic.commands.AddCommand;
import tutorly.model.person.Address;
import tutorly.model.person.Email;
import tutorly.model.person.Memo;
import tutorly.model.person.Name;
import tutorly.model.person.Person;
import tutorly.model.person.Phone;
import tutorly.model.tag.Tag;
import tutorly.testutil.PersonBuilder;

public class AddCommandParserTest {
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + MEMO_DESC_BOB, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + MEMO_DESC_BOB,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + MEMO_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple memo
        assertParseFailure(parser, MEMO_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMO));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_MEMO));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid memo
        assertParseFailure(parser, INVALID_MEMO_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMO));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid memo
        assertParseFailure(parser, validExpectedPersonString + INVALID_MEMO_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMO));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + MEMO_DESC_AMY,
                new AddCommand(new PersonBuilder(AMY).withTags().build()));

        // missing phone prefix
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + MEMO_DESC_AMY,
                new AddCommand(new PersonBuilder(AMY).withEmptyPhone().build()));

        // missing email prefix
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + MEMO_DESC_AMY,
                new AddCommand(new PersonBuilder(AMY).withEmptyEmail().build()));

        // missing address prefix
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND + MEMO_DESC_AMY,
                new AddCommand(new PersonBuilder(AMY).withEmptyAddress().build()));

        // missing memo prefix
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(new PersonBuilder(AMY).withEmptyMemo().build()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + MEMO_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + MEMO_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + MEMO_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + MEMO_DESC_BOB, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND + MEMO_DESC_BOB, Tag.MESSAGE_CONSTRAINTS);

        // invalid memo
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + INVALID_MEMO_DESC, Memo.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
