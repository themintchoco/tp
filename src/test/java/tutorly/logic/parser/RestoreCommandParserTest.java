package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.RestoreStudentCommand;
import tutorly.model.person.Identity;
import tutorly.model.person.Name;

public class RestoreCommandParserTest {
    private final RestoreCommandParser parser = new RestoreCommandParser();

    @Test
    public void parse_id_returnsRestoreCommand() {
        Identity identity = new Identity(Integer.parseInt(VALID_ID_AMY));
        assertParseSuccess(parser, VALID_ID_AMY, new RestoreStudentCommand(identity));
    }

    @Test
    public void parse_name_returnsRestoreCommand() {
        Identity identity = new Identity(new Name(VALID_NAME_AMY));
        assertParseSuccess(parser, VALID_NAME_AMY, new RestoreStudentCommand(identity));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "10_Amy",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreStudentCommand.MESSAGE_USAGE));
    }
}
