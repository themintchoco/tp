package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.DeleteStudentCommand;
import tutorly.model.person.Identity;
import tutorly.model.person.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteStudentCommandParserTest {

    private final DeleteStudentCommandParser parser = new DeleteStudentCommandParser();

    @Test
    public void parse_id_returnsDeleteCommand() {
        Identity identity = new Identity(Integer.parseInt(VALID_ID_AMY));
        assertParseSuccess(parser, VALID_ID_AMY, new DeleteStudentCommand(identity));
    }

    @Test
    public void parse_name_returnsDeleteCommand() {
        Identity identity = new Identity(new Name(VALID_NAME_AMY));
        assertParseSuccess(parser, VALID_NAME_AMY, new DeleteStudentCommand(identity));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "10_Amy",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE));
    }
}
