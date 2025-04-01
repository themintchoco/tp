package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorly.logic.commands.CommandTestUtil.INVALID_TIMESLOT_DESC;
import static tutorly.logic.commands.CommandTestUtil.SUBJECT_DESC;
import static tutorly.logic.commands.CommandTestUtil.TIMESLOT_DESC;
import static tutorly.logic.commands.CommandTestUtil.VALID_SUBJECT;
import static tutorly.logic.commands.CommandTestUtil.VALID_TIMESLOT;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CliSyntax.PREFIX_TIMESLOT;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.AddSessionCommand;
import tutorly.model.session.Session;
import tutorly.model.session.Subject;
import tutorly.model.session.Timeslot;

public class AddSessionCommandParserTest {

    private final AddSessionCommandParser parser = new AddSessionCommandParser();

    @Test
    public void parse_validInput_success() {
        String userInput = " " + PREFIX_TIMESLOT + "25 Mar 2025 10:00-12:00 " + PREFIX_SUBJECT + "Mathematics";
        Timeslot timeslot = new Timeslot(LocalDateTime.of(2025, 3, 25, 10, 0),
                LocalDateTime.of(2025, 3, 25, 12, 0));
        Session expectedSession = new Session(timeslot, new Subject("Mathematics"));
        AddSessionCommand expectedCommand = new AddSessionCommand(expectedSession);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingDate_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE);
        assertParseFailure(parser, SUBJECT_DESC, expectedMessage);
    }

    @Test
    public void parse_missingSubject_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TIMESLOT_DESC, expectedMessage);
    }

    @Test
    public void parse_emptySubject_throwsParseException() {
        String userInput = TIMESLOT_DESC + " " + PREFIX_SUBJECT;
        assertParseFailure(parser, userInput, Subject.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidTimeslotFormat_throwsParseException() {
        String userInput = INVALID_TIMESLOT_DESC + SUBJECT_DESC;
        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_TIMESLOT_FORMAT);
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        String userInput = VALID_TIMESLOT + " " + VALID_SUBJECT;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
