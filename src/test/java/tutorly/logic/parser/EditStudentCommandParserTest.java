package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static tutorly.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static tutorly.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static tutorly.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static tutorly.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static tutorly.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static tutorly.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static tutorly.logic.commands.CommandTestUtil.MEMO_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tutorly.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static tutorly.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static tutorly.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static tutorly.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_MEMO_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tutorly.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tutorly.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorly.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorly.testutil.TypicalIdentities.IDENTITY_FIRST_PERSON;
import static tutorly.testutil.TypicalIdentities.IDENTITY_SECOND_PERSON;
import static tutorly.testutil.TypicalIdentities.IDENTITY_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import tutorly.logic.Messages;
import tutorly.logic.commands.EditStudentCommand;
import tutorly.logic.commands.EditStudentCommand.EditPersonDescriptor;
import tutorly.model.person.Address;
import tutorly.model.person.Email;
import tutorly.model.person.Identity;
import tutorly.model.person.Name;
import tutorly.model.person.Phone;
import tutorly.model.tag.Tag;
import tutorly.testutil.EditPersonDescriptorBuilder;

public class EditStudentCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE);

    private final EditStudentCommandParser parser = new EditStudentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "1", EditStudentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Identity targetIdentity = IDENTITY_SECOND_PERSON;
        String userInput = targetIdentity.getId() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND + MEMO_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withMemo(VALID_MEMO_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIdentity, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Identity targetIdentity = IDENTITY_FIRST_PERSON;
        String userInput = targetIdentity.getId() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIdentity, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Identity targetdentity = IDENTITY_THIRD_PERSON;
        String userInput = targetdentity.getId() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetdentity, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetdentity.getId() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditStudentCommand(targetdentity, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetdentity.getId() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditStudentCommand(targetdentity, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetdentity.getId() + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditStudentCommand(targetdentity, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetdentity.getId() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditStudentCommand(targetdentity, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // memo
        userInput = targetdentity.getId() + MEMO_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withMemo(VALID_MEMO_AMY).build();
        expectedCommand = new EditStudentCommand(targetdentity, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Identity taregtIdentity = IDENTITY_FIRST_PERSON;
        String userInput = taregtIdentity.getId() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = taregtIdentity.getId() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = taregtIdentity.getId() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = taregtIdentity.getId() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Identity taregtIdentity = IDENTITY_THIRD_PERSON;
        String userInput = taregtIdentity.getId() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditStudentCommand expectedCommand = new EditStudentCommand(taregtIdentity, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
