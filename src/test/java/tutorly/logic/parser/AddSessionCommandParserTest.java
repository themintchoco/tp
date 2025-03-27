package tutorly.logic.parser;

import static tutorly.logic.parser.CliSyntax.PREFIX_DATE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorly.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import tutorly.logic.commands.AddSessionCommand;
import tutorly.model.session.Session;

public class AddSessionCommandParserTest {

    private final AddSessionCommandParser parser = new AddSessionCommandParser();

    @Test
    public void parse_validInput_success() {
        String userInput = " " + PREFIX_DATE + "2025-03-25 " + PREFIX_SUBJECT + "Mathematics";
        Session expectedSession = new Session(0, LocalDate.of(2025, 3, 25), "Mathematics");
        AddSessionCommand expectedCommand = new AddSessionCommand(expectedSession);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingDate_throwsParseException() {
        String userInput = " " + PREFIX_SUBJECT + "Mathematics";
        String expectedMessage = "Invalid command format! \n"
                + "session add: Creates a tutoring session. Parameters: d/DATE sub/SUBJECT\n"
                + "Example: session add d/2025-03-18 sub/Mathematics";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingSubject_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2025-03-18";
        String expectedMessage = "Invalid command format! \n"
                + "session add: Creates a tutoring session. Parameters: d/DATE sub/SUBJECT\n"
                + "Example: session add d/2025-03-18 sub/Mathematics";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptySubject_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2025-03-18 " + PREFIX_SUBJECT;
        String expectedMessage = "Subject cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String userInput = " d/2025/03/27 s/Mathematics"; // Wrong date format
        String expectedMessage = "Invalid command format! \n"
                + "session add: Creates a tutoring session. Parameters: d/DATE sub/SUBJECT\n"
                + "Example: session add d/2025-03-18 sub/Mathematics";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidDateValue_throwsParseException() {
        String userInput = " d/2025-02-30 s/Mathematics"; // Feb 30 does not exist
        String expectedMessage = "Invalid command format! \n"
                + "session add: Creates a tutoring session. Parameters: d/DATE sub/SUBJECT\n"
                + "Example: session add d/2025-03-18 sub/Mathematics";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        String userInput = " 2025-03-27 Mathematics"; // Missing 'd/' and 's/' prefixes
        String expectedMessage = "Invalid command format! \n"
                + "session add: Creates a tutoring session. Parameters: d/DATE sub/SUBJECT\n"
                + "Example: session add d/2025-03-18 sub/Mathematics";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
