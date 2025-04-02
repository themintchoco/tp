package tutorly.logic.parser;

import static tutorly.logic.commands.CommandTestUtil.FEEDBACK_DESC;
import static tutorly.logic.commands.CommandTestUtil.ID_DESC_SESSION;
import static tutorly.logic.commands.CommandTestUtil.VALID_FEEDBACK;
import static tutorly.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static tutorly.logic.commands.CommandTestUtil.VALID_ID_SESSION;
import static tutorly.logic.parser.CliSyntax.PREFIX_FEEDBACK;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorly.logic.parser.ParserUtil.MESSAGE_INVALID_IDENTITY;

import org.junit.jupiter.api.Test;

import tutorly.logic.Messages;
import tutorly.logic.commands.AttendanceFeedbackCommand;
import tutorly.model.attendancerecord.Feedback;
import tutorly.model.person.Identity;

public class AttendanceFeedbackCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AttendanceFeedbackCommand.MESSAGE_USAGE);

    private final AttendanceFeedbackCommandParser parser = new AttendanceFeedbackCommandParser();

    @Test
    public void parse_emptyPreamble_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingSessionId_failure() {
        assertParseFailure(parser, VALID_ID_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_ID_AMY + " " + PREFIX_SESSION, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIdentity_failure() {
        assertParseFailure(parser, "-1 " + ID_DESC_SESSION + FEEDBACK_DESC, MESSAGE_INVALID_IDENTITY);
    }

    @Test
    public void parse_missingFeedback_failure() {
        assertParseFailure(parser, VALID_ID_AMY + ID_DESC_SESSION, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validEmptyFeedback_success() {
        assertParseSuccess(parser,
                VALID_ID_AMY + ID_DESC_SESSION + " " + PREFIX_FEEDBACK,
                new AttendanceFeedbackCommand(
                        new Identity(Integer.parseInt(VALID_ID_AMY)), Integer.parseInt(VALID_ID_SESSION),
                        Feedback.empty()));
    }

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser,
                VALID_ID_AMY + ID_DESC_SESSION + FEEDBACK_DESC,
                new AttendanceFeedbackCommand(
                        new Identity(Integer.parseInt(VALID_ID_AMY)), Integer.parseInt(VALID_ID_SESSION),
                        new Feedback(VALID_FEEDBACK)));
    }
}
