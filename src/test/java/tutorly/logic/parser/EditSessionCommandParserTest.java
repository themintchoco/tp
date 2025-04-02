package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.SUBJECT_DESC;
import static tutorly.logic.commands.CommandTestUtil.TIMESLOT_DESC;
import static tutorly.logic.commands.CommandTestUtil.VALID_SUBJECT;
import static tutorly.logic.commands.CommandTestUtil.VALID_TIMESLOT;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.EditSessionCommand;
import tutorly.logic.commands.EditSessionCommand.EditSessionDescriptor;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.testutil.EditSessionDescriptorBuilder;


public class EditSessionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditSessionCommand.MESSAGE_USAGE);

    private final EditSessionCommandParser parser = new EditSessionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid index
        assertParseFailure(parser, "-100" + TIMESLOT_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_success() throws ParseException {
        // no leading and trailing whitespaces
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder()
                .withTimeslot(VALID_TIMESLOT).withSubject(VALID_SUBJECT).build();
        EditSessionCommand expectedCommand = new EditSessionCommand(1, descriptor);
        String userInput = "1 " + TIMESLOT_DESC + SUBJECT_DESC;
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
