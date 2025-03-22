package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorly.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.RestoreCommand;

public class RestoreCommandParserTest {
    private RestoreCommandParser parser = new RestoreCommandParser();

    @Test
    public void parse_validArgs_returnsRestoreCommand() {
        assertParseSuccess(parser, "1", new RestoreCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE));
    }
}
