package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.ID_DESC_SESSION;
import static tutorly.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_ID_SESSION;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorly.logic.parser.ParserUtil.MESSAGE_INVALID_IDENTITY;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.AssignSessionCommand;
import tutorly.model.person.Identity;

public class AssignCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignSessionCommand.MESSAGE_USAGE);

    private final AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_emptyPreamble_failure() {
        assertParseFailure(parser, ID_DESC_SESSION, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingSessionId_failure() {
        assertParseFailure(parser, VALID_ID_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_ID_AMY + " " + PREFIX_SESSION, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIdentity_failure() {
        assertParseFailure(parser, "-1 " + ID_DESC_SESSION, MESSAGE_INVALID_IDENTITY);
    }

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(
                parser,
                VALID_ID_AMY + ID_DESC_SESSION,
                new AssignSessionCommand(
                        new Identity(Integer.parseInt(VALID_ID_AMY)), Integer.parseInt(VALID_ID_SESSION)));
    }
}
